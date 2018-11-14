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
@Table(name = "ibge_microrregiao")
public class IbgeMicrorregiao implements br.leg.rr.al.core.jpa.Entity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8478014454981134643L;

	@Id
	@Column(unique = true, nullable = false)
	protected Integer id;

	private String nome;

	@ManyToOne(cascade=CascadeType.ALL)
	private IbgeMesorregiao mesorregiao;

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
	 * @return the mesorregiao
	 */
	public IbgeMesorregiao getMesorregiao() {
		return mesorregiao;
	}

	/**
	 * @param mesorregiao
	 *            the mesorregiao to set
	 */
	public void setMesorregiao(IbgeMesorregiao mesorregiao) {
		this.mesorregiao = mesorregiao;
	}
}
