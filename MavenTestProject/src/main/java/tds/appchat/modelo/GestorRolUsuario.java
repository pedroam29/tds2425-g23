package tds.appchat.modelo;

public class GestorRolUsuario {
	public static RolUsuario parseRol(String rol) {
		if (rol.equals(Normal.ID_NORMAL)) {
			return Normal.fromString(rol);
		} else {
			return Premium.fromString(rol);
		}
	}
}
