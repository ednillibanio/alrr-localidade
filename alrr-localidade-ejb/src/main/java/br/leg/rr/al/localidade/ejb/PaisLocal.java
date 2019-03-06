package br.leg.rr.al.localidade.ejb;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.DominioIndexadoJPADao;
import br.leg.rr.al.localidade.jpa.Pais;

@Local
public interface PaisLocal extends DominioIndexadoJPADao<Pais> {

	/**
	 * Método que busca e retorna o país Brasil.
	 * 
	 * @return Brasil.
	 */
	Pais getBrasil();

}
