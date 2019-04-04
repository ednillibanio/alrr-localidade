package br.leg.rr.al.localidade.domain;

public enum UfType {

	AC("Acre", "12"), AL("Alagoas", "27"), AP("Amapá", "16"), AM("Amazonas", "13"), BA("Bahia", "29"), CE("Ceará",
			"23"), DF("Distrito Federal", "53"), ES("Espírito Santo", "32"), GO("Goiás", "52"), MA("Maranhão",
					"21"), MT("Mato Grosso", "51"), MS("Mato Grosso do Sul", "50"), MG("Minas Gerais", "31"), PA("Pará",
							"15"), PB("Paraíba", "25"), PR("Paraná", "41"), PE("Pernambuco", "26"), PI("Piauí",
									"22"), RJ("Rio de Janeiro", "33"), RN("Rio Grande do Norte", "24"), RO("Rondônia",
											"11"), RR("Roraima", "14"), RS("Rio Grande do Sul", "43"), SC(
													"Santa Catarina", "42"), SP("São Paulo", "35"), SE("Sergipe",
															"28"), TO("Tocantins", "17"), FP("Fora do país", "00");

	private String ibgeId;

	private UfType(String label) {
		this.label = label;
	}

	private UfType(String label, String IbgeId) {
		this.label = label;
		this.ibgeId = IbgeId;
	}

	private String label;

	public String getLabel() {
		return label;
	}

	public String toString() {
		return this.name();
	}

	/**
	 * @return the ibgeId
	 */
	public String getIbgeId() {
		return ibgeId;
	}

	/**
	 * @param ibgeId
	 *            the ibgeId to set
	 */
	public void setIbgeId(String ibgeId) {
		this.ibgeId = ibgeId;
	}
}
