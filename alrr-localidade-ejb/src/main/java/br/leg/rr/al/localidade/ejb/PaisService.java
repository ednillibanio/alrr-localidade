package br.leg.rr.al.localidade.ejb;

import javax.ejb.Singleton;
import javax.inject.Named;

import br.leg.rr.al.core.dao.BaseDominioIndexadoJPADao;
import br.leg.rr.al.localidade.jpa.Pais;

@Named
@Singleton
public class PaisService extends BaseDominioIndexadoJPADao<Pais> implements PaisLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -614202649174886385L;

	@Override
	public Boolean jaExiste(Pais entidade) {
		return false;
	}

}
