package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import java.util.*;
public class StanzaProtected {

	static final protected int NUMERO_MASSIMO_DIREZIONI = 4;
	static final protected int NUMERO_MASSIMO_ATTREZZI = 10; 

	protected String nome;
	protected Map<String, Attrezzo> attrezzi;
	protected Map<Direzione, StanzaProtected> stanzeAdiacenti;


	/**
	 * Crea una stanza. Non ci sono stanze adiacenti, non ci sono attrezzi.
	 * @param nome il nome della stanza
	 */

	public StanzaProtected(String nome) {
		this.nome = nome;
		this.stanzeAdiacenti = new HashMap<>();
		this.stanzeAdiacenti.put(Direzione.nord, null);
		this.stanzeAdiacenti.put(Direzione.sud, null);
		this.stanzeAdiacenti.put(Direzione.ovest, null);
		this.stanzeAdiacenti.put(Direzione.est, null);
		this.attrezzi = new HashMap<>();
	}


	/**
	 * Imposta una stanza adiacente. 
	 *
	 * @param direzione direzione in cui sara' posta la stanza adiacente.
	 * @param stanza stanza adiacente nella direzione indicata dal primo parametro.
	 */

	public void impostaStanzaAdiacente(String direzione, StanzaProtected stanza) {
		if(this.stanzeAdiacenti.containsKey(Direzione.valueOf(direzione)))
			this.stanzeAdiacenti.put(Direzione.valueOf(direzione), stanza);
	}

	/**
	 * Restituisce la stanza adiacente nella direzione specificata
	 * @param direzione
	 */
	
	public StanzaProtected getStanzaAdiacente(String direzione) {
		return this.stanzeAdiacenti.get(Direzione.valueOf(direzione));
	}

	/**
	 * Restituisce la nome della stanza.
	 * @return il nome della stanza
	 */

	public String getNome() {
		return this.nome;
	}

	/**
	 * Restituisce la descrizione della stanza.
	 * @return la descrizione della stanza
	 */

	public String getDescrizione() {
		return this.toString();
	}

	/**
	 * Restituisce la collezione di attrezzi presenti nella stanza.
	 * @return la collezione di attrezzi nella stanza.
	 */

	public Map<String, Attrezzo> getAttrezzi() {
		return this.attrezzi;
	}

	/**
	 * Mette un attrezzo nella stanza.
	 * @param attrezzo l'attrezzo da mettere nella stanza.
	 * @return true se riesce ad aggiungere l'attrezzo, false atrimenti. 
	 */

	public boolean addAttrezzo(Attrezzo attrezzo) {
		if(attrezzo==null)
			return false;
		return this.attrezzi.putIfAbsent(attrezzo.getNome(), attrezzo)==null;
	}

	/**
	 * Restituisce una rappresentazione stringa di questa stanza,
	 * stampadone la descrizione, le uscite e gli eventuali attrezzi contenuti
	 * @return la rappresentazione stringa
	 */

	public String toString() {
		StringBuilder risultato = new StringBuilder();
		risultato.append(this.getNome());
		risultato.append("\nUscite:");
		for (Direzione direzione : this.stanzeAdiacenti.keySet())
			if (direzione!=null && this.stanzeAdiacenti.get(direzione)!=null)
				risultato.append(" " + direzione);

		risultato.append("\nAttrezzi nella stanza: ");
		risultato.append(this.attrezzi.values().toString());
		return risultato.toString();
	}

	/**
	 * Controlla se un attrezzo esiste nella stanza (uguaglianza sul nome).
	 * @return true se l'attrezzo esiste nella stanza, false altrimenti.
	 */
	
	public boolean hasAttrezzo(String nomeAttrezzo) {	
		return this.attrezzi.containsKey(nomeAttrezzo);
	}

	/**
	 * Restituisce l'attrezzo nomeAttrezzo se presente nella stanza.
	 * @param nomeAttrezzo
	 * @return l'attrezzo presente nella stanza.
	 * 		   null se l'attrezzo non e' presente.
	 */

	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		return this.attrezzi.get(nomeAttrezzo);
	}
	/**
	 * Rimuove un attrezzo dalla stanza (ricerca in base al nome).
	 * @param nomeAttrezzo
	 * @return true se l'attrezzo e' stato rimosso, false altrimenti
	 */


	public boolean removeAttrezzoDaStanza (Attrezzo attrezzo) {
		return this.attrezzi.remove(attrezzo.getNome(), attrezzo);
	}

}
