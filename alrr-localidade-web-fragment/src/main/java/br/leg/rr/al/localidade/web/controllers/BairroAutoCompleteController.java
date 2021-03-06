package br.leg.rr.al.localidade.web.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.leg.rr.al.core.web.controller.AutoCompleteIndexadoController;
import br.leg.rr.al.localidade.ejb.BairroLocal;
import br.leg.rr.al.localidade.jpa.Bairro;

@Named
@RequestScoped
public class BairroAutoCompleteController extends AutoCompleteIndexadoController<Bairro> {

	@EJB
	private BairroLocal bean;

	@Override
	@PostConstruct
	public void init() {
		setBean(bean);

	}
}
