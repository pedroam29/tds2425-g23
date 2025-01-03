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

import javax.imageio.ImageIO;


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
	public List<Mensaje> getChatMensajes(Usuario u){
		return null;
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
	 * Devuelve la Imagen a partir de la URL de los parámetros
	 * @return imagen de perfil
	 */
	public Image getImagen()
	{
		Image imagen = null;
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
	
	public void addContacto(ContactoIndividual c) {
		this.contactos.add(c);
	}
	
	public boolean isClave(String clave)
	{
		return this.contrasena.equals(clave);
	}
	public boolean hasContactoIndividual(ContactoIndividual cont) {
		return contactos.stream().anyMatch(c -> c instanceof ContactoIndividual && c.equals(cont));
	}
	public boolean hasGrupo(String nombreGrupo) {
		return contactos.stream().anyMatch(g -> g instanceof Grupo && g.getNombre().equals(nombreGrupo));
	}
	
	public void addGrupo(Grupo g) {
		contactos.add(g);
	}
	
	public boolean existeContacto(String telefono) {
		return contactos.stream().anyMatch(c -> c instanceof ContactoIndividual && ((ContactoIndividual) c).getTelefono().equals(telefono));
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
	public void enviarMensaje(Usuario receptor, String contenido) {
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
