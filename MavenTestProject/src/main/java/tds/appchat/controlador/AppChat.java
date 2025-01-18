package tds.appchat.controlador;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import tds.appchat.modelo.Contacto;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Descuento;
import tds.appchat.modelo.DescuentoIntervaloFechas;
import tds.appchat.modelo.DescuentoMensaje;
import tds.appchat.modelo.Grupo;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.Mensaje3;
import tds.appchat.modelo.Premium;
import tds.appchat.modelo.RepositorioUsuarios;
import tds.appchat.modelo.Usuario;
import tds.appchat.persistencia.AdaptadorMensajeTDS;
import tds.appchat.persistencia.DAOException;
import tds.appchat.persistencia.FactoriaDAO;
import tds.appchat.persistencia.IAdaptadorContacto;
import tds.appchat.persistencia.IAdaptadorContactoIndividualDAO;
import tds.appchat.persistencia.IAdaptadorGrupoDAO;
import tds.appchat.persistencia.IAdaptadorMensajeDAO;
import tds.appchat.persistencia.IAdaptadorUsuarioDAO;
import tds.appchat.vista.ContactoCellRenderer;
import tds.appchat.vista.VentanaLogin;


import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

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
	private IAdaptadorContacto adaptadorContacto;
	private IAdaptadorGrupoDAO adaptadorGrupo;
	private IAdaptadorMensajeDAO adaptadorMensaje;

	public static AppChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AppChat();
		//Haciendo el constructor privado solo habrá una única instancia. Patrón Singleton
		return unicaInstancia;
	}
	//Para que no se pueda crear fuera de esta clase
	private AppChat(){
		inicializarAdaptadores();
		inicializarRepositorios();
	}
	
	/**
	 * Funcion que inicializa los adaptadores para persistencia
	 */
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {}
		
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorContactoIndividual = factoria.getContactoDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
		//adaptadorContacto = factoria.getContactoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
	}
	
	private void inicializarRepositorios() {
		repoUsuarios = RepositorioUsuarios.getUnicaInstancia();
	}
	
	/**
	 * Se obtoeme un objeto Imagen a partir de un string.
	 * @param URL mediante una string
	 * @return
	 */
	public static Image obtenerImagenPerfilUrl(int altura, int anchura, String url) {
		//Si la imagen falla, será esta
		Image imagen = null;
		try {
			imagen = ImageIO.read(AppChat.class.getResource("/imagenes/usuario.png"));
		} catch (IOException e) { }
		
		try {
			URL urlImagen = new URL(url);
			imagen = ImageIO.read(urlImagen);
			imagen = imagen.getScaledInstance(altura, anchura, Image.SCALE_SMOOTH);
		} catch (MalformedURLException e) {
			//Que no haga nada
		} catch (IOException e) {
			//Que no haga nada
			e.printStackTrace();
		} catch (Exception e){
			//Que no haga nada, la imagen será guardada
		}
		return imagen;
	}
	
	////
	//Operaciones intermedias de acceso a atributos del usuario actual
	///
	/**
	 * @return devuelve el nombre del usuario actual
	 */
	public String getNombreUsuarioActual() {
		return usuarioActual.getNombre();
	}
	/**
	 * @return devuelve el número de teléfono del usuario actual
	 */
	public String getTelefonoUsuarioActual() {
		return usuarioActual.getTelefono();
	}
//	/**
//	 * @return devuelve los chats recientes del usuario actual
//	 */
//	public List<Mensaje3> obtenerChatsRecientesUsuario(){
//		return usuarioActual.obtenerUltimosChats();
//	}
	
//	/**
//	 * Se obtiene una lista con los últimos mensajes que obtiene de cada contacto
//	 * 
//	 * @return LinkedList<Mensaje> de los últimos mensajes recibidos por cada contacto
//	 */
//	public List<Mensaje3> obtenerUltimosChatsUsuario(){
//		return usuarioActual.obtenerUltimosChats();
//	}
	
	/**
	 * @return devuelve la lista con todos los contactos del usuario actual
	 */
	public List<Contacto> contactosUsuarioActual(){
		return usuarioActual.getContactos();
	}
	public String [] contactosUsuarioActualString() {
		String[] nombres = AppChat.getUnicaInstancia().contactosUsuarioActual().stream()
		.map(a -> a.getNombre()).toArray(String[]::new);
		return nombres;
	}
	public Contacto [] contactosUsuarioActualArray() {
		return contactosUsuarioActual().stream().toArray(Contacto[]::new);
	}
