package br.leg.rr.al.localidade.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

import br.leg.rr.al.core.jpa.DominioIndexado;

/**
 * <p>
 * Classe persistente que representa a tabela "Unidade Federativa" que possui os
 * seguintes indices:
 * <ul>
 * <li>uf_idx - (pais_id, LOWER(nome))</li>
 * <li>uf_idx - (pais_id, LOWER(sigla))</li>
 * </ul>
 * Obs.: A função <strong>lower</strong> é utilizada para evitar que valores
 * sejam duplicados no banco de dados em casos que não devem. O postgres é case
 * sensitive nos campos varchar. Constraints não funciona nesses casos.
 * </p>
 * 
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Entity
@Table(name = "unidade_federativa")
@Indexed
public class UnidadeFederativa extends DominioIndexado {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1338795676941062754L;

	@Column(length = 5, nullable = true)
	private String sigla;

	@Column(length = 2, nullable = true)
	private String ibgeId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "pais_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "pais_fk"))
	private Pais pais;

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getIbgeId() {
		return ibgeId;
	}

	public void setIbgeId(String ibgeId) {
		this.ibgeId = ibgeId;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

}
