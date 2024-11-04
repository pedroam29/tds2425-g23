package tds.appchat.modelo;

import java.util.ArrayList;
import java.util.List;

public class Grupo extends Contacto{

	private String imagen;
	private List<ContactoIndividual> miembros;
	
	public Grupo(String nombre, String imagen, List<ContactoIndividual> miembros) {
		super(nombre);
		this.imagen = imagen;
		this.miembros = miembros;
	}

	public String getImagen() {
		return imagen;
	}

	public List<ContactoIndividual> getMiembros() {
		return miembros;
	}
	
	
}
