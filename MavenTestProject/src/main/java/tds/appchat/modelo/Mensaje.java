package tds.appchat.modelo;

import java.time.LocalDateTime;

import tds.appchat.controlador.AppChat;

public class Mensaje {
	private int codigo;
	private String texto;
	
	private String tlfEmisor;
	private String tlfReceptor; //Que pasa si el grupo es 
	
	private boolean grupo; //Este booleano indicará si el mensaje enviado es para un grupo o no
	
	//private Usuario emisor;
	//private Contacto receptor;
	
	private LocalDateTime fechaHora;
	private int emoticono;
	
	public Mensaje (String texto, LocalDateTime hora, String tlfEmisor, String tlfReceptor) {
		this.texto = texto;
        this.fechaHora = hora;
        this.tlfEmisor = tlfEmisor;
        this.tlfReceptor = tlfReceptor;
        emoticono = -1;
        grupo = false;
	}
	
	public Mensaje (int emoticono, LocalDateTime hora, String tlfEmisor, String tlfReceptor) {
		this.emoticono = emoticono;
        this.fechaHora = hora;
        this.tlfEmisor = tlfEmisor;
        this.tlfReceptor = tlfReceptor;
        this.texto = "";
        grupo = false;
	}
	
	
	public Mensaje(String texto, int emoticono, LocalDateTime hora) {
		this.texto = texto;
		this.emoticono = emoticono;
		this.fechaHora = hora;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setGrupo(boolean a) {
		this.grupo = a;
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

	public void setEmisor(String emisor) {
		this.tlfEmisor = emisor;
	}
	public boolean esNombreGrupo(String emisor) {
		return grupo && tlfReceptor.equals(emisor);
	}
	public boolean isGrupo() {
		return grupo;
	}
	
	public void setReceptor(String receptor) {
		this.tlfReceptor = receptor;
	}

	//Getters
	public String getTexto() {
		return texto;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	
	public boolean esEmisor(String telefono) {
		return this.tlfEmisor.equals(telefono);
	}
	
	public String getTlfEmisor() {
		return tlfEmisor;
	}
	
	public boolean esReceptor(String telefono) {
		return this.tlfReceptor.equals(telefono);
	}
	
	public String getTlfReceptor() {
		return tlfReceptor;
	}
	public String getNombreGrupo() {
		return tlfReceptor;
	}
	
//	public String getNombreEmisor() {
//		return this.emisor.getNombre();
//	}
//	public String getNombreReceptor() {
//		return this.receptor.getNombre();
//	}
//	public Contacto getReceptor() {
//		return receptor;
//	}
	
	//TODO: 
	@Override
	public String toString()
	{
		return fechaHora.toString() + tlfEmisor + tlfReceptor;
	}

}
