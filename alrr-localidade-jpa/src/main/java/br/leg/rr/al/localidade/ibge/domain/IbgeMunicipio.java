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
import javax.persistence.Transient;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 09-04-2018
 */
@Entity
@Table(name = "ibge_municipio")
public class IbgeMunicipio implements br.leg.rr.al.core.jpa.Entity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7732002472772084110L;

	@Id
	@Column(unique = true, nullable = false)
	protected Integer id;

	private String nome;

	@ManyToOne(cascade = CascadeType.ALL)
	private IbgeMicrorregiao microrregiao;

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
	 * @return the microrregiao
	 */
	public IbgeMicrorregiao getMicrorregiao() {
		return microrregiao;
	}

	/**
	 * @param microrregiao
	 *            the microrregiao to set
	 */
	public void setMicrorregiao(IbgeMicrorregiao microrregiao) {
		this.microrregiao = microrregiao;
	}

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
	 * Retorna a UF que o município faz parte. Para retornar a entidade IbgeUF, é
	 * percorrido as entidades Microrregiao e Mesorregiao.
	 * 
	 * @return A UF que o município faz parte.
	 */
	@Transient
	public IbgeUF getUF() {
		return getMicrorregiao().getMesorregiao().getUF();
	}

}
