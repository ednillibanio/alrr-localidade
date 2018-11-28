/**
 * 
 */
package br.leg.rr.al.localidade.ibge.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 09-04-2018
 */
@Entity
@Table(name = "ibge_mesorregiao")
public class IbgeMesorregiao implements br.leg.rr.al.core.jpa.Entity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2243171808106081910L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer id;

	private String nome;

	@ManyToOne(cascade = CascadeType.ALL)
	private IbgeUF UF;

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
	 * @return the UF
	 */
	public IbgeUF getUF() {
		return UF;
	}

	/**
	 * @param uf
	 *            the uf to set
	 */
	public void setUF(IbgeUF UF) {
		this.UF = UF;
	}
}
