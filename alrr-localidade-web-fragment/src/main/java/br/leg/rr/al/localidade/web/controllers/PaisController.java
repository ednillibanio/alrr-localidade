package br.leg.rr.al.localidade.web.controllers;

import java.util.HashMap;
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
import br.leg.rr.al.localidade.ejb.MunicipioLocal;
import br.leg.rr.al.localidade.ejb.PaisLocal;
import br.leg.rr.al.localidade.jpa.Pais;

@Named
@ViewScoped
public class PaisController extends DialogControllerEntityStatus<Pais, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7882112613545512515L;

	Logger logger = LoggerFactory.getLogger(PaisController.class);

	@EJB
	private PaisLocal bean;

	// ************ FILTROS DE PESQUISA ************//
	/**
	 * valor do filtro 'nome' do pesquisar municipio.
	 */
	private String nome;

	private StatusType situacao;
	// ********************************************//

	@PostConstruct
	public void init() {
		setBean(bean);

		jaExisteMsg = "Pais já existe.";
		setNovoDialogName("dlg-pais");
		setEditarDialogName("dlg-pais");
		setDetalhesDialogName("dlg-pais-detalhes");
	}

	@Override
	public void prePesquisar() {

		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(MunicipioLocal.PESQUISAR_PARAM_NOME, nome);
		filtros.put(MunicipioLocal.PESQUISAR_PARAM_SITUACAO, situacao);

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
	 * Transformar primeira letra de cada palavra em maiúscula.
	 */
	public void capitalizeNacionalidade() {
		String texto = StringHelper.capitalizeFully(getEntity().getNacionalidade());
		getEntity().setNacionalidade(texto);
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

}
