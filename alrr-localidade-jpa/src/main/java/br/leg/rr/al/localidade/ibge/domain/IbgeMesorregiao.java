/**
 * 
 */
package br.leg.rr.al.localidade.ibge.domain;

import java.io.Serializable;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 09-04-2018
 */
public class IbgeMesorregiao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2243171808106081910L;

	private String id;

	private String nome;

	private IbgeUF UF;

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

	public IbgeUF getUF() {
		return UF;
	}

	public void setUF(IbgeUF uF) {
		UF = uF;
	}

}
