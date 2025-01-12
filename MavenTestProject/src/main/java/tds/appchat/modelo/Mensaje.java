package tds.appchat.modelo;

import java.time.LocalDateTime;

public class Mensaje {
	private int codigo;
	private String texto;
	private Usuario emisor;
	private Contacto receptor;
	private LocalDateTime fechaHora;
	private int emoticono;
	
	public Mensaje (String texto, LocalDateTime hora, Usuario emisor, Contacto receptor) {
		this.texto = texto;
        this.fechaHora = hora;
        this.emisor = emisor;
        this.receptor = receptor;
	}
	
	public Mensaje (int emoticono, LocalDateTime hora, Usuario emisor, Contacto receptor) {
		this.emoticono = emoticono;
        this.fechaHora = hora;
        this.emisor = emisor;
        this.receptor = receptor;
	}

	public Mensaje(String texto, int emoticono, LocalDateTime hora) {
		this.texto = texto;
		this.emoticono = emoticono;
		this.fechaHora = hora;
	}
	
	public int getCodigo() {
		return codigo;
	}



	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}



	public int getEmoticono() {
		return emoticono;
	}



	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public void setEmoticono(int emoticono) {
		this.emoticono = emoticono;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}

	public void setReceptor(Contacto receptor) {
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
	public Contacto getReceptor() {
		return receptor;
	}
	
	//TODO: 
	@Override
	public String toString()
	{
		return "";
		
	}

}
