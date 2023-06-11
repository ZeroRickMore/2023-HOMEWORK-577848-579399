package it.uniroma3.diadia;
import java.io.FileNotFoundException;
import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.FabbricaDiComandi;
import it.uniroma3.diadia.comandi.FabbricaDiComandiRiflessiva;
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

	public DiaDia(IO io) {
		this.partita = new Partita();
		this.ioconsole = io;
	}

	public DiaDia(Labirinto labirinto, IO io) {
		this(io);
		this.partita = new Partita(labirinto);	
	}

	public IO getIoconsole() {
		return ioconsole;
	}

	public Partita getPartita() {
		return partita;
	}

	/**
	 * A ripetizione chiede istruzioni finché la partita non finisce.
	 */

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
	 * @return True se l'istruzione è eseguita e il gioco continua, false altrimenti.
	 * 
	 */

	public boolean processaIstruzione(String istruzione) {
		AbstractComando comandoDaEseguire;
		FabbricaDiComandi factory = new FabbricaDiComandiRiflessiva();
		comandoDaEseguire = factory.costruisciComando(istruzione);
		comandoDaEseguire.esegui(this.partita, this.ioconsole); //chiamata polimorfa a un metodo "esegui"
		if (this.partita.vinta())
			ioconsole.mostraMessaggio("Hai vinto!");
		if (!this.partita.giocatoreIsVivo())
			ioconsole.mostraMessaggio("Hai esaurito i CFU...");
		return this.partita.isFinita();
	}

	public static void main(String[] argc) throws FileNotFoundException, FormatoFileNonValidoException {
		/* N.B. unica istanza di IOConsole
		di cui sia ammessa la creazione */
		Scanner sc = new Scanner(System.in);
		IO io = new IOConsole(sc);
		Labirinto labirinto = Labirinto.newBuilder("labirinto.txt").getLabirinto();
		DiaDia gioco = new DiaDia(labirinto, io);
		gioco.gioca();
		sc.close();
	}

}