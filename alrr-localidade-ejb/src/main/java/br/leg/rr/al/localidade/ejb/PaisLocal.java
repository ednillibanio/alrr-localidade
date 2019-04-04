package br.leg.rr.al.localidade.ejb;

import java.util.List;

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

	List<Pais> buscarPorNacionalidadeIndexado(String texto);

}
