package br.leg.rr.al.localidade.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.leg.rr.al.core.jpa.BaseEntity;
import br.leg.rr.al.localidade.domain.UfType;

/**
 * Classe persistente que representa a tabela "cep".
 * 
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Entity
@Table
public class Cep extends BaseEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1696673731486221778L;

	@Column(nullable = false, length = 8)
	private String numero;

	@Column(nullable = true, length = 250)
	private String logradouro;

	@Column(nullable = true, length = 250)
	private String bairro;

	@Column(nullable = false, length = 250)
	private String municipio;

	@Enumerated(EnumType.STRING)
	@Column(name = "uf", nullable = false, length = 2)
	private UfType uf;

	@Transient
	private Boolean erro;

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

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public UfType getUf() {
		return uf;
	}

	public void setUf(UfType uf) {
		this.uf = uf;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getErro() {
		return erro;
	}

	public void setErro(Boolean erro) {
		this.erro = erro;
	}

}
