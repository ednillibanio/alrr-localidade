package br.leg.rr.al.localidade.jpa;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.leg.rr.al.core.jpa.BaseEntityStatus;
import br.leg.rr.al.localidade.domain.EnderecoType;
import br.leg.rr.al.localidade.domain.EnderecoTypeConverter;

/**
 * Classe persistente que representa a tabela "endereco".
 * 
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Entity
@Table
public class Endereco extends BaseEntityStatus<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1696673731486221778L;

	@Column(nullable = true, length = 8)
	private String cep;

	@Column(nullable = false, length = 250)
	private String logradouro;

	@Column(length = 10, nullable = true)
	private String numero;

	@Column(length = 100, nullable = true)
	private String complemento;

	@Convert(converter = EnderecoTypeConverter.class)
	@Column(name = "tipo_endereco", length = 1, nullable = false)
	private EnderecoType tipo;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "bairro_id", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "bairro_fk"))
	private Bairro bairro;

	// uni-directional many-to-one association to Municipio
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "localidade_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "localidade_fk"))
	private Municipio municipio;

	@Column(length = 20, nullable = true)
	private String latitude;

	@Column(length = 20, nullable = true)
	private String longitude;

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public EnderecoType getTipo() {
		return tipo;
	}

	public void setTipo(EnderecoType tipoEndereco) {
		this.tipo = tipoEndereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
