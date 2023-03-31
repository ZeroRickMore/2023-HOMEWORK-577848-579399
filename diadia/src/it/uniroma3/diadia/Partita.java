package it.uniroma3.diadia;

// import it.uniroma3.diadia.attrezzi.*;
import it.uniroma3.diadia.giocatore.*;
import it.uniroma3.diadia.ambienti.*;

/**
 * Questa classe modella una partita del gioco
 *
 * @author  docente di POO
 * @see Stanza
 * @version base
 */

public class Partita {

	private Stanza stanzaCorrente;
	private boolean finita;
	private Labirinto labirinto;
	private Giocatore giocatore;
	
	
	public Partita(){
		this.labirinto = new Labirinto();
		this.stanzaCorrente = this.labirinto.getStanzaIniziale();
		this.finita = false;
		this.giocatore = new Giocatore();
	}
    
	public Giocatore getGiocatore() {
		return giocatore;
	}
	
	public Labirinto getLabirinto() {
		return labirinto;
	}

	public Stanza getStanzaCorrente() {
		return this.stanzaCorrente;
	}
	
	public void setStanzaCorrente(Stanza stanzaCorrente) {
		this.stanzaCorrente = stanzaCorrente;
	}

	
	/**
	 * Restituisce vero se e solo se la partita e' stata vinta
	 * @return vero se partita vinta
	 */
	
	public boolean vinta() {
		return this.getStanzaCorrente() == this.labirinto.getStanzaVincente();
	}

	
	/**
	 * Restituisce vero se e solo se la partita e' finita
	 * @return vero se partita finita
	 */
	
	public boolean isFinita() {
		return this.finita || vinta() || this.giocatore.getCfu() == 0;
	}

	
	/**
	 * Imposta la partita come finita
	 *
	 */
	
	public void setFinita() {
		this.finita = true;
	}

}
