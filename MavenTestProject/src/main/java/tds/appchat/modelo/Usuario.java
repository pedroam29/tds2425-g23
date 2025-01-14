package tds.appchat.modelo;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
	
	private List<Mensaje> mensajesRecibidos;
	private List<Mensaje> mensajesEnviados;
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
		this.mensajesRecibidos=new LinkedList<>();
		this.mensajesEnviados=new LinkedList<>();
		this.contactos=new LinkedList<Contacto>();
		this.descuento = null;
		this.fechaRegistro = LocalDate.now();
		this.premium = false;
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
	public List<Mensaje> getEnviados() {
		return new LinkedList<Mensaje>(mensajesEnviados);
	}
	
	public void setEnviados(List<Mensaje> enviados) {
		this.mensajesEnviados = new LinkedList<Mensaje>(enviados);
	}
	
	public List<Mensaje> getRecibidos() {
		return new LinkedList<Mensaje>(mensajesRecibidos);
	}
	
	public void setRecibidos(List<Mensaje> recibidos) {
		this.mensajesEnviados = new LinkedList<Mensaje>(recibidos);
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
	
	//////////////
	// MENSAJES //
	//////////////
	
	/**
	 * Dado un contacto se obtiene su contacto
	 * 
	 * @param telefono
	 * @return
	 */
	public ContactoIndividual getContactoDesdeTelefono(String telefono) {
		ContactoIndividual contacto = (ContactoIndividual) contactos.stream()
				.filter(c -> (c instanceof ContactoIndividual) & ((ContactoIndividual) c).isTelefono(telefono))
				.findFirst().get();
		return contacto;
	}
	
	public ContactoIndividual getContactoDesdeMensaje(Mensaje m) {
		return getContactoDesdeTelefono(telefonoDeMensaje(m));
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public List<Mensaje> getConversacionGrupo(String nombre){
		//Grupo grupo = (Grupo) contactos.stream().filter(g -> g instanceof Grupo && g.isNombre(nombre)).findFirst().get();
		//Se toman todos los mensajes de este grupo que 
		List<Mensaje> mensajesGrupo = mensajesEnviados.stream()
				.filter(m -> m.esNombreGrupo(nombre))
				.sorted((m1, m2) -> m2.getFechaHora().compareTo(m1.getFechaHora()))
				.collect(Collectors.toList());
		return mensajesGrupo;
	}
	
	public List<Mensaje> getConversacionFromTelefono(String telefono){
		List<Mensaje> conversacion = new LinkedList<Mensaje>();
		//Se supone que el teléfono está registrado en el
		//Todos los mensajes enviados cuyo receptor sea el buscado
		List<Mensaje> enviadosConversacion = mensajesEnviados.stream().filter(m -> m.esReceptor(telefono)).collect(Collectors.toList());
		//Todos los mensajes recibidos cuyo emiser sea el buscado
		List<Mensaje> recibidosConversacion = mensajesRecibidos.stream().filter(m -> m.esEmisor(telefono)).collect(Collectors.toList());
		
		conversacion.addAll(recibidosConversacion);
		conversacion.addAll(enviadosConversacion);
		
		conversacion.stream().sorted((m1, m2) -> m2.getFechaHora().compareTo(m1.getFechaHora())).collect(Collectors.toList());
		return conversacion;
	}
	public List<Mensaje> getConversacionFromContacto(Contacto c){
		if (c instanceof Grupo)
			return getConversacionGrupo(c.getNombre());
		return getConversacionFromTelefono(((ContactoIndividual) c).getTelefono());
	}
	/**
	 * Dado un mensaje se obtendrá su 
	 * @param mensaje
	 * @return
	 */
	public List<Mensaje> getConversacionFromMensaje(Mensaje mensaje) {
		if (mensaje.isGrupo())
			return getConversacionGrupo(mensaje.getTlfReceptor());
		return getConversacionFromTelefono(telefonoDeMensaje(mensaje));
	}

	private String telefonoDeMensaje(Mensaje m) {
		//Se devuelve el que no sea 
		return isTelefono(m.getTlfEmisor()) ? m.getTlfReceptor() : m.getTlfEmisor();
	}
	
	public boolean esEmisor(Mensaje m) {
		return m.esEmisor(telefono);
	}
	public List<Mensaje> obtenerUltimosChats(){
		//Se tendrá un conjunto para ir almacenando
		Set<String> telefonos = new HashSet<String>();
		Set<String> grupos = new HashSet<String>();
		
		//Se van a recorrer todos los teléfonos e insertando en un set
		List<Mensaje> mensajes = new LinkedList<Mensaje>(mensajesEnviados);
		mensajes.addAll(mensajesRecibidos);
		
		List<Mensaje> mensajesDevolver = mensajes.stream().filter(m -> {
			if (m.isGrupo())
				if(grupos.contains(m.getNombreGrupo()))
					return false;
				else {
					grupos.add(m.getNombreGrupo());
					return true;
				}
			
			if (telefonos.contains(m))
				return false;
			else {
				telefonos.add(telefonoDeMensaje(m));
				return true;
			}
		})
		.collect(Collectors.toList());
		
		return mensajesDevolver;
	}
	
	/////////////////////////
	/// ENVÍO DE MENSAJES ///
	/////////////////////////
	
	public void addMensajeEnviados(Mensaje mensaje) {
		mensajesEnviados.add(mensaje);
	}
	public void recibirMensaje(Mensaje mensaje) {
		mensajesRecibidos.add(mensaje);
	}
	public Mensaje enviarMensajeGrupo(Grupo grupo, String texto) {
		Mensaje mensaje = new Mensaje(texto, LocalDateTime.now(), telefono, grupo.getNombre());
		mensaje.setGrupo(true); //Es un mensaje a grupos
		addMensajeEnviados(mensaje);
		
		//Ahora por cada miembro del grupo se le enviará el mensaje individualmente
		//TODO: Manera para que sea el grupo el que se encargue de enviar el mensaje a los contactos
		for (ContactoIndividual c : grupo.getMiembros()) {
			enviarMensajeContacto(c, texto);
		}
		return mensaje;}
	
	public Mensaje enviarMensajeContacto(ContactoIndividual contacto, String texto) {
		Mensaje mensaje = new Mensaje(texto, LocalDateTime.now(), telefono, contacto.getTelefono());
		contacto.enviarMensaje(mensaje);
		addMensajeEnviados(mensaje);
		return mensaje;
	}
	
	public Mensaje enviarMensaje(Contacto contacto, String texto) {
		if (contacto instanceof ContactoIndividual)
			return enviarMensajeContacto((ContactoIndividual) contacto, texto);
		else //if (contacto instanceof Grupo)
			return enviarMensajeGrupo((Grupo) contacto, texto);
		
		//Caso imposible de que se haya pasado un Objeto contacto que no sea ni individual ni grupo.
	}
//	public Contacto getContactoFromTelefono(String telefono){
//		ContactoIndividual contacto = (ContactoIndividual) contactos.stream().filter(c -> (c instanceof ContactoIndividual))
//				.findFirst().get();
//	}
	public Mensaje enviarMensajeTelefono(String telefono, String texto){
		//Se crea el mensaje
		Mensaje m = new Mensaje(texto, LocalDateTime.now(), this.telefono, telefono);
		if(existeContacto(telefono))
			return enviarMensaje(getContactoDesdeTelefono(telefono), texto);
		//Puede ser que devuelva null
		return AppChat.getUnicaInstancia().enviarMensajeContactoDesconocido(m);
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
	
	public boolean existeContacto(String telefono) {
		return contactos.stream().anyMatch(c -> c instanceof ContactoIndividual && ((ContactoIndividual) c).isTelefono(telefono));
	}
	
	public ContactoIndividual crearContacto(String nombre, Usuario usuarioActual) {

		ContactoIndividual nuevoContacto = new ContactoIndividual(nombre, usuarioActual.getTelefono(), usuarioActual);
		addContacto(nuevoContacto);
		return nuevoContacto;
	}
	
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
	public boolean realizarPago()
	{
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
