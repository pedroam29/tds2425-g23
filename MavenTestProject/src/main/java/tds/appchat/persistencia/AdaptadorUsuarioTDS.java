package tds.appchat.persistencia;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.appchat.modelo.Contacto;
import tds.appchat.modelo.Descuento;
import tds.appchat.modelo.GestorRolUsuario;
import tds.appchat.modelo.Grupo;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.RolUsuario;
import tds.appchat.modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;


public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO{
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;
	private SimpleDateFormat dateFormat;

	private final static String SEPARADOR_CONTACTOS = " ";
	private final static String SEPARADOR_MENSAJES = " ";
	private final static String SEPARADOR_USUARIO_MENSAJES = ":";
	private final static String SEPARADOR_USUARIOS = ",";
	
	public static AdaptadorUsuarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorUsuarioTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() {
		try {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		} catch (Exception e){
			
		}
		
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario = null;
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
			
		} catch (NullPointerException e) {}
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
						new Propiedad("premium", Boolean.toString(usuario.isPremium())),
						new Propiedad("descuento", usuario.getDescuentoID()),
						new Propiedad("mensajes", obtenerCodigosMensajes(usuario.getMensajesPorUsuario())),
						new Propiedad("mensajesGrupos", obtenerCodigosMensajesGrupos(usuario.getMensajesGrupos())),
						new Propiedad("rolUsuario", usuario.getRolUsuario().toString()),
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
			//} else if (prop.getNombre().equals("mensajesRecibidos")) {
			//	prop.setValor(obtenerCodigosMensajes(usuario.getRecibidos()));
			} else if (prop.getNombre().equals("descuento")) {
				prop.setValor(usuario.getDescuentoID());
			} else if (prop.getNombre().equals("rolUsuario")) {
				prop.setValor(usuario.getRolUsuario().toString());
			} else if (prop.getNombre().equals("premium")) {
				prop.setValor(Boolean.toString(usuario.isPremium()));
			//} else if(prop.getNombre().equals("mensajesEnviados")) {
			//  prop.setValor(obtenerCodigosMensajes(usuario.getEnviados()));
			} else if(prop.getNombre().equals("mensajes")) {
				prop.setValor(obtenerCodigosMensajes(usuario.getMensajesPorUsuario()));
			} else if(prop.getNombre().equals("mensajesGrupos")) {
				prop.setValor(obtenerCodigosMensajesGrupos(usuario.getMensajesGrupos()));
			} else if(prop.getNombre().equals("contactos")) {
				prop.setValor(obtenerCodigosContactos(usuario.getContactos()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Usuario recuperarUsuario(int codigo) {
		
		if (PoolDAO.getInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getInstancia().getObjeto(codigo);
		
		Entidad eUsuario;
		String usuario;
		String contrasena;
		Date fechaNacimiento = null;
		String imagenPerfilUrl;
		String saludo;
		String telefono;
		Descuento descuento;
		Boolean premium;
		RolUsuario rol;
		
		HashMap<String, List<Mensaje>> mensajes;
		HashMap<Grupo, List<Mensaje>> mensajesGrupo;
		
		List<Contacto> contactos;

		eUsuario = servPersistencia.recuperarEntidad(codigo);
		usuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, "usuario");
		contrasena = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contrasena");
		imagenPerfilUrl = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagenPerfil");
		saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, "saludo");
		telefono = servPersistencia.recuperarPropiedadEntidad(eUsuario, "telefono");
		descuento = Descuento.fromString(servPersistencia.recuperarPropiedadEntidad(eUsuario, "descuento"));
		premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium"));
		rol = GestorRolUsuario.parseRol(servPersistencia.recuperarPropiedadEntidad(eUsuario, "rolUsuario"));
		//recibidos = obtenerMensajesCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "mensajesRecibidos"));
		//enviados = obtenerMensajesCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "mensajesEnviados"));
		
		try {
			fechaNacimiento = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"));
		} catch (ParseException e) {
			//Si falla a la hora de convertir un string a fecha
		}
		contactos = obtenerContactosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"));
		
		Usuario usr = new Usuario(usuario, telefono, contrasena, fechaNacimiento, imagenPerfilUrl, saludo);
		//Se inserta en el PoolDao
		PoolDAO.getInstancia().addObjeto(codigo, usr);
		
		mensajes = obtenerMensajesCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "mensajes"));
		mensajesGrupo = obtenerMensajesGruposCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "mensajesGrupos"));
		
		usr.setDescuento(descuento);
		usr.setContactos(contactos);
		usr.setPremium(premium);
		usr.setCodigo(codigo);
		usr.setMensajesGrupos(mensajesGrupo);
		usr.setMensajesPorUsuario(mensajes);
		usr.setRolUsuario(rol);
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
	
	/**
	 * A partir de una lista de mensajes se obtiene un string con
	 * sus códigos separados por SEPARADOR_MENSAJES
	 * 
	 * @param mensajes
	 * @return
	 */
	private String obtenerCodigosMensajes(HashMap<String,List<Mensaje>> mensajes) {
		
		String stringMensajes = mensajes.entrySet().stream()
			.map(e ->
			e.getKey()
			+ SEPARADOR_USUARIO_MENSAJES +
				e.getValue().stream()
				.map(m -> Integer.toString(m.getCodigo()))
				.collect(Collectors.joining(SEPARADOR_MENSAJES))			
			).collect(Collectors.joining(SEPARADOR_USUARIOS));
		
	    return stringMensajes;
	}
	/**
	 * Se obtien el hash de mensajes a partir del string
	 * 
	 * @param mensajes
	 * @return Hash de mensajes por usuario
	 */
	private HashMap<String,List<Mensaje>> obtenerMensajesCodigos(String mensajes) {
		
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();
		//AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		
		HashMap<String,List<Mensaje>> mapaMensajes = new HashMap<String, List<Mensaje>>();
		if (!mensajes.isEmpty()) {
			for (String mensaje : mensajes.split(SEPARADOR_USUARIOS)) {
				String[] mensajesPorUsuario = mensaje.split(SEPARADOR_USUARIO_MENSAJES);
				String tlf = mensajesPorUsuario[0];
				
				List<Mensaje> msg = Arrays.stream(mensajesPorUsuario[1].split(SEPARADOR_MENSAJES))
						.map(m -> adaptadorMensaje.recuperarMensaje(Integer.parseInt(m))).collect(Collectors.toList());
				mapaMensajes.put(tlf, msg);
			}
		}
	    return mapaMensajes;
	}
	
	/**
	 * A partir de una lista de mensajes se obtiene un string con
	 * sus códigos separados por SEPARADOR_MENSAJES
	 * 
	 * @param mensajes
	 * @return
	 */
	private String obtenerCodigosMensajesGrupos(HashMap<Grupo,List<Mensaje>> mensajes) {
		String stringMensajes = mensajes.entrySet().stream()
			.map(e ->
			Integer.toString(e.getKey().getCodigo())
			+ SEPARADOR_USUARIO_MENSAJES +
				e.getValue().stream()
				.map(m -> Integer.toString(m.getCodigo()))
				.collect(Collectors.joining(SEPARADOR_MENSAJES))			
			).collect(Collectors.joining(SEPARADOR_USUARIOS));
		
	    return stringMensajes;
	}
	
	private HashMap<Grupo,List<Mensaje>> obtenerMensajesGruposCodigos(String mensajes) {
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();
		AdaptadorGrupoTDS adaptadorGrupo = AdaptadorGrupoTDS.getUnicaInstancia();
		
		HashMap<Grupo,List<Mensaje>> mapaMensajes = new HashMap<Grupo, List<Mensaje>>();
		if (!mensajes.isEmpty()) {
			for (String mensaje : mensajes.split(SEPARADOR_USUARIOS)) {
				String[] mensajesPorGrupo = mensaje.split(SEPARADOR_USUARIO_MENSAJES);
				//Usuario u = adaptadorUsuario.recuperarUsuario(Integer.parseInt(mensajesPorUsuario[0]));
				Grupo g = adaptadorGrupo.recuperarGrupo(Integer.parseInt(mensajesPorGrupo[0]));
				List<Mensaje> msg = Arrays.stream(mensajesPorGrupo[1].split(SEPARADOR_MENSAJES))
						.map(m -> adaptadorMensaje.recuperarMensaje(Integer.parseInt(m))).collect(Collectors.toList());
				mapaMensajes.put(g, msg);
			}
		}
	    return mapaMensajes;
	}
	
	
	/**
	 * Dadao un String de códigos de mensajes devuelve
	 * una lista con mensajes.
	 * 
	 * @param mensajes
	 * @return
	 */
