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
	public void setMiembros(List<ContactoIndividual> miembros)
	{
		this.miembros = miembros;
	}
	public boolean addMiembro(ContactoIndividual c) {
		return miembros.add(c);
	}
	
	public boolean eliminarMiembro(ContactoIndividual c){
		return miembros.remove(c);
	}
	public boolean contieneContacto(ContactoIndividual c) {
		return this.miembros.contains(c);
	}
	@Override
	public String toString() {
		return super.toString() + "\nMiembros: " + miembros;
	}
}
