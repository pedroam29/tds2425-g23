package tds.appchat.persistencia;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.appchat.modelo.Grupo;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.Mensaje3;
import tds.appchat.modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;


public class AdaptadorMensajeTDS implements IAdaptadorMensajeDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensajeTDS unicaInstancia = null;
	
	private static IAdaptadorUsuarioDAO adaptadorUsuario;
	private static IAdaptadorGrupoDAO adaptadorGrupo;
	
	private SimpleDateFormat dateFormat; 
	
	//Constantes
	private final static String MENSAJE = "mensaje";
	
	private final static String HORA = "hora";
	private final static String EMOTICONO = "emoticono";
	private final static String MENSAJE_GRUPO = "mensaje_grupo";
	private final static String GRUPO = "grupo";
	private final static String RECEPTOR = "tlfReceptor";
	private final static String EMISOR = "tlfEmisor";
	private final static String TEXTO = "texto";
	
	public static AdaptadorMensajeTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorMensajeTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorGrupo = AdaptadorGrupoTDS.getUnicaInstancia();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	}
	
	public void registrarMensaje(Mensaje mensaje) {
		//if (servPersistencia.recuperarEntidad(mensaje.getCodigo()) != null)
		//	return;
		//Si no está registrada:
		Entidad eMensaje = new Entidad();
		eMensaje.setNombre(MENSAJE);

		//Los atributos del mensaje
		Propiedad texto = new Propiedad(TEXTO, mensaje.getTexto());
		Propiedad hora = new Propiedad(HORA, mensaje.getFechaHora().toString());
		System.out.println("Se inserta: " + mensaje.getFechaHora().toString());
		Propiedad mensajeGrupo = new Propiedad(MENSAJE_GRUPO, Boolean.toString(mensaje.isMensajeGrupo()));
		
		//Si es grupo
		Propiedad receptor = new Propiedad(RECEPTOR, !mensaje.isMensajeGrupo() ? Integer.toString(mensaje.getReceptor().getCodigo()) : "NA");
		Propiedad emisor = new Propiedad(EMISOR, Integer.toString(mensaje.getEmisor().getCodigo()));
		
		Propiedad grupo = new Propiedad(GRUPO, mensaje.isMensajeGrupo() ? Integer.toString(mensaje.getGrupo().getCodigo()) : "NA");
		
		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(texto, hora,  receptor, emisor, grupo, mensajeGrupo)));
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		
		mensaje.setCodigo(eMensaje.getId());
		System.out.println("SE INSERTA COMO CóDIGO: " + mensaje.getCodigo());
		//Se inserta le objeto
		

		// Se guarda en el pool
		PoolDAO.getInstancia().addObjeto(mensaje.getCodigo(), mensaje);
	}
	
	public void borrarMensaje(Mensaje mensaje) {
		// Se borran los elementos de las tablas que lo componen (Usuario y Contacto)
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		servPersistencia.borrarEntidad(eMensaje);
		
		// Si está en el pool, borramos del pool
		if (PoolDAO.getInstancia().contiene(mensaje.getCodigo()))
			PoolDAO.getInstancia().removeObjeto(mensaje.getCodigo());
	}
	
	public void modificarMensaje(Mensaje mensaje) {
		
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		
		for (Propiedad prop : eMensaje.getPropiedades()) {
            // Usamos if-else para cada uno de los atributos
            if (prop.getNombre().equals(HORA)) {
                prop.setValor(mensaje.getFechaHora().toString());
            //} else if (prop.getNombre().equals(EMOTICONO)) {
            //    prop.setValor(Integer.toString(mensaje.getEmoticono()));
            } else if (prop.getNombre().equals(GRUPO)) {
                prop.setValor(Integer.toString(mensaje.getGrupo().getCodigo()));
            } else if (prop.getNombre().equals(MENSAJE_GRUPO)) {
                prop.setValor(Boolean.toString(mensaje.isMensajeGrupo()));
            } else if (prop.getNombre().equals(RECEPTOR)) {
                prop.setValor(Integer.toString(mensaje.getReceptor().getCodigo()));
            } else if (prop.getNombre().equals(EMISOR)) {
                prop.setValor(Integer.toString(mensaje.getEmisor().getCodigo()));
            } else if (prop.getNombre().equals(TEXTO)) {
                prop.setValor(mensaje.getTexto());
            }
        }	
	}
	
	public Mensaje recuperarMensaje(int codigo) {
		
		//Comprobar si se encuentra en el PoolDAO se devuelve
		if (PoolDAO.getInstancia().contiene(codigo))
			return (Mensaje) PoolDAO.getInstancia().getObjeto(codigo);
		System.out.println("Codigo: " + Integer.toString(codigo));
		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		String texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, TEXTO);
		//TODO: quitar
		System.out.println(servPersistencia.recuperarPropiedadEntidad(eMensaje, HORA) + servPersistencia.recuperarPropiedadEntidad(eMensaje, TEXTO));
		LocalDateTime hora = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, HORA));
		
		Mensaje mensaje = new Mensaje(texto, hora);
		mensaje.setCodigo(codigo);
		PoolDAO.getInstancia().addObjeto(codigo, mensaje);
		
		//Una vez insertado en el PoolDao ya se pueden obtener el resto de atributos
		
		Boolean mensajeGrupo = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eMensaje, MENSAJE_GRUPO));
		
		Grupo grupo = mensajeGrupo ? adaptadorGrupo.recuperarGrupo(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, GRUPO))) : null; 
		Usuario receptor = !mensajeGrupo ? adaptadorUsuario.recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, RECEPTOR))) : null;
		
		Usuario emisor = adaptadorUsuario.recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, EMISOR)));
		
		mensaje.setGrupo(grupo);
		mensaje.setMensajeGrupo(mensajeGrupo);
		mensaje.setEmisor(emisor);
		mensaje.setReceptor(receptor);
		
		return mensaje;
	}
	
	public List<Mensaje> recuperarTodosMensajes() {
		List<Mensaje> mensajes = new LinkedList<>();
		List<Entidad> eMensajes = servPersistencia.recuperarEntidades(MENSAJE);

		for (Entidad eMensaje : eMensajes) {
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		}
		return mensajes;
	}
}
