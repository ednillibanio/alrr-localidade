package br.leg.rr.al.localidade.domain;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

public class ViaCep implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4903769604268956674L;

	private String cep;

	private String logradouro;

	private String bairro;

	private String localidade;

	private String ibge;

	@Enumerated(EnumType.STRING)
	private UfType uf;

	@Transient
	private Boolean erro = false;

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

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}
