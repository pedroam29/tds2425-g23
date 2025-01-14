package tds.appchat.modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public abstract class Contacto {
	private int codigo;
	protected String nombre;
	private List<Mensaje> mensajes;

    public Contacto(String nombre) {
    	this.codigo = 0;
        this.nombre = nombre;
        this.mensajes = new LinkedList<Mensaje>();
    }

    public Contacto(String nombre, List<Mensaje> mensajes) {
		this.nombre = nombre;
		this.mensajes = mensajes;
	}
    
	public int getCodigo() {
		return codigo;
	}
	

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}
	
	public boolean isNombre(String nombre) {
		return nombre.equals(nombre);
	}
	
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	
	public List<Mensaje> getMensajesEnviados() {
		return mensajes;
	}
	
	public abstract List<Mensaje> getMensajesRecibidos(Optional<Usuario> usuario);
	//public abstract void enviarMensaje(Mensaje message);

	public void addMensajes(List<Mensaje> mensajes) {
		this.mensajes.addAll(mensajes);
	}
	
	
	
	@Override
	public String toString() {
		return this.nombre + this.codigo;
	}
}
