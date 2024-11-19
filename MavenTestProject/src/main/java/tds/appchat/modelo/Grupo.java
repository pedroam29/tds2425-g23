package tds.appchat.modelo;

import java.util.LinkedList;
import java.util.List;

public class Grupo extends Contacto{

	private String imagen;
	private List<ContactoIndividual> miembros;
	
	public Grupo(String nombre, String imagen) {
		super(nombre);
		this.imagen = imagen;
		this.miembros = new LinkedList<>();
	}
	
	public String getImagen() {
		return imagen;
	}

	public List<ContactoIndividual> getMiembros() {
		return miembros;
	}
	
	public void addMiembro(ContactoIndividual c) {
		miembros.add(c);

	}
	
	public boolean contieneContacto(ContactoIndividual c) {
		return this.miembros.contains(c);
	}
	
}
