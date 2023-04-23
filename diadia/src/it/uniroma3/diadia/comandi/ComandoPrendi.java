package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi implements Comando{

	private String nomeAttrezzoDaPrendere;

	@Override
	public void esegui(Partita partita, IO io) {
		
		if(nomeAttrezzoDaPrendere == null) {
			io.mostraMessaggio("Devi specificare quale strumento vuoi prendere");
			return;
		}

		Attrezzo attrezzoDaPrendere = partita.getStanzaCorrente().getAttrezzo(nomeAttrezzoDaPrendere);
		if(attrezzoDaPrendere == null) {
			io.mostraMessaggio("L'attrezzo inserito non è presente nella stanza!");
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
				io.mostraMessaggio("L'attrezzo non è presente in questa stanza!");
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
}
