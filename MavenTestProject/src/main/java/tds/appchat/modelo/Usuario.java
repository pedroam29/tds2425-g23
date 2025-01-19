package tds.appchat.modelo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
	
	private final static URL URL_DEFAULT = Usuario.class.getResource("/imagenes/usuario.png");
	
	
	private int codigo;
	private final String nombre;
	private final Date fechaNacimiento;
	private final String telefono;
	private final String contrasena;
	private String imagenPerfilUrl;
	private String saludo;
	private URL imagenPerfil;
	
	//Generacion de descuentos
	private final LocalDate fechaRegistro;
	private Descuento descuento;
	//Tipo de Rol
	private boolean premium;
	private RolUsuario rolUsuario;
	
	//Una opción para facilitar la usabilidad es utilizar, cada vez que se quiera enviar un mensaje
	private HashMap<String, List<Mensaje>> mensajesPorUsuario;
	private HashMap<Grupo, List<Mensaje>> mensajesGrupos;
	
	private List<Contacto> contactos;
	
	public Usuario(String nombre, String telefono, String contrasena,Date fechaNacimiento, URL imagenPerfil, String saludo) {
		this.codigo = 0;
		this.nombre = nombre;
		this.telefono = telefono;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.imagenPerfil = imagenPerfil;
		this.saludo = saludo;

		this.contactos=new LinkedList<Contacto>();
		this.descuento = null;
		this.fechaRegistro = LocalDate.now();
		this.rolUsuario = new Normal();
		this.premium = false;
		this.mensajesPorUsuario = new HashMap<String, List<Mensaje>>();
		this.mensajesGrupos = new HashMap<Grupo, List<Mensaje>>();
	}
	
	public Usuario(String nombre) {
		this.codigo = 0;
		this.nombre=nombre;
		this.fechaNacimiento = new Date();
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
	
	public URL getImagenPerfil() {
		return imagenPerfil;
	}
	public void setImagenPerfil(URL u) {
		this.imagenPerfil = u;
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
	
	public HashMap<Grupo, List<Mensaje>> getMensajesGrupos(){
		return mensajesGrupos;
	}
	
	public void setMensajesGrupos(HashMap<Grupo, List<Mensaje>> mensajesGrupos){
		this.mensajesGrupos = mensajesGrupos;
	}
	
	public String getNombre() {
		return nombre;
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
	
	public void setRolUsuario(RolUsuario rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	public RolUsuario getRolUsuario() {
		return rolUsuario;
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
	public Image getImagen(int tam) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(imagenPerfil);
		} catch (IOException e) {
			try {
				image = ImageIO.read(URL_DEFAULT);
			} catch (IOException e1) {
				//Caso imposible que suceda a menos que se cambie
				//La fotografia de lugar de la carpeta recursos
			}
			e.printStackTrace();
		}
		//Todas las imagenes serán en formato 1x1
		Image imagenReescalada = image.getScaledInstance(tam, tam, Image.SCALE_SMOOTH);
		return imagenReescalada;
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
	
	public Optional<Contacto> obtenerContactoPorNombre(String nombreContacto) {
		Optional<Contacto> contacto = contactos.stream().filter(c -> c.isNombre(nombreContacto)).findFirst();
		return contacto;
	}
	
	//////////////
	// MENSAJES //
	//////////////
	
	
	/////////////////////////
	/// ENVÍO DE MENSAJES ///
	/////////////////////////
	
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
	
	/**
	 * Se envia de tipo emoticono un mensaje a partir de un usuario
	 * @param Usuario usuario receptor
	 * @param Texto cuerpo del mensaje
	 * @return
	 */
	public Mensaje enviarMensaje(Usuario u, int emoticono) {
		Mensaje m = new Mensaje(emoticono, LocalDateTime.now() , this, u);
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
	
	/**
	 * 
	 * @param Mensaje que envia el usuario al grupo.
	 * 
	 * @param Grupo al que enviar
	 * @param emoticono: emoticono identificado por int
	 * @return Lista de los mensajes individuales que se le ha mandado a cada integrante del grupo
	 */
	public List<Mensaje> enviarMensajeGrupo(Mensaje m, Grupo g, String texto){
		if(mensajesGrupos.containsKey(g)) {
			mensajesGrupos.get(g).add(m);
		} else {
			List<Mensaje> msjGrupo = new LinkedList<Mensaje>();
			msjGrupo.add(m);
			mensajesGrupos.put(g, msjGrupo);
		}
		
		//Mensajes que se envian a un grupo
		List<Mensaje> mensajes = g.getMiembros().stream()	//Se hace un stream de los contactos
												.map(c -> enviarMensaje(c.getUsuario(), texto)) //Se envia mensaje 
												.collect(Collectors.toList());	//Se almaena como lista
		return mensajes;
	}
	
	/**
	 * 
	 * @param Mensaje que envia el usuario al grupo.
	 * 
	 * @param Grupo al que enviar
	 * @param emoticono: emoticono identificado por int
	 * @return Lista de los mensajes individuales que se le ha mandado a cada integrante del grupo
	 */
	public List<Mensaje> enviarMensajeGrupo(Mensaje m, Grupo g, int emoticono){
		if(mensajesGrupos.containsKey(g)) {
			mensajesGrupos.get(g).add(m);
		} else {
			List<Mensaje> msjGrupo = new LinkedList<Mensaje>();
			msjGrupo.add(m);
			mensajesGrupos.put(g, msjGrupo);
		}
		
		//Mensajes que se envian a un grupo
		List<Mensaje> mensajes = g.getMiembros().stream()	//Se hace un stream de los contactos
												.map(c -> enviarMensaje(c.getUsuario(), emoticono)) //Se envia mensaje 
												.collect(Collectors.toList());	//Se almaena como lista
		return mensajes;
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
	 * 
	 * @return
	 */
	public List<Mensaje> obtenerConversacionesRecientes(){
		List<Mensaje> mensajes = new LinkedList<>();
		
		//Se supone que los mensajes en estas listas está insertado en orden
		mensajes.addAll(mensajesPorUsuario.values().stream()
			    .map(t -> t.get(t.size() - 1)) // Obtenemos el último valor sin hacer cast
			    .collect(Collectors.toList()));		  //Se almacena en una lista
		
		//Se insertan también los mensajes de los grupos
		mensajes.addAll(mensajesGrupos.values().stream()
			    .map(t -> t.get(t.size() - 1)) // Obtenemos el último valor sin hacer cast
			    .collect(Collectors.toList()));
		
		mensajes = mensajes.stream().sorted( (m1,m2) -> m1.getFechaHora().compareTo(m2.getFechaHora())).collect(Collectors.toList());
		
		return mensajes;
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
		if (mensajesGrupos.containsKey(g)) {
			return mensajesGrupos.get(g);
		}
		//En caso de que no haya mensajes se devolverá una lista vacia.
		return new LinkedList<Mensaje>();
	}
	
	public List<Mensaje> obtenerTodosMensajes(){
		
		List<Mensaje> mensajes = mensajesPorUsuario.values().stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());
		
		return mensajes;
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
	
	public List<Mensaje> obtenerMensajesContacto(Contacto c){
		if (c instanceof Grupo)
			return obtenerConversacionGrupo((Grupo) c);
		else
			return obtenerConversacionUsuario(((ContactoIndividual) c).getUsuario());
	}
	
	public boolean esUsuarioContacto(Usuario u) {
		return contactos.stream().anyMatch(c -> c instanceof ContactoIndividual
				&& ((ContactoIndividual) c).isTelefono(u.getTelefono()));
	}
	public boolean esEmisor(Mensaje m) {
		return m.esEmisor(this);
	}
	


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
		//Necesario obtener
		
		return 0L;
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
		this.rolUsuario = new Premium();
	}
	
	public LocalDate obtenerFechaExpiracion() {
		if (premium)
			return ((Premium) rolUsuario).getFechaExpiracion();
		return null;
	}
	
	@Override
	public String toString() {
		
		return "Nombre: " + nombre + "\nContactos: " + contactos;
	}
	
}
