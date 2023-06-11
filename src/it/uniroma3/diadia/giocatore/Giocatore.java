/* 
 * – La classe Giocatore ha la responsabilità di gestire i CFU del giocatore e
 * di memorizzare gli attrezzi in un oggetto istanza della classe Borsa
 */

package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.Config;

public class Giocatore {

	static final private int CFU_INIZIALI = Config.getCFUiniziali();
	
	private String nome;
	private int CFU;
	private Borsa borsa;
	
	
	public Giocatore() {
		this.nome = null;
		this.CFU = CFU_INIZIALI;
		this.borsa = new Borsa();
	}
	
	public Borsa getBorsa() {
		return borsa;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCfu() {
		return this.CFU;
	}
	

	public void setCfu(int cfu) {
		this.CFU = cfu;		
	}	
	
	public String toString() {
		return "Il numero di CFU è " + this.CFU + "\n" + this.borsa;
	}
	
}
