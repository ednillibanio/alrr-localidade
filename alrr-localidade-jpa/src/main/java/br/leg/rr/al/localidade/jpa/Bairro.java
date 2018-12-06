package br.leg.rr.al.localidade.jpa;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Classe persistente que representa a tabela "bairro".
 * 
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "bairro_uq", columnNames = { "nome", "municipio_id" }) })
public class Bairro extends Localidade {

	/**
	 * 
	 */
	private static final long serialVersionUID = 796491013446731504L;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "municipio_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "municipio_fk"), nullable = false)
	private Municipio municipio;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

}
