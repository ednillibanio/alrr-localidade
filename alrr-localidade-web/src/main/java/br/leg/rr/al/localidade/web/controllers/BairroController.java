/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.localidade.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.leg.rr.al.core.CoreUtilsValidationMessages;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.web.controller.status.DialogControllerEntityStatus;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.ejb.BairroLocal;
import br.leg.rr.al.localidade.ejb.LuceneSearchRotina;
import br.leg.rr.al.localidade.jpa.Bairro;
import br.leg.rr.al.localidade.jpa.Municipio;

@Named
@ViewScoped
public class BairroController extends DialogControllerEntityStatus<Bairro, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 510705009675249799L;

	Logger logger = LoggerFactory.getLogger(BairroController.class);

	LuceneSearchRotina luceneSearchRotina;
	/**
	 * valor do filtro 'nome' do pesquisar municipio.
	 */
	private String nome;

	private List<Municipio> municipiosSelecionados;

	private String municipio;

	/**
	 * filtro das UFs selecionadas para pesquisar municipio.
	 */
	private List<UfType> uFsSelecionadas;

	private StatusType situacao;

	@EJB
	private BairroLocal bean;

	@PostConstruct
	public void init() {
		setBean(bean);
		/*
		 * luceneSearchRotina = new LuceneSearchRotina(); luceneSearchRotina.init();
		 */
		jaExisteMsg = "Bairro já existe.";
		setNovoDialogName("dlg-bairro");
		setEditarDialogName("dlg-bairro");
		setDetalhesDialogName("dlg-bairro-detalhes");
	}

	/**
	 * Método usado para buscar bairros na entidade Bairro. A busca é realizada por
	 * parte do nome do bairro informado.
	 * 
	 * @param nome nome do bairro.
	 * @return lista de bairros. <code>null </code> se nenhum encontrado.
	 */
	public List<Bairro> completeByNome(String nome) {
		if (StringUtils.isNotBlank(nome) && (!nome.equals(" - "))) {
			try {
				return bean.buscarPorNome(nome);
			} catch (BeanException e) {
				FacesMessageUtils.addFatal(e.getMessage());
				e.printStackTrace();
			}

		}

		return null;
	}

	@Override
	public String pesquisar() {

		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(BairroLocal.PESQUISAR_PARAM_NOME, nome);
		filtros.put(BairroLocal.PESQUISAR_PARAM_UFS, uFsSelecionadas);
		filtros.put(BairroLocal.PESQUISAR_PARAM_MUNICIPIO_NOME, municipio);
		filtros.put(BairroLocal.PESQUISAR_PARAM_SITUACAO, situacao);

		try {
			setEntities(bean.pesquisar(filtros));
			if (getEntities().size() > 0) {
				createDataModel(getEntities());
			} else {
				FacesMessageUtils.addError(CoreUtilsValidationMessages.REGISTRO_NAO_ENCONTRADO);
			}
		} catch (Exception e) {
			FacesMessageUtils.addFatal(e.getMessage());

		}

		return null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Municipio> getMunicipiosSelecionados() {
		return municipiosSelecionados;
	}

	public void setMunicipiosSelecionados(List<Municipio> municipiosSelecionados) {
		this.municipiosSelecionados = municipiosSelecionados;
	}

	public List<UfType> getuFsSelecionadas() {
		return uFsSelecionadas;
	}

	public void setuFsSelecionadas(List<UfType> uFsSelecionadas) {
		this.uFsSelecionadas = uFsSelecionadas;
	}

	public StatusType getSituacao() {
		return situacao;
	}

	public void setSituacao(StatusType situacao) {
		this.situacao = situacao;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

}