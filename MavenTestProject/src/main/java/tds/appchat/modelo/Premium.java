package tds.appchat.modelo;

import java.time.LocalDate;
import java.time.Period;

public class Premium implements RolUsuario {
	private final static String SEPARADOR = "***";
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
		//El precio final tras el descuento
		precio = tipoDescuentoAplicado.calcularDescuento(PRECIO_PREMIUM);
		//Fecha expiración
		fechaExpiracion = LocalDate.now().plus(DURACION_SUSCRIPCION);
	}
	
	public boolean realizarPago(){
		return true;
	}
	
	public void setFechaExpiracion(LocalDate fecha) {
		this.fechaExpiracion = fecha;
	}
	public LocalDate getFechaExpiracion() {
		return fechaExpiracion;
	}
	
	public static double getPrecioPremium(){
		return PRECIO_PREMIUM;
	}
	public double getPrecio() {
		return descuentoAplicado == null ? PRECIO_PREMIUM : descuentoAplicado.calcularDescuento(PRECIO_PREMIUM);
	}
	@Override
	public String toString() {

		return fechaExpiracion.toString() +  SEPARADOR + ((descuentoAplicado == null) ? "" : descuentoAplicado.toString());
	}
	
	public static RolUsuario fromString(String s) {
		String [] elems = s.split(SEPARADOR);
		Premium p = (elems[1].isEmpty()) ? new Premium() : new Premium(Descuento.fromString(elems[1])) ;
		p.setFechaExpiracion(LocalDate.parse(elems[0]));
		
		return p;
	}
}