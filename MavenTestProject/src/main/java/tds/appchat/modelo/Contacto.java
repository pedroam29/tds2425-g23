package tds.appchat.modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public abstract class Contacto {
	private int codigo;
	protected String nombre;
	private List<Mensaje3> mensajes;

    public Contacto(String nombre) {
    	this.codigo = 0;
        this.nombre = nombre;
        this.mensajes = new LinkedList<Mensaje3>();
    }

    public Contacto(String nombre, List<Mensaje3> mensajes) {
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
	
	public List<Mensaje3> getMensajesEnviados() {
		return mensajes;
	}
	
	public abstract List<Mensaje3> getMensajesRecibidos(Optional<Usuario> usuario);
	//public abstract void enviarMensaje(Mensaje message);

	public void addMensajes(List<Mensaje3> mensajes) {
		this.mensajes.addAll(mensajes);
	}
	
	
	
	@Override
	public String toString() {
		return this.nombre + this.codigo;
	}
}
