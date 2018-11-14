package br.leg.rr.al.commons.web.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.leg.rr.al.core.CoreUtilsValidationMessages;
import br.leg.rr.al.core.utils.StringHelper;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.core.web.util.FacesUtils;
import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.ejb.MunicipioLocal;
import br.leg.rr.al.localidade.ibge.ejb.IbgeMunicipioLocal;
import br.leg.rr.al.localidade.ibge.jpa.IbgeMunicipio;
import br.leg.rr.al.localidade.jpa.Municipio;
import br.leg.rr.al.localidade.utils.MunicipioUtils;

@Named
@ViewScoped
public class IbgeMunicipioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5501394136141782069L;

	Logger logger = LoggerFactory.getLogger(IbgeMunicipioController.class);

	@EJB
	private MunicipioLocal bean;

	@EJB
	private IbgeMunicipioLocal ibgeBean;

	private Integer progress = 0;
	private Integer tamanho;
	private Integer soma;
	private Boolean cancelado = false;
	private Boolean start = false;

	@PostConstruct
	public void init() {

	}

	public void importar() {

		try {
			List<IbgeMunicipio> municipios = ibgeBean.buscarMunicipios();

			if (municipios != null) {
				tamanho = municipios.size();
				soma = 0;
				progress = 0;
				String nome = null;
				UfType uf = null;
				Integer ibgeId = null;
				Municipio loc = null;
				for (IbgeMunicipio mun : municipios) {
					soma++;
					if (cancelado) {
						break;
					}
					nome = StringHelper.capitalizeFully(mun.getNome());
					uf = UfType.valueOf(mun.getUF().getSigla());
					ibgeId = mun.getId();

					// verifica se existe o municipio ibge já é cadastrado na base local.
					loc = bean.buscarPorIbgeId(ibgeId);
					if (loc != null) {

						// verifica se o nome sofreu
						// alterações no cadastro do ibge.
						if (!loc.getNome().toLowerCase().equals(nome.toLowerCase())) {

							loc.setNome(nome);
							// Se sofreu alterações, irá atualiza-lo na base local.
							bean.atualizar(loc);
						}

					} else {
						// verifica se o municipio já existe na base local. Esta condição é para
						// municipios que não possui ibge id.
						loc = bean.buscarPorUf(uf, nome);

						// cadastra um novo municipio ibge na base local
						if (loc == null) {
							loc = MunicipioUtils.converterIbgeMunicipioParaMunicipio(mun);
							bean.salvar(loc);

							// atualiza o cadastro do municipio local com o ibge id.
						} else if (loc != null && loc.getIbgeId() == null) {
							loc.setIbgeId(ibgeId);
							bean.atualizar(loc);
						}
					}
					progress = (soma * 100) / tamanho;
				}
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			FacesMessageUtils.addFatal(e.getMessage());
		} catch (ConnectException e) {
			logger.error(e.getMessage());
			FacesMessageUtils.addFatal(e.getMessage());

		} catch (IOException e) {
			logger.error(e.getMessage());
			FacesMessageUtils.addFatal(CoreUtilsValidationMessages.ERROR_503);
			FacesUtils.hideDialog("dlgImportarMunicipiosWV");

		} catch (Exception e) {
			logger.error(e.getMessage());
			FacesMessageUtils.addFatal(e.getMessage());
		}

	}

	public void cancelar() {
		if (start) {
			FacesMessageUtils.addWarn("A importação dos municípios foi cancelada!");
		}
		cancelado = true;
		soma = 0;
		tamanho = 0;
	}

	public void onComplete() {
		FacesMessageUtils.addInfo("Municípios importados com sucesso!");
		soma = 0;
		progress = 0;
		tamanho = 0;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public MunicipioLocal getBean() {
		return bean;
	}

	public void setBean(MunicipioLocal bean) {
		this.bean = bean;
	}

	public IbgeMunicipioLocal getIbgeBean() {
		return ibgeBean;
	}

	public void setIbgeBean(IbgeMunicipioLocal ibgeBean) {
		this.ibgeBean = ibgeBean;
	}

	public Boolean getStart() {
		return start;
	}

	public void setStart(Boolean start) {
		this.start = start;
	}

}
