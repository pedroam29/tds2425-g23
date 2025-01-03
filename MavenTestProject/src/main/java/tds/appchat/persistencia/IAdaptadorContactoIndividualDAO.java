package tds.appchat.persistencia;

import java.util.List;

import tds.appchat.modelo.ContactoIndividual;

public interface IAdaptadorContactoIndividualDAO extends IAdaptadorContacto {
	public void registrarContacto(ContactoIndividual contact);
	public void borrarContacto(ContactoIndividual contact);
	public void modificarContacto(ContactoIndividual contact);
	public ContactoIndividual recuperarContacto(int codigo);
	public List<ContactoIndividual> recuperarTodosContactos();
}
