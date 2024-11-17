package tds.appchat.modelo;

import java.time.LocalDateTime;

public class Mensaje {
	private final String texto;
	private final Usuario emisor;
	private final Usuario receptor;
	private LocalDateTime fechaHora;
	
	/*public Mensaje(String texto, String telefonoEmisor, String telefonoReceptor) {
		this.texto = texto;
        this.telefonoEmisor = telefonoEmisor;
        this.telefonoReceptor = telefonoReceptor;
        this.fechaHora = LocalDateTime.now();
	}*/
	public Mensaje (String texto, Usuario emisor, Usuario receptor) {
		this.texto = texto;
        
        this.emisor = emisor;
        this.receptor = receptor;
	}
	
	public String getTexto() {
		return texto;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public Usuario getEmisor() {
		return emisor;
	}
	
	public String getNombreEmisor() {
		return this.emisor.getNombre();
	}
	public String getNombreReceptor() {
		return this.receptor.getNombre();
	}
	public Usuario getReceptor() {
		return receptor;
	}

}
