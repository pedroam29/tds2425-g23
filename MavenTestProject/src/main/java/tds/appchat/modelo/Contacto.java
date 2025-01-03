package tds.appchat.modelo;

public abstract class Contacto {
	private int codigo;
	protected String nombre;

    public Contacto(String nombre) {
    	this.codigo = 0;
        this.nombre = nombre;
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
	
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	
	@Override
	public String toString() {
		return this.nombre + this.codigo;
	}
}
