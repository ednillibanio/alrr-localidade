package br.leg.rr.al.localidade.ejb;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.localidade.jpa.Bairro;
import br.leg.rr.al.localidade.jpa.Cep;
import br.leg.rr.al.localidade.jpa.Endereco;
import br.leg.rr.al.localidade.jpa.Municipio;

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

	@Override
	public Endereco preencherEnderecoPorCep(Cep cep) {
		Endereco end = null;
		Bairro bairro = new Bairro();
		if (cep != null) {
			end = new Endereco();
			Municipio loc = localidadeBean.buscarPorUf(cep.getUf(), cep.getMunicipio());
			end.setMunicipio(loc);
			end.setCep(cep.getNumero());
			end.setLogradouro(cep.getLogradouro());
			if (StringUtils.isNotBlank(cep.getBairro())) {
				bairro = bairroBean.buscarPorLocalidadeId(loc.getId(), cep.getBairro());
				end.setBairro(bairro);
			}
		}
		return end;

	}

}
