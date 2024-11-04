package tds.appchat.modelo;

public abstract class Contacto {
	protected String nombre;

    public Contacto(String nombre) {
        this.nombre = nombre;
    }

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
}
