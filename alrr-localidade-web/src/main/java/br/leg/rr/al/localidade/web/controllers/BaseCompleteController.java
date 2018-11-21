package br.leg.rr.al.localidade.web.controllers;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.dao.JPADaoStatus;
import br.leg.rr.al.core.jpa.EntityStatus;
import br.leg.rr.al.core.web.util.FacesMessageUtils;

public abstract class BaseCompleteController<T extends EntityStatus<ID>, ID extends Serializable> {

	private JPADaoStatus<T, ID> bean;

	public BaseCompleteController() {

	}

	/**
	 * Definir o bean que irá buscar as entidades por nome;
	 */
	public abstract void init();

	/**
	 * Método usado para buscar Localidades. A busca é realizada por parte do
	 * nome informado.
	 * 
	 * @param nome
	 * @return lista de localidades. <code>null </code> se nenhum encontrado.
	 */
	public List<T> completeByNome(String nome) {
		if (StringUtils.isNotBlank(nome) && (!nome.equals(" - "))) {
			try {
				return getBean().buscarPorNome(nome);
			} catch (BeanException e) {
				FacesMessageUtils.addFatal(e.getMessage());
				e.printStackTrace();
			}

		}

		return null;
	}

	public JPADaoStatus<T, ID> getBean() {
		return bean;
	}

	public void setBean(JPADaoStatus<T, ID> bean) {
		this.bean = bean;
	}
}
