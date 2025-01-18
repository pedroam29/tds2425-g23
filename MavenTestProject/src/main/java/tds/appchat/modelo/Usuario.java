package tds.appchat.modelo;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

import javax.imageio.ImageIO;

import tds.appchat.controlador.AppChat;


public class Usuario {
	private int codigo;
	private final String nombre;
	private final Date fechaNacimiento;
	private final String email;
	private final String telefono;
	private final String contrasena;
	private String imagenPerfilUrl;
	private String saludo;
	
	//Generacion de descuentos
	private final LocalDate fechaRegistro;
	private Descuento descuento;
	//Tipo de Rol
	private boolean premium;
	private RolUsuario rolUsuario;
	
	//Una opción para facilitar la usabilidad es utilizar, cada vez que se quiera enviar un mensaje
	private HashMap<String, List<Mensaje>> mensajesPorUsuario;
	private HashMap<String, List<Mensaje>> mensajesGrupos;
	
	private List<Mensaje3> mensajesRecibidos;
	private List<Mensaje3> mensajesEnviados;
	private List<Contacto> contactos;
	
	public Usuario(String nombre, String telefono, String contrasena,Date fechaNacimiento, String imagenPerfilUrl, String saludo, String email) {
		this.codigo = 0;
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.imagenPerfilUrl = imagenPerfilUrl;
		this.saludo = saludo;
		this.mensajesRecibidos=new LinkedList<Mensaje3>();
		this.mensajesEnviados=new LinkedList<Mensaje3>();
		this.contactos=new LinkedList<Contacto>();
		this.descuento = null;
		this.fechaRegistro = LocalDate.now();
		this.premium = false;
		this.mensajesPorUsuario = new HashMap<String, List<Mensaje>>();
		this.mensajesGrupos = new HashMap<String, List<Mensaje>>();
	}
	
