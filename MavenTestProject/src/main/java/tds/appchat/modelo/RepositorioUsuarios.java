package tds.appchat.modelo;

import java.util.HashMap;
import java.util.Map;

public class RepositorioUsuarios {
	private Map<String, Usuario> usuarios = new HashMap<>();
	
	public Usuario obtenerUsuario(String telefono) {
		return usuarios.get(telefono);
	}
	public void agregarUsuario(Usuario usuario) {
		usuarios.put(usuario.getTelefono(), usuario);
	}
}
