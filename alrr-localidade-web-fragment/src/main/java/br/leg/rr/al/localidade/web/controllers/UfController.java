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
import br.leg.rr.al.localidade.ejb.UnidadeFederativaLocal;
import br.leg.rr.al.localidade.jpa.Pais;
import br.leg.rr.al.localidade.jpa.UnidadeFederativa;

@Named
@ViewScoped
public class UfController extends DialogControllerEntityStatus<UnidadeFederativa, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5802114735950762438L;

	Logger logger = LoggerFactory.getLogger(UfController.class);

	@EJB
	private UnidadeFederativaLocal bean;

	// ************ FILTROS DE PESQUISA ************//
	/**
	 * valor do filtro 'nome' do pesquisar uf.
	 */
	private String nome;

	private List<Pais> paisesSelecionados;

	private StatusType situacao;
	// ********************************************//

	@PostConstruct
	public void init() {
		setBean(bean);

		jaExisteMsg = "Unidade Federativa já existe.";
		setNovoDialogName("dlg-uf");
		setEditarDialogName("dlg-uf");
		setDetalhesDialogName("dlg-uf-detalhes");
	}

	@Override
	public void prePesquisar() {

		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(UnidadeFederativaLocal.PESQUISAR_PARAM_NOME, nome);
		filtros.put(UnidadeFederativaLocal.PESQUISAR_PARAM_PAISES, paisesSelecionados);
		filtros.put(UnidadeFederativaLocal.PESQUISAR_PARAM_SITUACAO, situacao);

		setFiltros(filtros);
	}

	/**
	 * Transformar primeira letra de cada palavra em maiúscula.
	 */
	public void capitalizeNome() {
		String nome = StringHelper.capitalizeFully(getEntity().getNome());
		getEntity().setNome(nome);
	}

	/**
	 * @return valor do filtro 'nome' da pesquisa municipio.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param valor do filtro 'nome' da pesquisa municipio.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	public StatusType getSituacao() {
		return situacao;
	}

	public void setSituacao(StatusType situacao) {
		this.situacao = situacao;
	}

	public List<Pais> getPaisesSelecionados() {
		return paisesSelecionados;
	}

	public void setPaisesSelecionados(List<Pais> paisesSelecionados) {
		this.paisesSelecionados = paisesSelecionados;
	}

}
