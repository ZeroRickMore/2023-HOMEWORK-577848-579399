package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.personaggi.AbstractPersonaggio;

public class ComandoRegala extends AbstractComando{
	
	private static final String MESSAGGIO_NO_PARAMETRO = "Devi specificare quale attrezzo vuoi regalare!";
	private static final String MESSAGGIO_NO_REGALO_IN_BORSA = "L'attrezzo che vuoi regalare non è presente nella borsa!";
	private static final String MESSAGGIO_CHI = "A chi vuoi regalare l'attrezzo?";

	@Override
	public void esegui(Partita partita, IO io) {
		AbstractPersonaggio personaggio = partita.getStanzaCorrente().getPersonaggio();
		if(this.getParametro()==null) {
			io.mostraMessaggio(MESSAGGIO_NO_PARAMETRO);
		}
		else if(!partita.getGiocatore().getBorsa().hasAttrezzo(this.getParametro())) {
			io.mostraMessaggio(MESSAGGIO_NO_REGALO_IN_BORSA);
		}
		else if (personaggio == null){
			io.mostraMessaggio(MESSAGGIO_CHI);
		}
		else {
			io.mostraMessaggio(personaggio.riceviRegalo(partita.getGiocatore().getBorsa().removeAttrezzoDaBorsa(this.getParametro()), partita));
		}
	}
}


