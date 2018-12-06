/**
 * 
 */
package br.leg.rr.al.localidade.ibge.domain;

import java.io.Serializable;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 09-04-2018
 */
public class IbgeMunicipio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7732002472772084110L;

	private String id;

	private String nome;

	private IbgeMicrorregiao microrregiao;

	/**
	 * Retorna a UF que o município faz parte. Para retornar a entidade IbgeUF, é
	 * percorrido as entidades Microrregiao e Mesorregiao.
	 * 
	 * @return A UF que o município faz parte.
	 */
	public IbgeUF getUF() {
		return getMicrorregiao().getMesorregiao().getUF();
	}

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

	public IbgeMicrorregiao getMicrorregiao() {
		return microrregiao;
	}

	public void setMicrorregiao(IbgeMicrorregiao microrregiao) {
		this.microrregiao = microrregiao;
	}

}
