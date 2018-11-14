package br.leg.rr.al.commons.web.controllers;

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
import br.leg.rr.al.core.web.controller.DialogCrudViewController;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.ejb.MunicipioLocal;
import br.leg.rr.al.localidade.jpa.Municipio;

@Named
@ViewScoped
public class MunicipioController extends DialogCrudViewController<Municipio, Integer> {

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
	private List<UfType> uFsSelecionadas;

	private StatusType situacao;
	// ********************************************//

	@PostConstruct
	public void init() {
		setBean(bean);

		jaExisteMsg = "Município já existe.";
		setNovoDialogName("dlg-municipio");
		setEditarDialogName("dlg-municipio");
	}

	@Override
	public String pesquisar() {

		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(MunicipioLocal.PESQUISAR_PARAM_NOME, nome);
		filtros.put(MunicipioLocal.PESQUISAR_PARAM_UFS, uFsSelecionadas);
		filtros.put(MunicipioLocal.PESQUISAR_PARAM_SITUACAO, situacao);

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

	/**
	 * Método usado para buscar Localidades. A busca é realizada por parte do nome
	 * informado.
	 * 
	 * @param nome
	 * @return lista de localidades. <code>null </code> se nenhum encontrado.
	 */
	public List<Municipio> completeByNome(String nome) {
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

	/**
	 * @return filtro das UFs selecionadas para pesquisar municipio.
	 */
	public List<UfType> getuFsSelecionadas() {
		return uFsSelecionadas;
	}

	/**
	 * @param filtro das UFs selecionadas para pesquisar municipio.
	 */
	public void setuFsSelecionadas(List<UfType> uFsSelecionadas) {
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
