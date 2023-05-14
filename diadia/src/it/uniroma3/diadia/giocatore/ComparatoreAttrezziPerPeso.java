package it.uniroma3.diadia.giocatore;
import java.util.Comparator;

import it.uniroma3.diadia.attrezzi.*;

public class ComparatoreAttrezziPerPeso implements Comparator<Attrezzo>{
	
	@Override
	public int compare(Attrezzo a1, Attrezzo a2) {
		if (a1.getPeso() - a2.getPeso() == 0) {
			return a1.getNome().compareToIgnoreCase(a2.getNome());
		}
		return a1.getPeso() - a2.getPeso();
	}
}
