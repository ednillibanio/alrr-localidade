/*******************************************************************************
 * ALE-RR Licença
 * Copyright (C) 2018, ALE-RR
 * Boa Vista, RR - Brasil
 * Todos os direitos reservados.
 * 
 * Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e 
 * não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.localidade.ibge.ejb;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import br.leg.rr.al.localidade.domain.UfType;
import br.leg.rr.al.localidade.ibge.domain.IbgeMunicipio;

/**
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Local
public interface IbgeMunicipioLocal {

	static final String URL_IBGE_MUNICIPIOS = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios";

	static final String URL_IBGE_MUNICIPIOS_POR_UFS = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";

	/**
	 * Busca todos os municipios de acordo com a UF informada no parametro.
	 * 
	 * @param uf filtro dos municipios a serem buscados.
	 * @return lista de municipios encontrados. Retorna null se nenhum municipio for
	 *         encontrado.
	 * @throws IOException
	 */
	List<IbgeMunicipio> buscarMunicipiosPorUF(UfType uf) throws IOException;

	/**
	 * Busca todos os municipios no site do IGBE.
	 * 
	 * @return lista de municipios.
	 * @throws IOException
	 */
	List<IbgeMunicipio> buscarMunicipios() throws IOException;

}
