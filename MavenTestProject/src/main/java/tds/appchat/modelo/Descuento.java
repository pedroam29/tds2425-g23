package tds.appchat.modelo;

public abstract class Descuento {
	/**
	 * Se devolverá el precio reducido según el tipo de descuento
	 * 
	 * @param precio inicial
	 * @return precio rebajado
	 */
	public abstract double calcularDescuento(double precio);
}
