package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.*;

public interface Comando {
	
	public void esegui(Partita partita, IO io); // esecuzione del comando

	public void setParametro(String parametro); // set parametro del comando

	public String getNome();
	
	public String getParametro();

}
