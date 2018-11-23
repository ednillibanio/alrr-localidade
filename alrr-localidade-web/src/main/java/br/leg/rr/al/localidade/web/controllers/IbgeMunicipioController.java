package br.leg.rr.al.localidade.web.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.commons.lang3.exception.ExceptionUtils;
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

	@Resource
	UserTransaction utx;

	@EJB
	private IbgeMunicipioLocal ibgeBean;

	private Integer progress = 0;
	private Integer tamanho;
	private Integer soma;
	private Boolean cancelado = false;
	private Boolean start = false;
	private String statusMessage;

	@PostConstruct
	public void init() {

	}

	public void importar() {

		try {
			progress = 0;
			// tem que deixar esse espaço dentro do statusMessage
			setStatusMessage(" preparando para a importação... aguarde...");

			List<IbgeMunicipio> municipios = ibgeBean.buscarMunicipios();

			if (municipios != null) {
				try {

					utx.begin();

					tamanho = municipios.size();
					soma = 0;
					cancelado = false;
					String nome = null;
					UfType uf = null;
					Integer ibgeId = null;
					Municipio loc = null;
					setStatusMessage(" &#8212; importando municípios do IBGE...");

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

					// cancela toda a transação de inserção ou atualização caso tenha cancelado a
					// operação.
					if (cancelado && utx.getStatus() != Status.STATUS_NO_TRANSACTION) {
						utx.rollback();
					} else {
						utx.commit();
					}

				} catch (NotSupportedException | SystemException e) {
					logger.error(e.getMessage());
				}
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			FacesMessageUtils.addFatal(e.getMessage());
		} catch (ConnectException e) {
			logger.error(e.getMessage());
			FacesMessageUtils.addFatal("Servidor IBGE temporariamente indisponível. Tente mais tarde.");
		} catch (IOException e) {
			// FIXME melhorar esse tratamento, pois pode retornar o código 500
			String msg = ExceptionUtils.getMessage(e);
			logger.error(msg);
			FacesMessageUtils.addFatal(CoreUtilsValidationMessages.ERROR_503);

		} catch (Exception e) {
			logger.error(e.getMessage());
			FacesMessageUtils.addFatal(e.getMessage());
		} finally {
			start = false;
		}

	}

	public void cancelar() {
		if (start) {
			FacesMessageUtils.addWarn("A importação dos municípios foi cancelada!");
			setStatusMessage(" &#8212; importação cancelada.");
			FacesUtils.execute("PF('progressBarWV').cancel();");
		}
		progress = 0;
		cancelado = true;
		FacesUtils.update("frm-municipios-ibge:progressBarId");
		FacesUtils.update("frm-municipios-ibge:growl");
	}

	public void onComplete() {
		FacesMessageUtils.addInfo("Municípios importados com sucesso!");
		setStatusMessage(" &#8212; Municípios importados com sucesso!");
		FacesUtils.update("frm-municipios-ibge:progressBarId");
		FacesUtils.update("frm-municipios-ibge:growl");
	}

	public Integer getProgress() {
		FacesUtils.update("frm-municipios-ibge:progressBarId");
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

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
