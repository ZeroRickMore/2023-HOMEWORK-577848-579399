package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public 	class ComandoFake extends AbstractComando {

	@Override
	public void esegui(Partita partita, IO io) {
		return;
	}
	
}
