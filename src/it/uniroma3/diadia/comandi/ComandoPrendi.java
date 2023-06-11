package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi extends AbstractComando {

	static final private String msgNonPresente = "L'attrezzo inserito non è presente nella stanza!";
	static final private String msgParametroMancante = "Devi specificare quale strumento vuoi prendere";

	@Override
	public void esegui(Partita partita, IO io) {
		
		if(this.getParametro() == null) {
			io.mostraMessaggio(msgParametroMancante);
			return;
		}

		Attrezzo attrezzoDaPrendere = partita.getStanzaCorrente().getAttrezzo(this.getParametro());
		if(attrezzoDaPrendere == null) {
			io.mostraMessaggio(msgNonPresente);
			return;
		}
		else {
			partita.getStanzaCorrente().removeAttrezzoDaStanza(attrezzoDaPrendere);
			boolean attrezzoRimossoDaStanzaChecker = partita.getGiocatore().getBorsa().addAttrezzo(attrezzoDaPrendere);
			if (attrezzoRimossoDaStanzaChecker) {
				io.mostraMessaggio("Hai preso l'attrezzo: " + this.getParametro() + ", di peso: " + attrezzoDaPrendere.getPeso());
				return;
			}
			else 
				io.mostraMessaggio(msgParametroMancante);
			return;
		}
	}
	
	public String getMsgnonpresente() {
		return msgNonPresente;
	}

	public String getMsgparametromancante() {
		return msgParametroMancante;
	}
}
