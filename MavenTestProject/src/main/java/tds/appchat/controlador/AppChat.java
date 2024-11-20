package tds.appchat.controlador;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.RepositorioUsuarios;
import tds.appchat.modelo.Usuario;


public class AppChat {
	private final static AppChat unicaInstancia = new AppChat();
	private static Usuario usuarioActual;
	private static RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
	
public static AppChat getUnicaInstancia() {
//		if (unicaInstancia == null)
//			unicaInstancia = new AppChat();
		//Haciendo el constructor privado solo habrá una única instancia.
		return unicaInstancia;
}
	//Para que no se pueda crear fuera de esta clase;
	private AppChat(){}
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
		//En vez de hacer equals hacer funcion en usuario por patrón.
		Optional<Usuario> optUsr = repoUsuarios.getAllUsuarios().stream()
				.filter(usr -> usr.getNombre().equals(usuario) && usr.isClave(contrasena))
				.findFirst();
		
		if(optUsr.isPresent()) {
			usuarioActual = optUsr.get();
			return true;
		}
		return false;
		
	}
	
	public static boolean existeTelefono(String telefono) {
		//Solución temporal: puede ser necesario tener que crear una funcion dentre de usuario para comprobar 
		boolean existe = repoUsuarios.getAllUsuarios().stream()
				.anyMatch(u->u.getTelefono().equals(telefono));
		return existe;
	}
}
