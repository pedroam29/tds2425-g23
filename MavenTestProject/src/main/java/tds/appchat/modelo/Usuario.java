package tds.appchat.modelo;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;


public class Usuario {
	private final String nombre;
	private final Date fechaNacimiento;
	private final String email;
	private final String telefono;
	private final String contrasena;
	private String imagenPerfilUrl;
	private String saludo;
	private List<Mensaje> recibidos;
	private List<Mensaje> enviados;
	private List<Contacto> contactos;
	
	public Usuario(String nombre, String telefono, String contrasena,Date fechaNacimiento, String imagenPerfilUrl, String saludo, String email) {
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.imagenPerfilUrl = imagenPerfilUrl;
		this.saludo = saludo;
		this.recibidos=new LinkedList<>();
		this.enviados=new LinkedList<>();
		this.contactos=new LinkedList<>();
	}
	
	public Usuario(String nombre) {
		this.nombre=nombre;
		this.fechaNacimiento = new Date();
		this.email = "";
		this.telefono = "";
		this.contrasena = "";
	}
	
	public ContactoIndividual getContactoIndividual(Usuario u) {
		return null;
	}

	public List<Mensaje> getChatMensajes(Usuario u){
		return null;
	}
	
	
	public String getImagenPerfilUrl() {
		return imagenPerfilUrl;
	}
	public List<Mensaje> getEnviados() {
		return new LinkedList<Mensaje>(enviados);
	}
	
	public void setEnviados(List<Mensaje> enviados) {
		this.enviados = new LinkedList<Mensaje>(enviados);
	}
	
	public List<Mensaje> getRecibidos() {
		return new LinkedList<Mensaje>(recibidos);
	}
	
	public void setRecibidos(List<Mensaje> recibidos) {
		this.enviados = new LinkedList<Mensaje>(recibidos);
	}
	
	public List<Contacto> getContactos() {
		return new LinkedList<Contacto>(contactos);
	}
	
	public void setContactos(List<Contacto> contactos) {
		this.contactos = new LinkedList<Contacto>(contactos);
	}

	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}
	public String getTelefono() {
		return telefono;
	}

	public String getContrasena() {
		return contrasena;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	/**
	 * Devuelve la Imagen a partir de la URL de los parámetros
	 * @return imagen de perfil
	 */
	public Image getImagen()
	{
		Image imagen = null;
		try {
			URL urlImagen = new URL(imagenPerfilUrl);
			imagen = (Image) ImageIO.read(urlImagen);
		} catch (MalformedURLException e) {
			// La url no es correcta:
			e.printStackTrace();
		} catch (IOException e) {
			// Fallo en la creación de la imagen.
			e.printStackTrace();
		}
		return imagen;
	}
	public String getImagenUrl() {
		return imagenPerfilUrl;
	}

	public String getSaludo() {
		return saludo;
	}
	
	public void addContacto(ContactoIndividual c) {
		this.contactos.add(c);
	}
	
	public boolean hasContactoIndividual(ContactoIndividual cont) {
		return contactos.stream().anyMatch(c -> c instanceof ContactoIndividual && c.equals(cont));
	}
	
	public boolean hasGrupo(String nombreGrupo) {
		return contactos.stream().anyMatch(g -> g instanceof Grupo && g.getNombre().equals(nombreGrupo));
	}
	
	public void addGrupo(Grupo g) {
		contactos.add(g);
	}
	
	public boolean existeContacto(String telefono) {
		return contactos.stream().anyMatch(c -> c instanceof ContactoIndividual && ((ContactoIndividual) c).getTelefono().equals(telefono));
	}
	
	public ContactoIndividual crearContacto(String nombre, Usuario usuarioActual) {

		ContactoIndividual nuevoContacto = new ContactoIndividual(nombre, usuarioActual.getTelefono(), usuarioActual);
		addContacto(nuevoContacto);
		return nuevoContacto;
	}
	
	public Grupo crearGrupo(String nombreGrupo, String imagen) {

		Grupo nuevoGrupo = new Grupo(nombreGrupo, imagen);
		
		this.addGrupo(nuevoGrupo);
		return nuevoGrupo;
	}
	
	public void addIntegranteGrupo(Grupo g, ContactoIndividual c) {
		if(!g.contieneContacto(c)) {
			g.addMiembro(c);
		}
	}
}
