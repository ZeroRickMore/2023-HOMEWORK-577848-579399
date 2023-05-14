package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi implements Comando{

	private String nomeAttrezzoDaPrendere;
	static final private String msgNonPresente = "L'attrezzo inserito non è presente nella stanza!";
	static final private String msgParametroMancante = "Devi specificare quale strumento vuoi prendere";

	@Override
	public void esegui(Partita partita, IO io) {
		
		if(nomeAttrezzoDaPrendere == null) {
			io.mostraMessaggio(msgParametroMancante);
			return;
		}

		Attrezzo attrezzoDaPrendere = partita.getStanzaCorrente().getAttrezzo(nomeAttrezzoDaPrendere);
		if(attrezzoDaPrendere == null) {
			io.mostraMessaggio(msgNonPresente);
			return;
		}
		else {
			partita.getStanzaCorrente().removeAttrezzoDaStanza(attrezzoDaPrendere);
			boolean attrezzoRimossoDaStanzaChecker = partita.getGiocatore().getBorsa().addAttrezzo(attrezzoDaPrendere);
			if (attrezzoRimossoDaStanzaChecker) {
				io.mostraMessaggio("Hai preso l'attrezzo: " + nomeAttrezzoDaPrendere + ", di peso: " + attrezzoDaPrendere.getPeso());
				return;
			}
			else 
				io.mostraMessaggio(msgParametroMancante);
			return;
		}
	}

	@Override
	public void setParametro (String parametro) {
		this.nomeAttrezzoDaPrendere = parametro;
	}

	@Override
	public String getNome() {
		return "prendi";
	}

	@Override
	public String getParametro() {
		return this.nomeAttrezzoDaPrendere;
	}
	
	public String getMsgnonpresente() {
		return msgNonPresente;
	}

	public String getMsgparametromancante() {
		return msgParametroMancante;
	}
}
