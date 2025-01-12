package tds.appchat.persistencia;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.appchat.modelo.Contacto;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Grupo;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;


public class AdaptadorMensajeTDS {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensajeTDS unicaInstancia = null;
	
	public static AdaptadorMensajeTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorMensajeTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public void registrarMensaje(Mensaje message) {
		Entidad eMensaje = new Entidad();
		boolean existe = true;

		// Si la entidad está registrada no la registra de nuevo
		try {
			eMensaje = servPersistencia.recuperarEntidad(message.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) {
			return;
		}

		// registrar primero los atributos que son objetos
		// registrar usuario emisor del mensaje
		registrarSiNoExisteUsuario(message.getEmisor());

		// registrar contacto receptor del mensaje
		registrarSiNoExistenContactosoGrupos(message.getReceptor());

		// Atributos propios del usuario
		eMensaje.setNombre("mensaje");

		// Se guarda el grupo receptor o el contacto, segun convenga
		boolean grupo = false;
		if (message.getReceptor() instanceof Grupo) {
			grupo = true;
		}

		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("texto", message.getTexto()),
				new Propiedad("hora", message.getFechaHora().toString()),
				new Propiedad("emoticono", String.valueOf(message.getEmoticono())),
				new Propiedad("receptor", String.valueOf(message.getReceptor().getCodigo())),
				new Propiedad("togroup", String.valueOf(grupo)),
				new Propiedad("emisor", String.valueOf(message.getEmisor().getCodigo())))));

		// Registrar entidad mensaje
		eMensaje = servPersistencia.registrarEntidad(eMensaje);

		// Identificador unico
		message.setCodigo(eMensaje.getId());
		
		// Guardamos en el pool
		PoolDAO.getInstancia().addObjeto(message.getCodigo(), message);
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

		// Se da el cambiazo a las propiedades del usuario
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "texto");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "texto", mensaje.getTexto());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "hora");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "hora", mensaje.getFechaHora().toString());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "emoticono");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "emoticono", String.valueOf(mensaje.getEmoticono()));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "receptor");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "receptor",
				String.valueOf(mensaje.getReceptor().getCodigo()));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "emisor");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "emisor", String.valueOf(mensaje.getEmisor().getCodigo()));

		boolean grupo = false;
		if (mensaje.getReceptor() instanceof Grupo) {
			grupo = true;
		}
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "togroup");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "togroup", String.valueOf(grupo));
	}
	
	public Mensaje recuperarMensaje(int codigo) {
		// Si la entidad esta en el pool la devuelve directamente
		if (PoolDAO.getInstancia().contiene(codigo))
			return (Mensaje) PoolDAO.getInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// recuperar entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		
		// recuperar propiedades que no son objetos
		// fecha
		String texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		LocalDateTime hora = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, "hora"));
		int emoticono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono"));
		Contacto receptor = null;
		Boolean toGroup = Boolean.valueOf(servPersistencia.recuperarPropiedadEntidad(eMensaje, "togroup"));
		Usuario emisor = null;

		Mensaje mensaje = new Mensaje(texto, emoticono, hora);
		mensaje.setCodigo(codigo);

		// Metemos el usuario en el pool antes de llamar a otros
		// adaptadores
		PoolDAO.getInstancia().addObjeto(codigo, mensaje);

		// recuperar propiedades que son objetos llamando a adaptadores
		// mensaje
		// Usuario emisor
		AdaptadorUsuarioTDS adaptadorU = AdaptadorUsuarioTDS.getUnicaInstancia();
		int codigoUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emisor"));
		emisor = adaptadorU.recuperarUsuario(codigoUsuario);
		mensaje.setEmisor(emisor);

		// Contacto o grupo receptor
		int codigoContacto = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "receptor"));
		if (toGroup) {
			AdaptadorGrupoTDS adaptadorG = AdaptadorGrupoTDS.getUnicaInstancia();
			receptor = adaptadorG.recuperarGrupo(codigoContacto);
		} else {
			AdaptadorContactoIndividualTDS adaptadorC = AdaptadorContactoIndividualTDS.getInstancia();
			receptor = adaptadorC.recuperarContacto(codigoContacto);
		}
		mensaje.setReceptor(receptor);

		// devolver el objeto mensaje con todo
		return mensaje;
	}
	
	public List<Mensaje> recuperarTodosMensajes() {
		List<Mensaje> mensajes = new LinkedList<>();
		List<Entidad> eMensajes = servPersistencia.recuperarEntidades("mensaje");

		for (Entidad eMensaje : eMensajes) {
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		}
		return mensajes;
	}
	
	private void registrarSiNoExistenContactosoGrupos(List<Contacto> contactos) {
		AdaptadorContactoIndividualTDS adaptadorContactos = AdaptadorContactoIndividualTDS.getInstancia();
		AdaptadorGrupoTDS adaptadorGrupos = AdaptadorGrupoTDS.getUnicaInstancia();
		contactos.stream().forEach(c -> {
			if (c instanceof ContactoIndividual) {
				adaptadorContactos.registrarContacto((ContactoIndividual) c);
			} else {
				adaptadorGrupos.registrarGrupo((Grupo) c);
			}
		});
	}

	private void registrarSiNoExistenContactosoGrupos(Contacto contacto) {
		LinkedList<Contacto> contactos = new LinkedList<>();
		contactos.add(contacto);
		registrarSiNoExistenContactosoGrupos(contactos);
	}

	private void registrarSiNoExisteUsuario(Usuario emisor) {
		AdaptadorUsuarioTDS.getUnicaInstancia().registrarUsuario(emisor);
	}
}
