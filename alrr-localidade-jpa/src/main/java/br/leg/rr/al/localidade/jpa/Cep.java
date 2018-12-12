package br.leg.rr.al.localidade.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.leg.rr.al.core.jpa.BaseEntity;

/**
 * Classe persistente que representa a tabela "cep".
 * 
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "numero_uq", columnNames = "numero"))
public class Cep extends BaseEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1696673731486221778L;

	@Column(nullable = false, length = 8)

	private String numero;

	@Column(nullable = true, length = 250)
	private String logradouro;

	// @Column(nullable = true, length = 250)
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "bairro_id", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "bairro_fk"))
	private Bairro bairro;

	// @Column(nullable = true, length = 250)
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "municipio_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "municipio_fk"))
	private Municipio municipio;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String cep) {
		this.numero = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

}
