package br.leg.rr.al.localidade.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import com.google.gson.Gson;

import br.leg.rr.al.core.dao.BaseJPADao;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.BaseEntityStatus_;
import br.leg.rr.al.localidade.domain.ViaCep;
import br.leg.rr.al.localidade.jpa.Bairro;
import br.leg.rr.al.localidade.jpa.Bairro_;
import br.leg.rr.al.localidade.jpa.Cep;
import br.leg.rr.al.localidade.jpa.Cep_;
import br.leg.rr.al.localidade.jpa.Municipio;
import br.leg.rr.al.localidade.jpa.Municipio_;
import br.leg.rr.al.localidade.jpa.UnidadeFederativa;
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
	private MunicipioLocal municipioBean;

	@EJB
	private BairroLocal bairroBean;

	@EJB
	private UnidadeFederativaLocal ufBean;

	@EJB
	private PaisLocal paisBean;

	//TODO: ver oq vai fazer com isso daqui. se não é lixo. Nem esta na interface Local dele.
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
	public Cep buscarCep(String arg) throws Exception {
		Cep cep = null;

		// 1- Busca cep na base local;
		cep = buscarCepBaseLocal(CepUtils.unformat(arg));

		// 2 - Busca cep via WebService se não encontrar na base local;
		if (cep == null) {
			try {
				cep = buscarCepWS(arg);

				// 2.1 - Cadastra o Cep na base de dados local se encontrar via
				// WebService.
				if (cep != null && cep.getNumero() != null && cep.getLogradouro() != null) {
					salvar(cep);
				}
			} catch (Exception e) {
				throw e;
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
				throw ex;
			}
		}
		return null;
	}

	@Override
	public Cep buscarCepWS(String arg) throws Exception {

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
				ViaCep cep = gson.fromJson(jsonSb.toString(), ViaCep.class);

				// Se houver erro ou não encontrar, define null.
				if (cep != null && cep.getErro()) {
					return null;
				}

				return completarCep(cep);

			} catch (IOException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}
		}
		return null;

	}

	private Cep completarCep(ViaCep via) {
		Municipio mun = null;
		Bairro bairro = null;
		Cep cep = new Cep();

		// Busca a Uf na base local.
		UnidadeFederativa uf = ufBean.buscarBrasilUfPorNome(via.getUf().getLabel());
		// Cadastra a UF caso não encontre na base local.
		if (uf == null) {
			uf = new UnidadeFederativa();
			uf.setIbgeId(via.getUf().getIbgeId());
			uf.setNome(via.getUf().getLabel());
			uf.setSigla(via.getUf().toString());
			uf.setSituacao(StatusType.ATIVO);
			uf.setPais(paisBean.getBrasil());
			ufBean.salvar(uf);
		}

		// Busca o município na base local.
		if (via.getIbge() != null) {
			mun = municipioBean.buscarPorIbgeId(via.getIbge());

		} else if (via.getLocalidade() != null && via.getUf() != null) {
			mun = municipioBean.buscarPorUf(via.getUf(), via.getLocalidade());
		}

		// Salva o novo município se não o encontrou na base local.
		if (mun == null) {
			mun = new Municipio();
			mun.setNome(via.getLocalidade());
			mun.setUf(uf);
			mun.setIbgeId(via.getIbge());
			municipioBean.salvar(mun);
		}

		if (via.getBairro() != null && mun != null) {

			// Busca o bairro na base local.
			bairro = bairroBean.buscarPorMunicipioId(mun.getId(), via.getBairro());

			// 4 - Salva o novo bairro se não encontrou na base local
			if (bairro == null) {
				bairro = new Bairro();
				bairro.setMunicipio(mun);
				bairro.setNome(via.getBairro());
				bairroBean.salvar(bairro);
			}
		}

		cep.setNumero(CepUtils.unformat(via.getCep()));
		cep.setLogradouro(WordUtils.capitalizeFully(via.getLogradouro()));
		cep.setBairro(bairro);
		cep.setMunicipio(mun);

		return cep;
	}

	@Override
	public Boolean jaExiste(Cep entidade) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Cep> root = cq.from(Cep.class);
		cq.select(cb.count(root));

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cep = cb.equal(root.get(Cep_.numero), entidade.getNumero());
		predicates.add(cep);

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			Predicate id = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(id);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		TypedQuery<Long> q = getEntityManager().createQuery(cq);

		if (q.getSingleResult() > 0) {
			throw new BeanException("Cep com este Número já existe. Informe outro valor.");
		}
		
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cep> pesquisar(Map<String, Object> params) {

		/** FILTROS **/
		List<Predicate> predicates = new ArrayList<Predicate>();
		Predicate cond = null;
		String numero = null;
		String logradouro = null;
		String bairro = null;
		String municipio = null;
		List<UnidadeFederativa> ufs = null;

		final CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Cep> cq = cb.createQuery(Cep.class);
		final Root<Cep> root = cq.from(Cep.class);

		cq.select(root).distinct(true);

		if (params.size() > 0) {

			if (params.containsKey(PESQUISAR_PARAM_NUMERO)) {

				numero = (String) params.get(PESQUISAR_PARAM_NUMERO);

				if (StringUtils.isNotBlank(numero)) {

					cond = cb.like(cb.lower(root.get(Cep_.numero)), "%" + numero + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_LOGRADOURO)) {
				logradouro = (String) params.get(PESQUISAR_PARAM_LOGRADOURO);

				if (StringUtils.isNotBlank(logradouro)) {

					cond = cb.like(cb.lower(root.get(Cep_.logradouro)), "%" + logradouro.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_BAIRRO)) {
				bairro = (String) params.get(PESQUISAR_PARAM_BAIRRO);

				if (StringUtils.isNotBlank(bairro)) {

					Join<Cep, Bairro> joinBairro = root.join(Cep_.bairro);
					cond = cb.like(cb.lower(joinBairro.get(Bairro_.nome)), "%" + bairro.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_MUNICIPIO)) {
				municipio = (String) params.get(PESQUISAR_PARAM_MUNICIPIO);

				if (StringUtils.isNotBlank(municipio)) {

					Join<Cep, Municipio> joinMunicipio = root.join(Cep_.municipio);
					cond = cb.like(cb.lower(joinMunicipio.get(Municipio_.nome)), "%" + municipio.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_UFS)) {

				ufs = (List<UnidadeFederativa>) params.get(PESQUISAR_PARAM_UFS);

				if (ufs != null && ufs.size() > 0) {

					Join<Cep, Municipio> joinMunicipio = root.join(Cep_.municipio);
					cond = joinMunicipio.get(Municipio_.uf).in(Arrays.asList(ufs));
					predicates.add(cond);
				}
			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
			return getResultList(cq);

		}

		return null;

	}

}
