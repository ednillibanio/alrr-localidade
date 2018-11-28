package br.leg.rr.al.localidade.ejb;

import java.util.List;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.dao.JPADaoStatus;
import br.leg.rr.al.localidade.jpa.Bairro;

@Local
public interface BairroLocal extends JPADaoStatus<Bairro, Integer> {

	/**
	 * Busca entidade {@code Bairro} pelo campo "nome".<br>
	 * 
	 * @value {@literal String};
	 */
	String PESQUISAR_PARAM_NOME = "nome";

	/**
	 * Busca pelo campo "uf" da entidade {@code Bairro}.<br>
	 * 
	 * @value {@literal List<UfType>};
	 */
	String PESQUISAR_PARAM_UFS = "ufs";

	/**
	 * Busca os bairros que contém parte do nome informado.
	 * 
	 * @param nome nome do bairro a ser pesquisado.
	 * @return lista de nomes que satisfazem o <param>nome</param> informado.
	 * @throws ControllerException
	 */
	@Override
	public List<Bairro> buscarPorNome(String nome) throws BeanException;

	/**
	 * Busca o Bairro pelo id da entidade municipio, e pelo nome do bairro.
	 * 
	 * @param municipioId id da entidade municipio.
	 * @param nome  nome do bairro.
	 * @return bairro encontrado de acordo com os valores informados nos parametros.
	 * @throws ControllerException
	 */
	public Bairro buscarPorMunicipioId(Integer municipioId, String nome) throws BeanException;

}
