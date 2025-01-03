package tds.appchat.modelo;


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
	
	@Override
	public String toString() {
		return super.toString();
	}
}
