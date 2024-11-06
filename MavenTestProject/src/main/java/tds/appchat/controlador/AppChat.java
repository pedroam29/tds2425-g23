package tds.appchat.controlador;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.RepositorioUsuarios;
import tds.appchat.modelo.Usuario;


public class AppChat {
	private static AppChat unicaInstancia;
	private static Usuario usuarioActual;
	private static RepositorioUsuarios repoUsuarios;
	
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
		repoUsuarios.agregarUsuario(usr);
		usuarioActual = usr;
		return true;
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
}
