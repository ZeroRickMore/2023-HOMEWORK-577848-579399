package it.uniroma3.diadia;
import it.uniroma3.diadia.comandi.*;


/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author  docente di POO 
 *         (da un'idea di Michael Kolling and David J. Barnes) 
 *          
 * @version base
 */

public class DiaDia {

	static final private String MESSAGGIO_BENVENUTO = ""+
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"+
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n"+
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n"+
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n"+
			"Per conoscere le istruzioni usa il comando 'aiuto'.";

	private Partita partita;
	private IO ioconsole;

	public DiaDia(IO io) { //crea nuova partita
		this.partita = new Partita();
		this.ioconsole = io;
	}

	public IO getIoconsole() {
		return ioconsole;
	}

	public void gioca() { 
		String istruzione;
		ioconsole.mostraMessaggio(MESSAGGIO_BENVENUTO);
		do	{	
			ioconsole.mostraMessaggio("\n---Inserire un comando valido---\n");
			istruzione = ioconsole.leggiRiga();
		}
		while (!processaIstruzione(istruzione)); //se non ho ancora vinto. processaIstruzione ritorna vero se ho vinto o se scrivo "fine".
	}

	/**
	 * Processa una istruzione 
	 *
	 * @return true se l'istruzione e' eseguita e il gioco continua, false altrimenti
	 */
	private boolean processaIstruzione(String istruzione) {
		Comando comandoDaEseguire;
		FabbricaDiComandi factory = new FabbricaDiComandiFisarmonica();
		comandoDaEseguire = factory.costruisciComando(istruzione);
		comandoDaEseguire.esegui(this.partita, this.ioconsole); //chiamata polimorfa a un metodo "esegui"
		if (this.partita.vinta())
			ioconsole.mostraMessaggio("Hai vinto!");
		if (!this.partita.giocatoreIsVivo())
			ioconsole.mostraMessaggio("Hai esaurito i CFU...");
		return this.partita.isFinita();
	}


	public static void main(String[] argc) {
		IO io = new IOConsole();
		DiaDia gioco = new DiaDia(io);
		gioco.gioca();
	}
}