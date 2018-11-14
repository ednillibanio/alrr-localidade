package br.leg.rr.al.localidade.domain;

import java.util.EnumMap;

import br.leg.rr.al.core.jpa.BasicEnum;

public enum EnderecoType implements BasicEnum<EnderecoType> {

	RESIDENCIAL("Residêncial"), COMERCIAL("Comercial"), COBRANCA("Cobrança"), ENTREGA("Entrega");

	private EnderecoType(String label) {
		this.label = label;
	}

	private String label;

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return label;
	}

	/**
	 * Contém os valores das chaves que serão armazenados no banco de dados.
	 * 
	 * @return Retorna uma lista com todos os EnderecoType.
	 */
	public EnumMap<EnderecoType, String> getEnumMap() {
		EnumMap<EnderecoType, String> map = new EnumMap<EnderecoType, String>(EnderecoType.class);
		map.put(EnderecoType.RESIDENCIAL, "1");
		map.put(EnderecoType.COMERCIAL, "2");
		map.put(EnderecoType.COBRANCA, "3");
		map.put(EnderecoType.ENTREGA, "4");
		return map;
	}

}
