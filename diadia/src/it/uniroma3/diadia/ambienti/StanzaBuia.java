package it.uniroma3.diadia.ambienti;

public class StanzaBuia extends Stanza {

	private String nomeEnabler;

	public StanzaBuia(String nome, String nomeAttrezzo) {
		super(nome);
		this.nomeEnabler = nomeAttrezzo;
	}

	@Override
	public String getDescrizione() {
		if(!this.hasAttrezzo(nomeEnabler)) {
			return "Qui c'è buio pesto";
		}
		else {
			return super.getDescrizione();
		}
	}
}
