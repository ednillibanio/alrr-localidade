/**
 * 
 */
package br.leg.rr.al.localidade.ibge.domain;

import java.io.Serializable;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 09-04-2018
 */
public class IbgeMicrorregiao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8478014454981134643L;

	private String id;

	private String nome;

	private IbgeMesorregiao mesorregiao;

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

	public IbgeMesorregiao getMesorregiao() {
		return mesorregiao;
	}

	public void setMesorregiao(IbgeMesorregiao mesorregiao) {
		this.mesorregiao = mesorregiao;
	}

	
}
