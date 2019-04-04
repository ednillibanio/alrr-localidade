package br.leg.rr.al.localidade.utils;

import br.leg.rr.al.core.utils.StringHelper;
import br.leg.rr.al.localidade.ibge.domain.IbgeMunicipio;
import br.leg.rr.al.localidade.jpa.Municipio;
import br.leg.rr.al.localidade.jpa.UnidadeFederativa;

public final class MunicipioUtils {

	/**
	 * <p>
	 * Método responsável por converter uma entidade do tipo IbgeMunicipio para
	 * Municipio. Este método é utilizado na importação dos municípios da base do
	 * igge para a base local.
	 * </p>
	 * 
	 * @param municipio
	 * @param uf
	 * @return
	 */
	public static Municipio converterIbgeMunicipioParaMunicipio(IbgeMunicipio municipio, UnidadeFederativa uf) {

		Municipio mun = new Municipio();
		String nome = StringHelper.capitalizeFully(municipio.getNome());
		mun.setIbgeId(municipio.getId());
		mun.setNome(nome);
		mun.setUf(uf);
		return mun;

	}
}
