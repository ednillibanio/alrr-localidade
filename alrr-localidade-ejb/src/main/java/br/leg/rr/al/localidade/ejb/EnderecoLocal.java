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
	 * Preenche o endereço de acordo com o <code>cep</code> informado, As entidades
	 * dependentes de <code>Endereco</code> serão carregadas (fetch).
	 * 
	 * @param cep entidade que contém os dados para serem pesquisados nas entidades
	 *            Bairro e Municipio.
	 * @return entidade Endereço contendo as informações coletadas do param
	 *         <code>cep</code>. Retorna <code>null</code> caso contrário.
	 */
	public Endereco criarEnderecoPorCep(Cep cep);

	/**
	 * Método secundário utilizado apenas para pegar o municipio do bairro e
	 * atribuir no campo municipio do endereco.
	 * 
	 * @param endereco
	 */
	void preencherEnderecoPeloBairro(Endereco endereco);

	/**
	 * Busca o endereço pelo número do cep informado.
	 * 
	 * @param numero numero do cep a ser pesquisado
	 * @return retorna endereco. Caso não encontre o cep, retorna null.
	 * @throws Exception
	 */

	Endereco buscarEnderecoPorCep(String numero) throws Exception;

	/**
	 * Limpa os valores do endereco. Geralmente usa esse método caso o usuário
	 * marque por exemplo, um check pra indicar se o endereço possui cep ou não. Se
	 * o usuário marcar o campo semCep, então o sistema deve limpar todos os valores
	 * do endereço. Esse procedimento evita que um endereço inválido seja salvo.
	 * 
	 * @param endereco
	 */
	void limparEndereco(Endereco endereco);

}
