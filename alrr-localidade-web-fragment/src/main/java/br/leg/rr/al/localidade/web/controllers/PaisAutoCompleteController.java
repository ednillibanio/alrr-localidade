package br.leg.rr.al.localidade.web.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.leg.rr.al.core.web.controller.AutoCompleteIndexadoController;
import br.leg.rr.al.localidade.ejb.PaisLocal;
import br.leg.rr.al.localidade.jpa.Pais;

@Named
@RequestScoped
public class PaisAutoCompleteController extends AutoCompleteIndexadoController<Pais> {

	@EJB
	private PaisLocal bean;

	@Override
	@PostConstruct
	public void init() {
		setBean(bean);

	}

}
