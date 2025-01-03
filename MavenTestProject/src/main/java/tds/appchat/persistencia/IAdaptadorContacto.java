package tds.appchat.persistencia;

import tds.appchat.modelo.Contacto;

public interface IAdaptadorContacto {
	
	public static final String ATRIB_GRUPO = "grupo";
	public static final String ATRIB_CONTACTO_INDIVIDUAL = "contacto";

	public Contacto recuperarContacto(int codigo);
}
