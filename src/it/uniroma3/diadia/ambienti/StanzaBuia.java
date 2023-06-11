package it.uniroma3.diadia.ambienti;

public class StanzaBuia extends Stanza {
	private final static String msgBuio = "Qui c'è buio pesto...";
	private String nomeEnabler;
	
	/**
	 * @param nome
	 * @param nomeAttrezzo
	 */
	
	public StanzaBuia(String nome, String nomeAttrezzo) {
		super(nome);
		this.nomeEnabler = nomeAttrezzo;
	}
	
	/**
	 * 
	 */
	@Override
	public String getDescrizione() {
		if(!this.hasAttrezzo(nomeEnabler)) {
			return msgBuio;
		}
		else {
			return super.getDescrizione();
		}
	}

	public String getMsgbuio() {
		return msgBuio;
	}
	
}
