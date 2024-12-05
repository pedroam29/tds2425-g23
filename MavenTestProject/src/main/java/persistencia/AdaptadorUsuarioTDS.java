package persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.appchat.modelo.Contacto;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;


public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO{
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;
	private SimpleDateFormat dateFormat;

	public static AdaptadorUsuarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorUsuarioTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario = null;
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
			
		} catch (NullPointerException e) {
		}
		if (eUsuario != null)
			return;

		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("usuario", usuario.getNombre()), new Propiedad("email", usuario.getNombre()),
						new Propiedad("fechaNacimiento", dateFormat.format(usuario.getFechaNacimiento())),
						new Propiedad("telefono", usuario.getTelefono()),
						new Propiedad("contrasena", usuario.getContrasena()),
						new Propiedad("imagenPerfil", usuario.getImagenPerfilUrl()),
						new Propiedad("saludo", usuario.getSaludo()),
						new Propiedad("mensajesRecibidos", obtenerCodigosRecibidos(usuario.getRecibidos())),
						new Propiedad("mensajesEnviados", obtenerCodigosEnviados(usuario.getEnviados())),
						new Propiedad("contactos", obtenerCodigosContactos(usuario.getContactos())))));

		// registrar entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId());
	}

	public void borrarUsuario(Usuario usuario) {
		Entidad eUsuario;

		eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		servPersistencia.borrarEntidad(eUsuario);
	}

	public void modificarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(usuario.getCodigo()));
			} else if (prop.getNombre().equals("usuario")) {
				prop.setValor(usuario.getNombre());
			} else if (prop.getNombre().equals("email")) {
				prop.setValor(usuario.getEmail());
			} else if (prop.getNombre().equals("contrasena")) {
				prop.setValor(usuario.getContrasena());
			} else if (prop.getNombre().equals("fechaNacimiento")) {
				prop.setValor(dateFormat.format(usuario.getFechaNacimiento()));
			} else if (prop.getNombre().equals("telefono")) {
				prop.setValor(String.valueOf(usuario.getTelefono()));
			} else if (prop.getNombre().equals("saludo")) {
				prop.setValor(usuario.getSaludo());
			} else if (prop.getNombre().equals("imagenPerfil")) {
				prop.setValor(usuario.getImagenPerfilUrl());
			} else if (prop.getNombre().equals("mensajesRecibidos")) {
				prop.setValor(obtenerCodigosRecibidos(usuario.getRecibidos()));
			} else if(prop.getNombre().equals("mensajesEnviados")) {
				prop.setValor(obtenerCodigosEnviados(usuario.getEnviados()));
			} else if(prop.getNombre().equals("contactos")) {
				prop.setValor(obtenerCodigosContactos(usuario.getContactos()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Usuario recuperarUsuario(int codigo) {
		Entidad eUsuario;
		String usuario;
		String email;
		String contrasena;
		Date fechaNacimiento = null;
		String imagenPerfilUrl;
		String saludo;
		String telefono;
	
		List<Mensaje> recibidos;
		List<Mensaje> enviados;
		List<Contacto> contactos;

		eUsuario = servPersistencia.recuperarEntidad(codigo);
		usuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, "usuario");
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		contrasena = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contrasena");
		imagenPerfilUrl = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagenPerfilUrl");
		saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, "saludo");
		telefono = servPersistencia.recuperarPropiedadEntidad(eUsuario, "telefono");
		
		try {
			fechaNacimiento = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	/*
		playlists = obtenerPlayListsDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "playlists"));
		recientes = obtenerCancionesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "recientes"));

*/
		contactos = obtenerContactosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"));
		
		Usuario usr = new Usuario(usuario, telefono, contrasena, fechaNacimiento, imagenPerfilUrl, saludo, email);
		usr.setContactos(contactos);
		/*
		usr.setPlayLists(playlists);
		usr.setRecientes(recientes);
		usr.setPremium(premium);
		usr.setDescuento(descuento);
		*/
		usr.setCodigo(codigo);
		return usr;
	}

	
	public List<Usuario> recuperarTodosUsuarios() {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("usuario");

		for (Entidad eUsuario : entidades) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}
	
	
	private String obtenerCodigosRecibidos(List<Mensaje> recibidos) {
		String lineas = "";
		for (Mensaje mensaje : recibidos) {
			lineas += mensaje.getCodigo() + " ";
		}
		return lineas.trim();
	}
	
	private String obtenerCodigosEnviados(List<Mensaje> enviados) {
		String lineas = "";
		for (Mensaje mensaje : enviados) {
			lineas += mensaje.getCodigo() + " ";
		}
		return lineas.trim();
	}
	
	private String obtenerCodigosContactos(List<Contacto> contactos) {
		String lineas = "";
		for (Contacto contacto : contactos) {
			lineas += contacto.getCodigo() + " ";
		}
		return lineas.trim();
	}
	
	private List<Contacto> obtenerContactosDesdeCodigos(String lineas) {
		List<Contacto> recibidos = new LinkedList<Contacto>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorContactoIndividualTDS adaptador = AdaptadorContactoIndividualTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			recibidos.add(adaptador.recuperarContacto(Integer.valueOf((String) strTok.nextElement())));
		}
		return recibidos;
	}
	/*
	private Set<PlayList> obtenerPlayListsDesdeCodigos(String lineas) {
		Set<PlayList> playlists = new HashSet<PlayList>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorPlayListTDS adaptadorPL = AdaptadorPlayListTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			playlists.add(adaptadorPL.recuperarPlayList(Integer.valueOf((String) strTok.nextElement())));
		}
		return playlists;
	}
*/
	
}
