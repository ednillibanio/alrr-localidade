package br.leg.rr.al.localidade.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.leg.rr.al.core.dao.BaseDominioIndexadoJPADao;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.BaseEntityStatus_;
import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.ibge.ejb.IbgeMunicipioLocal;
import br.leg.rr.al.localidade.jpa.Municipio;
import br.leg.rr.al.localidade.jpa.Municipio_;
import br.leg.rr.al.localidade.jpa.UnidadeFederativa;

@Named
@Stateless
public class MunicipioService extends BaseDominioIndexadoJPADao<Municipio> implements MunicipioLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7074097657495549567L;

	Logger logger = LoggerFactory.getLogger(BaseDominioIndexadoJPADao.class);

	@EJB
	private IbgeMunicipioLocal ibgeBean;

	@EJB
	private UnidadeFederativaLocal ufBean;

	@Override
	public Municipio buscarPorUf(UnidadeFederativa uf, String nome) throws BeanException {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Municipio> cq = createCriteriaQuery();
		Root<Municipio> root = cq.from(Municipio.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		cq.select(root);

		if (uf != null) {

			Predicate cond1 = cb.equal(root.get(Municipio_.uf), uf);
			predicates.add(cond1);
		}

		if (StringUtils.isNotBlank(nome)) {
			Predicate cond2 = cb.equal(cb.lower(root.get(Municipio_.nome)), nome.toLowerCase());
			predicates.add(cond2);
		}

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return getSingleResult(cq);

	}

	@Override
	public List<Municipio> buscarPorNome(String nome, UnidadeFederativa uf) throws BeanException {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Municipio> cq = createCriteriaQuery();
		Root<Municipio> root = cq.from(Municipio.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		cq.select(root);

		if (uf != null) {
			Predicate cond1 = cb.equal(root.get(Municipio_.uf), uf);
			predicates.add(cond1);
		}

		if (StringUtils.isNotBlank(nome)) {
			Predicate cond2 = cb.like(cb.lower(root.get(Municipio_.nome)), "%" + nome.toLowerCase() + "%");
			predicates.add(cond2);
		}

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return getResultList(cq);

	}

	/**
	 * 
	 * @param entidade
	 * @return
	 */
	private Boolean jaExisteIbgeId(Municipio entidade) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Municipio> root = cq.from(Municipio.class);
		cq.select(cb.count(root));

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(root.get(Municipio_.ibgeId), entidade.getIbgeId());
		predicates.add(cond);

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			cond = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(cond);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<Long> q = getEntityManager().createQuery(cq);

		return (q.getSingleResult() > 0);
	}

	@Override
	public Boolean jaExiste(Municipio entidade) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Municipio> root = cq.from(Municipio.class);
		cq.select(cb.count(root));

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (entidade.getIbgeId() != null) {
			if (jaExisteIbgeId(entidade)) {
				throw new BeanException("Município com este Cód. Ibge já existe. Informe outro valor.");
			}
		}

		Predicate nome = cb.equal(root.get(Municipio_.nome), entidade.getNome());
		predicates.add(nome);

		Predicate uf = cb.equal(root.get(Municipio_.uf), entidade.getUf());
		predicates.add(uf);

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			Predicate id = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(id);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		TypedQuery<Long> q = getEntityManager().createQuery(cq);

		if (q.getSingleResult() > 0) {
			throw new BeanException("Município com este Nome e Uf já existe. Informe outro valor.");
		}
		return false;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Municipio> pesquisar(Map<String, Object> params) {

		/** FILTROS **/
		List<Predicate> predicates = new ArrayList<Predicate>();
		Predicate cond = null;
		String nome = null;
		StatusType sit = null;
		List<UnidadeFederativa> ufs = null;

		final CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Municipio> cq = cb.createQuery(Municipio.class);
		final Root<Municipio> root = cq.from(Municipio.class);
		cq.select(root).distinct(true);

		if (params.size() > 0) {

			if (params.containsKey(PESQUISAR_PARAM_NOME)) {

				nome = (String) params.get(PESQUISAR_PARAM_NOME);

				if (StringUtils.isNotBlank(nome)) {

					cond = cb.like(cb.lower(root.get(Municipio_.nome)), "%" + nome.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_SITUACAO)) {
				sit = (StatusType) params.get(PESQUISAR_PARAM_SITUACAO);
				if (sit != null) {
					cond = cb.equal(root.get(Municipio_.situacao), sit);
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_UFS)) {

				ufs = (List<UnidadeFederativa>) params.get(PESQUISAR_PARAM_UFS);

				if (ufs != null && ufs.size() > 0) {

					cond = root.get(Municipio_.uf).in(Arrays.asList(ufs));
					predicates.add(cond);
				}
			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
			return getResultList(cq);

		}

		return null;

	}

	@Override
	public Municipio buscarPorIbgeId(String id) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Municipio> cq = createCriteriaQuery();
		Root<Municipio> root = cq.from(Municipio.class);
		cq.select(root);

		Predicate cond = cb.equal(root.get(Municipio_.ibgeId), id);
		cq.where(cond);

		return getSingleResult(cq);
	}

	// TODO: Implementar esse método. Permitir consulta inserido o nome + uf. Talvez
	// de problema porque uf é enum. vai dar trabalho.
	@SuppressWarnings("unchecked")
	public List<Municipio> buscarPeloNomeIndexado(String nome, UfType uf) {
		throw new NotImplementedException(
				"Método buscarPeloNomeIndexado(String nome, UfType uf) não implementado ainda.");
		// return null;
	}

	@Override
	public List<Municipio> buscarAtivosPeloNomeIndexado(String nome, UfType uf) {

		List<Municipio> result = buscarPeloNomeIndexado(nome, uf);

		if (result != null && result.size() > 0) {
			result = result.stream().filter(entidade -> entidade.getSituacao().equals(StatusType.ATIVO))
					.collect(Collectors.toList());
		}

		return result;
	}

	@Override
	public Municipio buscarPorUf(UfType ufType, String nome) throws BeanException {
		UnidadeFederativa uf = ufBean.buscarBrasilUfPorSigla(ufType.toString());
		return buscarPorUf(uf, nome);
	}

	@Override
	public List<Municipio> buscarPorNome(String nome, UfType ufType) throws BeanException {
		UnidadeFederativa uf = ufBean.buscarBrasilUfPorSigla(ufType.toString());
		return buscarPorNome(nome, uf);
	}

}
