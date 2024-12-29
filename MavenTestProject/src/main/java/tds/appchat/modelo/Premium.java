package tds.appchat.modelo;

public class Premium implements RolUsuario {
	
	private final static double PRECIO_PREMIUM = 15.00;
	private Descuento tipoDescuentoAplicado;
	private double precio;
	
	public Premium(Descuento tipoDescuentoAplicado) {
		this.tipoDescuentoAplicado = tipoDescuentoAplicado;
		precio = tipoDescuentoAplicado.calcularDescuento(PRECIO_PREMIUM);
	}
	public boolean realizarPago(){
		return true;
	}
	public static double getPrecioPremium()
	{
		return PRECIO_PREMIUM;
	}
}