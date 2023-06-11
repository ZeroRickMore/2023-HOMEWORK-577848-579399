package it.uniroma3.personaggi;

import it.uniroma3.diadia.*;
import it.uniroma3.diadia.attrezzi.*;

public class Mago extends AbstractPersonaggio{

	private static final String MESSAGGIO_DONO = "Sei un vero simpaticone, " +
			"con una mia magica azione, troverai un nuovo oggetto " +
			"per il tuo borsone!";
	private static final String MESSAGGIO_SCUSE = "Mi spiace, ma non ho piu' nulla...";
	private static final String MESSAGGIO_REGALO_RICEVUTO = "Abracadabra! Guarda nella stanza ;)";

	public Mago(String nome, String presentazione, Attrezzo attrezzo) {
		super(nome, presentazione, attrezzo);
	}

	public Mago() {
		//importante per la newinstance !!
	}

	@Override
	public String agisci(Partita partita) {
		String msg;
		if (this.getAttrezzo()!=null) {
			partita.getStanzaCorrente().addAttrezzo(this.getAttrezzo());
			this.setAttrezzo(null);
			msg = MESSAGGIO_DONO;
		}
		else {
			msg = MESSAGGIO_SCUSE;
		}
		return msg;
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		attrezzo.setPeso((attrezzo.getPeso())/2);
		partita.getStanzaCorrente().addAttrezzo(attrezzo);
		return MESSAGGIO_REGALO_RICEVUTO;
	}
}
