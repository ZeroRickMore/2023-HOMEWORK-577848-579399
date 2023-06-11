package it.uniroma3.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Cane extends AbstractPersonaggio{

	
	private static final String MESSAGGIO_MORSO_SALUTO = "Woof woof! ARF!!! >:( \n(Non osare salutarmi, umano! "
			+ "Ti ho morso e ti ho mangiato un CFU >:( )";
	private static final String NOME_CIBO_PREFERITO = "Osso";

	private static final String MESSAGGIO_MORSO_REGALO_SBAGLIATO = "Woof woof! ARF!!! >:( \n(Ma che ci faccio con questo?!"
			+ "Ti ho morso e ti ho mangiato un CFU >:( )";

	private static final String MESSAGGIO_MORSO_REGALO_GIUSTO = "Slurp! Woof woof!!! :) \n(Grazie, non mangiavo da una vita..."
			+ "Ecco a te un regalo per ringraziarti!";

	
	public Cane(String nome, String presentaz, Attrezzo attrezzo) {
		super(nome, presentaz, attrezzo);
	}

	public Cane() {
		//importante per la newinstance !!
	}
	
	/**
	 * Se salutato, morde il giocatore e gli diminuisce di 1 i CFU
	 */

	@Override
	public String agisci(Partita partita) {
		partita.getGiocatore().setCfu(partita.getGiocatore().getCfu()-1);
		return MESSAGGIO_MORSO_SALUTO;
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		if(NOME_CIBO_PREFERITO.equalsIgnoreCase(attrezzo.getNome())) {
			partita.getStanzaCorrente().addAttrezzo(this.getAttrezzo());
			return MESSAGGIO_MORSO_REGALO_GIUSTO;
		}
		else {
			partita.getGiocatore().setCfu(partita.getGiocatore().getCfu()-1);
			return MESSAGGIO_MORSO_REGALO_SBAGLIATO;
		}
	}

}
