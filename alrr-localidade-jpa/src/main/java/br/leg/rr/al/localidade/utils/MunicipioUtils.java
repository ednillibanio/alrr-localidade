package br.leg.rr.al.localidade.utils;

import br.leg.rr.al.core.utils.StringHelper;
import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.ibge.domain.IbgeMunicipio;
import br.leg.rr.al.localidade.jpa.Municipio;

public final class MunicipioUtils {

	public static Municipio converterIbgeMunicipioParaMunicipio(IbgeMunicipio municipio) {

		Municipio mun = new Municipio();
		String nome = StringHelper.capitalizeFully(municipio.getNome());
		mun.setIbgeId(municipio.getId());
		mun.setNome(nome);
		mun.setUf(UfType.valueOf(municipio.getUF().getSigla()));
		return mun;

	}
}
