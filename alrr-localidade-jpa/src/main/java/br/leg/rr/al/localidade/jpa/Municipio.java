package br.leg.rr.al.localidade.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Indexed;

import br.leg.rr.al.localidade.domain.UfType;

/**
 * Classe persistente que representa a tabela "municipio".
 * 
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "nome", "uf" }), indexes = {
		@Index(name = "municipio_idx1", columnList = "nome"),
		@Index(name = "municipio_idx2", columnList = "nome, uf") })
@Indexed
public class Municipio extends Localidade {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1184733666808220254L;

	// TODO: Ver se vai inserir a data de aniversário aqui do municipio. Seria legal
	// também pra uf.

	@Column(name = "ibge_id", nullable = true)
	private String ibgeId;

	@NotNull(message = "Preenchimento obrigatório do campo: UF.")
	@Enumerated(EnumType.STRING)
	@Column(name = "uf", nullable = false, length = 2)
	private UfType uf;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public UfType getUf() {
		return uf;
	}

	public void setUf(UfType uf) {
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

}
