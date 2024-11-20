package tds.appchat.modelo;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class RepositorioUsuarios {
	private Map<String, Usuario> usuarios = new HashMap<>();
	
	public Usuario obtenerUsuario(String telefono) {
		return usuarios.get(telefono);
	}
	public boolean agregarUsuario(Usuario usuario) {
		//Si ya está el telefono registrado devuelve falso y se indica:
		if (usuarios.containsKey(usuario.getTelefono()))
			return false;
		usuarios.put(usuario.getTelefono(), usuario);
		return true;
	}
	
	public List<Usuario> getAllUsuarios() {
		return new ArrayList<Usuario>(usuarios.values());
	}
	
	public Optional<Usuario> getUsuarioNumTelf(String numTelefono) {
		return usuarios.values().stream().filter(u -> u.getTelefono() == numTelefono).findAny();
	}
}
