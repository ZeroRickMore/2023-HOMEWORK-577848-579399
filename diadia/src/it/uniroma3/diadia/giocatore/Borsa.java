/** 
 * Classe che riguarda l'oggetto "borsa" del nostro gioco, in cui possiamo riporre gli attrezzi durante il percorso,
 * per poterne portare più di uno.
 * Ha una capienza ben definita dal <<peso>>.
 */
	


package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.attrezzi.*;

public class Borsa {

	public final static int DEFAULT_PESO_MAX_BORSA = 10;
	private Attrezzo[] attrezzi;
	private int numeroAttrezzi;
	private int pesoMax;


	public Borsa() {					   //crea la borsa partendo dal peso max di default (10)
		this(DEFAULT_PESO_MAX_BORSA);
	}

	public Borsa(int pesoMax) {			   //crea la borsa partendo da un pesoMax dato come parametro 
		this.pesoMax = pesoMax;
		this.attrezzi = new Attrezzo[10];  // speriamo bastino... (no)
		this.numeroAttrezzi = 0;
	}

	/**
	 * Scritto da noi, lo lasciamo?
	 */
	
	public void getArrayAttrezzi() { //TODO utile per la fase di testing. Si ometterà l'impiego di ioconsole.mostraMessaggio per questo metodo
		for(int i = 0; i<10; i++) {
			if(this.attrezzi[i] != null)
				System.out.println("Ecco il tuo elemento -> " + this.attrezzi[i].getNome());
			else
				System.out.println(" ----------- NULL ----------");
		}
		System.out.println("Questi erano gli attrezzi della stanza");
	}
	
	public boolean addAttrezzo(Attrezzo attrezzo) {			//aggiunge un attrezzo alla borsa, ha come parametro l'oggetto attrezzo
		if (attrezzo == null) {
			return false;
		}
		if (this.getPeso() + attrezzo.getPeso() > this.getPesoMax())
			return false;
		if (this.numeroAttrezzi==10)
			return false;
		this.attrezzi[this.numeroAttrezzi] = attrezzo;
		this.numeroAttrezzi++;
		return true;
	}

	public int getPesoMax() {
		return pesoMax;
	}

	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		Attrezzo a = null;
		for (int i= 0; i<this.numeroAttrezzi; i++)
			if (this.attrezzi[i].getNome().equals(nomeAttrezzo))
				a = attrezzi[i];
		return a;
	}

	public int getPeso() {
		int peso = 0;
		for (int i= 0; i<this.numeroAttrezzi; i++)
			peso += this.attrezzi[i].getPeso();

		return peso;
	}

	public boolean isEmpty() {
		return this.numeroAttrezzi == 0;
	}

	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.getAttrezzo(nomeAttrezzo)!=null;
	}

	/**
	 * Rimuove un attrezzo dalla borsa usando come parametro il nome dell'attrezzo
	 */
	
	public Attrezzo removeAttrezzoDaBorsa(String nomeAttrezzo) {
		Attrezzo a = null;
		for(int i=0; i<numeroAttrezzi; i++) {
			if(this.attrezzi[i].getNome().equals(nomeAttrezzo)) {
				a = this.attrezzi[i];
				for(int j = i; j<numeroAttrezzi ; j++) {
					if(j==numeroAttrezzi-1) { 			//se sto all'ultima posizione evito di andare out of bounds e metto a null
						this.attrezzi[j] = null;
						break;
					}
					this.attrezzi[j] = this.attrezzi[j+1];			
				}		
				numeroAttrezzi--;
				return a;
			} 
		}
		return a;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();

		if (!this.isEmpty()) {
			s.append("Contenuto borsa ("+this.getPeso()+"kg/"+this.getPesoMax()+"kg): ");
			for (int i= 0; i<this.numeroAttrezzi; i++)
				s.append(attrezzi[i].toString()+" ");
		}
		else
			s.append("Borsa vuota");
		return s.toString();
	}

}