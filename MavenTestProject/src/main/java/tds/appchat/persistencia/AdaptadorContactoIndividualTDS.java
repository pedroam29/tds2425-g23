package tds.appchat.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorContactoIndividualTDS implements IAdaptadorContactoIndividualDAO{
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoIndividualTDS unicaInstancia = null;
	
	private AdaptadorContactoIndividualTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public static AdaptadorContactoIndividualTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorContactoIndividualTDS();
		return unicaInstancia;
	}
	
	public void registrarContacto(ContactoIndividual contacto) {
		Entidad eContact = new Entidad();
		boolean existe = true;

		// Si la entidad está registrada no la registra de nuevo
		try {
			eContact = servPersistencia.recuperarEntidad(contacto.getCodigo());
		} catch (NullPointerException e) { }
		
		if (eContact != null)
			return;

		// Registramos al usuario correspondiente al contacto si no existe.
		registrarSiNoExisteUser(contacto.getUsuario());

		// Atributos propios del contacto

		eContact = new Entidad();
		eContact.setNombre(IAdaptadorContacto.ATRIB_CONTACTO_INDIVIDUAL);
		eContact.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", contacto.getNombre()),
				new Propiedad("telefono", String.valueOf(contacto.getTelefono())),
				new Propiedad("usuario", String.valueOf(contacto.getUsuario().getCodigo())))));
		
		// Registrar entidad usuario
		eContact = servPersistencia.registrarEntidad(eContact);
		
		
		// Identificador unico
		contacto.setCodigo(eContact.getId());
		
		// Guardamos en el pool
		PoolDAO.getInstancia().addObjeto(contacto.getCodigo(), contacto);
	}
	
	public void borrarContacto(ContactoIndividual contact) {
		// Borramos los elementos de las tablas que lo componen (En este caso borramos
		// los mensajes del contacto a eliminar)
		Entidad eContact;
		
		eContact = servPersistencia.recuperarEntidad(contact.getCodigo());
		servPersistencia.borrarEntidad(eContact);
		
		// Si está en el pool, borramos del pool
		if (PoolDAO.getInstancia().contiene(contact.getCodigo()))
			PoolDAO.getInstancia().removeObjeto(contact.getCodigo());
	}
	
	public void modificarContacto(ContactoIndividual contact) {
		Entidad eContact = servPersistencia.recuperarEntidad(contact.getCodigo());

		// Se da el cambiazo a las propiedades del contacto
		servPersistencia.eliminarPropiedadEntidad(eContact, "nombre");
		servPersistencia.anadirPropiedadEntidad(eContact, "nombre", contact.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eContact, "telefono");
		servPersistencia.anadirPropiedadEntidad(eContact, "telefono", String.valueOf(contact.getTelefono()));
		servPersistencia.eliminarPropiedadEntidad(eContact, "usuario");
		servPersistencia.anadirPropiedadEntidad(eContact, "usuario", String.valueOf(contact.getUsuario().getCodigo()));
	}
	
	public ContactoIndividual recuperarContacto(int codigo) {
		// Si la entidad esta en el pool la devuelve directamente
		if (PoolDAO.getInstancia().contiene(codigo))
			return (ContactoIndividual) PoolDAO.getInstancia().getObjeto(codigo);

		// Sino, la recupera de la base de datos
		// Recuperamos la entidad
		Entidad eContact = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContact, "nombre");
		
		String telefono = servPersistencia.recuperarPropiedadEntidad(eContact, "telefono");
		
		ContactoIndividual contact = new ContactoIndividual(nombre, telefono, null);
		contact.setCodigo(codigo);

		// Metemos al contacto en el pool antes de llamar a otros adaptadores
		PoolDAO.getInstancia().addObjeto(codigo, contact);
		

		// Obtener usuario del contacto
		servPersistencia.recuperarPropiedadEntidad(eContact, "usuario");
		
		contact.setUsuario(obtenerUsuarioDesdeCodigo(servPersistencia.recuperarPropiedadEntidad(eContact, "usuario")));

		// Devolvemos el objeto contacto
		return contact;
	}

	
	public List<ContactoIndividual> recuperarTodosContactos() {
		List<ContactoIndividual> contactos = new LinkedList<>();
		List<Entidad> eContacts = servPersistencia.recuperarEntidades("contacto");

		for (Entidad eContact : eContacts)
			contactos.add(recuperarContacto(eContact.getId()));
		return contactos;
	}
	
	private void registrarSiNoExisteUser(Usuario admin) {
		AdaptadorUsuarioTDS adaptadorUsuarios = AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuarios.registrarUsuario(admin);
	}
	
	private Usuario obtenerUsuarioDesdeCodigo(String codigo) {
		AdaptadorUsuarioTDS adaptadorUsuarios = AdaptadorUsuarioTDS.getUnicaInstancia();
		return adaptadorUsuarios.recuperarUsuario(Integer.valueOf(codigo));
	}
}
