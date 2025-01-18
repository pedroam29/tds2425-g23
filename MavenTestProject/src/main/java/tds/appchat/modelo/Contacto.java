package tds.appchat.modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


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
	
	public boolean isNombre(String nombre) {
		return nombre.equals(nombre);
	}
	
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	
	
	
	@Override
	public String toString() {
		return this.nombre;
	}
}
