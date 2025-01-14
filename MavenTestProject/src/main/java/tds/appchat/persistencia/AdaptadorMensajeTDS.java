package tds.appchat.persistencia;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.appchat.modelo.Mensaje;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;


public class AdaptadorMensajeTDS implements IAdaptadorMensajeDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensajeTDS unicaInstancia = null;
	
	//Constantes
	private final static String MENSAJE = "mensaje";
	
	private final static String HORA = "hora";
	private final static String EMOTICONO = "emoticono";
	private final static String GRUPO = "grupo";
	private final static String TLF_RECEPTOR = "tlfReceptor";
	private final static String TLF_EMISOR = "tlfEmisor";
	private final static String TEXTO = "texto";
	
	public static AdaptadorMensajeTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorMensajeTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public void registrarMensaje(Mensaje mensaje) {
		
		Entidad eMensaje = new Entidad();

		// Si la entidad está registrada no la registra de nuevo
		if (servPersistencia.recuperarEntidad(mensaje.getCodigo()) != null)
			return;
		
		eMensaje.setNombre(MENSAJE);

		Propiedad texto = new Propiedad(TEXTO, mensaje.getTexto());
		Propiedad hora = new Propiedad(HORA, mensaje.getFechaHora().toString());
		Propiedad emisor = new Propiedad(TLF_EMISOR, mensaje.getTlfEmisor());
		Propiedad receptor = new Propiedad(TLF_RECEPTOR, mensaje.getTlfReceptor());
		Propiedad grupo = new Propiedad(GRUPO, Boolean.toString(mensaje.isGrupo()));
		
		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(texto, hora, emisor, receptor, grupo)));
		
		//Una vez tiene las prioridades escritas
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		mensaje.setCodigo(eMensaje.getId());
		
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
            } else if (prop.getNombre().equals(EMOTICONO)) {
                prop.setValor(Integer.toString(mensaje.getEmoticono()));
            } else if (prop.getNombre().equals(GRUPO)) {
                prop.setValor(Boolean.toString(mensaje.isGrupo()));
            } else if (prop.getNombre().equals(TLF_RECEPTOR)) {
                prop.setValor(mensaje.getTlfReceptor());
            } else if (prop.getNombre().equals(TLF_EMISOR)) {
                prop.setValor(mensaje.getTlfEmisor());
            } else if (prop.getNombre().equals(TEXTO)) {
                prop.setValor(mensaje.getTexto());
            }
        }	
	}
	
	public Mensaje recuperarMensaje(int codigo) {
		
		//Comprobar si se encuentra en el PoolDAO
		if (PoolDAO.getInstancia().contiene(codigo))
			return (Mensaje) PoolDAO.getInstancia().getObjeto(codigo);

		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		
		String texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, TEXTO);
		LocalDateTime hora = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, HORA));
		int emoticon = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, EMOTICONO));
		String receptor = servPersistencia.recuperarPropiedadEntidad(eMensaje, TLF_RECEPTOR);
		String emisor = servPersistencia.recuperarPropiedadEntidad(eMensaje, TLF_EMISOR);
		Boolean grupo = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eMensaje, GRUPO));

		
		Mensaje mensaje = new Mensaje(texto, hora, emisor, receptor);
		mensaje.setCodigo(codigo);
		mensaje.setGrupo(grupo);
		mensaje.setEmoticono(emoticon);

		//Se inserta en el PoolDAO
		PoolDAO.getInstancia().addObjeto(codigo, mensaje);
		
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
