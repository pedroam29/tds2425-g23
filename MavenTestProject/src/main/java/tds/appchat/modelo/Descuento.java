package tds.appchat.modelo;

public abstract class Descuento {
	
	public abstract double calcularDescuento(double precio);
	
	public static boolean esUsuarioAptoDescuento(Usuario usuario){
		return false;
	}
}
