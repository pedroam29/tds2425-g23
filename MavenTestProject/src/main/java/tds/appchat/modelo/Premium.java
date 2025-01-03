package tds.appchat.modelo;

import java.time.LocalDate;
import java.time.Period;

public class Premium implements RolUsuario {
	
	private final static double PRECIO_PREMIUM = 15.00;
	//Un año de duración de suscripción de tipo Premium
	private final static Period DURACION_SUSCRIPCION = Period.ofYears(1);
	
	private Descuento descuentoAplicado = null;
	private double precio;
	private LocalDate fechaExpiracion; 
	
	/**
	 * Constructor por si no hubiera descuento, equivaldría a utilizar el
	 * 
	 */
	public Premium(){
		precio = PRECIO_PREMIUM;
		fechaExpiracion = LocalDate.now().plus(DURACION_SUSCRIPCION);
	}
	
	public Premium(Descuento tipoDescuentoAplicado) {
		//El objeto descuento que se aplica:
		this.descuentoAplicado = tipoDescuentoAplicado;
		//El precio final
		precio = tipoDescuentoAplicado.calcularDescuento(PRECIO_PREMIUM);
		//Fecha expiración
		fechaExpiracion = LocalDate.now().plus(DURACION_SUSCRIPCION);
	}
	
	public boolean realizarPago(){
		return true;
	}
	
	public static double getPrecioPremium(){
		return PRECIO_PREMIUM;
	}
}