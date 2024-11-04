package tds.appchat.modelo;

import java.sql.Date;
import java.util.List;


public class Usuario {
	private final String nombre;
	private final Date fechaNacimiento;
	private final String email;
	private final String telefono;
	private final String contrasena;
	private final String imagenPerfilUrl;
	private final String saludo;
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

	public List<Mensaje> getRecibidos() {
		return recibidos;
	}

	public List<Mensaje> getEnviados() {
		return enviados;
	}

	public List<Contacto> getContactos() {
		return contactos;
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
	
	
}
