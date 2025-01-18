package tds.appchat.modelo;

import java.time.LocalDateTime;

public class Mensaje {
	//Codigo identificador del objeto
	private int codigo;
	
	//Cuerpo del mensaje
	private String texto;
	private int emoticono;
	
	//Extremos de la comunicacion
	private Usuario emisor;
	private Usuario receptor;
	
	//Metadatos del mensaje
	//Hora
	private LocalDateTime fechaHora;
	
	
	//En caso de que sea mensaje a grupo
	//Se utilizarán estos atributosadaptadorMensaje
	private boolean mensajeGrupo;
	private Grupo grupo;
	
	//Setters y getters
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Usuario getEmisor() {
		return emisor;
	}
	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public Usuario getReceptor() {
		return receptor;
	}
	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}
	public boolean isMensajeGrupo() {
		return mensajeGrupo;
	}
	public void setMensajeGrupo(boolean mensajeGrupo) {
		this.mensajeGrupo = mensajeGrupo;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
	public boolean esEmisor(Usuario u) {
		return emisor.equals(u);
	}
	
	public Mensaje(String texto, LocalDateTime fechaHora) {
		this.texto = texto;
		this.fechaHora = fechaHora;
		mensajeGrupo = false;
	}
	
	public Mensaje(String texto, LocalDateTime fechaHora, Usuario emisor, Usuario receptor) {
		this.texto = texto;
		this.fechaHora = fechaHora;
		this.emisor = emisor;
		this.receptor = receptor;
		mensajeGrupo = false;
	}
	
	public Mensaje(String texto, LocalDateTime fechaHora, Usuario emisor, Grupo g) {
		this.texto = texto;
		this.fechaHora = fechaHora;
		this.emisor = emisor;
		this.grupo = g;
		mensajeGrupo = true;
	}
}
