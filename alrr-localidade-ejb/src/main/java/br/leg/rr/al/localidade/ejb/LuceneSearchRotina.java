package br.leg.rr.al.localidade.ejb;
/*******************************************************************************
 * ALE-RR Licença
 * Copyright (C) 2018, ALE-RR
 * Boa Vista, RR - Brasil
 * Todos os direitos reservados.
 * 
 * Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e 
 * não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 10-04-2018
 */
public class LuceneSearchRotina {

	@PersistenceContext
	private EntityManager entityManager;

	@PostConstruct
	public void init() {
		/*
		 * FullTextEntityManager fullTextEntityManager =
		 * Search.getFullTextEntityManager(entityManager); try {
		 * fullTextEntityManager.createIndexer().startAndWait(); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}
}
