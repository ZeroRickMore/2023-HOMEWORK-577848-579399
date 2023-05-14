package it.uniroma3.diadia.ambienti;

public class StanzaBloccata extends Stanza {

	private String direzioneBloccata;
	private String nomeAttrezzoEnabler;
	
	/**
	 * 
	 * @param nome
	 * @param direzioneBloccata
	 * @param nomeAttrezzoEnabler
	 */
	public StanzaBloccata(String nome, String direzioneBloccata, String nomeAttrezzoEnabler) {
		super(nome);
		this.direzioneBloccata = direzioneBloccata;
		this.nomeAttrezzoEnabler = nomeAttrezzoEnabler;
	}
	
	/*
	 * 
	 */
	@Override
	public Stanza getStanzaAdiacente(String direzione) {
		
		if(direzione.equals(direzioneBloccata)) { //se voglio andare nella direzione bloccata
			if(!this.hasAttrezzo(nomeAttrezzoEnabler)) //se non ho l'attrezzo
				return this; //restituisci la stessa stanza perché non posso andarci	
		}
		
		return super.getStanzaAdiacente(direzione); //se non ho ridato la stanza, i casi restanti sono che posso andarci
	}
	/*
	 * 
	 */
	@Override
	public String getDescrizione() {
		return "Ti trovi in: " + super.getDescrizione() + "\nPer andare in direzione " + this.direzioneBloccata + 
				"\nnella stanza deve essere presente l'attrezzo " + this.nomeAttrezzoEnabler;
	}
	
	
}