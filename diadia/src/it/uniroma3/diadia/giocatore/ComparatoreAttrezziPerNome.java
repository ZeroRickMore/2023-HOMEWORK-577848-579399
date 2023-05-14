package it.uniroma3.diadia.giocatore;

import java.util.Comparator;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComparatoreAttrezziPerNome implements Comparator<Attrezzo>{
	
	@Override
	public int compare(Attrezzo a1, Attrezzo a2) {
		return a1.getNome().compareToIgnoreCase(a2.getNome());
	}
}
