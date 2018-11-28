package br.leg.rr.al.localidade.ejb;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.JPADao;
import br.leg.rr.al.localidade.jpa.Cep;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 09-04-2017
 */
@Local
public interface CepLocal extends JPADao<Cep, Integer> {

	/**
	 * Busca entidade {@code Cep} pelo campo "numero".<br>
	 * 
	 * @value {@literal String};
	 */
	String PESQUISAR_PARAM_NUMERO = "numero";

	/**
	 * Busca entidade {@code Cep} pelo campo "municipio".<br>
	 * 
	 * @value {@literal String};
	 */
	String PESQUISAR_PARAM_MUNICIPIO = "municipio";

	/**
	 * Busca pelo campo "uf" da entidade {@code Cep}.<br>
	 * 
	 * @value {@literal List<UfType>};
	 */
	String PESQUISAR_PARAM_UFS = "ufs";

	/**
	 * Busca pelo campo "bairro" da entidade {@code Cep}.<br>
	 * 
	 * @value {@literal String};
	 */
	String PESQUISAR_PARAM_BAIRRO = "bairro";

	/**
	 * Busca pelo campo "logradouro" da entidade {@code Cep}.<br>
	 * 
	 * @value {@literal String};
	 */
	String PESQUISAR_PARAM_LOGRADOURO = "logradouro";

	/**
	 * <p>
	 * Método que busca o cep tanto nos correios quanto na base local. Primeiramente
	 * busca o cep na base local. Caso não o encontre, irá pesquisar via web
	 * service. <br>
	 * Toda consulta web service que o cep informado for encontrado, será salvo na
	 * base de dados local. Incluindo as Localidades e Bairros.
	 * </p>
	 * 
	 * @see #buscarCepBaseLocal(String)
	 * @see #buscarCepWS(String)
	 * @param cep
	 * @return retorna o endereço ou nulo caso não encontre.
	 * @throws Exception
	 */
	public Cep buscarCep(String cep) throws Exception;

	/**
	 * Método que busca o cep na base de dados local.
	 * 
	 * @param cep valor a ser consultado. O valor poderá ser formatado. Será tratado
	 *            dentro do método.
	 * @return Cep encontrado na base de dados local. Caso contrário,
	 *         <code>null</coce>.
	 */
	public Cep buscarCepBaseLocal(String cep);

	/**
	 * Método que busca o cep via web service.
	 * 
	 * @param cep valor a ser consultado. O valor poderá ser formatado. Será tratado
	 *            dentro do método.
	 * @return Cep encontrado na base de dados local. Caso contrário,
	 *         <code>null</coce>.
	 * @throws Exception
	 */
	public Cep buscarCepWS(String cep) throws Exception;
}
