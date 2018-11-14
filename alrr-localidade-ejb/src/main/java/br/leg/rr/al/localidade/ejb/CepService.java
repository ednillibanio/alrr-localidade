package br.leg.rr.al.localidade.ejb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import com.google.gson.Gson;

import br.leg.rr.al.core.dao.BaseJPADao;
import br.leg.rr.al.localidade.jpa.Bairro;
import br.leg.rr.al.localidade.jpa.Cep;
import br.leg.rr.al.localidade.jpa.Cep_;
import br.leg.rr.al.localidade.jpa.Municipio;
import br.leg.rr.al.localidade.utils.CepUtils;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 09-08-2017
 */
@Named
@Stateless
public class CepService extends BaseJPADao<Cep, Integer> implements CepLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7570695428497812951L;

	@EJB
	private MunicipioLocal localidadeBean;

	@EJB
	private BairroLocal bairroBean;

	public void completeEnderecoByCep() {

		// 1 - buscar cep via WS
		// 2 - Verifica se existe Municipio. Caso não existir, cadastra.
		// 3 - Verifica se existe Bairro. Caso não existir, cadastra.
		// 4 - Verifica se existe Logradouro. Caso não existir, cadastra.

		// String json;

		/*
		 * try { URL url = new URL("http://viacep.com.br/ws/" + getCep() + "/json");
		 * URLConnection urlConnection = url.openConnection(); InputStream is =
		 * urlConnection.getInputStream(); BufferedReader br = new BufferedReader(new
		 * InputStreamReader(is));
		 * 
		 * StringBuilder jsonSb = new StringBuilder();
		 * 
		 * br.lines().forEach(l -> jsonSb.append(l.trim())); Gson gson = new Gson();
		 * JsonObject obj = gson.fromJson(jsonSb.toString(), JsonObject.class);
		 * 
		 * // Municipio String logradouro = obj.get("logradouro").getAsString(); String
		 * nome = obj.get("municipio").getAsString(); String uf =
		 * obj.get("uf").getAsString(); String bairro = obj.get("bairro").getAsString();
		 * 
		 * UfType uft = UfType.valueOf(uf.toUpperCase()); if (uft != null &&
		 * StringUtils.isNotBlank(nome)) { Municipio loc =
		 * localidadeBean.buscarPorUf(uft, nome); if (loc == null) { loc = new
		 * Municipio(); loc.setNome(nome); loc.setUf(uft); localidadeBean.salvar(loc); }
		 * 
		 * getEntity().setBairro(bairro); getEntity().setCep(cep);
		 * getEntity().setLocalidade(loc); getEntity().setLogradouro(logradouro); }
		 * 
		 * } catch (Exception e) { throw new RuntimeException(e); }
		 */

	}

	@Override
	public Cep buscarCep(String arg) {
		Cep cep = null;

		// 1- Busca cep na base local;
		cep = buscarCepBaseLocal(CepUtils.unformat(arg));

		// 2 - Busca cep via WebService se não encontrar na base local;
		if (cep == null) {
			cep = buscarCepWS(arg);

			// 2.1 - Cadastra o Cep na base de dados local se encontrar o cep via
			// WebService.
			if (cep != null && cep.getNumero() != null && cep.getLogradouro() != null) {
				cep.setNumero(CepUtils.unformat(cep.getNumero()));
				formatarCep(cep);
				salvar(cep);
			}
		}

		// 3 - Caso encontre a entidade cep, verifica se já foi cadastrado a
		// municipio e o bairro.
		if (cep != null && cep.getMunicipio() != null && cep.getUf() != null) {
			Municipio loc = localidadeBean.buscarPorUf(cep.getUf(), cep.getMunicipio());
			// 3 - Se não existe municipio na base, o cadastra;
			if (loc == null) {
				loc = new Municipio();
				loc.setNome(cep.getMunicipio());
				loc.setUf(cep.getUf());
				localidadeBean.salvar(loc);
			}
			if (cep.getBairro() != null) {
				Bairro bairro = bairroBean.buscarPorLocalidadeId(loc.getId(), cep.getBairro());
				// 4 - Se não existe bairro na base de dados, o cadastra;
				if (bairro == null) {
					bairro = new Bairro();
					bairro.setMunicipio(loc);
					bairro.setNome(cep.getBairro());
					bairroBean.salvar(bairro);
				}
			}
		}

		return cep;

	}

	@Override
	public Cep buscarCepBaseLocal(String cep) {

		String tmp = CepUtils.unformat(cep);

		if (StringUtils.isNotBlank(tmp)) {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Cep> cq = createCriteriaQuery();
			Root<Cep> root = cq.from(Cep.class);
			cq.select(root);

			cq.where(cb.equal(root.get(Cep_.numero), tmp));
			TypedQuery<Cep> q = getEntityManager().createQuery(cq);

			try {
				return q.getSingleResult();
			} catch (NoResultException | EntityNotFoundException ex) {
				return null;
			} catch (NonUniqueResultException ex) {
				ex.printStackTrace();
				throw ex;
			}
		}
		return null;
	}

	@SuppressWarnings("null")
	@Override
	public Cep buscarCepWS(String arg) {

		String tmp = CepUtils.unformat(arg);

		if (StringUtils.isNotBlank(tmp)) {
			try {

				// 1- busca cep via WS.
				URL url = new URL("http://viacep.com.br/ws/" + tmp + "/json");
				URLConnection urlConnection = url.openConnection();
				InputStream is = urlConnection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				StringBuilder jsonSb = new StringBuilder();

				br.lines().forEach(l -> jsonSb.append(l.trim()));
				Gson gson = new Gson();
				Cep cep = gson.fromJson(jsonSb.toString(), Cep.class);

				// Se houver erro ou não encontrar, define null.
				if (cep != null || cep.getErro()) {
					cep = null;
				}
				return cep;

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;

	}

	@Override
	public Boolean jaExiste(Cep entidade) {
		return false;
	}

	/**
	 * Método que faz o <i>capitalize</i> das Strings: bairro, logradouro e
	 * municipio. Todas as letras de ínicio de palavra são transformadas para
	 * <i>upper</i>.
	 * 
	 * @param cep objeto a ser transformado.
	 */
	private void formatarCep(Cep cep) {
		String bairro = WordUtils.capitalizeFully(cep.getBairro());
		String log = WordUtils.capitalizeFully(cep.getLogradouro());
		String loc = WordUtils.capitalizeFully(cep.getMunicipio());
		cep.setBairro(bairro);
		cep.setMunicipio(loc);
		cep.setLogradouro(log);
	}

}
