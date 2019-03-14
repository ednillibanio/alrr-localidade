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
import br.leg.rr.al.localidade.ejb.MunicipioLocal;
import br.leg.rr.al.localidade.jpa.Municipio;
import br.leg.rr.al.localidade.jpa.UnidadeFederativa;

@Named
@ViewScoped
public class MunicipioController extends DialogControllerEntityStatus<Municipio, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7882112613545512515L;

	Logger logger = LoggerFactory.getLogger(MunicipioController.class);

	@EJB
	private MunicipioLocal bean;

	// ************ FILTROS DE PESQUISA ************//
	/**
	 * valor do filtro 'nome' do pesquisar municipio.
	 */
	private String nome;

	/**
	 * filtro das UFs selecionadas para pesquisar municipio.
	 */
	private List<UnidadeFederativa> uFsSelecionadas;

	private StatusType situacao;
	// ********************************************//

	@PostConstruct
	public void init() {
		setBean(bean);

		jaExisteMsg = "Município já existe.";
		setNovoDialogName("dlg-municipio");
		setEditarDialogName("dlg-municipio");
		setDetalhesDialogName("dlg-municipio-detalhes");
	}

	@Override
	public void prePesquisar() {

		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(MunicipioLocal.PESQUISAR_PARAM_NOME, nome);
		filtros.put(MunicipioLocal.PESQUISAR_PARAM_UFS, uFsSelecionadas);
		filtros.put(MunicipioLocal.PESQUISAR_PARAM_SITUACAO, situacao);

		setFiltros(filtros);
	}

	/**
	 * Remove as ufs selecionadas do filtro ufsSelecionadas
	 */
	public void removerUfsSelecionadas() {
		if (uFsSelecionadas != null) {
			uFsSelecionadas.clear();
		}
	}

	/**
	 * Transformar primeira letra de cada palavra em maiúscula.
	 */
	public void capitalizeNome() {
		String nome = StringHelper.capitalizeFully(getEntity().getNome());
		getEntity().setNome(nome);
	}

	/**
	 * @return filtro das UFs selecionadas para pesquisar municipio.
	 */
	public List<UnidadeFederativa> getuFsSelecionadas() {
		return uFsSelecionadas;
	}

	/**
	 * @param filtro das UFs selecionadas para pesquisar municipio.
	 */
	public void setuFsSelecionadas(List<UnidadeFederativa> uFsSelecionadas) {
		this.uFsSelecionadas = uFsSelecionadas;
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
