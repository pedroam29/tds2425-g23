package tds.appchat.controlador;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorUsuarioDAO;
import tds.appchat.modelo.Contacto;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Grupo;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.RepositorioUsuarios;
import tds.appchat.modelo.Usuario;



public class AppChat {
	private final static AppChat unicaInstancia = new AppChat();
	private Usuario usuarioActual;
	private RepositorioUsuarios repoUsuarios;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoIndividualDAO adaptadorContactoIndividual;
	
	public static AppChat getUnicaInstancia() {
//		if (unicaInstancia == null)
//			unicaInstancia = new AppChat();
		//Haciendo el constructor privado solo habrá una única instancia.
		return unicaInstancia;
	}
	//Para que no se pueda crear fuera de esta clase;
	private AppChat(){
		inicializarAdaptadores();
		inicializarRepositorios();
	}
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorContactoIndividual = factoria.getContactoDAO();
	}
	
	private void inicializarRepositorios() {
		repoUsuarios = RepositorioUsuarios.getUnicaInstancia();
		
	}
	public String getNombreUsuarioActual() {
		return usuarioActual.getNombre();
	}
	
	//usando string de java 8
	public List<Mensaje> obtenerChatsRecientesUsuario(){
		return usuarioActual.getRecibidos();
	}
	public List<Contacto> contactosUsuarioActual(){
		return usuarioActual.getContactos();
	}
	
	public boolean registrarUsuario(String nombre, String telefono, String contrasena, Date fechaNacimiento, String imagenPerfilUrl, String saludo, String email) {
		Usuario usr = new Usuario(nombre, telefono, contrasena, fechaNacimiento, imagenPerfilUrl, saludo, email);
		if(repoUsuarios.agregarUsuario(usr)) {
			usuarioActual = usr;
			adaptadorUsuario.registrarUsuario(usr);
			return true;
		}
		return false;
	}
	
	public boolean loginUsuario(String telefono, String contrasena) {
		
		//En vez de hacer equals hacer funcion en usuario por patrón.
		Optional<Usuario> optUsr = repoUsuarios.getAllUsuarios().stream()
				.filter(usr -> usr.getTelefono().equals(telefono) && usr.isClave(contrasena))
				.findFirst();
		
		if(optUsr.isPresent()) {
			usuarioActual = optUsr.get();
			
			for(Contacto contacto: usuarioActual.getContactos()) {
				System.out.println(contacto.getNombre());
			}
			return true;
		}
		return false;
		
	}
	
	public void logoutUsuario() {
		usuarioActual = null;
	}
	
	public boolean existeTelefono(String telefono) {
		//Solución temporal: puede ser necesario tener que crear una funcion dentre de usuario para comprobar 
		boolean existe = repoUsuarios.getAllUsuarios().stream()
				.anyMatch(u->u.getTelefono().equals(telefono));
		return existe;
	}
	public ContactoIndividual crearContacto(String nombre, String numTelefono) {
		// Si no tiene el contacto guardado lo guarda
		if (!usuarioActual.existeContacto(numTelefono)) {
			Optional<Usuario> usuarioOpt = repoUsuarios.getUsuarioNumTelf(numTelefono);
			
			if (usuarioOpt.isPresent()) {
				
				ContactoIndividual nuevoContacto = usuarioActual.crearContacto(nombre, usuarioOpt.get());
				

				adaptadorContactoIndividual.registrarContacto(nuevoContacto);

				adaptadorUsuario.modificarUsuario(usuarioActual);
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
	
	public void enviarMensajePorTelefono(String telefonoReceptor, String texto) {
        Usuario receptor = repoUsuarios.obtenerUsuarioPorTelefono(telefonoReceptor);
        usuarioActual.enviarMensaje(receptor, texto);

    }
	
	public void enviarMensajePorNombre(String nombreContacto, String texto) {
        // Buscar al contacto en la lista de contactos del usuario actual
        ContactoIndividual contacto = usuarioActual.getContactos().stream()
                .filter(c -> c instanceof ContactoIndividual) // Filtrar contactos individuales
                .map(c -> (ContactoIndividual) c) // Convertir a ContactoIndividual
                .filter(c -> c.getNombre().equalsIgnoreCase(nombreContacto)) // Buscar por nombre
                .findFirst()
                .orElse(null);

        // Enviar el mensaje al usuario asociado al contacto
        Usuario receptor = contacto.getUsuario();
        usuarioActual.enviarMensaje(receptor, texto);

    }
	
	public void enviarMensajeAGrupo(String nombreGrupo, String texto) {
		
        // Buscar el grupo en la lista de contactos del usuario actual
        Grupo grupo = usuarioActual.getContactos().stream()
                .filter(c -> c instanceof Grupo) // Filtrar solo los contactos tipo Grupo
                .map(c -> (Grupo) c) // Convertir a tipo Grupo
                .filter(g -> g.getNombre().equals(nombreGrupo)) // Buscar por nombre
                .findFirst()
                .orElse(null);

        // Enviar el mensaje de forma individual a cada miembro del grupo
        usuarioActual.enviarMensajeAGrupo(grupo, texto);

    }
	
	

}
