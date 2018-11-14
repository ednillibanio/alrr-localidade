package br.leg.rr.al.localidade.ejb;

import javax.ejb.Local;

import br.leg.rr.al.localidade.jpa.Cep;
import br.leg.rr.al.localidade.jpa.Endereco;

/**
 * 
 * @author ednil
 *
 */
@Local
public interface EnderecoLocal {

	/**
	 * Buscao o endereço de acordo com o argumento <code>cep</code>, e preenche a
	 * entidade <code>Endereco</code>. As entidades dependentes de
	 * <code>Endereco</code> serão carregadas (fetch).
	 * 
	 * @param cep
	 *            entidade que contém os dados para serem pesquisados nas entidades
	 *            Bairro e Municipio.
	 * @return entidade Endereço contendo as informações coletadas do param
	 *         <code>cep</code>. Retorna <code>null</code> caso contrário.
	 */
	public Endereco preencherEnderecoPorCep(Cep cep);
}
