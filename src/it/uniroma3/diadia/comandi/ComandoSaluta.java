package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.personaggi.AbstractPersonaggio;

public class ComandoSaluta extends AbstractComando{
	
	private static final String MESSAGGIO_CHI = "Chi dovrei salutare?";

	@Override
	public void esegui(Partita partita, IO io) {
		AbstractPersonaggio personaggio = partita.getStanzaCorrente().getPersonaggio();
		if (personaggio!=null) {
			io.mostraMessaggio(personaggio.saluta());
		}
		else {
			io.mostraMessaggio(MESSAGGIO_CHI);
		}

	}
}
