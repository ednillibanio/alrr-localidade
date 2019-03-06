package br.leg.rr.al.localidade.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.leg.rr.al.core.dao.BaseDominioIndexadoJPADao;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.jpa.BaseEntityStatus_;
import br.leg.rr.al.localidade.jpa.Pais;
import br.leg.rr.al.localidade.jpa.Pais_;

@Named
@Singleton
public class PaisService extends BaseDominioIndexadoJPADao<Pais> implements PaisLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -614202649174886385L;

	@Override
	public Boolean jaExiste(Pais entidade) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Pais> root = cq.from(Pais.class);
		cq.select(cb.count(root));

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate nome = cb.equal(root.get(Pais_.nome), entidade.getNome());
		predicates.add(nome);

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			Predicate id = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(id);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		TypedQuery<Long> q = getEntityManager().createQuery(cq);
		if (q.getSingleResult() > 0) {
			throw new BeanException("País com este Nome já existe. Informe outro valor.");
		}

		return false;

	}

	public Pais getBrasil() {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Pais> cq = createCriteriaQuery();
		Root<Pais> root = cq.from(Pais.class);
		cq.select(root).distinct(true);

		Predicate nome = cb.equal(cb.lower(root.get(Pais_.nome)), "Brasil".toLowerCase());
		cq.where(nome);
		return getSingleResult(cq);

	}

}
