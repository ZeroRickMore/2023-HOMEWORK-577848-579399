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

public class Stanza {

	static final private int NUMERO_MASSIMO_DIREZIONI = 4;
	static final private int NUMERO_MASSIMO_ATTREZZI = 10;

	private String nome;
	private Attrezzo[] attrezzi;
	private int numeroAttrezzi;
	private Stanza[] stanzeAdiacenti;
	private int numeroStanzeAdiacenti;
	private String[] direzioni;

	/**
	 * Crea una stanza. Non ci sono stanze adiacenti, non ci sono attrezzi.
	 * @param nome il nome della stanza
	 */
	
	public Stanza(String nome) {
		this.nome = nome;
		this.numeroStanzeAdiacenti = 0;
		this.numeroAttrezzi = 0;
		this.direzioni = new String[NUMERO_MASSIMO_DIREZIONI]; //gli dico che ho 4 direzioni
		this.stanzeAdiacenti = new Stanza[NUMERO_MASSIMO_DIREZIONI]; //gli dico che ho 4 stanze
		this.attrezzi = new Attrezzo[NUMERO_MASSIMO_ATTREZZI]; //gli dico che ho 10 attrezzi
	}


	/**
	 * Imposta una stanza adiacente. 
	 *
	 * @param direzione direzione in cui sara' posta la stanza adiacente.
	 * @param stanza stanza adiacente nella direzione indicata dal primo parametro.
	 */

	public void impostaStanzaAdiacente(String direzione, Stanza stanza) {
		boolean aggiornato = false; //non ho aggiornato la stanza adiacente
		for(int i=0; i<this.direzioni.length; i++)
			if (direzione.equals(this.direzioni[i])) { //quando la direzione della stanza è quella che gli ho dato (ci posso entrare solo dopo la prima dichiarazione)
				this.stanzeAdiacenti[i] = stanza; //metti la stanza in input in quella direzione
				aggiornato = true; //l'ho aggiornata 
			}
		if (!aggiornato) //prima dichiarazione della direzione
			if (this.numeroStanzeAdiacenti < NUMERO_MASSIMO_DIREZIONI) {
				this.direzioni[numeroStanzeAdiacenti] = direzione; //imposto la direzione per la prima volta
				this.stanzeAdiacenti[numeroStanzeAdiacenti] = stanza; //e in quella direzione ci metto la stanza
				this.numeroStanzeAdiacenti++; // quindi ho un'altra stanza adiacente 
			}
	}

	
	/**
	 * Restituisce la stanza adiacente nella direzione specificata
	 * @param direzione
	 */
	
	public Stanza getStanzaAdiacente(String direzione) {
		Stanza stanza = null;
		for(int i=0; i<this.numeroStanzeAdiacenti; i++)
			if (this.direzioni[i].equals(direzione))
				stanza = this.stanzeAdiacenti[i];
		return stanza;
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
	
	public Attrezzo[] getAttrezzi() {
		return this.attrezzi;
	}

	/**
	 * Mette un attrezzo nella stanza.
	 * @param attrezzo l'attrezzo da mettere nella stanza.
	 * @return true se riesce ad aggiungere l'attrezzo, false atrimenti. 
	 */
	
	public boolean addAttrezzo(Attrezzo attrezzo) {
		if (attrezzo == null) {
			return false;
		}
		if (this.numeroAttrezzi < NUMERO_MASSIMO_ATTREZZI) {
			this.attrezzi[numeroAttrezzi] = attrezzo;
			this.numeroAttrezzi++;
			return true;
		} 
		else {
			return false;
		}
	}

	/**
	 * Restituisce una rappresentazione stringa di questa stanza,
	 * stampadone la descrizione, le uscite e gli eventuali attrezzi contenuti
	 * @return la rappresentazione stringa
	 */
	
	public String toString() {
		StringBuilder risultato = new StringBuilder();
		risultato.append(this.nome);
		risultato.append("\nUscite: ");
		for (String direzione : this.direzioni)
			if (direzione!=null)
				risultato.append(" " + direzione);
		risultato.append("\nAttrezzi nella stanza: ");
		for (Attrezzo attrezzo : this.attrezzi) {
			if (attrezzo!=null)
			risultato.append(attrezzo.toString()+" ");
		}
		return risultato.toString();
	}

	/**
	 * Controlla se un attrezzo esiste nella stanza (uguaglianza sul nome).
	 * @return true se l'attrezzo esiste nella stanza, false altrimenti.
	 */
	
	public boolean hasAttrezzo(String nomeAttrezzo) {	
		for (Attrezzo i : this.attrezzi) {
			if (i != null && i.getNome().equals(nomeAttrezzo))
				return true;
		}
		return false;
	} 

	/**
	 * Restituisce l'attrezzo nomeAttrezzo se presente nella stanza.
	 * @param nomeAttrezzo
	 * @return l'attrezzo presente nella stanza.
	 * 		   null se l'attrezzo non e' presente.
	 */
	
	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		Attrezzo attrezzoCercato = null;
		for (Attrezzo i : this.attrezzi) {
			if (i != null && i.getNome().equals(nomeAttrezzo)) {
				attrezzoCercato = i;
				break;
			}
		}
		return attrezzoCercato; 
	}

	/**
	 * Rimuove un attrezzo dalla stanza (ricerca in base al nome).
	 * @param nomeAttrezzo
	 * @return true se l'attrezzo e' stato rimosso, false altrimenti
	 */
	
	public boolean removeAttrezzoDaStanza(Attrezzo attrezzo) {
		for(int i=0; i<numeroAttrezzi; i++) {
			if(this.attrezzi[i].getNome().equals(attrezzo.getNome())) {
				for(int j = i; j<numeroAttrezzi ; j++) {
					if(j==numeroAttrezzi-1) { //se sto all'ultima posizione evito di andare out of bounds e metto a null
						this.attrezzi[j] = null;
						break;
					}
					this.attrezzi[j] = this.attrezzi[j+1];			
				}		
				numeroAttrezzi--;
				return true;
			}
		}
		return false;
	}


	public String[] getDirezioni() {
		String[] direzioni = new String[this.numeroStanzeAdiacenti];
		for(int i=0; i<this.numeroStanzeAdiacenti; i++)
			direzioni[i] = this.direzioni[i];
		return direzioni;
	}

}