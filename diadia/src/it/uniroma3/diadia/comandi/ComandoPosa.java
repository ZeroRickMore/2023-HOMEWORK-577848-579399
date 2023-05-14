package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.*;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa implements Comando{
	
	private String nomeAttrezzoDaPosare;
	private static final String msgNoParametro = "Devi specificare quale strumento vuoi posare";
	private static final String msgNonTrovato = "L'attrezzo non è presente nella borsa!";
	
	@Override
	public void esegui(Partita partita, IO io) {
		
		if(nomeAttrezzoDaPosare == null) {
			io.mostraMessaggio(msgNoParametro);
			return;
		}	
		
		Attrezzo attrezzoRimossoDaBorsaChecker = partita.getGiocatore().getBorsa().removeAttrezzoDaBorsa(nomeAttrezzoDaPosare); 
		//con removeAttrezzoDaBorsa rimuovo l'attrezzo con quel nome, se lo trova, e lo restituisce come parametro da aggiungere nella stanza
		if(attrezzoRimossoDaBorsaChecker != null) {	
			partita.getStanzaCorrente().addAttrezzo(attrezzoRimossoDaBorsaChecker);		
			io.mostraMessaggio("Hai posato l'attrezzo: " + nomeAttrezzoDaPosare); 
		}
		else {
			io.mostraMessaggio(msgNonTrovato);
		}
	}
	
	@Override
	public void setParametro (String parametro) {
		this.nomeAttrezzoDaPosare = parametro;
	}
	
	@Override
	public String getNome() {
		return "posa";
	}
	
	@Override
	public String getParametro() {
		return this.nomeAttrezzoDaPosare;
	}
	
	public String getMsgnoparametro() {
		return msgNoParametro;
	}

	public String getMsgnontrovato() {
		return msgNonTrovato;
	}

}
