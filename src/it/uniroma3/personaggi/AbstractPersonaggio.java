package it.uniroma3.personaggi;

import it.uniroma3.diadia.*;
import it.uniroma3.diadia.attrezzi.*;

public abstract class AbstractPersonaggio {
	private String nome;
	private String presentazione;
	private boolean haSalutato;
	private Attrezzo attrezzo;

	public AbstractPersonaggio(String nome, String presentaz, Attrezzo attrezzo) {
		this.nome = nome;
		this.presentazione = presentaz;
		this.haSalutato = false;
		this.attrezzo = attrezzo;
	}
	
	public AbstractPersonaggio() {
		
	}

	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getPresentazione() {
		return presentazione;
	}
	
	public void setPresentazione(String presentazione) {
		this.presentazione = presentazione;
	}

	public void setHaSalutato(boolean haSalutato) {
		this.haSalutato = haSalutato;
	}

	public Attrezzo getAttrezzo() {
		return this.attrezzo;
	}
	
	public void setAttrezzo(Attrezzo attrezzo) {
		this.attrezzo = attrezzo;
	}

	public boolean haSalutato() {
		return this.haSalutato;
	}

	public String saluta() {
		StringBuilder risposta = new StringBuilder("Ciao, io sono ");
		risposta.append(this.getNome()+". ");
		
		if (!haSalutato) 
			risposta.append(this.presentazione);
		else
			risposta.append("Ci siamo gia' presentati!");
		
		this.haSalutato = true;
		return risposta.toString();
	}

	abstract public String agisci(Partita partita);
	
	abstract public String riceviRegalo(Attrezzo attrezzo, Partita partita);

	@Override
	public String toString() {
		return this.getNome();
	}
}
