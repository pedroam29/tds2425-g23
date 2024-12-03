package persistencia;

public class TDSFactoriaDAO extends FactoriaDAO{
	
	public TDSFactoriaDAO () {
	}
	
	
	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getUnicaInstancia();
	}


	@Override
	public IAdaptadorContactoIndividualDAO getContactoDAO() {
		return AdaptadorContactoIndividualTDS.getInstancia();
	}

}
