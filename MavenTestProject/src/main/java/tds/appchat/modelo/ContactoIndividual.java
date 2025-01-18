package tds.appchat.modelo;

import java.awt.Image;
import java.util.List;
import java.util.Optional;

public class ContactoIndividual extends Contacto{

	private String telefono;
	private Usuario usuario;
	
	public ContactoIndividual(String nombre, String telefono, Usuario usuario) {
		super(nombre);
		this.telefono = telefono;
		this.usuario = usuario;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario=usuario;
	}
	
	public void addGrupo(Grupo grupo) {
		usuario.addGrupo(grupo);
	}
	public boolean isTelefono(String telefono) {
		return usuario.isTelefono(telefono);
	}
	public Image getImagen(){
		return usuario.getImagen();
	}
	
	public String getUrlImagen(){
		return usuario.getImagenPerfilUrl();
	}
	@Override
	public String toString() {
		return super.toString();
	}
	
	
	@Override
	public List<Mensaje3> getMensajesRecibidos(Optional<Usuario> usuario) {
		// TODO Auto-generated method stub
		return null;
	}
	//Es el otro usuario el que recibe el mensaje.
	public void enviarMensaje(Mensaje mensaje) {
		usuario.recibirMensaje(mensaje);
		
	}
}
