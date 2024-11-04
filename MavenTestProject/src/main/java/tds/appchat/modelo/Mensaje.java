package tds.appchat.modelo;

import java.time.LocalDateTime;

public class Mensaje {
	private String texto;
	private String usuario;
	private String imagen;
	private Usuario emisor;
	private Usuario receptor;
	private LocalDateTime fechaHora;
	
	/*public Mensaje(String texto, String telefonoEmisor, String telefonoReceptor) {
		this.texto = texto;
        this.telefonoEmisor = telefonoEmisor;
        this.telefonoReceptor = telefonoReceptor;
        this.fechaHora = LocalDateTime.now();
	}*/
	public Mensaje (String contacto, String mensaje, String imagen) {
		this.usuario=contacto;
		this.texto=mensaje;
		this.imagen=imagen;
	}
	public String getTexto() {
		return texto;
	}
	public String getUsuario() {
		return usuario;
	}
	public String getImagen() {
		return imagen;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public Usuario getEmisor() {
		return emisor;
	}
	public Usuario getReceptor() {
		return receptor;
	}

}
