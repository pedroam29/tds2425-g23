package tds.appchat.persistencia;

import java.util.List;

import tds.appchat.modelo.Mensaje;

public interface IAdaptadorMensajeDAO {
	public void registrarMensaje(Mensaje mensaje);
	public void borrarMensaje(Mensaje mensaje);
	public void modificarMensaje(Mensaje mensaje);
	public Mensaje recuperarMensaje(int codigo);
	public List<Mensaje> recuperarTodosMensajes();
}
