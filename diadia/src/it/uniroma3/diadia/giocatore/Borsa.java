/** 
 * Classe che riguarda l'oggetto "borsa" del nostro gioco, in cui possiamo riporre gli attrezzi durante il percorso,
 * per poterne portare più di uno.
 * Ha una capienza ben definita dal <<peso>>.
 */



package it.uniroma3.diadia.giocatore;

import java.util.*;
import it.uniroma3.diadia.attrezzi.*;

public class Borsa {

	public final static int DEFAULT_PESO_MAX_BORSA = 10;
	private Map<String, Attrezzo> attrezzi;
	private int pesoMax;


	public Borsa() {					   //crea la borsa partendo dal peso max di default (10)
		this(DEFAULT_PESO_MAX_BORSA);
	}

	public Borsa(int pesoMax) {	//crea la borsa partendo da un pesoMax dato come parametro 
		this.pesoMax = pesoMax;
		this.attrezzi = new HashMap<>();
	}

	public boolean addAttrezzo(Attrezzo attrezzo) {
		if((this.getPeso()+attrezzo.getPeso())<=this.pesoMax)
			return this.attrezzi.putIfAbsent(attrezzo.getNome(), attrezzo)==null;
		else return false;
	}

	public int getPesoMax() {
		return pesoMax;
	}

	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		return this.attrezzi.get(nomeAttrezzo);
	}

	public int getPeso() {
		int peso = 0;
		for(String i: this.attrezzi.keySet()) {
			peso += this.attrezzi.get(i).getPeso();
		}
		return peso;
	}


	public boolean isEmpty() {
		return this.attrezzi.isEmpty();
	}
	
	/**
	 * Restituisce la lista degli attrezzi nella borsa ordinati per peso e
	   quindi, a parità di peso, per nome.
	 */
	
	public List<Attrezzo> getContenutoOrdinatoPerPeso() {
		List<Attrezzo> listaOrdinataPerPeso = new ArrayList<>();
		listaOrdinataPerPeso.addAll(this.attrezzi.values());
		Collections.sort(listaOrdinataPerPeso,  new ComparatoreAttrezziPerPeso());
		return listaOrdinataPerPeso;
	}
	
	/**
	 * Restituisce l'insieme degli attrezzi nella borsa ordinati per nome.
	 */
	public SortedSet<Attrezzo> getContenutoOrdinatoPerNome() {
		SortedSet<Attrezzo> setAttrezziOrdinatiPerNome = new TreeSet<Attrezzo>(new ComparatoreAttrezziPerNome());
		setAttrezziOrdinatiPerNome.addAll(this.attrezzi.values());
		return setAttrezziOrdinatiPerNome;
	}
	
	/**
	 * Restituisce una mappa che associa un intero (rappresentante un
	peso) con l’insieme (comunque non vuoto) degli attrezzi di tale
	peso: tutti gli attrezzi dell'insieme che figura come valore hanno
	lo stesso peso pari all'intero che figura come chiave.
	 */
	public Map<Integer,Set<Attrezzo>> getContenutoRaggruppatoPerPeso() {
		Map<Integer,Set<Attrezzo>> mappaRaggruppataPerPeso = new HashMap<>();
		for(String nomeAttrezzo: this.attrezzi.keySet()) {
			if(mappaRaggruppataPerPeso.containsKey(this.attrezzi.get(nomeAttrezzo).getPeso())) {
				mappaRaggruppataPerPeso.get(this.attrezzi.get(nomeAttrezzo).getPeso()).add(this.attrezzi.get(nomeAttrezzo));
			}
			else {
				SortedSet<Attrezzo> tmp = new TreeSet<>(new ComparatoreAttrezziPerNome());
				tmp.add(this.attrezzi.get(nomeAttrezzo));
				mappaRaggruppataPerPeso.put(this.attrezzi.get(nomeAttrezzo).getPeso(), tmp);
			}
		}
		return mappaRaggruppataPerPeso; 
	}
	
	/**
	 * Restituisce l'insieme gli attrezzi nella borsa
	ordinati per peso e quindi, a parità di peso, per
	nome
	 */
	public SortedSet<Attrezzo> getSortedSetOrdinatoPerPeso() {
		SortedSet<Attrezzo> setAttrezziOrdinatiPerPesoPoiNome = new TreeSet<Attrezzo>(new ComparatoreAttrezziPerPeso());
		setAttrezziOrdinatiPerPesoPoiNome.addAll(this.attrezzi.values());
		return setAttrezziOrdinatiPerPesoPoiNome;
	}

	/**
	 * Controlla se un attrezzo è presente nella borsa usando il nome come parametro
	 */
	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.attrezzi.containsKey(nomeAttrezzo);
	}

	/**
	 * Rimuove un attrezzo dalla borsa usando come parametro il nome dell'attrezzo
	 */

	public Attrezzo removeAttrezzoDaBorsa(String nomeAttrezzo) {
		return this.attrezzi.remove(nomeAttrezzo);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		if (!this.isEmpty()) {
			s.append("Contenuto borsa ("+this.getPeso()+"kg/"+this.getPesoMax()+"kg):\nAttrezzi raggruppati per peso -> ");
			s.append(this.getContenutoRaggruppatoPerPeso().toString());
		}
		else
			s.append("Borsa vuota");
		return s.toString();
	}

}