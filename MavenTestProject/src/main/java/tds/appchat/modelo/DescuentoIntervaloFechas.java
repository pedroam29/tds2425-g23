package tds.appchat.modelo;

import java.time.LocalDate;

public class DescuentoIntervaloFechas extends Descuento {
	
	private final static LocalDate INICIO_INTERVALO = LocalDate.of(2024, 20, 10);
	private final static LocalDate FIN_INTERVALO = LocalDate.of(2025, 1, 18);
	
	private double totalDescuento = 0.3;
	@Override
	public double calcularDescuento(double precio) {
		return precio * (1 - totalDescuento);
	}
	
	/**
	 * Comprobación de si es apto para este tipo de descuento.
	 * @param Usuario actual
	 * @return Si la fecha de registro de usuario entra en el intervalo del descuento
	 */
	public static boolean esUsuarioAptoDescuento(Usuario usuario)
	{
		return usuario.getFechaRegistro().isAfter(INICIO_INTERVALO)
			&& usuario.getFechaRegistro().isBefore(FIN_INTERVALO);
	}
}
