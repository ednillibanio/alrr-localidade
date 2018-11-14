/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.commons.web.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.web.controller.DialogCrudViewController;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.localidade.ejb.BairroLocal;
import br.leg.rr.al.localidade.jpa.Bairro;

@Named
@ViewScoped
public class BairroController extends DialogCrudViewController<Bairro, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 510705009675249799L;

	@EJB
	private BairroLocal bean;

	@PostConstruct
	public void init() {
		setBean(bean);
		setNovoDialogName("dlg-bairro");
		setEditarDialogName("dlg-bairro");
	}

	/**
	 * Método usado para buscar bairros na entidade Bairro. A busca é realizada por
	 * parte do nome do bairro informado.
	 * 
	 * @param nome
	 *            nome do bairro.
	 * @return lista de bairros. <code>null </code> se nenhum encontrado.
	 */
	public List<Bairro> completeByNome(String nome) {
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

}
