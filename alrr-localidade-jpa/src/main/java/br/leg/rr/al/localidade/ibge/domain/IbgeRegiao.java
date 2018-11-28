/**
 * 
 */
package br.leg.rr.al.localidade.ibge.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 09-04-2018
 */
@Entity
@Table(name = "ibge_regiao")
public class IbgeRegiao implements br.leg.rr.al.core.jpa.Entity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4024189806541451063L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer id;

	private String nome;

	private String sigla;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.leg.rr.al.core.jpa.EntityStatus#getId()
	 */
	@Override
	public Integer getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.leg.rr.al.core.jpa.EntityStatus#setId(java.io.Serializable)
	 */
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla
	 *            the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