	public Usuario(String nombre) {
		this.codigo = 0;
		this.nombre=nombre;
		this.fechaNacimiento = new Date();
		this.email = "";
		this.telefono = "";
		this.contrasena = "";
		this.premium = false;
		this.fechaRegistro = LocalDate.now();
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public ContactoIndividual getContactoIndividual(Usuario otroUsuario) {
	    return contactos.stream()
	            .filter(contacto -> contacto instanceof ContactoIndividual) // Filtra solo contactos individuales
	            .map(contacto -> (ContactoIndividual) contacto)             // Mapea a ContactoIndividual
	            .filter(contactoInd -> contactoInd.getUsuario().equals(otroUsuario)) // Compara usuarios
	            .findFirst()                                                // Busca el primero que coincida
	            .orElse(null);                                              // Devuelve null si no encuentra nada
	}
	
	public String getImagenPerfilUrl() {
		return imagenPerfilUrl;
	}
	public List<Mensaje3> getEnviados() {
		return new LinkedList<Mensaje3>(mensajesEnviados);
	}
	
	public void setEnviados(List<Mensaje3> enviados) {
		this.mensajesEnviados = new LinkedList<Mensaje3>(enviados);
	}
	
	public List<Mensaje3> getRecibidos() {
		return new LinkedList<Mensaje3>(mensajesRecibidos);
	}
	
	public void setRecibidos(List<Mensaje3> recibidos) {
		this.mensajesEnviados = new LinkedList<Mensaje3>(recibidos);
	}
	
	public List<Contacto> getContactos() {
		return new LinkedList<Contacto>(contactos);
	}
	
	public void setContactos(List<Contacto> contactos) {
		this.contactos = new LinkedList<Contacto>(contactos);
	}

	public HashMap<String,List<Mensaje>> getMensajesPorUsuario(){
		return mensajesPorUsuario;
	}
	
	public void setMensajesPorUsuario(HashMap<String,List<Mensaje>> mensajesPorUsuario ){
		this.mensajesPorUsuario = mensajesPorUsuario;
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
	
	public LocalDate getFechaRegistro(){
		return fechaRegistro;
	}
	
	public Descuento getDescuento(){
		return descuento;
	}
	/**
	 * Función para persistencia
	 * @return
	 */
	public String getDescuentoID() {
		if (descuento == null)
			return Descuento.ID_NO_DESCUENTO;
		return descuento.toString();
	}
	
	/**
	 * Devuelve la Imagen a partir de la URL de los atributos
	 * @return imagen de perfil
	 */
	public Image getImagen()
	{
		Image imagen = null;
		try {
			imagen = (Image) ImageIO.read(Usuario.class.getResource("/imagenes/flecha-inv.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public String getSaludo() {
		return saludo;
	}
	
	public boolean isPremium(){
		return premium;
	}
	public void setPremium(boolean p) {
		this.premium = p;
	}
	public void setDescuento(Descuento d) {
		this.descuento = d;
	}
	
	public void addContacto(ContactoIndividual c) {
		this.contactos.add(c);
	}
	
	public boolean isClave(String clave){
		return this.contrasena.equals(clave);
	}
	public boolean isTelefono(String telefono){
		return this.telefono.equals(telefono);
	}

	public boolean hasContactoIndividual(ContactoIndividual cont) {
		return contactos.stream().anyMatch(c -> c instanceof ContactoIndividual && c.equals(cont));
	}
	
	public boolean hasTelefono(String telefono) {
		return contactos.stream()
				.anyMatch(c -> c instanceof ContactoIndividual && ((ContactoIndividual) c).isTelefono(telefono));
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	/// PROVISIONAL TODO: BORRAR ANTES DE MANDAR
	////////////////////////////////////////////////////
	/**
	 * Se envia un mensaje a partir de un usuario
	 * @param Usuario usuario receptor
	 * @param Texto cuerpo del mensaje
	 * @return
	 */
	public Mensaje enviarMensaje(Usuario u, String texto) {
		Mensaje m = new Mensaje(texto, LocalDateTime.now() , this, u);
		u.recibirMensaje(m);
		if (mensajesPorUsuario.containsKey(u.getTelefono()))
			mensajesPorUsuario.get(u.getTelefono()).add(m);
		else {
			List<Mensaje> mensajes = new LinkedList<Mensaje>();
			mensajes.add(m);
			mensajesPorUsuario.put(u.getTelefono(), mensajes);
		}
		return m;
	}
	
	public List<Mensaje> enviarMensajeGrupo(Mensaje m, Grupo g, String texto){
		return null;
	}
	
	/**
	 * Se recibe un mensaje, que irá añadido a la lista de recibidos
	 * 
	 * @param mensaje
	 */
	public void recibirMensaje(Mensaje mensaje) {
		
		if (!mensajesPorUsuario.containsKey(mensaje.getEmisor().getTelefono())) {
			List<Mensaje> mensajes = new LinkedList<Mensaje>();
			mensajes.add(mensaje);
			mensajesPorUsuario.put(mensaje.getEmisor().getTelefono(), mensajes);
			return;
		}
		mensajesPorUsuario.get(mensaje.getEmisor().getTelefono()).add(mensaje);
	}
	
	/**
	 * Se obtiene las conversaciones recientes
	 * @return
	 */
	public List<Mensaje> obtenerConversacionesRecientes(){
		
		//Se supone que los mensajes en estas listas está insertado en orden
		return mensajesPorUsuario.values().stream()
			    .map(t -> t.get(t.size() - 1)) // Obtenemos el último valor sin hacer cast
			    .collect(Collectors.toList());		  //Se almacena en una lista
	}
	
	/**
	 * Se obtiene una conversacion con un usuario solamente obteniendo
	 * el valor de la calve usuario en el mapa de 
	 * 
	 * @param Usuario
	 * @return Lista de mensajes de la conversacion con el usuario
	 */
	public List<Mensaje> obtenerConversacionUsuario(Usuario u){
		//Se supondrá que la lista está en orden, ya que se insertan
		if (mensajesPorUsuario.containsKey(u.getTelefono()))
			return mensajesPorUsuario.get(u.getTelefono());
		//En caso de que no esté el usuario en el mensaje se pasará una lista vacia
		return new LinkedList<Mensaje>();
	}
	/**
	 * Se obtiene la conversacion con un grupo
	 * 
	 * @param grupo
	 * @return Lista de mensajes de la conversacion
	 */
	public List<Mensaje> obtenerConversacionGrupo(Grupo g){
		//TODO: Por implementar
		return null;
	}
	/**
	 * Se obtiene un contacto a partir de un usuario
	 * @param usuario
	 * @return Contacto
	 */
	public Contacto obtenerContactoUsuario(Usuario u) {
		return contactos.stream()
				.filter(c -> c instanceof ContactoIndividual
						&& ((ContactoIndividual) c).isTelefono(u.getTelefono())) //Contacto que sea ContactoIndividual y qye 
				.findFirst().get();
	}
	
	public boolean esUsuarioContacto(Usuario u) {
		return contactos.stream().anyMatch(c -> c instanceof ContactoIndividual
				&& ((ContactoIndividual) c).isTelefono(u.getTelefono()));
	}
	public boolean esEmisor(Mensaje m) {
		return m.esEmisor(this);
	}
	
	//////////////
	// MENSAJES //
	//////////////
//
//	/**
//	 * Se obtiene un teléfono desde Mensaje
//	 * @param m
//	 * @return
//	 */
//	public String getTelefonoDesdeMensaje(Mensaje3 m) {
//		//Se devuelve el que no sea 
//		String tlf = isTelefono(m.getTlfEmisor()) ? m.getTlfReceptor() : m.getTlfEmisor(); 
//		
//		if (isTelefono(m.getTlfEmisor()))
//			return m.getTlfReceptor();
//		else
//			return m.getTlfEmisor();
//	}
//	/**
//	 * Devuelve si para un mensaje, es este Usuario el emisor
//	 * 
//	 * @param m
//	 * @return
//	 */
//	public boolean esEmisor(Mensaje3 m) {
//		return m.esEmisor(telefono);
//	}
//	
//	
//	public ContactoIndividual getContactoDesdeMensaje(Mensaje3 m) {
//		return getContactoDesdeTelefono(getTelefonoDesdeMensaje(m));
//	}
//	
//	
//	/**
//	 * 
//	 * 
//	 * @return
//	 */
//	public List<Mensaje3> getConversacionGrupo(String nombre){
//		//Grupo grupo = (Grupo) contactos.stream().filter(g -> g instanceof Grupo && g.isNombre(nombre)).findFirst().get();
//		//Se toman todos los mensajes de este grupo que 
//		List<Mensaje3> mensajesGrupo = mensajesEnviados.stream()
//				.filter(m -> m.esNombreGrupo(nombre))
//				.sorted((m1, m2) -> m2.getFechaHora().compareTo(m1.getFechaHora()))
//				.collect(Collectors.toList());
//		return mensajesGrupo;
//	}
//	
//	public List<Mensaje3> getConversacionFromTelefono(String telefono){
//		List<Mensaje3> conversacion = new LinkedList<Mensaje3>();
//		//Se supone que el teléfono está registrado en el
//		//Todos los mensajes enviados cuyo receptor sea el buscado
//		List<Mensaje3> enviadosConversacion = mensajesEnviados.stream().filter(m -> m.esReceptor(telefono)).collect(Collectors.toList());
//		//Todos los mensajes recibidos cuyo emiser sea el buscado
//		List<Mensaje3> recibidosConversacion = mensajesRecibidos.stream().filter(m -> m.esEmisor(telefono)).collect(Collectors.toList());
//		
//		conversacion.addAll(recibidosConversacion);
//		conversacion.addAll(enviadosConversacion);
//		
//		conversacion.stream().sorted((m1, m2) -> m2.getFechaHora().compareTo(m1.getFechaHora())).collect(Collectors.toList());
//		return conversacion;
//	}
//	
//	public List<Mensaje3> getConversacionFromContacto(Contacto c){
//		if (c instanceof Grupo)
//			return getConversacionGrupo(c.getNombre());
//		return getConversacionFromTelefono(((ContactoIndividual) c).getTelefono());
//	}
//	/**
//	 * Dado un mensaje se obtendrá su 
//	 * @param mensaje
//	 * @return
//	 */
//	public List<Mensaje3> getConversacionFromMensaje(Mensaje3 mensaje) {
//		if (mensaje.isGrupo())
//			return getConversacionGrupo(mensaje.getTlfReceptor());
//		return getConversacionFromTelefono(getTelefonoDesdeMensaje(mensaje));
//	}
//	
//	/**
//	 * Se devuelve una lista con el último mensaje por
//	 * cada conversación.
//	 * 
//	 * @return
//	 */
//	public List<Mensaje3> obtenerUltimosChats(){
//		//Se tendrá un conjunto para ir almacenando
//		Set<String> telefonos = new HashSet<String>();
//		Set<String> grupos = new HashSet<String>();
//		
//		//Se van a recorrer todos los teléfonos e insertando en un set
//		List<Mensaje3> mensajes = new LinkedList<Mensaje3>(mensajesEnviados);
//		mensajes.addAll(mensajesRecibidos);
//		
//		List<Mensaje3> mensajesDevolver = mensajes.stream().filter(m -> {
//			if (m.isGrupo()) {
//				if(grupos.contains(m.getNombreGrupo()))
//					return false;
//				else {
//					grupos.add(m.getNombreGrupo());
//					return true;
//				}
//			} else {
//				if (telefonos.contains(getTelefonoDesdeMensaje(m)))
//					return false;
//				else {
//					telefonos.add(getTelefonoDesdeMensaje(m));
//					return true;
//				}
//			}
//		})
//		.collect(Collectors.toList());
//		
//		return mensajesDevolver;
//	}
//	
//	/////////////////////////
//	/// ENVÍO DE MENSAJES ///
//	/////////////////////////
//	
//	/**
//	 * Se llama cuando se quiere añadir un mensaje a la
//	 * lista de enviados
//	 * 
//	 * @param mensaje
//	 */
//	public void addMensajeEnviados(Mensaje3 mensaje) {
//		mensajesEnviados.add(mensaje);
//	}
//	
//	
//	/**
//	 * Envía un mensaje a un grupo
//	 * 
//	 * @param grupo
//	 * @param texto
//	 * @return mensaje enviado
//	 */
//	public Mensaje3 enviarMensajeGrupo(Grupo grupo, String texto) {
//		Mensaje3 mensaje = new Mensaje3(texto, LocalDateTime.now(), telefono, grupo.getNombre());
//		mensaje.setGrupo(true); //Es un mensaje a grupos
//		addMensajeEnviados(mensaje);
//		
//		//Ahora por cada miembro del grupo se le enviará el mensaje individualmente
//		//TODO: Manera para que sea el grupo el que se encargue de enviar el mensaje a los contactos
//			for (ContactoIndividual c : grupo.getMiembros()) {
//				enviarMensajeContacto(c, texto);
//			}
//		return mensaje;
//		}
//	
//	/**
//	 * Envía un mensaje a un ContactoIndividual
//	 * 
//	 * @param grupo
//	 * @param texto
//	 * @return mensaje enviado
//	 */
//	public Mensaje3 enviarMensajeContacto(ContactoIndividual contacto, String texto) {
//		Mensaje3 mensaje = new Mensaje3(texto, LocalDateTime.now(), telefono, contacto.getTelefono());
//		contacto.enviarMensaje(mensaje);
//		addMensajeEnviados(mensaje);
//		return mensaje;
//	}
//	
//	/**
//	 * Se envía un mensaje a un contacto
//	 * 
//	 * @param contacto
//	 * @param texto
//	 * @return mensaje enviado
//	 */
//	public Mensaje3 enviarMensaje(Contacto contacto, String texto) {
//		if (contacto instanceof ContactoIndividual)
//			return enviarMensajeContacto((ContactoIndividual) contacto, texto);
//		else //if (contacto instanceof Grupo)
//			return enviarMensajeGrupo((Grupo) contacto, texto);
//		
//		//Caso imposible de que se haya pasado un Objeto contacto que no sea ni individual ni grupo.
//	}
//	
//	/**
//	 * Se envía el mensaje desde un teléfono
//	 * 
//	 * @param telefono
//	 * @param texto
//	 * @return Mensaje enviado
//	 */
//	public Mensaje3 enviarMensajeTelefono(String telefono, String texto){
//		if(existeContacto(telefono))
//			return enviarMensaje(getContactoDesdeTelefono(telefono), texto);
//		//Se crea el mensaje en caso de que no esté el teléfono registrado en contactos
//		Mensaje3 m = new Mensaje3(texto, LocalDateTime.now(), this.telefono, telefono);
//		addMensajeEnviados(m);
//		return AppChat.getUnicaInstancia().enviarMensajeContactoDesconocido(telefono, m);
//	}
//	
//	///////
//	// Gestión de contactos
//	///////
	
	/**
	 * 
	 * @param telefono
	 * @return true si el telefono tiene un contacto asociado en este 
	 */
	public boolean existeContacto(String telefono) {
		return contactos.stream().anyMatch(c -> c instanceof ContactoIndividual && ((ContactoIndividual) c).isTelefono(telefono));
	}

	/**
	 * Dado un telefono se obtiene su contacto
	 * 
	 * @param telefono
	 * @return
	 */
	public ContactoIndividual getContactoDesdeTelefono(String telefono) {
		if (!existeContacto(telefono))
			return null;
		ContactoIndividual contacto = (ContactoIndividual) contactos.stream()
				.filter(c -> (c instanceof ContactoIndividual) & ((ContactoIndividual) c).isTelefono(telefono))
				.findFirst().get();
		return contacto;
	}
	
	/**
	 * Se crea un contacto y se añade a la lista de contactos del usuario
	 * 
	 * @param nombre
	 * @param usuarioActual
	 * @return devuelve el contacto creado
	 */
	public ContactoIndividual crearContacto(String nombre, Usuario usuarioActual) {

		ContactoIndividual nuevoContacto = new ContactoIndividual(nombre, usuarioActual.getTelefono(), usuarioActual);
		addContacto(nuevoContacto);
		return nuevoContacto;
	}
	
	
	///////////////////////
	// Gestión de grupos //
	///////////////////////
	
	public Grupo crearGrupo(String nombreGrupo, String imagen) {

		Grupo nuevoGrupo = new Grupo(nombreGrupo, imagen);
		addGrupo(nuevoGrupo);
		return nuevoGrupo;
	}
	
	public boolean addIntegranteGrupo(Grupo g, ContactoIndividual c) {
		if(contactos.contains(c) && contactos.contains(g) && !g.contieneContacto(c)) {
			g.addMiembro(c);
			return true;
		}
		return false;
	}
	
	public boolean eliminarIntegranteGrupo(Grupo g, ContactoIndividual c) {
		if(contactos.contains(c) && contactos.contains(g) &&  g.contieneContacto(c)) {
			g.eliminarMiembro(c);
			return true;
		}
		return false; 
	}
	
	public boolean eliminarGrupo(Grupo g) {
		if (contactos.contains(g)) {
			contactos.remove(g);
			return true;
		}
		return false; 
	}
	
	public Grupo getGrupoFromNombre(String nombre) {
		//
		Grupo grupo = (Grupo) contactos.stream().filter(g -> (g instanceof Grupo && g.getNombre().equals(nombre)))
				.findFirst().get();
		return grupo;
	}
	
	public boolean hasGrupo(String nombreGrupo) {
		return contactos.stream().anyMatch(g -> g instanceof Grupo && g.getNombre().equals(nombreGrupo));
	}
	
	public void addGrupo(Grupo g) {
		contactos.add(g);
	}
	
	/*
	public void enviarMensaje(Contacto receptor, String contenido) {
        // Crear mensaje y agregarlo a las listas de mensajes
        Mensaje mensaje = new Mensaje(contenido, this, receptor);
        this.mensajesEnviados.add(mensaje);
        receptor.recibirMensaje(mensaje);
 
    }
	
	private void recibirMensaje(Mensaje mensaje) {
		this.mensajesRecibidos.add(mensaje);
	}
	
	public void enviarMensajeAGrupo(Grupo grupo, String contenido) {
		for(ContactoIndividual contacto : grupo.getMiembros()) {
			Usuario receptor = contacto.getUsuario();
			Mensaje mensaje = new Mensaje(contenido, this, receptor);
			this.mensajesEnviados.add(mensaje);
			receptor.recibirMensaje(mensaje);
		}
	}
	*/
	
	//////////////////////////
	// Descuentos y premium //
	//////////////////////////
	
	public long getTotalMensajesEnviadosUltimoMes()
	{
		//TODO: ¿Debería comprobarse si está en el mismo mes para el descuento la clase
		// Mensaje o el Usuario?
		long total = mensajesEnviados.stream()
				.filter(m -> m.getFechaHora().isBefore(LocalDateTime.now().plusMonths(1)))
				.count();
		
		return total;
	}
	
	public void comprobarDescuentos() {
		//TODO: Posibilidad de hacer la comprobación de descuentos
		//En otra clase, aplicando patrón para que la clase usuario
		//No tenga que conocer el número de clases que hay, solamente reciba su contacto
		
		if (DescuentoMensaje.esUsuarioAptoDescuento(this))
			this.nuevoDescuento(new DescuentoMensaje());
		if (DescuentoIntervaloFechas.esUsuarioAptoDescuento(this))
			nuevoDescuento(new DescuentoIntervaloFechas());
	}
	
	public void nuevoDescuento(Descuento descuento){
		//Se supondrá que un descuento es mejor que otro siempre que
		//De un precio menor en el momento en el que se
		if (this.descuento == null)
			this.descuento = descuento;
		if (descuento.calcularDescuento(Premium.getPrecioPremium()) < this.descuento.calcularDescuento(Premium.getPrecioPremium()))
			this.descuento = descuento;
	}
	
	//La realización del pago queda fuera del dominio de esta aplicación
	//Se supondrá pago correcto para todos los casos.
	public boolean realizarPago() {
		return true;
	}
	
	public void convertirPremium(){
		this.premium = true;
		this.rolUsuario = new Premium(descuento);
	}
	
	@Override
	public String toString() {
		
		return "Nombre: " + nombre + "\nContactos: " + contactos;
	}
	
}
