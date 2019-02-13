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

import br.leg.rr.al.core.CoreUtilsValidationMessages;
import br.leg.rr.al.core.web.controller.DialogController;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.ejb.CepLocal;
import br.leg.rr.al.localidade.jpa.Cep;

@Named
@ViewScoped
public class CepController extends DialogController<Cep, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7882112613545512515L;

	Logger logger = LoggerFactory.getLogger(CepController.class);

	@EJB
	private CepLocal bean;

	// ************ FILTROS DE PESQUISA ************//
	private String numero;

	private String logradouro;

	private String bairro;

	/**
	 * valor do filtro 'municipio' do pesquisar Cep.
	 */
	private String municipio;

	/**
	 * filtro das UFs selecionadas para pesquisar Cep.
	 */
	private List<UfType> uFsSelecionadas;

	@PostConstruct
	public void init() {
		setBean(bean);

		jaExisteMsg = "Cep já existe.";
		setNovoDialogName("dlg-cep");
		setEditarDialogName("dlg-cep");
		setDetalhesDialogName("dlg-cep-detalhes");
	}

	@Override
	public String pesquisar() {

		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(CepLocal.PESQUISAR_PARAM_NUMERO, numero);
		filtros.put(CepLocal.PESQUISAR_PARAM_MUNICIPIO, getMunicipio());
		filtros.put(CepLocal.PESQUISAR_PARAM_UFS, uFsSelecionadas);
		filtros.put(CepLocal.PESQUISAR_PARAM_BAIRRO, getBairro());
		filtros.put(CepLocal.PESQUISAR_PARAM_LOGRADOURO, logradouro);

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

	@Override
	public String novo() {
		numero = null;
		return super.novo();
	}

	@Override
	protected void posInserir() {
		numero = null;
		super.posInserir();
	}

	@Override
	protected void posEditar() {
		super.posEditar();
		this.numero = getEntity().getNumero();
	}

	/**
	 * Busca Cep informado.
	 * 
	 */
	public void buscarCep() {

		Cep cep;
		try {
			cep = bean.buscarCepWS(numero);

			if (cep == null) {
				FacesMessageUtils.addError("Não foi possível encontrar o cep: " + numero);
			} else {
				setEntity(cep);
			}
		} catch (Exception e) {
			FacesMessageUtils.addFatal(e.getMessage());
		}

	}

	/**
	 * @return filtro das UFs selecionadas para pesquisar Cep.
	 */
	public List<UfType> getuFsSelecionadas() {
		return uFsSelecionadas;
	}

	/**
	 * @param filtro das UFs selecionadas para pesquisar Cep.
	 */
	public void setuFsSelecionadas(List<UfType> uFsSelecionadas) {
		this.uFsSelecionadas = uFsSelecionadas;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

}
