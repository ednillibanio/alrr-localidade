package br.leg.rr.al.localidade.web.controllers;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.leg.rr.al.core.web.util.FacesUtils;

@Named
@RequestScoped
public class LogoutController {

	public String logout() {

		HttpServletRequest req = FacesUtils.getServletRequest();
		HttpServletResponse res = FacesUtils.getServletResponse();
		try {
			req.logout();
			// FacesUtils.redirect(req.getContextPath());
			res.sendRedirect(req.getContextPath());

		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "home";
	}
}
