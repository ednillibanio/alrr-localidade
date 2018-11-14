package br.leg.rr.al.localidade.utils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.localidade.domain.UfType;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 19-04-2012
 */
@Named
@RequestScoped
public class LocalidadeEnumFactory {

	/**
	 * 
	 * @return lista de Unidades Federativas do Brasil.
	 */
	public UfType[] getUfs() {
		return UfType.values();
	}

	public StatusType[] getStatusType() {
		return StatusType.values();
	}

}
