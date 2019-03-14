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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.utils.StringHelper;
import br.leg.rr.al.core.web.controller.status.DialogControllerEntityStatus;
import br.leg.rr.al.localidade.ejb.BairroLocal;
import br.leg.rr.al.localidade.ejb.LuceneSearchRotina;
import br.leg.rr.al.localidade.jpa.Bairro;
import br.leg.rr.al.localidade.jpa.Municipio;
import br.leg.rr.al.localidade.jpa.UnidadeFederativa;

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
	private List<UnidadeFederativa> uFsSelecionadas;

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
	 * Transformar primeira letra de cada palavra em maiúscula.
	 */
	public void capitalizeNome() {
		String nome = StringHelper.capitalizeFully(getEntity().getNome());
		getEntity().setNome(nome);

	}

	public void removerUfsSelecionadas() {
		if (uFsSelecionadas != null) {
			uFsSelecionadas.clear();
		}
	}

	@Override
	public void prePesquisar() {

		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(BairroLocal.PESQUISAR_PARAM_NOME, nome);
		filtros.put(BairroLocal.PESQUISAR_PARAM_UFS, uFsSelecionadas);
		filtros.put(BairroLocal.PESQUISAR_PARAM_MUNICIPIO_NOME, municipio);
		filtros.put(BairroLocal.PESQUISAR_PARAM_SITUACAO, situacao);

		setFiltros(filtros);
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

	public List<UnidadeFederativa> getuFsSelecionadas() {
		return uFsSelecionadas;
	}

	public void setuFsSelecionadas(List<UnidadeFederativa> uFsSelecionadas) {
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