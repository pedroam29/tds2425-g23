package tds.appchat.modelo;

/**
 * Clase de descuentos aplicados a un numero total de mensajes. 
 */
public class DescuentoMensaje extends Descuento{
	public static int NUM_MENSAJES = 2000;
	private double totalDescuento = 0.7;
	@Override
	public double calcularDescuento(double precio){
		return precio*(1 - totalDescuento);
	}
	
	public static boolean esUsuarioAptoDescuento(Usuario usuario) {
		return usuario.getTotalMensajesEnviadosUltimoMes() >  NUM_MENSAJES;
	}
}
