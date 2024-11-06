package tds.appchat.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RepositorioUsuarios {
	private Map<String, Usuario> usuarios = new HashMap<>();
	
	public Usuario obtenerUsuario(String telefono) {
		return usuarios.get(telefono);
	}
	public void agregarUsuario(Usuario usuario) {
		usuarios.put(usuario.getTelefono(), usuario);
	}
	
	public List<Usuario> getAllUsuarios() {
		return new ArrayList<Usuario>(usuarios.values());
	}
}
