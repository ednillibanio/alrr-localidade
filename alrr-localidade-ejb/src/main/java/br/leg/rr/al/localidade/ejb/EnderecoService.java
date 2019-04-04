package br.leg.rr.al.localidade.ejb;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;

import br.leg.rr.al.localidade.jpa.Bairro;
import br.leg.rr.al.localidade.jpa.Cep;
import br.leg.rr.al.localidade.jpa.Endereco;

@Named
@Stateless
public class EnderecoService implements EnderecoLocal, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3567552233130001152L;

	@EJB
	private MunicipioLocal localidadeBean;

	@EJB
	private BairroLocal bairroBean;

	@EJB
	private CepLocal cepBean;

	@Override
	public Endereco criarEnderecoPorCep(Cep cep) {
		Endereco end = null;
		if (cep != null) {
			end = new Endereco();
			end.setMunicipio(cep.getMunicipio());
			end.setCep(cep.getNumero());
			end.setLogradouro(cep.getLogradouro());
			if (cep.getBairro() != null) {
				end.setBairro(cep.getBairro());
			}

		}
		return end;

	}

	@Override
	public void preencherEnderecoPeloBairro(Endereco endereco) {

		Bairro bairro = endereco.getBairro();

		if (bairro != null && bairro.getMunicipio() != null) {
			endereco.setMunicipio(bairro.getMunicipio());
		}

	}

	@Override
	public Endereco buscarEnderecoPorCep(String numero) throws Exception {

		Cep cep = null;

		cep = cepBean.buscarCep(numero);
		return criarEnderecoPorCep(cep);

	}

	@Override
	public void limparEndereco(Endereco endereco) {

		endereco.setCep(null);
		endereco.setBairro(null);
		endereco.setMunicipio(null);
		endereco.setComplemento(null);
		endereco.setLogradouro(null);
		endereco.setNumero(null);

	}

}
