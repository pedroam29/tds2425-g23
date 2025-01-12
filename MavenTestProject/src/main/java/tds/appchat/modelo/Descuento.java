package tds.appchat.modelo;

public abstract class Descuento {
	private static final String SEPARADOR = ":";
	/**
	 * Se devolverá el precio reducido según el tipo de descuento
	 * 
	 * @param precio inicial
	 * @return precio rebajado
	 */
	public abstract double calcularDescuento(double precio);
	
	public static Descuento fromString(String s) {		
		switch(s.split(SEPARADOR)[1]) {
			case DescuentoMensaje.ID : 
				return new DescuentoMensaje();
			case DescuentoIntervaloFechas.ID :
				return new DescuentoIntervaloFechas();
		}
		return null;
	}
}
