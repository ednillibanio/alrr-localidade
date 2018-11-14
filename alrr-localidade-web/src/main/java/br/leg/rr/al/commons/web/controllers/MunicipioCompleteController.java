package br.leg.rr.al.commons.web.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.ejb.MunicipioLocal;
import br.leg.rr.al.localidade.jpa.Municipio;

@Named
@RequestScoped
public class MunicipioCompleteController {

	@EJB
	private MunicipioLocal bean;

	/**
	 * Método usado para buscar Localidades. A busca é realizada por parte do nome
	 * informado.
	 * 
	 * @param nome
	 *            atributo nome da entidade municipio
	 * @return lista de localidades. <code>null </code> se nenhum encontrado.
	 */
	public List<Municipio> completeByNome(String nome) {
		if (StringUtils.isNotBlank(nome) && (!nome.equals(" - "))) {
			try {
				if (StringUtils.contains(nome, "-")) {
					String suf = StringUtils.trim(StringUtils.substringAfter(nome, "-"));
					String snome = StringUtils.trim(StringUtils.substringBefore(nome, "-"));
					if (suf.length() == 2) {
						UfType uf = UfType.valueOf(suf.toUpperCase());
						return bean.buscarPorNome(snome, uf);
					}
				}
				return bean.buscarPorNome(nome);
			} catch (BeanException e) {
				FacesMessageUtils.addFatal(e.getMessage());
				e.printStackTrace();
			}

		}

		return null;
	}

}
