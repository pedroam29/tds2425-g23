package persistencia;


import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorMensajeTDS {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensajeTDS unicaInstancia = null;
	
	public static AdaptadorMensajeTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorMensajeTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

}
