package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public abstract class AbstractComando {
	
	private String parametro;
	
	public abstract void esegui(Partita partita, IO io); // esecuzione del comando
	
	public void setParametro(String parametro) { // set parametro del comando
		this.parametro = parametro;
	}
	
	public String getNome() {
		return this.getClass().getName().substring(34).toLowerCase();
	}
	
	public String getParametro() {
		return this.parametro;
	}
	
}
