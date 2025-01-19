package tds.appchat.modelo;

/**
 * Clase de descuentos aplicados a un numero total de mensajes. 
 */
public class DescuentoMensaje extends Descuento{
	
	public final static String ID = "DM";
	
	private static int NUM_MENSAJES = 5;
	
	private double totalDescuento = 0.7;
	@Override
	public double calcularDescuento(double precio){
		return precio*(1 - totalDescuento);
	}
	
	public static boolean esUsuarioAptoDescuento(Usuario usuario) {
		return usuario.getTotalMensajesEnviadosUltimoMes() >  NUM_MENSAJES;
	}
	
	@Override
	public String toString() {
		return ID;
	}
}
