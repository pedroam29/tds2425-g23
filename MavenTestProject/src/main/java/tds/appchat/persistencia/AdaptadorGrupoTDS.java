package tds.appchat.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Grupo;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorGrupoTDS implements IAdaptadorGrupoDAO {
	
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorGrupoTDS unicaInstancia = null;
	
	private final static String NOMBRE_GRUPO = "grupo";
	private final static String ATRIB_NOMBRE = "nombre";
	private final static String ATRIB_MIEMBROS = "miembros";
	private final static String ATRIB_IMAGEN = "imagen";
	
	private final static String SEP_CODIGOS = " ";
	
	private AdaptadorGrupoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public static AdaptadorGrupoTDS getUnicaInstancia(){
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorGrupoTDS();
		return unicaInstancia;
	}
	
	@Override
	public void registrarGrupo(Grupo grupo) {
		
		//Si estaba registrado con anterioridad no se realiza nada
		if (servPersistencia.recuperarEntidad(grupo.getCodigo()) != null)
			return;
		
		Entidad eGrupo = new Entidad();
		eGrupo.setNombre(NOMBRE_GRUPO + AppChat.getUnicaInstancia().getTelefonoUsuarioActual());
		
		Propiedad nombre = new Propiedad(ATRIB_NOMBRE, grupo.getNombre());
		Propiedad imagen = new Propiedad(ATRIB_IMAGEN, grupo.getImagen());
		Propiedad miembros = new Propiedad(ATRIB_MIEMBROS,obtenerCodigosContacto(grupo.getMiembros()));
		
		eGrupo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(nombre, imagen, miembros)));
		
		// Una vez creadas las propiedades se registra en el siervidor
		eGrupo = servPersistencia.registrarEntidad(eGrupo);
		
		// Se obtiene el id único para el grupo
		grupo.setCodigo(eGrupo.getId());
		
		// Se guarda en el Pool
		PoolDAO.getInstancia().addObjeto(grupo.getCodigo(), grupo);
	}

	@Override
	public void borrarGrupo(Grupo grupo) {

		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());

		servPersistencia.borrarEntidad(eGrupo);
		
		if (PoolDAO.getInstancia().contiene(grupo.getCodigo()))
			PoolDAO.getInstancia().removeObjeto(grupo.getCodigo());

	}

	@Override
	public void modificarGrupo(Grupo grupo) {
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());

		//Se elimina la deseada atributo por atributo
		servPersistencia.eliminarPropiedadEntidad(eGrupo, ATRIB_NOMBRE);
		servPersistencia.anadirPropiedadEntidad(eGrupo, ATRIB_NOMBRE, grupo.getNombre());
		
		servPersistencia.eliminarPropiedadEntidad(eGrupo, ATRIB_MIEMBROS);
		servPersistencia.anadirPropiedadEntidad(eGrupo, ATRIB_MIEMBROS, obtenerCodigosContacto(grupo.getMiembros()));
		
		servPersistencia.eliminarPropiedadEntidad(eGrupo, ATRIB_IMAGEN);
		servPersistencia.anadirPropiedadEntidad(eGrupo, ATRIB_IMAGEN, grupo.getImagen());
	}

	@Override
	public Grupo recuperarGrupo(int codigo) {
		//Antes de la búsqueda en la base de datos se
		//Comprueba si está en el PoolDAO 
		if (PoolDAO.getInstancia().contiene(codigo))
			return (Grupo) PoolDAO.getInstancia().getObjeto(codigo);
		
		// De no estarlo se tiene que obtener del servidor de persistencia
		
		Entidad eGrupo = servPersistencia.recuperarEntidad(codigo);
		
		//Se tienen que obtener los campos del objeto individualmente
		String nombre = servPersistencia.recuperarPropiedadEntidad(eGrupo, ATRIB_NOMBRE);
		String imagen = servPersistencia.recuperarPropiedadEntidad(eGrupo, ATRIB_IMAGEN);
		List<ContactoIndividual> miembros = obtenerMiembros(servPersistencia.recuperarPropiedadEntidad(eGrupo, ATRIB_MIEMBROS));
		
		Grupo grupo = new Grupo(nombre, imagen);
		grupo.setCodigo(codigo);
		grupo.setMiembros(miembros);
		
		return grupo;
	}

	@Override
	public List<Grupo> recuperarTodosGrupos() {
			List<Grupo> grupos = new LinkedList<>();
			List<Entidad> eContacts = servPersistencia.recuperarEntidades(NOMBRE_GRUPO + AppChat.getUnicaInstancia().getTelefonoUsuarioActual());

			for (Entidad eContact : eContacts) {
				grupos.add(recuperarGrupo(eContact.getId()));
			}

		return grupos;
	}
	
	private String obtenerCodigosContacto(List<ContactoIndividual> contactos) {
		String codigos = "";
		for (ContactoIndividual c : contactos)
			codigos = Integer.toString(c.getCodigo()) + SEP_CODIGOS;
		return codigos;
	}
	
	private List<ContactoIndividual> obtenerMiembros(String codigos){
		List<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		
		StringTokenizer strTok = new StringTokenizer(codigos, SEP_CODIGOS);	
		while (strTok.hasMoreTokens()) {
			try {
				contactos.add(TDSFactoriaDAO.getInstancia().getContactoDAO()
				.recuperarContacto(Integer.valueOf((String)strTok.nextElement())));
			} catch (Exception e) {}
		}
		return contactos;
	}
}
