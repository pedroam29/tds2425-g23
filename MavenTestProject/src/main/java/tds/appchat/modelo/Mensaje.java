package tds.appchat.modelo;

import java.time.LocalDateTime;

public class Mensaje {
	private String texto;
	private String telefonoEmisor;
	private String telefonoReceptor;
	private LocalDateTime fechaHora;
	
	public Mensaje(String texto, String telefonoEmisor, String telefonoReceptor) {
		this.texto = texto;
        this.telefonoEmisor = telefonoEmisor;
        this.telefonoReceptor = telefonoReceptor;
        this.fechaHora = LocalDateTime.now();
	}
	public String getTexto() {
		return texto;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public String getTelefonoEmisor() {
		return telefonoEmisor;
	}
	public String getTelefonoReceptor() {
		return telefonoReceptor;
	}

}
