package br.leg.rr.al.commons.web.controllers;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.localidade.ejb.BairroLocal;
import br.leg.rr.al.localidade.ejb.CepLocal;
import br.leg.rr.al.localidade.ejb.EnderecoLocal;
import br.leg.rr.al.localidade.ejb.MunicipioLocal;
import br.leg.rr.al.localidade.jpa.Bairro;
import br.leg.rr.al.localidade.jpa.Cep;
import br.leg.rr.al.localidade.jpa.Endereco;
import br.leg.rr.al.localidade.jpa.Municipio;

@Named
@ViewScoped
public class EnderecoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8778872964198415981L;

	private Endereco endereco;

	private Boolean semCep = false;

	@EJB
	private MunicipioLocal localidadeBean;

	@EJB
	private BairroLocal bairroBean;

	@EJB
	private CepLocal cepBean;

	@EJB
	private EnderecoLocal enderecoBean;

	/**
	 * Método usado para buscar Localidades. A busca é realizada por parte do nome
	 * informado.
	 * 
	 * @param nome
	 *            atributo nome da entidade municipio
	 * @return lista de localidades. <code>null </code> se nenhum encontrado.
	 */
	public List<Municipio> completeLocalidadePorNome(String nome) {
		if (StringUtils.isNotBlank(nome) && (!nome.equals(" - "))) {
			try {
				return localidadeBean.buscarPorNome(nome);
			} catch (BeanException e) {
				FacesMessageUtils.addFatal(e.getMessage());
				e.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * Método usado para buscar bairros na entidade Bairro. A busca é realizada por
	 * parte do nome do bairro informado.
	 * 
	 * @param nome
	 *            nome do bairro.
	 * @return lista de bairros. <code>null </code> se nenhum encontrado.
	 */
	public List<Bairro> completeBairroPorNome(String nome) {
		if (StringUtils.isNotBlank(nome) && (!nome.equals(" - "))) {
			try {
				return getBairroBean().buscarPorNome(nome);
			} catch (BeanException e) {
				FacesMessageUtils.addFatal(e.getMessage());
				e.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * Busca o Endereço pelo Cep informado.
	 * 
	 */
	public void buscarEnderecoPorCep() {

		Cep cep = getCepBean().buscarCep(getEndereco().getCep());
		Endereco end = enderecoBean.preencherEnderecoPorCep(cep);
		if (end != null) {
			setEndereco(end);
		} else {
			FacesMessageUtils.addError("Não foi possível localizar o endereço pelo cep: " + getEndereco().getCep());
		}
	}

	/**
	 * Preenche a Municipio do Endereço a partir do Bairro selecionado.
	 * 
	 */
	public void preencherLocalidadePeloBairro(SelectEvent event) {
		Bairro bairro = getEndereco().getBairro();
		if (bairro != null && bairro.getMunicipio() != null) {
			getEndereco().setMunicipio(bairro.getMunicipio());
		}

	}

	/**
	 * Limpa o campo </code>cep</code> do endereco caso o valor do
	 * <code>semCep</code> seja true. Isso significa que não existe cep para o
	 * endereço informado. Esse procedimento evita que um cep invalido seja salvo.
	 */
	public void limparCep() {
		if (semCep == true) {
			getEndereco().setCep(null);
		} else {
			getEndereco().setBairro(null);
			getEndereco().setMunicipio(null);
			getEndereco().setComplemento(null);
			getEndereco().setLogradouro(null);
			getEndereco().setNumero(null);
		}
	}

	/**
	 * 
	 * @param evt
	 *//*
		 * public void buscarLocalidadePorBairro(AjaxBehaviorEvent event) { Bairro
		 * bairro = getEndereco().getBairro(); if (bairro != null &&
		 * bairro.getLocalidade() != null) {
		 * getEndereco().setLocalidade(bairro.getLocalidade()); }
		 * 
		 * }
		 */

	public CepLocal getCepBean() {
		return cepBean;
	}

	public void setCepBean(CepLocal cepBean) {
		this.cepBean = cepBean;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public BairroLocal getBairroBean() {
		return bairroBean;
	}

	public void setBairroBean(BairroLocal bairroBean) {
		this.bairroBean = bairroBean;
	}

	public Boolean getSemCep() {
		return semCep;
	}

	public void setSemCep(Boolean semCep) {
		this.semCep = semCep;
	}

}
