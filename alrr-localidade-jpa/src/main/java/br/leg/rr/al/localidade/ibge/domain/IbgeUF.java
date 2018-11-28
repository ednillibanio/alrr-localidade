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
@Table(name = "ibge_uf")
public class IbgeUF implements br.leg.rr.al.core.jpa.Entity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5498375831722516201L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer id;

	private String nome;

	private String sigla;

	@ManyToOne(cascade = CascadeType.ALL)
	private IbgeRegiao regiao;

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

	/**
	 * @return the regiao
	 */
	public IbgeRegiao getRegiao() {
		return regiao;
	}

	/**
	 * @param regiao
	 *            the regiao to set
	 */
	public void setRegiao(IbgeRegiao regiao) {
		this.regiao = regiao;
	}
}
