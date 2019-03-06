package br.leg.rr.al.localidade.ejb;

import java.util.List;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.dao.DominioIndexadoJPADao;
import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.jpa.Municipio;
import br.leg.rr.al.localidade.jpa.UnidadeFederativa;

@Local
public interface MunicipioLocal extends DominioIndexadoJPADao<Municipio> {

	/**
	 * Busca pelo campo "uf" da entidade {@code Municipio}.<br>
	 * 
	 * @value {@literal List<UnidadeFederativa>};
	 */
	String PESQUISAR_PARAM_UFS = "ufs";

	/**
	 * Busca Municipio por Unidade Federativa.
	 * 
	 * @param uf   nome da uf usada na busca da municipio.
	 * @param nome nome da municipio que deve ser pesquisada junto com a uf
	 *             informada.
	 * @return municipio que satisfaz a condição de busca, informada pelos
	 *         parametros do método.
	 * @throws ControllerException
	 */
	public Municipio buscarPorUf(UnidadeFederativa uf, String nome) throws BeanException;

	/**
	 * Busca Municipio por Unidade Federativa.
	 * 
	 * @param ufType nome da uf usada na busca da municipio.
	 * @param nome   nome da municipio que deve ser pesquisada junto com a uf
	 *               informada.
	 * @return municipio que satisfaz a condição de busca, informada pelos
	 *         parametros do método.
	 * @throws ControllerException
	 */
	public Municipio buscarPorUf(UfType ufType, String nome) throws BeanException;

	/**
	 * @param nome
	 * @param uf
	 * @return
	 * @throws BeanException
	 */
	List<Municipio> buscarPorNome(String nome, UnidadeFederativa uf) throws BeanException;

	/**
	 * @param nome
	 * @param ufType
	 * @return
	 * @throws BeanException
	 */
	List<Municipio> buscarPorNome(String nome, UfType ufType) throws BeanException;

	/**
	 * Busca o município pelo código do ibge.
	 * 
	 * @param ibgeId código do município no ibge
	 * @return retorna o município que satisfaz o parametro id. Caso não encontre o
	 *         município pelo id, retornará null.
	 */
	Municipio buscarPorIbgeId(String ibgeId);

	/**
	 * 
	 * @param nome
	 * @param ufType
	 * @return
	 */
	List<Municipio> buscarPeloNomeIndexado(String nome, UfType ufType);

	/**
	 * 
	 * @param nome
	 * @param ufType
	 * @return
	 */
	List<Municipio> buscarAtivosPeloNomeIndexado(String nome, UfType ufType);

}
