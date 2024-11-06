package tds.appchat.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


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
	
	public Usuario(String nombre, String telefono, String contrasena, Date fechaNacimiento, String imagenPerfilUrl, String saludo, String email) {
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

	public String getImagen() {
		return imagenPerfilUrl;
	}

	public String getSaludo() {
		return saludo;
	}
	
	public void addContacto(String nombre, String telefono, RepositorioUsuarios repo) {
		Usuario usuarioAgregar = repo.obtenerUsuario(telefono);
		if(usuarioAgregar == null) {
			
		}
		
		ContactoIndividual nuevoContacto = new ContactoIndividual(nombre, usuarioAgregar);
		this.contactos.add(nuevoContacto);
		
	}
	
	
}
