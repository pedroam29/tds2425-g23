package tds.appchat.modelo;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

public class Grupo extends Contacto{

	private String imagen;
	private List<ContactoIndividual> miembros;
	
	public Grupo(String nombre, String imagen) {
		super(nombre);
		this.imagen = imagen;
		this.miembros = new LinkedList<>();
	}
	
	public static URL imagenDefault() {
		return Grupo.class.getResource("/imagenes/grupo-default.png");
	}
	
	public URL getURLImagen() {
		try {
			return new URL(imagen);
		} catch (MalformedURLException e) {
			//No puede estar mal formada, se ha tomado correctamente
			return imagenDefault();
		}
	}
	
	/**
	 * Devuelve la Imagen a partir de la URL de los atributos
	 * @return imagen de perfil
	 */
	public String getUrlImagen() {
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
		return super.toString();
	}
	
}
