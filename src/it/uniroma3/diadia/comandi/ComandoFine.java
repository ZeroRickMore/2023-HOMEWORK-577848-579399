package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.*;

public class ComandoFine extends AbstractComando {
	
	private final static String msgFine = "Grazie di aver giocato!";
	
	@Override
	public void esegui(Partita partita, IO io) {
		io.mostraMessaggio(msgFine);  // si desidera smettere
		partita.setFinita();
	}
	
	public String getMsgfine() {
		return msgFine;
	}

}
