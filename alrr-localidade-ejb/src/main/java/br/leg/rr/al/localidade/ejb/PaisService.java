package br.leg.rr.al.localidade.ejb;

import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Named;

import br.leg.rr.al.core.dao.BaseJPADaoStatus;
import br.leg.rr.al.localidade.jpa.Pais;

@Named
@Singleton
public class PaisService extends BaseJPADaoStatus<Pais, Integer> implements PaisLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -614202649174886385L;

	@Override
	public List<Pais> buscarPorNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean jaExiste(Pais entidade) {
		return false;
	}

}
