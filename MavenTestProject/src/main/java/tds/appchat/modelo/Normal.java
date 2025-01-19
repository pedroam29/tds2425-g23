package tds.appchat.modelo;

public class Normal implements RolUsuario {
	public final static String ID_NORMAL = "Normal";
	@Override
	public String toString() {
		return ID_NORMAL;
	}
	
	public static RolUsuario fromString(String s) {
		return new Normal();
	}
	
}
