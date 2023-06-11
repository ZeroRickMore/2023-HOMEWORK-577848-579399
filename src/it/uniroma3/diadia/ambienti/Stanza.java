/**
 * Classe Stanza - una stanza in un gioco di ruolo.
 * Una stanza e' un luogo fisico nel gioco.
 * E' collegata ad altre stanze attraverso delle uscite.
 * Ogni uscita e' associata ad una direzione.
 * 
 * @author docente di POO 
 * @see Attrezzo
 * @version base
 */

package it.uniroma3.diadia.ambienti;
import it.uniroma3.diadia.attrezzi.*;
import it.uniroma3.diadia.giocatore.ComparatoreAttrezziPerNome;
import it.uniroma3.personaggi.AbstractPersonaggio;

import java.util.*;


public class Stanza {

	private String nome;
	private Map<String, Attrezzo> attrezzi;
	private Map<Direzione,Stanza> stanzeAdiacenti;
	private AbstractPersonaggio personaggio;

	/**
	 * Crea una stanza. Non ci sono stanze adiacenti, non ci sono attrezzi.
	 * @param nome il nome della stanza
	 */

	public Stanza(String nome) {
		this.nome = nome;
		this.stanzeAdiacenti = new HashMap<>();
		this.attrezzi = new HashMap<>();

	}


	/**
	 * Imposta una stanza adiacente. 
	 *
	 * @param direzione direzione in cui sara' posta la stanza adiacente.
	 * @param stanza stanza adiacente nella direzione indicata dal primo parametro.
	 */

	public void impostaStanzaAdiacente(Direzione direzione, Stanza stanza) {
			this.stanzeAdiacenti.put(direzione, stanza);
	}

	public Map<Direzione, Stanza> getStanzeAdiacenti() {
		return stanzeAdiacenti;
	}

	/**
	 * Restituisce la stanza adiacente nella direzione specificata
	 * @param direzione
	 */

	public Stanza getStanzaAdiacente(Direzione direzione) {
		return this.stanzeAdiacenti.get(direzione);
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


	public SortedSet<Attrezzo> getContenutoOrdinatoPerNome() {
		SortedSet<Attrezzo> setAttrezziOrdinatiPerNome = new TreeSet<Attrezzo>(new ComparatoreAttrezziPerNome());
		setAttrezziOrdinatiPerNome.addAll(this.attrezzi.values());
		return setAttrezziOrdinatiPerNome;
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
				risultato.append(" " + direzione.name());
		risultato.append("\nAttrezzi nella stanza: ");
		risultato.append(this.getContenutoOrdinatoPerNome().toString());
		risultato.append("\nPersonaggi nella stanza: ");
		if(this.personaggio!=null)
		risultato.append(this.personaggio.toString());
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


	public Set<Direzione> getDirezioni() {
		return this.stanzeAdiacenti.keySet();
	}

	@Override
	public boolean equals(Object o) {
		Stanza that = (Stanza) o;
		return this.getNome().equals(that.getNome());
	}

	@Override
	public int hashCode() {
		return this.getNome().hashCode();
	}


	public AbstractPersonaggio getPersonaggio() {
		return this.personaggio;
	}
	
	public void setPersonaggio(AbstractPersonaggio personaggio) {
		this.personaggio = personaggio;
	}
}