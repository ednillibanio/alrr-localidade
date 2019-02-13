/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.localidade.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.leg.rr.al.core.jpa.DominioIndexado;

/**
 * Classe persistente que representa a tabela "pais".
 * 
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Entity
@Table
public class Pais extends DominioIndexado {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1338795676941062754L;

	@Column(length = 3, nullable = true)
	private String sigla;

	@Column(name = "codigo_discagem", length = 4, nullable = true)
	private String codigoDiscagem;

	@Column(length = 70, nullable = true)
	private String nacionalidade;

	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return the codigoDiscagem
	 */
	public String getCodigoDiscagem() {
		return codigoDiscagem;
	}

	/**
	 * @param codigoDiscagem the codigoDiscagem to set
	 */
	public void setCodigoDiscagem(String codigoDiscagem) {
		this.codigoDiscagem = codigoDiscagem;
	}

	/**
	 * @return the nacionalidade
	 */
	public String getNacionalidade() {
		return nacionalidade;
	}

	/**
	 * @param nacionalidade the nacionalidade to set
	 */
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

}
