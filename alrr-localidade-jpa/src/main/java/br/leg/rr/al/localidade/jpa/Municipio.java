package br.leg.rr.al.localidade.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Indexed;

import br.leg.rr.al.core.jpa.DominioIndexado;

/**
 * <p>
 * * Classe persistente que representa a tabela "pais" que possui os seguintes
 * indices:
 * <ul>
 * <li>municipio_idx1 - (uf_id, LOWER(nome))</li>
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
@Table
@Indexed
public class Municipio extends DominioIndexado {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1184733666808220254L;

	// TODO: Ver se vai inserir a data de aniversário aqui do municipio. Seria legal
	// também pra uf.

	@Column(name = "ibge_id", nullable = true, unique = true)
	private String ibgeId;

	@NotNull(message = "Uf: campo obrigatório.")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "uf_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "uf_fk"))
	private UnidadeFederativa uf;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public UnidadeFederativa getUf() {
		return uf;
	}

	public void setUf(UnidadeFederativa uf) {
		this.uf = uf;
	}

	/**
	 * @return the ibgeId
	 */
	public String getIbgeId() {
		return ibgeId;
	}

	/**
	 * @param ibgeId the ibgeId to set
	 */
	public void setIbgeId(String ibgeId) {
		this.ibgeId = ibgeId;
	}

	/**
	 * Retorna o país da Unidade Federativa do Município
	 * 
	 * @return pais
	 */
	@Transient
	public Pais getPais() {
		return uf.getPais();
	}

}
