package tds.appchat.persistencia;

import beans.Entidad;
import tds.appchat.modelo.Contacto;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
/**
 * Clase adaptadora del resto de contactos
 */
public class AdaptadorContactoTDS implements IAdaptadorContacto {
	
	private static AdaptadorContactoTDS instancia;
	private static ServicioPersistencia servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	
	//Tiene que implementar la funcion IAdaptadorContacto y tener la funcion por tanto de recuperarContacto
	private static IAdaptadorContacto adaptadorGrupo = AdaptadorGrupoTDS.getUnicaInstancia();
	private static IAdaptadorContacto adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getInstancia();
	
	/**
	 * Patrón Singleton para la clase AdaptadorContactoTDS,
	 * esta será la única instancia
	 * 
	 * @return Unica instancia de AdaptadorControlador
	 */
	public static AdaptadorContactoTDS getInstancia() {
		if (instancia == null)
			instancia = new AdaptadorContactoTDS();
		return instancia;
	}
	/**
	 * De esta manera el Usuario no necesitará conocer los detalles
	 * de implementación de los contacos
	 * Cuando obtenga el código decidirá, obtendrá el tipo de instancia
	 * y según sea hará una cosa u otra
	 */
	@Override
	public Contacto recuperarContacto(int codigo) {
		
		Entidad e = servPersistencia.recuperarEntidad(codigo);		
		switch (e.getNombre()) {
			case IAdaptadorContacto.ATRIB_GRUPO:
				return adaptadorGrupo.recuperarContacto(codigo);
				
			case IAdaptadorContacto.ATRIB_CONTACTO_INDIVIDUAL:
				return adaptadorContactoIndividual.recuperarContacto(codigo);
		}
		return null;
	}
//TODO:
}
