package tds.appchat.modelo;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;




public class RepositorioUsuarios {
	private Map<String, Usuario> usuarios = new HashMap<>();
	
	private static RepositorioUsuarios unicaInstancia = new RepositorioUsuarios();
	
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;

	
	private RepositorioUsuarios() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorUsuario = dao.getUsuarioDAO();
  			this.cargarUsuarios();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	private void cargarUsuarios() {
		usuarios = new HashMap<String, Usuario>();
		List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		usuariosBD.stream().forEach(u -> usuarios.put(u.getTelefono(), u));
	}
	public static RepositorioUsuarios getUnicaInstancia() {
		return unicaInstancia;
	}
	
	public Usuario obtenerUsuarioPorTelefono(String telefono) {
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
		return usuarios.values().stream().filter(u -> u.getTelefono().equals(numTelefono)).findAny();
	}
}
