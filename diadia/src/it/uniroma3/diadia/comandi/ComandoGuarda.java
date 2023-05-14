package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.*;
import it.uniroma3.diadia.Partita;

public class ComandoGuarda implements Comando{

	@Override
	public void esegui(Partita partita, IO io) {
		if(partita.getStanzaCorrente().getDescrizione() != "Qui c'è buio pesto...") {
			io.mostraMessaggio("Ti trovi in: " + partita.getStanzaCorrente().getDescrizione() + "\n" + partita.getGiocatore());
		} else
		io.mostraMessaggio(partita.getStanzaCorrente().getDescrizione() + "\n" + partita.getGiocatore());
	}

	@Override
	public void setParametro(String parametro) {
		// Guarda non ha parametri
		return;
	}

	@Override
	public String getNome() {
		return "guarda";
	}

	@Override
	public String getParametro() {
		return null;
	}


}
