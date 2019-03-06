package br.leg.rr.al.localidade.web.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.leg.rr.al.core.web.controller.AutoCompleteIndexadoController;
import br.leg.rr.al.localidade.ejb.UnidadeFederativaLocal;
import br.leg.rr.al.localidade.jpa.UnidadeFederativa;

@Named
@RequestScoped
public class UfAutoCompleteController extends AutoCompleteIndexadoController<UnidadeFederativa> {

	@EJB
	private UnidadeFederativaLocal bean;

	@Override
	@PostConstruct
	public void init() {
		setBean(bean);

	}

}
