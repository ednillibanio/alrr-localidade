package br.leg.rr.al.localidade.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import br.leg.rr.al.core.dao.BaseJPADaoStatus;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.BaseEntityStatus_;
import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.jpa.Bairro;
import br.leg.rr.al.localidade.jpa.Bairro_;
import br.leg.rr.al.localidade.jpa.Municipio;
import br.leg.rr.al.localidade.jpa.Municipio_;

@Named
@Stateless
public class BairroService extends BaseJPADaoStatus<Bairro, Integer> implements BairroLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8406245992680569087L;

	@Override
	public List<Bairro> buscarPorNome(String nome) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Bairro> cq = createCriteriaQuery();
		Root<Bairro> root = cq.from(Bairro.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		cq.select(root);

		if (StringUtils.isNotBlank(nome)) {
			Predicate cond = cb.like(cb.lower(root.get(Bairro_.nome)), "%" + nome.toLowerCase() + "%");
			predicates.add(cond);
		}

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return getResultList(cq);
	}

	@Override
	public Bairro buscarPorMunicipioId(Integer municipioId, String nome) throws BeanException {

		if (StringUtils.isNotBlank(nome) && municipioId != null) {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Bairro> cq = createCriteriaQuery();
			Root<Bairro> root = cq.from(Bairro.class);
			cq.select(root);

			List<Predicate> predicates = new ArrayList<Predicate>();
			Predicate cond1 = cb.equal(cb.lower(root.get(Bairro_.nome)), nome.toLowerCase());
			Predicate cond2 = cb.equal(root.get(Bairro_.municipio), municipioId);
			predicates.add(cond1);
			predicates.add(cond2);
			cq.where(cb.and(predicates.toArray(new Predicate[] {})));

			TypedQuery<Bairro> q = getEntityManager().createQuery(cq);

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

	/**
	 * Verifica se já existe um bairro com o mesmo nome e municipio. Caso já exista,
	 * retorna true.
	 */
	@Override
	public Boolean jaExiste(Bairro entidade) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Bairro> cq = createCriteriaQuery();
		Root<Bairro> root = cq.from(Bairro.class);
		cq.select(root);

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate nome = cb.equal(root.get(Bairro_.nome), entidade.getNome());
		predicates.add(nome);

		Predicate mun = cb.equal(root.get(Bairro_.municipio), entidade.getMunicipio());
		predicates.add(mun);

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			Predicate id = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(id);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		return (!getResultList(cq).isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bairro> pesquisar(Map<String, Object> params) {

		/** FILTROS **/
		List<Predicate> predicates = new ArrayList<Predicate>();
		Predicate cond = null;
		String nome = null;
		String municipio = null;
		List<UfType> ufs = null;
		StatusType sit = null;

		final CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Bairro> cq = cb.createQuery(Bairro.class);
		final Root<Bairro> root = cq.from(Bairro.class);

		Join<Bairro, Municipio> joinMunicipio = root.join(Bairro_.municipio);

		cq.select(root).distinct(true);

		if (params.size() > 0) {

			if (params.containsKey(PESQUISAR_PARAM_NOME)) {
				nome = (String) params.get(PESQUISAR_PARAM_NOME);

				if (StringUtils.isNotBlank(nome)) {

					cond = cb.like(cb.lower(root.get(Bairro_.nome)), "%" + nome.toLowerCase() + "%");
					predicates.add(cond);
				}
			}
			if (params.containsKey(PESQUISAR_PARAM_SITUACAO)) {
				sit = (StatusType) params.get(PESQUISAR_PARAM_SITUACAO);

				if (sit != null) {
					cond = cb.equal(root.get(Bairro_.situacao), sit);
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_MUNICIPIO_NOME)) {
				municipio = (String) params.get(PESQUISAR_PARAM_MUNICIPIO_NOME);

				if (StringUtils.isNotBlank(municipio)) {
					cond = cb.like(cb.lower(joinMunicipio.get(Municipio_.nome)), "%" + municipio.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_UFS)) {

				ufs = (List<UfType>) params.get(PESQUISAR_PARAM_UFS);

				if (ufs != null && ufs.size() > 0) {

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