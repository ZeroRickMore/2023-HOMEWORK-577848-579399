package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.*;

public class ComandoFine implements Comando {
	
	private final static String msgFine = "Grazie di aver giocato!";
	
	@Override
	public void esegui(Partita partita, IO io) {
		io.mostraMessaggio(msgFine);  // si desidera smettere
		partita.setFinita();
	}

	@Override
	public void setParametro(String parametro) {
		// Fine non ha parametri
		return;
	}

	@Override
	public String getNome() {
		return "fine";
	}
	
	@Override
	public String getParametro() {
		return null;
	}
	
	public String getMsgfine() {
		return msgFine;
	}

}
