CREATE UNIQUE INDEX pais_idx1 on localidade_schema.pais (LOWER(pais.nome));  
CREATE UNIQUE INDEX pais_idx2 on localidade_schema.pais (LOWER(pais.sigla));


CREATE UNIQUE INDEX bairro_idx1 on localidade_schema.bairro (municipio_id, LOWER(bairro.nome));
CREATE UNIQUE INDEX cep_idx1 on localidade_schema.cep (municipio_id, cep.numero);
CREATE UNIQUE INDEX municipio_idx1 on localidade_schema.municipio (uf_id, LOWER(municipio.nome));
CREATE UNIQUE INDEX municipio_idx2 on localidade_schema.municipio (municipio.ibgeid);


CREATE UNIQUE INDEX uf_idx1 on localidade_schema.unidade_federativa (pais_id, LOWER(unidade_federativa.nome));
CREATE UNIQUE INDEX uf_idx2 on localidade_schema.unidade_federativa (pais_id, LOWER(unidade_federativa.sigla));
CREATE UNIQUE INDEX uf_idx3 on localidade_schema.unidade_federativa (unidade_federativa.ibgeid);
