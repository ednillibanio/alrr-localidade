package br.leg.rr.al.localidade.ejb;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.DominioIndexadoJPADao;
import br.leg.rr.al.localidade.jpa.Pais;
import br.leg.rr.al.localidade.jpa.UnidadeFederativa;

@Local
public interface UnidadeFederativaLocal extends DominioIndexadoJPADao<UnidadeFederativa> {

	/**
	 * Busca pelos campo "pais" da entidade {@code Pais}.<br>
	 * 
	 * @value {@literal List<Pais>};
	 */
	String PESQUISAR_PARAM_PAISES = "paises";

	/**
	 * Método que busca e retorna a Uf igual a Roraima.
	 * 
	 * @return Uf Roraima.
	 */
	UnidadeFederativa getRoraima();

	/**
	 * Busca Uf do Brasil pelo nome da uf.<br>
	 * Ex: nome = "Roraima"
	 * 
	 * @param nome uf do brasil. Deve ser o nome exato. Caso contrário não
	 *             encontrará.
	 * @return uf do Brasil ou null caso não encontrado.
	 */
	UnidadeFederativa buscarBrasilUfPorNome(String nome);

	/**
	 * Busca Uf do Brasil pela sigla da uf.<br>
	 * Ex: nome = "RR"
	 * 
	 * @param sigla uf do brasil. Deve ser a sigla exata. Caso contrário não
	 *              encontrará.
	 * @return uf do Brasil ou null caso não encontrado.
	 */
	UnidadeFederativa buscarBrasilUfPorSigla(String sigla);

	UnidadeFederativa buscarBrasilUfPorIbgeId(String ibgeId);

	/**
	 * 
	 * 
	 * @param nome
	 * @return uf do Brasil ou null caso não encontrado.
	 */
	/**
	 * Busca Uf pelo país e nome da uf.
	 * 
	 * @param pais país que contém a uf.
	 * @param nome uf a ser pesquisado no pais informado. Deve ser nome exato. Caso
	 *             contrário não encontrará.
	 * @return uf do país informado ou null caso não encontrado.
	 */
	UnidadeFederativa buscar(Pais pais, String nome);

	UnidadeFederativa buscarPorSigla(Pais pais, String sigla);

}
