/* 
 * Classe che crea un labirinto già definito di stanze ed attrezzi dentro esse, impostando i metodi getStanzaIniziale e 
 * getStanzaVincente per poter cambiare il labirinto in qualsiasi momento, senza andare a modificare nulla nel resto 
 * del codice.
 */

package it.uniroma3.diadia.ambienti;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import it.uniroma3.diadia.CaricatoreLabirinto;
import it.uniroma3.diadia.FormatoFileNonValidoException;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.personaggi.Cane;
import it.uniroma3.personaggi.Mago;
import it.uniroma3.personaggi.Strega;


public class Labirinto {
	
	private Stanza stanzaIniziale;
	private Stanza stanzaVincente;
	
	private Labirinto(String nomeFile) throws FileNotFoundException, FormatoFileNonValidoException {
		CaricatoreLabirinto caricatore = new CaricatoreLabirinto(nomeFile);
		caricatore.carica();
		this.stanzaIniziale = caricatore.getStanzaIniziale();
		this.stanzaVincente = caricatore.getStanzaVincente();
	}
	
	
	
	// GETTERS AND SETTERS
	
	public Labirinto() {
		this.stanzaIniziale = null;
		this.stanzaVincente= null ;
	}



	public void setStanzaIniziale(Stanza stanzaIniziale) {
		this.stanzaIniziale = stanzaIniziale;
	}

	public void setStanzaVincente(Stanza stanzaVincente) {
		this.stanzaVincente = stanzaVincente;
	}
	
	public Stanza getStanzaIniziale() {
		return stanzaIniziale;
	}

	public Stanza getStanzaVincente() {
		return stanzaVincente;
	}
	
	// GETTERS AND SETTERS
	
	 /**
     * Crea tutte le stanze e le porte di collegamento
     */
	
	public void creaStanzeDefault() { 

		/* crea gli attrezzi */
    	Attrezzo lanterna = new Attrezzo("lanterna", 3);
		Attrezzo osso = new Attrezzo("osso", 1);
    	
		/* crea stanze del labirinto */
		Stanza atrio = new Stanza("Atrio");
		Stanza aulaN11 = new Stanza("Aula N11");
		Stanza aulaN10 = new Stanza("Aula N10");
		Stanza laboratorio = new Stanza("Laboratorio Campus");
		Stanza biblioteca = new Stanza("Biblioteca");
		
		/* collega le stanze */
		atrio.impostaStanzaAdiacente(Direzione.nord, biblioteca);
		atrio.impostaStanzaAdiacente(Direzione.est, aulaN11);
		atrio.impostaStanzaAdiacente(Direzione.sud, aulaN10);
		atrio.impostaStanzaAdiacente(Direzione.ovest, laboratorio);
		aulaN11.impostaStanzaAdiacente(Direzione.est, laboratorio);
		aulaN11.impostaStanzaAdiacente(Direzione.ovest, atrio);
		aulaN10.impostaStanzaAdiacente(Direzione.nord, atrio);
		aulaN10.impostaStanzaAdiacente(Direzione.est, aulaN11);
		aulaN10.impostaStanzaAdiacente(Direzione.ovest, laboratorio);
		laboratorio.impostaStanzaAdiacente(Direzione.est, atrio);
		laboratorio.impostaStanzaAdiacente(Direzione.ovest, aulaN11);
		biblioteca.impostaStanzaAdiacente(Direzione.sud, atrio);

        /* pone gli attrezzi nelle stanze */
		aulaN10.addAttrezzo(lanterna);
		atrio.addAttrezzo(osso);
		
		 //il gioco comincia nell'atrio
          
		stanzaIniziale = atrio;
		stanzaVincente = biblioteca;
    }
	
	public static LabirintoBuilder newBuilder(String fileName) throws FileNotFoundException, FormatoFileNonValidoException {
		return new LabirintoBuilder(fileName);
	}
	
	public static class LabirintoBuilder {
		
		private Labirinto labirinto;
		private Stanza stanzaCheStoModificando;
		private Map<String, Stanza> mapNome2Stanza; // "Cucina" -> new Stanza("Cucina")
		
		/**
		 * Crea un labirintoBuilder con direzioni di default
		 * @throws FormatoFileNonValidoException 
		 * @throws FileNotFoundException 
		 */

