package br.leg.rr.al.localidade.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BaseDominioIndexadoJPADao;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.BaseEntityStatus_;
import br.leg.rr.al.localidade.jpa.Pais;
import br.leg.rr.al.localidade.jpa.UnidadeFederativa;
import br.leg.rr.al.localidade.jpa.UnidadeFederativa_;

@Named
@Stateless
public class UnidadeFederativaService extends BaseDominioIndexadoJPADao<UnidadeFederativa>
		implements UnidadeFederativaLocal {

	@EJB
	private PaisLocal paisBean;
	/**
	 * 
	 */
	private static final long serialVersionUID = -614202649174886385L;

	/**
	 * Verifica se já existe Uf com o IgbeId informado na entidade
	 * 
	 * @param entidade objeto que contém os valores a serem verificados.
	 * @return true caso já exista.
	 */
	private Boolean jaExisteIbgeId(UnidadeFederativa entidade) {

		final CriteriaBuilder cb = getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<UnidadeFederativa> root = cq.from(UnidadeFederativa.class);
		cq.select(cb.count(root));

		List<Predicate> predicates = new ArrayList<Predicate>();

		// Predicate pais;
		Predicate cond = cb.equal(root.get(UnidadeFederativa_.ibgeId), entidade.getIbgeId());
		predicates.add(cond);

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			cond = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(cond);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		TypedQuery<Long> query = getEntityManager().createQuery(cq);
		return (query.getSingleResult() > 0);

	}

	/**
	 * Verifica se já existe Uf no país com a sigla informada na entidade
	 * 
	 * @param entidade objeto que contém os valores a serem verificados.
	 * @return true caso já exista.
	 */
	private Boolean jaExisteSigla(UnidadeFederativa entidade) {
		final CriteriaBuilder cb = getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<UnidadeFederativa> root = cq.from(UnidadeFederativa.class);
		cq.select(cb.count(root));

		List<Predicate> predicates = new ArrayList<Predicate>();

		// Predicate pais;
		Predicate cond = cb.equal(root.get(UnidadeFederativa_.pais), entidade.getPais());
		predicates.add(cond);

		cond = cb.equal(root.get(UnidadeFederativa_.sigla), entidade.getSigla());
		predicates.add(cond);

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			cond = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(cond);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<Long> query = getEntityManager().createQuery(cq);
		return (query.getSingleResult() > 0);
	}

	@Override
	public Boolean jaExiste(UnidadeFederativa entidade) {
		final CriteriaBuilder cb = getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<UnidadeFederativa> root = cq.from(UnidadeFederativa.class);
		cq.select(cb.count(root));

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (jaExisteIbgeId(entidade)) {
			throw new BeanException("Uf com Cod. Ibge já existe. Informe outro valor.");
		} else if (jaExisteSigla(entidade)) {
			throw new BeanException("Uf com esta Sigla já existe. Informe outro valor.");
		} else {
			// Predicate pais;
			Predicate cond = cb.equal(root.get(UnidadeFederativa_.pais), entidade.getPais());
			predicates.add(cond);

			// Nome da Uf
			cond = cb.equal(root.get(UnidadeFederativa_.nome), entidade.getNome());
			predicates.add(cond);

			// condição para não verificar a mesma entidade se já existir.
			if (entidade.getId() != null) {
				Predicate id = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
				predicates.add(id);
			}

			cq.where(predicates.toArray(new Predicate[predicates.size()]));
			TypedQuery<Long> query = getEntityManager().createQuery(cq);

			if (query.getSingleResult() > 0) {
				throw new BeanException("Uf com este Nome já existe. Informe outro valor.");
			}

			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadeFederativa> pesquisar(Map<String, Object> params) {

		/** FILTROS **/
		List<Predicate> predicates = new ArrayList<Predicate>();
		Predicate cond = null;
		String nome = null;
		List<Pais> paises = null;
		StatusType sit = null;

		final CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<UnidadeFederativa> cq = cb.createQuery(UnidadeFederativa.class);
		final Root<UnidadeFederativa> root = cq.from(UnidadeFederativa.class);
		cq.select(root).distinct(true);

		if (params.size() > 0) {

			if (params.containsKey(PESQUISAR_PARAM_NOME)) {

				nome = (String) params.get(PESQUISAR_PARAM_NOME);

				if (StringUtils.isNotBlank(nome)) {

					cond = cb.like(cb.lower(root.get(UnidadeFederativa_.nome)), "%" + nome.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_SITUACAO)) {
				sit = (StatusType) params.get(PESQUISAR_PARAM_SITUACAO);
				if (sit != null) {
					cond = cb.equal(root.get(UnidadeFederativa_.situacao), sit);
					predicates.add(cond);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_PAISES)) {

				paises = (List<Pais>) params.get(PESQUISAR_PARAM_PAISES);

				if (paises != null && paises.size() > 0) {

					cond = root.get(UnidadeFederativa_.pais).in(Arrays.asList(paises));
					predicates.add(cond);
				}
			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
			return getResultList(cq);

		}

		return null;

	}

	@Override
	public UnidadeFederativa getRoraima() {
		return buscarBrasilUfPorNome("Roraima");
	}

	@Override
	public UnidadeFederativa buscarBrasilUfPorNome(String nome) {
		return buscar(paisBean.getBrasil(), nome);
	}

	@Override
	public UnidadeFederativa buscar(Pais pais, String nome) {

		if (pais == null || StringUtils.isAllEmpty(nome)) {
			return null;
		}

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<UnidadeFederativa> cq = createCriteriaQuery();
		Root<UnidadeFederativa> root = cq.from(UnidadeFederativa.class);
		cq.select(root);

		Predicate cond1 = cb.equal(root.get(UnidadeFederativa_.pais), pais);
		Predicate cond2 = cb.equal(cb.lower(root.get(UnidadeFederativa_.nome)), nome.toLowerCase());
		cq.where(cond1, cond2);

		return getSingleResult(cq);

	}

	@Override
	public UnidadeFederativa buscarBrasilUfPorSigla(String sigla) {
		return buscarPorSigla(paisBean.getBrasil(), sigla);
	}

	@Override
	public UnidadeFederativa buscarBrasilUfPorIbgeId(String ibgeId) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<UnidadeFederativa> cq = createCriteriaQuery();
		Root<UnidadeFederativa> root = cq.from(UnidadeFederativa.class);
		cq.select(root);

		Predicate cond1 = cb.equal(root.get(UnidadeFederativa_.ibgeId), ibgeId);
		cq.where(cond1);

		return getSingleResult(cq);

	}

	@Override
	public UnidadeFederativa buscarPorSigla(Pais pais, String sigla) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<UnidadeFederativa> cq = createCriteriaQuery();
		Root<UnidadeFederativa> root = cq.from(UnidadeFederativa.class);
		cq.select(root);

		Predicate cond1 = cb.equal(root.get(UnidadeFederativa_.sigla), sigla);
		Predicate cond2 = cb.equal(root.get(UnidadeFederativa_.pais), pais);
		cq.where(cond1, cond2);

		return getSingleResult(cq);
	}

}