//	private List<Mensaje3> obtenerMensajesCodigos(String mensajes) {
//		
//		List<Mensaje3> listaMensajes = new LinkedList<Mensaje3>();
//		
//		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();
//		
//		StringTokenizer strTok = new StringTokenizer(mensajes, SEPARADOR_MENSAJES);
//		while(strTok.hasMoreTokens()) {
//			String s = strTok.nextToken();			
//			listaMensajes.add(adaptadorMensaje.recuperarMensaje(Integer.valueOf(s)));	
//		}
//		
//		return listaMensajes;
//	}
	
//	private List<Mensaje> obtenerMensajesDesdeCodigos(String mensajes) {
//		HashMap<Usuario,List<Mensaje>> mensaje = new HashMap<Usuario, List<Mensaje>>();
//		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();
//		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
//		
//		StringTokenizer strTok = new StringTokenizer(mensajes, SEPARADOR_MENSAJES);
//		while(strTok.hasMoreTokens()) {
//			String s = strTok.nextToken();			
//			listaMensajes.add(adaptadorMensaje.recuperarMensaje(Integer.valueOf(s)));	
//		}
//		
//		return listaMensajes;
//	}
	
	/**
	 * A partir de una lista de conctactos se obtiene
	 * un String con sus códigos separados por SEPARADOR_CONTACTOS
	 * 
	 * @param contactos
	 * @return
	 */
	private String obtenerCodigosContactos(List<Contacto> contactos) {
		String lineas = "";
		for (Contacto contacto : contactos) {
			lineas += contacto.getCodigo() + SEPARADOR_CONTACTOS;
		}
		return lineas.trim();
	}
	/**
	 * A partir de un string de códigos de contactos mediante adaptador
	 * contacto. Es 
	 * 
	 * @param lineas
	 * @return
	 */
	private List<Contacto> obtenerContactosDesdeCodigos(String lineas) {
		
		List<Contacto> recibidos = new LinkedList<Contacto>();
		AdaptadorContactoTDS adaptadorContacto = AdaptadorContactoTDS.getInstancia();
		
		StringTokenizer strTok = new StringTokenizer(lineas, SEPARADOR_CONTACTOS);
		
		//Es el adaptador de contactos el que se encarga de decidir si un contacto es 

		while(strTok.hasMoreTokens()) {
			String a = strTok.nextToken();			
			recibidos.add(adaptadorContacto.recuperarContacto(Integer.valueOf(a)));
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
