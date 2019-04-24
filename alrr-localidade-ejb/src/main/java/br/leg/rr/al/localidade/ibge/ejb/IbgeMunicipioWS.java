/*******************************************************************************
 * ALE-RR Licença
 * Copyright (C) 2018, ALE-RR
 * Boa Vista, RR - Brasil
 * Todos os direitos reservados.
 * 
 * Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e 
 * não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.localidade.ibge.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.ibge.domain.IbgeMunicipio;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 10-04-2018
 */
@Named
@Stateless
public class IbgeMunicipioWS implements IbgeMunicipioLocal, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7570695428497812951L;

	private static final Logger logger = LoggerFactory.getLogger(IbgeMunicipioWS.class);

	@Override
	public List<IbgeMunicipio> buscarMunicipiosPorUF(UfType uf) throws IOException {
		return buscarMunicipios(uf);
	}

	private List<IbgeMunicipio> buscarMunicipios(UfType uf) throws IOException {

		BufferedReader br = null;
		URL url = null;

		try {
			if (uf != null) {
				url = new URL(URL_IBGE_MUNICIPIOS_POR_UFS + uf.getIbgeId() + "/municipios");
			} else {
				url = new URL(URL_IBGE_MUNICIPIOS);
			}

			Charset charset = Charset.forName("UTF-8");
			URLConnection urlConnection = url.openConnection();

			InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream(), charset);

			String jString = IOUtils.toString(isr);

			// br = new BufferedReader(isr);
			// StringBuilder jsonSb = new StringBuilder();
			// br.lines().forEach(l -> jsonSb.append(l.trim()));
			Gson gson = new Gson();

			// List<IbgeMunicipio> municipios = gson.fromJson(jsonSb.toString(), new
			// TypeToken<List<IbgeMunicipio>>() {
			// }.getType());
			Type type = new TypeToken<List<IbgeMunicipio>>() {
			}.getType();
			List<IbgeMunicipio> municipios = gson.fromJson(jString, type);

			return municipios;

		} catch (MalformedURLException e) {
			throw e;
		} catch (ConnectException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	@Override
	public List<IbgeMunicipio> buscarMunicipios() throws IOException {
		return buscarMunicipiosPorUF(null);

	}

}
