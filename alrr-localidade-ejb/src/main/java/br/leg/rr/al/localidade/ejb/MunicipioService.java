package br.leg.rr.al.localidade.ejb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BaseJPADaoStatus;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.domain.StatusType;
import br.leg.rr.al.core.jpa.BaseEntityStatus_;
import br.leg.rr.al.core.utils.StringHelper;
import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.ibge.domain.IbgeMunicipio;
import br.leg.rr.al.localidade.ibge.ejb.IbgeMunicipioLocal;
import br.leg.rr.al.localidade.jpa.Municipio;
import br.leg.rr.al.localidade.jpa.Municipio_;
import br.leg.rr.al.localidade.utils.MunicipioUtils;

@Named
@Stateless
public class MunicipioService extends BaseJPADaoStatus<Municipio, Integer> implements MunicipioLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7074097657495549567L;

	@EJB
	private IbgeMunicipioLocal ibgeBean;

	@Override
	public Municipio buscarPorUf(UfType uf, String nome) throws BeanException {

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
	public List<Municipio> buscarPorNome(String nome, UfType uf) throws BeanException {

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

	@Override
	public Boolean jaExiste(Municipio entidade) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Municipio> cq = createCriteriaQuery();
		Root<Municipio> root = cq.from(Municipio.class);
		cq.select(root);

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate nome = cb.equal(root.get(Municipio_.nome), entidade.getNome());
		predicates.add(nome);

		Predicate uf = cb.equal(root.get(Municipio_.uf), entidade.getUf());
		predicates.add(uf);

		if (entidade.getIbgeId() != null) {
			Predicate id = cb.notEqual(root.get(Municipio_.ibgeId), entidade.getIbgeId());
			predicates.add(id);
		}

		// condição para não verificar a mesma entidade se já existir.
		if (entidade.getId() != null) {
			Predicate id = cb.notEqual(root.get(BaseEntityStatus_.id), entidade.getId());
			predicates.add(id);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		return (!getResultList(cq).isEmpty());

	}

	@Override
	public void importarMunicipiosDoIBGE() throws IOException {
		List<IbgeMunicipio> municipios = ibgeBean.buscarMunicipios();

		if (municipios != null && municipios.size() > 0) {

			String nome = null;
			UfType uf = null;
			String ibgeId = null;
			Municipio loc = null;
			for (IbgeMunicipio mun : municipios) {

				nome = StringHelper.capitalizeFully(mun.getNome());
				uf = UfType.valueOf(mun.getUF().getSigla());
				ibgeId = mun.getId().toString();

				// verifica se existe o municipio ibge já é cadastrado na base local.
				loc = buscarPorIbgeId(ibgeId);
				if (loc != null) {

					// verifica se o nome sofreu
					// alterações no cadastro do ibge.
					if (!loc.getNome().toLowerCase().equals(nome.toLowerCase())) {

						loc.setNome(nome);
						// Se sofreu alterações, irá atualiza-lo na base local.
						atualizar(loc);
					}

				} else {
					// verifica se o municipio já existe na base local. Esta condição é para
					// municipios que não possui ibge id.
					loc = buscarPorUf(uf, nome);

					// cadastra um novo municipio ibge na base local
					if (loc == null) {
						loc = MunicipioUtils.converterIbgeMunicipioParaMunicipio(mun);
						salvar(loc);

					}
					// atualiza o cadastro do municipio local com o ibge id.
					else if (loc != null && loc.getIbgeId() == null) {
						loc.setIbgeId(ibgeId);
						atualizar(loc);
					}
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Municipio> pesquisar(Map<String, Object> params) {

		/** FILTROS **/
		List<Predicate> predicates = new ArrayList<Predicate>();
		Predicate cond = null;
		String nome = null;
		StatusType sit = null;
		List<UfType> ufs = null;

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

				ufs = (List<UfType>) params.get(PESQUISAR_PARAM_UFS);

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

}