		public LabirintoBuilder(){

			this.labirinto = new Labirinto();
			this.stanzaCheStoModificando = null;
			this.mapNome2Stanza = new HashMap<>();	
		}
		
		public LabirintoBuilder(String nomeFile) throws FileNotFoundException, FormatoFileNonValidoException {
			this.labirinto = new Labirinto(nomeFile);
		}
		
		public Stanza getStanzaCheStoModificando() {
			return stanzaCheStoModificando;
		}
		

		public Map<String, Stanza> getMapNome2Stanza() {
			return mapNome2Stanza;
		}
		

		/**
		 * Aggiunge una stanza iniziale al labirinto.
		 * @param Il nome della stanza iniziale.
		 * @return MethodChain -> Labirinto su cui sto lavorando
		 */
		
		public LabirintoBuilder addStanzaIniziale(String nomeStanzaIniziale) {
			Stanza stanzaIniziale = new Stanza(nomeStanzaIniziale);
			this.mapNome2Stanza.putIfAbsent(nomeStanzaIniziale, stanzaIniziale);
			this.labirinto.setStanzaIniziale(stanzaIniziale);
			this.stanzaCheStoModificando = stanzaIniziale;
			return this;
		}
		
		public LabirintoBuilder setComeStanzaIniziale(String nomeStanzaIniziale){
			if(this.mapNome2Stanza.containsKey(nomeStanzaIniziale)) {
				this.stanzaCheStoModificando = this.mapNome2Stanza.get(nomeStanzaIniziale);
				this.labirinto.setStanzaIniziale(stanzaCheStoModificando);
			}
			return this;
		}
		/**
		 * Aggiunge una stanza finale (vincente) al labirinto.
		 * @param Il nome della stanza vincente.
		 * @return MethodChain -> Labirinto su cui sto lavorando
		 */
		
		public LabirintoBuilder addStanzaVincente(String nomeStanzaVincente) {
			Stanza stanzaVincente = new Stanza(nomeStanzaVincente);
			this.mapNome2Stanza.putIfAbsent(nomeStanzaVincente, stanzaVincente);
			this.labirinto.setStanzaVincente(stanzaVincente);
			this.stanzaCheStoModificando = stanzaVincente;
			return this;
		}
		
		public LabirintoBuilder setComeStanzaVincente(String nomeStanzaVincente){
			if(this.mapNome2Stanza.containsKey(nomeStanzaVincente)) {
				this.stanzaCheStoModificando = this.mapNome2Stanza.get(nomeStanzaVincente);
				this.labirinto.setStanzaVincente(stanzaCheStoModificando);
			}
			return this;
		}
		
		
		public LabirintoBuilder  addMago(String nome, String presentazione, Attrezzo attrezzo) {
			Mago m = new Mago(nome, presentazione, attrezzo);
			if(this.stanzaCheStoModificando==null)
				return this;
			this.stanzaCheStoModificando.setPersonaggio(m);
			return this;
		}

		public LabirintoBuilder addCane(String nome, String presentazione, Attrezzo attrezzo) {
			Cane c = new Cane(nome, presentazione, attrezzo);
			if(this.stanzaCheStoModificando==null)
				return this;
			this.stanzaCheStoModificando.setPersonaggio(c);
			return this;
		}

		public LabirintoBuilder  addStrega(String nome, String presentazione, Attrezzo attrezzo) {
			Strega s = new Strega(nome, presentazione, attrezzo);
			if(this.stanzaCheStoModificando==null)
				return this;
			this.stanzaCheStoModificando.setPersonaggio(s);
			return this;
		}
		
		/**
		 * Crea un nuovo attrezzo con i parametri inseriti e lo aggiunge nell'ultima stanza aggiunta al labirinto.
		 * @param Nome dell'attrezzo da aggiungere,
		 * @param Peso dell'attrezzo da aggiungere.
		 * @return MethodChain -> Labirinto su cui sto lavorando
		 */
		
		public LabirintoBuilder addAttrezzo(String nomeAttrezzo, int peso) {
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			this.stanzaCheStoModificando.addAttrezzo(attrezzo);
			return this;
		}
		
