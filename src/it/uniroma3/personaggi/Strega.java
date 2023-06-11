package it.uniroma3.personaggi;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Strega extends AbstractPersonaggio{
	
	private static final String MESSAGGIO_NON_SALUTATO = "NEANCHE MI SALUTI E VUOI AIUTO DA ME? SPARISCI!";

	private static final String MESSAGGIO_SALUTATO = "Che persona gentile, va bene, ti aiuterò! Andrai in una stanza piena di"
			+ "tesori, guarda bene!";
	
	private static final String MESSAGGIO_REGALO = "AHFHAHAJHHAJFHGJASFGHAHAHHAHAHAHAHHA";

	public Strega(String nome, String presentaz, Attrezzo attrezzo) {
		super(nome, presentaz, attrezzo);
	}
	
	public Strega() {
		//importante per la newinstance !!
	}

	@Override
	public String agisci(Partita partita) {
		if(!this.haSalutato()) {
			List<Stanza> stanzeAdiacenti = new ArrayList<Stanza>(partita.getStanzaCorrente().getStanzeAdiacenti().values());
			Stanza stanzaConMenoAttrezzi = stanzeAdiacenti.get(0);
			for(Stanza i : stanzeAdiacenti) {
				if(i.getAttrezzi().size() < stanzaConMenoAttrezzi.getAttrezzi().size()) {
					stanzaConMenoAttrezzi = i;
				}
			}
			partita.setStanzaCorrente(stanzaConMenoAttrezzi);
			return MESSAGGIO_NON_SALUTATO;
		}
		else {
			List<Stanza> stanzeAdiacenti = new ArrayList<Stanza>(partita.getStanzaCorrente().getStanzeAdiacenti().values());
			Stanza stanzaConPiuAttrezzi = stanzeAdiacenti.get(0);
			for(Stanza i : stanzeAdiacenti) {
				if(i.getAttrezzi().size() > stanzaConPiuAttrezzi.getAttrezzi().size()) {
					stanzaConPiuAttrezzi = i;
				}
			}
			partita.setStanzaCorrente(stanzaConPiuAttrezzi);
			return MESSAGGIO_SALUTATO;
		}
		
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		this.setAttrezzo(attrezzo);
		StringBuilder stringa = new StringBuilder();
		for(int i=0; i<1000;i++)
			stringa.append(MESSAGGIO_REGALO);
		return new String(stringa);
	}
}