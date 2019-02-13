package br.leg.rr.al.localidade.web.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.leg.rr.al.localidade.ejb.LuceneSearchLocal;

@Named
@ViewScoped
public class LuceneController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7451781218204241606L;

	@EJB
	LuceneSearchLocal bean;

	public String init() {
		bean.indexar();
		return null;
	}
}