		/**
		 * Aggiunge un collegamento tra stanze. La prima stanza è collegata alla seconda nella direzione inserita.
		 * Automaticamente, collega le stanze in ambi i versi (Stanza1 collegata a Stanza2 a nord, mentre Stanza2 ha Stanza1 a sud).
		 * Se non esiste una delle due stanze che voglio mettere vicine, semplicemente mi restituisce il labirinto così com'è.
		 * 
		 * ESEMPIO: ("Cucina", "Atrio", "Nord") -> Cucina è connessa a NORD verso Atrio; Atrio è connesso a SUD verso Cucina.
		 * @param Nome della stanza1 (che resta ferma) che vuoi collegare a una stanza adiacente.
		 * @param Nome della stanza2 da mettere vicino all'altra
		 * @param Direzione in cui Stanza1 ha Stanza2.
		 * @return MethodChain -> Labirinto su cui sto lavorando
		 */
		
		public LabirintoBuilder addAdiacenza(String nomeStanzaCorrente, String nomeStanzaDaAdiacentare, Direzione direzioneDiAdiacenza) {
			if(mapNome2Stanza.containsKey(nomeStanzaCorrente) && mapNome2Stanza.containsKey(nomeStanzaDaAdiacentare)) { //se le stanze da mettere vicine esistono nella nostra mappa
				mapNome2Stanza.get(nomeStanzaCorrente).impostaStanzaAdiacente(direzioneDiAdiacenza, mapNome2Stanza.get(nomeStanzaDaAdiacentare));
				mapNome2Stanza.get(nomeStanzaDaAdiacentare).impostaStanzaAdiacente(direzioneDiAdiacenza.getDirezioneOpposta(), mapNome2Stanza.get(nomeStanzaCorrente));
			}
			else throw new IllegalArgumentException("Stanze non trovate! Bisogna prima aggiungerle! metodo addStanza()");
			return this;
		}
		
		/**
		 * Aggiunge una stanza all'elenco delle stanze esistenti,
		 * e restituisce la stanza in questione, per essere usata come variabile di istanza del LabirintoBuilder su cui lavorare.
		 * USARE SOLO DENTRO LABIRINTOBUILDER, NON INVOCARE AL DI FUORI (MethodChain interrotta altrimenti).
		 * @param nomeStanzaAggiunta
		 * @return La stanza che sto aggiungendo all'elenco.
		 */
		
		public LabirintoBuilder addStanza(String nomeStanzaAggiunta) {
			this.stanzaCheStoModificando = new Stanza(nomeStanzaAggiunta);
			this.mapNome2Stanza.put(nomeStanzaAggiunta, stanzaCheStoModificando);
			return this;
		}	

		/**
		 * 
		 * @param nomeStanzaAggiunta
		 * @return
		 */
		
		public LabirintoBuilder addStanzaMagica(String nomeStanzaAggiunta) {
			this.stanzaCheStoModificando = new StanzaMagica(nomeStanzaAggiunta);
			this.mapNome2Stanza.put(nomeStanzaAggiunta, stanzaCheStoModificando);
			return this;
		}
		
		
		public LabirintoBuilder addStanzaMagica(String nomeStanzaAggiunta, int sogliaMagica) {
			this.stanzaCheStoModificando = new StanzaMagica(nomeStanzaAggiunta, sogliaMagica);
			this.mapNome2Stanza.put(nomeStanzaAggiunta, stanzaCheStoModificando);
			return this;
		}
		/**
		 * 
		 * @param nomeStanzaAggiunta
		 * @param nomeEnabler
		 * @return
		 */
		
		public LabirintoBuilder addStanzaBuia(String nomeStanzaAggiunta, String nomeEnabler) {
			this.stanzaCheStoModificando = new StanzaBuia(nomeStanzaAggiunta, nomeEnabler);
			this.mapNome2Stanza.put(nomeStanzaAggiunta, stanzaCheStoModificando);
			return this;
		}
		/**
		 * 
		 * @param nomeStanzaAggiunta
		 * @param nomeDirezioneBloccata
		 * @param nomeEnabler
		 * @return
		 */
		
		public LabirintoBuilder addStanzaBloccata(String nomeStanzaAggiunta, Direzione nomeDirezioneBloccata, String nomeEnabler) {
			this.stanzaCheStoModificando = new StanzaBloccata(nomeStanzaAggiunta, nomeDirezioneBloccata, nomeEnabler);
			this.mapNome2Stanza.put(nomeStanzaAggiunta, stanzaCheStoModificando);
			return this;
		}
		
		/**
		 * 
		 * @return Labirinto creato con il builder.
		 */
		
		public Labirinto getLabirinto() {
			return this.labirinto;
		}
		
	}

}
