package tds.appchat.controlador;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import tds.appchat.modelo.Contacto;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Descuento;
import tds.appchat.modelo.DescuentoIntervaloFechas;
import tds.appchat.modelo.DescuentoMensaje;
import tds.appchat.modelo.Grupo;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.Premium;
import tds.appchat.modelo.RepositorioUsuarios;
import tds.appchat.modelo.Usuario;
import tds.appchat.persistencia.DAOException;
import tds.appchat.persistencia.FactoriaDAO;
import tds.appchat.persistencia.IAdaptadorContactoIndividualDAO;
import tds.appchat.persistencia.IAdaptadorGrupoDAO;
import tds.appchat.persistencia.IAdaptadorUsuarioDAO;
import tds.appchat.vista.VentanaLogin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class AppChat {
	private static AppChat unicaInstancia;
	private Usuario usuarioActual;
	private RepositorioUsuarios repoUsuarios;
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoIndividualDAO adaptadorContactoIndividual;
	private IAdaptadorGrupoDAO adaptadorGrupo;

	public static AppChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AppChat();
		//Haciendo el constructor privado solo habrá una única instancia.
		return unicaInstancia;
	}
	//Para que no se pueda crear fuera de esta clase
	private AppChat(){
		inicializarAdaptadores();
		inicializarRepositorios();
	}
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {}
		
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorContactoIndividual = factoria.getContactoDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
	}
	
	private void inicializarRepositorios() {
		repoUsuarios = RepositorioUsuarios.getUnicaInstancia();
	}
	
	////
	//Operaciones intermedias de acceso a atributos del usuario actual
	///
	
	public String getNombreUsuarioActual() {
		return usuarioActual.getNombre();
	}
	public String getTelefonoUsuarioActual() {
		return usuarioActual.getTelefono();
	}
	public List<Mensaje> obtenerChatsRecientesUsuario(){
		return usuarioActual.getRecibidos();
	}
	public List<Contacto> contactosUsuarioActual(){
		return usuarioActual.getContactos();
	}
	
	////
	// Lógica del programa
	////
	
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
		//TODO: Cambiar equals para favorecer patrón experto.
		Optional<Usuario> optUsr = repoUsuarios.getAllUsuarios().stream()
				.filter(usr -> usr.getTelefono().equals(telefono) && usr.isClave(contrasena))
				.findFirst();
		
		if(optUsr.isPresent()) {
			usuarioActual = optUsr.get();
			return true;
		}
		return false;
		
	}
	
	public void logoutUsuario() {
		usuarioActual = null;
	}
	
	
	public boolean existeTelefono(String telefono) {
		//TODO: Solución temporal: puede ser necesario tener que crear una funcion dentre de usuario para comprobar 
		boolean existe = repoUsuarios.getAllUsuarios().stream()
				.anyMatch(u->u.getTelefono().equals(telefono));
		return existe;
	}
	/**
	 * 
	 * @param nombre
	 * @param numTelefono
	 * @return
	 */
	public ContactoIndividual crearContacto(String nombre, String numTelefono) {
		// Si no tiene el contacto guardado lo guarda
		if (!usuarioActual.existeContacto(numTelefono)) {
			Optional<Usuario> usuarioOpt = repoUsuarios.getUsuarioNumTelf(numTelefono);
			
			if (usuarioOpt.isPresent()) {
				ContactoIndividual nuevoContacto = usuarioActual.crearContacto(nombre, usuarioOpt.get());
				
				//Se registra el contacto en la persistencia de contactos
				adaptadorContactoIndividual.registrarContacto(nuevoContacto);
				adaptadorUsuario.modificarUsuario(usuarioActual);
				
				return nuevoContacto;
			}
		}
		return null;
	}
	
	////
	// GESTIÓN DE GRUPOS:
	////
	
	/**
	 * Función para crear un grupo a partir de sus dos atributos imagen
	 * y nombre. Se encarga también de añadirlo al servidor de persistencia
	 * y a las estructuras de datos del usuario.
	 * 
	 * @param nombreGrupo
	 * @param imagen: imagen del grupo en formato url
	 * @return Se devuelve el grupo creado
	 * @throws IllegalArgumentException cuando existe otro grupo con ese nombre
	 */
	public Grupo crearGrupo(String nombreGrupo, String imagen) {
		//TODO: Se puede eliminar este ya que en la pestaña de crear grupo
		if(nombreGrupo.isEmpty()) {
			throw new IllegalArgumentException("El nombre del grupo no puede estar vacío.");
		}
		
		if(usuarioActual.hasGrupo(nombreGrupo)){
			throw new IllegalArgumentException("Ya existe un grupo con este nombre.");
		}
		// Se crea el grupo y se añade el grupo al usuario actual
		Grupo nuevoGrupo = usuarioActual.crearGrupo(nombreGrupo, imagen);
		
		// Para la persistencia
		adaptadorGrupo.registrarGrupo(nuevoGrupo);
		//Es necesario que se actualice en la bases de datos que contiene el 
		adaptadorUsuario.modificarUsuario(usuarioActual);
		return nuevoGrupo;
	}
	
	/**
	 * Se añade el contacto al grupo siempre que el usuario lo tenga en su
	 * estructura de contactos y este no sea miembro actual del grupo. Una
	 * vez comprobado se añade a los contactos del usuario y miembros del 
	 * grupo, también se modifica en el servidor de persistencia.
	 * 
	 * @param grupo
	 * @param contacto
	 * @return
	 */
	public boolean addContactoGrupo(Grupo grupo, ContactoIndividual contacto) {
		//usuarioActual.hasContactoIndividual(contacto) && !grupo.contieneContacto(contacto)
		if(usuarioActual.addIntegranteGrupo(grupo, contacto)){
			adaptadorGrupo.modificarGrupo(grupo);
		
			//Valor de retorno para que sea más facil a la hora de hacer la vista
			return true;
		}
		return false;
	}
	/**
	 * Se elimina el contacto de un grupo y se modifica en la persistencia
	 * @param grupo
	 * @param contacto
	 * @return
	 */
	public boolean eliminarContactoGrupo(Grupo grupo, ContactoIndividual contacto) {
		
		if(usuarioActual.eliminarIntegranteGrupo(grupo, contacto)){
			adaptadorGrupo.modificarGrupo(grupo);
			return true;
		}
		return false;
	}
	
	public boolean eliminarGrupo(Grupo grupo){
		
		if(usuarioActual.eliminarGrupo(grupo)) {
			adaptadorGrupo.borrarGrupo(grupo);
			return true;
		}
		return false;	
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
	/**
	 * Se obtiene el mejor descuento posible para el usuario
	 * @return Descuento con mayor reducción de precio
	 */
	public Descuento obtenerDescuento() {
		usuarioActual.comprobarDescuentos();
		//Si ninguno se ha insertado será null.
		return usuarioActual.getDescuento();
	}	
	
	/**
	 * El manejo de convertir en premium
	 */
	public void convertirPremium(){
		//Si no es premium
		if (!usuarioActual.isPremium()) {
			//Con esta funcion se pondrá el descuento que más beneficie
			obtenerDescuento();
			boolean pagoExitoso = usuarioActual.realizarPago();
			if (pagoExitoso) {
				usuarioActual.convertirPremium();
				adaptadorUsuario.modificarUsuario(usuarioActual);
			}
		}
	}
	
	//TODO: Mejorar. Versión simple que solo 
	public void convertirPDF(String ruta, List<Mensaje> conversacion) throws DocumentException {
		FileOutputStream archivo = null;
		try {
			archivo = new FileOutputStream(ruta);
		} catch (FileNotFoundException e) {	}
		
	    Document documento = new Document();
		PdfWriter.getInstance(documento, archivo);
		documento.open();
		for (Mensaje m : conversacion)
		{
			documento.add(new Paragraph(m.toString()));
		}

		documento.close();
	}
	/**
	 * 
	 * @return El precio actual de la suscripción Premium
	 */
	public double obtenerPrecioPremium(){
		//Se obtendrá de la clase premium
		return Premium.getPrecioPremium();
	}
	
	/**
	 * En el main del controlador se comienza el programa, se creará siempre
	 * una ventana login y a aprtir de esta se puede acceder a todo.
	 * Es necesario haber ejecutado antes el jar del servidor de persistencia
	 * sino el programa no funcionará
	 * 
	 * @param args Argumentos de inicio de programa, no se utilizan
	 */
	public static void main(String[] args) {
		VentanaLogin window = new VentanaLogin();
		window.setVisible(true);
	}
}
