package br.leg.rr.al.localidade.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import br.leg.rr.al.core.jpa.DominioIndexado;

/**
 * <p>
 * Classe persistente que representa a tabela "pais" que possui os seguintes
 * indices:
 * <ul>
 * <li>lower(nome)</li>
 * <li>lower(sigla)</li>
 * </ul>
 * Obs.: A função <strong>lower</strong> é utilizada para evitar que valores
 * sejam duplicados no banco de dados em casos que não devem. O postgres é case
 * sensitive nos campos varchar. Constraints não funciona nesses casos.
 * </p>
 * 
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Entity
@Table
@Indexed
public class Pais extends DominioIndexado {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1338795676941062754L;

	@Column(length = 3, nullable = true)
	private String sigla;

	@Column(name = "codigo_discagem", length = 4, nullable = true)
	private String codigoDiscagem;

	@Analyzer(definition = NOME_ANALYZER)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
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
