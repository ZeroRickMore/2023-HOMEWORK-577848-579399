package it.uniroma3.diadia;
import it.uniroma3.diadia.attrezzi.*;
import it.uniroma3.diadia.ambienti.*;


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

	static final private String[] elencoComandi = {"vai", "aiuto", "fine", "prendi", "posa"};

	private Partita partita;
	private IOConsole ioconsole;

	public DiaDia() { //crea nuova partita
		this.partita = new Partita();
		this.ioconsole = new IOConsole();
	}

	public IOConsole getIoconsole() {
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
		Comando comandoDaEseguire = new Comando(istruzione);
		String nome = comandoDaEseguire.getNome();
		if        ("fine".equals(nome)) {
			this.fine(); 
			return true;
		} else if ("vai".equals(nome))
			this.vai(comandoDaEseguire.getParametro());
		else if   ("aiuto".equals(nome))
			this.aiuto();
		else if   ("prendi".equals(nome))
			this.prendi(comandoDaEseguire.getParametro());
		else if   ("posa".equals(nome))
			this.posa(comandoDaEseguire.getParametro());
		else ioconsole.mostraMessaggio("Comando sconosciuto");
		if (this.partita.vinta()) {
			ioconsole.mostraMessaggio("Hai vinto!");
			return true;
		} else
			return false;
	}   

	// implementazioni dei comandi dell'utente:

	/**
	 * Stampa informazioni di aiuto.
	 */
	private void aiuto() {
		for(int i=0; i< elencoComandi.length; i++) 
			ioconsole.mostraMessaggio(elencoComandi[i]+" "); //avendo println nella IOConsole, i comandi saranno stampati uno sotto l'altro. (codice IOConsole non modificabile)

		ioconsole.mostraMessaggio("---Finito l'elenco dei comandi!---");
	}

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra 
	 * e ne stampa il nome, altrimenti stampa un messaggio di errore
	 */
	
	private void vai(String direzione) {
		while(direzione==null || direzione == " IstruzioneNonMessa@@" ) { //finché l'utente inserisce invio, quindi è null
			ioconsole.mostraMessaggio("Dove vuoi andare ?");
			direzione = ioconsole.leggiRiga();
		} 
		Stanza prossimaStanza = this.partita.getStanzaCorrente().getStanzaAdiacente(direzione);
		if (prossimaStanza == null)
			ioconsole.mostraMessaggio("\nDirezione non valida, ti trovi in:\n");
		else {
			this.partita.setStanzaCorrente(prossimaStanza);
			int cfu = this.partita.getGiocatore().getCfu();
			this.partita.getGiocatore().setCfu(cfu--);
		}
		ioconsole.mostraMessaggio(partita.getStanzaCorrente().getDescrizione());
	}

	/**
	 * Comando "Fine".
	 */
	
	private void fine() {
		ioconsole.mostraMessaggio("Grazie di aver giocato!");  // si desidera smettere
	}
	
	/**
	 * Prende un attrezzo dalla stanza corrente e lo mette nella borsa, usando come parametro il nome dell'attrezzo.
	 * Se l'attrezzo non è nella stanza dà un messaggio di errore.
	 */

	private void prendi(String nomeAttrezzoDaSpostare) {
		while(nomeAttrezzoDaSpostare == null) {
			ioconsole.mostraMessaggio("Quale strumento vuoi prendere?");
			nomeAttrezzoDaSpostare= ioconsole.leggiRiga();
		}	
		Attrezzo attrezzoDaSpostare = this.partita.getStanzaCorrente().getAttrezzo(nomeAttrezzoDaSpostare);
		if(attrezzoDaSpostare == null)
			ioconsole.mostraMessaggio("L'attrezzo inserito non è presente nella stanza!");
		else {
		partita.getStanzaCorrente().removeAttrezzoDaStanza(attrezzoDaSpostare);
		boolean attrezzoRimossoDaStanzaChecker = partita.getGiocatore().getBorsa().addAttrezzo(attrezzoDaSpostare);
		if (attrezzoRimossoDaStanzaChecker)
			ioconsole.mostraMessaggio("Hai preso l'attrezzo: " + nomeAttrezzoDaSpostare + ", di peso: " + attrezzoDaSpostare.getPeso());
		else 
			ioconsole.mostraMessaggio("L'attrezzo non è presente in questa stanza!");
		}
	}
	
	/**
	 * Posa un attrezzo dalla borsa e lo mette nella stanza corrente, usando come parametro il nome dell'attrezzo.
	 * Se l'attrezzo non è nella borsa dà un messaggio di errore.
	 */

	private void posa(String nomeAttrezzoDaPosare) {
		while(nomeAttrezzoDaPosare == null) {
			ioconsole.mostraMessaggio("Quale strumento vuoi posare?");
			nomeAttrezzoDaPosare = ioconsole.leggiRiga();
		}	
		Attrezzo attrezzoRimossoDaBorsaChecker = partita.getGiocatore().getBorsa().removeAttrezzoDaBorsa(nomeAttrezzoDaPosare); 
		//con removeAttrezzoDaBorsa rimuovo l'attrezzo con quel nome, se lo trova, e lo restituisce come parametro da aggiungere nella stanza
		if(attrezzoRimossoDaBorsaChecker != null) {	
			partita.getStanzaCorrente().addAttrezzo(attrezzoRimossoDaBorsaChecker);		
			ioconsole.mostraMessaggio("Hai posato l'attrezzo: " + nomeAttrezzoDaPosare); 
		}
		else {
			ioconsole.mostraMessaggio("L'attrezzo non è presente nella borsa!");
		}
	}
	
	
	public static void main(String[] argc) {
		DiaDia gioco = new DiaDia();
		gioco.gioca();
	}
}