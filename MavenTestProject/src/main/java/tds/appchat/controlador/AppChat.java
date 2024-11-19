package tds.appchat.controlador;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Grupo;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.RepositorioUsuarios;
import tds.appchat.modelo.Usuario;

public class AppChat {
	private static AppChat unicaInstancia;
	private static Usuario usuarioActual;
	private static RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
	
	public static AppChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AppChat();
		return unicaInstancia;
	}

	public String getNombreUsuarioActual() {
		return usuarioActual.getNombre();
	}
	
	//usando string de java 8
	public List<Mensaje> obtenerChatsRecientesUsuario(){
		
		return usuarioActual.getRecibidos();
	}
	
	public boolean registrarUsuario(String nombre, String telefono, String contrasena, Date fechaNacimiento, String imagenPerfilUrl, String saludo, String email) {
		Usuario usr = new Usuario(nombre, email, contrasena, fechaNacimiento, imagenPerfilUrl, saludo, email);
		if(repoUsuarios.agregarUsuario(usr)) {
			usuarioActual = usr;
			return true;
		}
		return false;
	}
	
	public static boolean loginUsuario(String usuario, String contrasena) {
		Optional<Usuario> optUsr = repoUsuarios.getAllUsuarios().stream()
				.filter(usr -> usr.getNombre().equals(usuario) && usr.getContrasena().equals(contrasena))
				.findFirst();
		
		if(optUsr.isPresent()) {
			usuarioActual = optUsr.get();
			return true;
		}
		return false;
		
	}
	
	public ContactoIndividual crearContacto(String nombre, String numTelefono) {
		// Si no tiene el contacto guardado lo guarda
		if (!usuarioActual.existeContacto(numTelefono)) {
			Optional<Usuario> usuarioOpt = repoUsuarios.getUsuarioNumTelf(numTelefono);

			if (usuarioOpt.isPresent()) {
				
				ContactoIndividual nuevoContacto = usuarioActual.crearContacto(nombre, usuarioOpt.get());
				

				//adaptadorContactoIndividual.registrarContacto(nuevoContacto);

				//adaptadorUsuario.modificarUsuario(usuarioActual);
				return nuevoContacto;
			}
		}
		return null;
	}
	
	public Grupo crearGrupo(String nombreGrupo, String imagen) {

		if(nombreGrupo.isEmpty()) {
			throw new IllegalArgumentException("El nombre del grupo no puede estar vacío.");
		}
		if(usuarioActual.hasGrupo(nombreGrupo)){
			throw new IllegalArgumentException("Ya existe un grupo con este nombre.");
		}
		// Se crea el grupo y se añade el grupo al usuario actual
		Grupo nuevoGrupo = usuarioActual.crearGrupo(nombreGrupo, imagen);
	

		// Conexion con persistencia
		//adaptadorGrupo.registrarGrupo(nuevoGrupo);

		//adaptadorUsuario.modificarUsuario(usuarioActual);

		return nuevoGrupo;
	} 
	
	public void addContactoGrupo(Grupo grupo, ContactoIndividual contacto) {
		if(usuarioActual.hasContactoIndividual(contacto)){
			usuarioActual.addIntegranteGrupo(grupo, contacto);
		}
		//Usuario usuario = contacto.getUsuario();
		//adaptadorUsuario.modificarUsuario(usuario);
		
	}
}
