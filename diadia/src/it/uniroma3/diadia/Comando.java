package it.uniroma3.diadia;

/**
 * Questa classe modella un comando.
 * Un comando consiste al piu' di due parole:
 * il nome del comando ed un parametro
 * su cui si applica il comando.
 * (Ad es. alla riga digitata dall'utente "vai nord"
 *  corrisponde un comando di nome "vai" e parametro "nord").
 *
 * @author  docente di POO
 * @version base
 */

public class Comando {

	private String nome;
	private String parametro;


	public Comando(DiaDia diaDia) {
		this.nome = null;
		this.parametro = null;
	}
	public Comando(String istruzione) {
		if(!istruzione.isBlank()) { //se l'istruzione non è vuota (preme invio e basta) e allo stesso tempo non contiene solo spazi. Copre i casi di exception dell'invio e fa si che non vada out of bounds
			this.nome = istruzione.split(" ")[0];    // prima parola: nome del comando
			if(istruzione.contains(" ")) { //grazie alla presenza dello spazio capisco se ha il parametro.
				istruzione = istruzione.concat(" x");  //la concat mi aggiunge uno spazio e un carattere generico così lo split non da errori out of bounds se si mette l'istruzione seguita da uno spazio
				this.parametro = istruzione.split(" ")[1]; // seconda parola: eventuale parametro
			}
		}
	}

	public String getNome() {
		return this.nome;
	}

	public String getParametro() {
		return this.parametro;
	}

	public boolean sconosciuto() {
		return (this.nome == null);
	}
}