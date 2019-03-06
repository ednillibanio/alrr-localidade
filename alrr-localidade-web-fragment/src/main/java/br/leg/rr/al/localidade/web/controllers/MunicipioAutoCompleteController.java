package br.leg.rr.al.localidade.web.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.leg.rr.al.core.web.controller.AutoCompleteIndexadoController;
import br.leg.rr.al.localidade.ejb.MunicipioLocal;
import br.leg.rr.al.localidade.jpa.Municipio;

@Named
@RequestScoped
public class MunicipioAutoCompleteController extends AutoCompleteIndexadoController<Municipio> {

	@EJB
	private MunicipioLocal bean;

	@Override
	@PostConstruct
	public void init() {
		setBean(bean);

	}

}
