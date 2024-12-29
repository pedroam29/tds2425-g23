package tds.appchat.modelo;

import java.time.LocalDateTime;

public class Mensaje {
	private int codigo;
	private String texto;
	private Usuario emisor;
	private Usuario receptor;
	private LocalDateTime fechaHora;
	private int emoticono;
	
	public Mensaje (String texto, Usuario emisor, Usuario receptor) {
		this.codigo = 0;
		this.texto = texto;
        this.fechaHora = LocalDateTime.now();
        this.emisor = emisor;
        this.receptor = receptor;
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

	public void setReceptor(Usuario receptor) {
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
	
	//TODO: 
	@Override
	public String toString()
	{
		return "";
		
	}

}
