package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.*;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa extends AbstractComando {
	
	private static final String msgNoParametro = "Devi specificare quale strumento vuoi posare";
	private static final String msgNonTrovato = "L'attrezzo non è presente nella borsa!";
	
	@Override
	public void esegui(Partita partita, IO io) {
		
		if(this.getParametro() == null) {
			io.mostraMessaggio(msgNoParametro);
			return;
		}	
		
		Attrezzo attrezzoRimossoDaBorsaChecker = partita.getGiocatore().getBorsa().removeAttrezzoDaBorsa(this.getParametro()); 
		//con removeAttrezzoDaBorsa rimuovo l'attrezzo con quel nome, se lo trova, e lo restituisce come parametro da aggiungere nella stanza
		if(attrezzoRimossoDaBorsaChecker != null) {	
			partita.getStanzaCorrente().addAttrezzo(attrezzoRimossoDaBorsaChecker);		
			io.mostraMessaggio("Hai posato l'attrezzo: " + this.getParametro()); 
		}
		else {
			io.mostraMessaggio(msgNonTrovato);
		}
	}

	public String getMsgnoparametro() {
		return msgNoParametro;
	}

	public String getMsgnontrovato() {
		return msgNonTrovato;
	}

}
