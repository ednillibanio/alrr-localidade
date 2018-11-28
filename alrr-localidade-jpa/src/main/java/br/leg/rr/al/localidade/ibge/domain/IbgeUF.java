/**
 * 
 */
package br.leg.rr.al.localidade.ibge.domain;

import java.io.Serializable;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 09-04-2018
 */
public class IbgeUF implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5498375831722516201L;

	private String id;

	private String nome;

	private String sigla;

	private IbgeRegiao regiao;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public IbgeRegiao getRegiao() {
		return regiao;
	}

	public void setRegiao(IbgeRegiao regiao) {
		this.regiao = regiao;
	}

}
