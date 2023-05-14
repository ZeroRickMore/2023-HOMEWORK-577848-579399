package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.*;

public class ComandoAiuto implements Comando{
	
	static final private String[] elencoComandi = {"vai", "aiuto", "fine", "prendi", "posa", "guarda"};
	static final private String fineElenco = "---Finito l'elenco dei comandi!---";
	
	@Override
	public void esegui(Partita partita, IO io) {
		for(int i=0; i< elencoComandi.length; i++) 
			io.mostraMessaggio(elencoComandi[i]+" "); //avendo println nella IOConsole, i comandi saranno stampati uno sotto l'altro. (codice IOConsole non modificabile)

		io.mostraMessaggio(fineElenco);
	}

	@Override
	public void setParametro(String parametro) {
		// Aiuto non ha parametri
		return;
	}
	
	
	@Override
	public String getNome() {
		return "aiuto";
	}
	
	@Override
	public String getParametro() {
		return null;
	}

	public String getFineElenco() {
		return fineElenco;
	}
	
	
	
}
