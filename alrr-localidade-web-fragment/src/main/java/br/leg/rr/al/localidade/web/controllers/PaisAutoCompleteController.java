package br.leg.rr.al.localidade.web.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

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

	/**
	 * Método que busca as entidades por nome indexado usando
	 * hibernate-search-annotation.
	 * 
	 * @param nome valor a ser pesquisado na lista de nomes indexados.
	 * @return lista de entidades encontradas ou null caso contrário.
	 */
	public List<Pais> completarPorNacionalidadeIndexado(String nome) {
		if (StringUtils.isNotBlank(nome)) {

			return bean.buscarPorNacionalidadeIndexado(nome);

		}

		return null;
	}
}
