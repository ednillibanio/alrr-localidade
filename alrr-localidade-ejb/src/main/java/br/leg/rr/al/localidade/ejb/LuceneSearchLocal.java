package br.leg.rr.al.localidade.ejb;

import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface LuceneSearchLocal extends Serializable {

	public void indexar();
}
