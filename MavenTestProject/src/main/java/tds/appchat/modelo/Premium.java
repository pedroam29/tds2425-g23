package tds.appchat.modelo;

import java.time.LocalDate;
import java.time.Period;

public class Premium implements RolUsuario {
	private final static Period DURACION_SUSCRIPCION = Period.ofYears(1);
	private final static double PRECIO_PREMIUM = 15.00;
	
	private LocalDate fechaExpiracion;
	
	public Premium() {
		fechaExpiracion = LocalDate.now().plus(DURACION_SUSCRIPCION);
	}
	
	public static double getPrecioPremium(){
		return PRECIO_PREMIUM;
	}
	
	public void setFechaExpiracion(LocalDate fecha) {
		this.fechaExpiracion = fecha;
	}
	public LocalDate getFechaExpiracion() {
		return fechaExpiracion;
	}
	
	public static RolUsuario fromString(String s) {
		Premium p = new Premium();
		p.setFechaExpiracion(LocalDate.parse(s));
		
		return p;
	}
	
	public String toString() {
		return fechaExpiracion.toString();
	}
}