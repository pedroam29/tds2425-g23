package tds.appchat.modelo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

public class Grupo extends Contacto{
	
	private final static URL DEFAULT_IMAGEN = Grupo.class.getResource("/imagenes/personas-128.png");
	
	private URL imagen;
	private List<ContactoIndividual> miembros;
	
	
	public Grupo(String nombre, URL imagen) {
		super(nombre);
		this.imagen = imagen;
		this.miembros = new LinkedList<ContactoIndividual>();
	}
	
	public Grupo(String nombre, String imagen) {
		super(nombre);
		try {
			this.imagen = new URL(imagen);
		} catch (MalformedURLException e) {
			this.imagen = DEFAULT_IMAGEN;
		}
		this.miembros = new LinkedList<ContactoIndividual>();
	}
	public Grupo(String nombre) {
		super(nombre);
		this.imagen = DEFAULT_IMAGEN;
		this.miembros = new LinkedList<ContactoIndividual>();
	}
	public static URL imagenDefault() {
		return DEFAULT_IMAGEN;
	}
	
	public URL getURLImagen() {
		return imagen;
	}
	
	public void setURLImagen(URL imagen) {
		this.imagen = imagen;
	}
	
	public Image getImagen(int tam) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(imagen);
		} catch (Exception e) {
			try {
				image = ImageIO.read(DEFAULT_IMAGEN);
			} catch (Exception e2) {
				
			}
		}
		//Todas las imagenes serán en formato 1x1
		Image imagenReescalada = image.getScaledInstance(tam, tam, Image.SCALE_SMOOTH);
		return imagenReescalada;
	}
	/**
	 * Devuelve la Imagen a partir de la URL de los atributos
	 * @return imagen de perfil
	 */
//	public String getUrlImagen() {
//		return imagen;
//	}
	
	
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
