package br.leg.rr.al.localidade.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BaseJPADaoStatus;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.localidade.jpa.Bairro;
import br.leg.rr.al.localidade.jpa.Bairro_;

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

	@Override
	public Boolean jaExiste(Bairro entidade) {
		return false;
	}

}
