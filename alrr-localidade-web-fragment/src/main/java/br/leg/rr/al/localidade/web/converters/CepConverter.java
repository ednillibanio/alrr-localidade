package br.leg.rr.al.localidade.web.converters;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import br.leg.rr.al.localidade.utils.CepUtils;

@Named
@RequestScoped
public class CepConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6530116112672863422L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		return CepUtils.unformat(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return CepUtils.format((String) value);
	}

}