//	/**
//	 * Se obtienen todos los mensajes a partir del mensaje del cell renderer
//	 * @param m
//	 * @return
//	 */
//	public List<Mensaje3> obtenerConversacionDesdeMensaje(Mensaje3 m){
//		//Si el emisor es el usuario actual entonces se devolverá el contacto actual
//		return usuarioActual.getConversacionFromMensaje(m);
//	}
//	
//	public List<Mensaje3> obtenerConversacionDesdeContacto(Contacto c){
//		//c.getMensajesRecibidos(null)
//		return usuarioActual.getConversacionFromContacto(c);
//	}
//	
//	public List<Mensaje3> obtenerConversacionDesdeTelefono(String t){
//		//c.getMensajesRecibidos(null)
//		return usuarioActual.getConversacionFromTelefono(t);
//	}
	
	public Contacto obtenerContactoDesdeTelefono(String t) {
		return usuarioActual.getContactoDesdeTelefono(t);
	}
	
	
	////
	////
	////
	
	
	/**
	 * Se obtiene una imagen obtiene de la url de los atributos de usuario actual
	 * @return Image de la foto de perfil del usuario.
	 */
	public Image getImagenUsuarioActual() {
		return obtenerImagenPerfilUrl(35,35,usuarioActual.getImagenPerfilUrl());
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
	/**
	 * Hace login de un usuario
	 * 
	 * @param telefono
	 * @param contrasena
	 * @return
	 */
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
	
	//////////////////////////
	///BUSQUEDA DE MENSAJES///
	//////////////////////////
	
	public List<Mensaje3> getMensajes(Contacto contacto) {
		// Si la conversacion es conmigo mismo es suficiente con mostrar mis mensajes
		if (contacto instanceof ContactoIndividual && !((ContactoIndividual) contacto).isUser(usuarioActual)) {
			return Stream
					.concat(contacto.getMensajesEnviados().stream(),
							contacto.getMensajesRecibidos(Optional.of(usuarioActual)).stream())
					.sorted().collect(Collectors.toList());
		} else {
			// Dentro de los enviados estan contenidos todos los mensajes
			return contacto.getMensajesEnviados().stream().sorted().collect(Collectors.toList());
		}
	}
	
	public List<Mensaje3> buscarMensajes(String telefono, String contacto, String text) {
		// Recupero los mensajes que he enviado
		List<Mensaje3> mensajes = AppChat.getUnicaInstancia().contactosUsuarioActual().stream()
				.flatMap(c -> AppChat.getUnicaInstancia().getMensajes(c).stream()).collect(Collectors.toList());

		return mensajes.stream()
				.filter(m -> telefono == null || telefono.isEmpty() || m.getTlfEmisor().equals(telefono))
				.filter(m -> text == "" || m.getTexto().contains(text))
				.collect(Collectors.toList());
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
	public boolean esTelefonoContacto(String telefono) {
		return usuarioActual.hasTelefono(telefono);
	}
	public Usuario obtenerUsuarioDesdeTelefono(String telefono) {
		return repoUsuarios.getUsuarioNumTelf(telefono).get();
	}
//	public String obtenerTelefonoDesdeMensaje(Mensaje3 m) {
//		return usuarioActual.getTelefonoDesdeMensaje(m);
//	}
	
	public Contacto obtenerContactoMensaje(Mensaje m) {
		return usuarioActual.getContactoDesdeTelefono(getTelefonoUsuarioActual());
	}
	/**
	 * 
	 * @return
	 */
	public String obtenerNombreContactoDesdeTelefono(String telefono) {
		Contacto contacto = usuarioActual.getContactoDesdeTelefono(telefono); 
		if (contacto == null)
			return telefono;
		return contacto.getNombre();
	}
	
	////////////////////////
	// Envío de mensajes: //
	////////////////////////
	
//	public boolean esMensajeEmisor(Mensaje3 m) {
//		return usuarioActual.esEmisor(m);
//	}
//	public Mensaje3 enviarMensajeGrupo(Grupo grupo, String texto) {
//		Mensaje3 m = usuarioActual.enviarMensajeGrupo(grupo, texto);
//		adaptadorMensaje.registrarMensaje(m);
//		adaptadorUsuario.modificarUsuario(usuarioActual);
//		return m;
//	}
//	
//	public Mensaje3 enviarMensajeContactoIndividual(ContactoIndividual contacto, String texto) {
//		Mensaje3 m = usuarioActual.enviarMensajeContacto(contacto, texto);
//		adaptadorMensaje.registrarMensaje(m);
//		adaptadorUsuario.modificarUsuario(usuarioActual);
//		adaptadorUsuario.modificarUsuario(contacto.getUsuario());
//		return m;
//	}
	
//	/**
//	 * Se envia un mensaje a un contacto:
//	 * 
//	 * @param contacto
//	 * @param texto
//	 * @return
//	 */
//	public Mensaje3 enviarMensaje(Contacto contacto, String texto) {
//		Mensaje3 mensaje = null;
//		//adaptadorMensaje.registrarMensaje(mensaje);
//		//adaptadorUsuario.modificarUsuario(usuarioActual);
//		
//		if (contacto instanceof ContactoIndividual)
//			mensaje = enviarMensajeContactoIndividual((ContactoIndividual) contacto, texto);
//		else if (contacto instanceof Grupo)
//			mensaje = enviarMensajeGrupo((Grupo) contacto, texto);
//		return mensaje;
//		//Si se quisiera guardar también en contactos se podría meter aquí también.
//	}
//	
//	/**
//	 * Se envia un mensaje a un usuario desconocido.
//	 * 
//	 * @param telefono
//	 * @param texto
//	 * @return Mensaje enviado
//	 */
//	public Mensaje3 enviarMensaje(String telefono, String texto) {
//		//No tendrá e
//		System.out.println("Se quiere enviar un mensaje al teléfono: " + telefono);
//		Mensaje3 m = usuarioActual.enviarMensajeTelefono(telefono, texto);
//		adaptadorMensaje.registrarMensaje(m);
//		adaptadorUsuario.modificarUsuario(usuarioActual);
//		adaptadorUsuario.modificarUsuario(repoUsuarios.getUsuarioNumTelf(telefono).get());
//		return m;
//	}
//	
//	public Mensaje3 enviarMensajeContactoDesconocido(String telefono, Mensaje3 m) {
//		Optional<Usuario> usr = repoUsuarios.getUsuarioNumTelf(telefono);
//		if (usr.isPresent()) {
//			usr.get().recibirMensaje(m);
////			adaptadorMensaje.registrarMensaje(m);
////			adaptadorUsuario.modificarUsuario(usuarioActual);
////			adaptadorUsuario.modificarUsuario(usr.get());
//			return m;
//		}
//		return null;
//	}
//	public void enviarMensaje(Contacto contacto, int emoji) {
//		Mensaje mensaje = new Mensaje(emoji, LocalDateTime.now(), usuarioActual, contacto);
//		contacto.sendMessage(mensaje);
//		adaptadorMensaje.registrarMensaje(mensaje);
//
//		if (contacto instanceof ContactoIndividual) {
//			adaptadorContactoIndividual.modificarContacto((ContactoIndividual) contacto);
//		} else {
//			adaptadorGrupo.modificarGrupo((Grupo) contacto);
//		}
//	}
	/*
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
	*/
	
	/**
	 * Funcion para conocer si Usuario es emisor del mensaje de parámetro
	 * 
	 * @param Mensaje
	 * @return True si el usuario actual es el emisor del mensaje
	 */
	public boolean esUsuarioEmisor(Mensaje m) {
		return usuarioActual.esEmisor(m);
	}
	
	public Usuario obtenerUsuarioDesdeMensaje(Mensaje m) {
		return esUsuarioEmisor(m) ? m.getReceptor() : m.getEmisor();
	}
	public boolean esUsuarioContacto(Usuario u) {
		return usuarioActual.esUsuarioContacto(u);
	}
	public Contacto obtenerContactoUsuario(Usuario u) {
		return usuarioActual.obtenerContactoUsuario(u);
	}
	/**
	 * Se envia el texto como mensaje a un usuairo
	 * @param usuario
	 * @param texto
	 * @return mensaje enviado
	 */
	public Mensaje enviarMensaje(Usuario u, String texto) {
		Mensaje m = usuarioActual.enviarMensaje(u, texto);
		
		//Persistencia del nuevo mensaje para emisor receptor y mensaje
		adaptadorMensaje.registrarMensaje(m);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		adaptadorUsuario.modificarUsuario(u);
		return m;
	}
	
	/**
	 * Se envia un mensaje a un grupo
	 * @param g
	 * @param texto
	 * @return Mensaje que almacena el emisor como enviado al grupo
	 */
	public Mensaje enviarMensajeGrupo(Grupo g, String texto) {
		Mensaje mensajeEmisor = new Mensaje(texto, LocalDateTime.now(), usuarioActual, g);
		//Cada mensaje que se envia a cada integrante del grupo
		List<Mensaje> mensajesEnviados = usuarioActual.enviarMensajeGrupo(mensajeEmisor,g,texto);
		adaptadorMensaje.registrarMensaje(mensajeEmisor);
		//Se obtiene una lista para facilitar la persistencia de mensajes
		mensajesEnviados.stream().forEach(m -> {
			adaptadorMensaje.registrarMensaje(m);
			//Por cada mensaje se modifica su receptor
			adaptadorUsuario.modificarUsuario(m.getReceptor());
		});
		adaptadorUsuario.modificarUsuario(usuarioActual);
		return mensajeEmisor;
	}
	
	public List<Mensaje> obtenerChatsRecientes(){
		return usuarioActual.obtenerConversacionesRecientes();
	}
	/**
	 * Devuelve la conversacion que se tiene con un usuario
	 * @param u
	 * @return
	 */
	public List<Mensaje> obtenerConversacion(Usuario u){
		return usuarioActual.obtenerConversacionUsuario(u);
	}
	
	/**
	 * 
	 */
	public List<Mensaje> obtenerConversacionGrupo(Grupo g){
		return usuarioActual.obtenerConversacionGrupo(g);
	}
	
	//////////////////
	/// Descuentos 	//
	//////////////////
	
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
	
	//TODO: Mejorar. Versión simple que solo pone los mensajes así
	public void convertirPDF(String ruta, List<Mensaje3> conversacion) throws DocumentException {
		FileOutputStream archivo = null;
		try {
			archivo = new FileOutputStream(ruta);
		} catch (FileNotFoundException e) {	}
		
	    Document documento = new Document();
		PdfWriter.getInstance(documento, archivo);
		documento.open();
		for (Mensaje3 m : conversacion) {
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
