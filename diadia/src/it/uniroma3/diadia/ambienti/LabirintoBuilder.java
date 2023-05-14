package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import java.util.*;

public class LabirintoBuilder {
	
	private Labirinto labirinto;
	private Stanza stanzaCheStoModificando;
	private Map<String, Stanza> mapNome2Stanza; // "Cucina" -> new Stanza("Cucina")
	final private Map<String, String> direzione2direzione;
	
	/**
	 * Crea un labirintoBuilder con direzioni di default
	 */
	
	public LabirintoBuilder() {
		
		this.labirinto = new Labirinto();
		this.stanzaCheStoModificando = null;
		this.mapNome2Stanza = new HashMap<>();
		
		//DEFINISCO LA MAPPA DELLE UNICHE DIREZIONI AMMESSE
		this.direzione2direzione = new HashMap<>();
		direzione2direzione.put("nord", "sud");
		direzione2direzione.put("sud", "nord");
		direzione2direzione.put("est", "ovest");
		direzione2direzione.put("ovest", "est");
		
	}
	
	public Stanza getStanzaCheStoModificando() {
		return stanzaCheStoModificando;
	}
	

	public Map<String, Stanza> getMapNome2Stanza() {
		return mapNome2Stanza;
	}
	
	
	public Map<String, String> getDirezione2direzione() {
		return direzione2direzione;
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
	
	public LabirintoBuilder addAdiacenza(String nomeStanzaCorrente, String nomeStanzaDaAdiacentare, String nomeDirezioneDiAdiacenza) {
		if(mapNome2Stanza.containsKey(nomeStanzaCorrente) && mapNome2Stanza.containsKey(nomeStanzaDaAdiacentare) && direzione2direzione.containsKey(nomeDirezioneDiAdiacenza)) { //se le stanze da mettere vicine esistono nella nostra mappa
			mapNome2Stanza.get(nomeStanzaCorrente).impostaStanzaAdiacente(nomeDirezioneDiAdiacenza, mapNome2Stanza.get(nomeStanzaDaAdiacentare));
			mapNome2Stanza.get(nomeStanzaDaAdiacentare).impostaStanzaAdiacente(direzione2direzione.get(nomeDirezioneDiAdiacenza), mapNome2Stanza.get(nomeStanzaCorrente));
		} // Se non esiste una delle due stanze che voglio mettere vicine, semplicemente mi restituisce il labirinto così com'è.
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
		this.mapNome2Stanza.putIfAbsent(nomeStanzaAggiunta, stanzaCheStoModificando);
		return this;
	}	

	/**
	 * 
	 * @param nomeStanzaAggiunta
	 * @return
	 */
	
	public LabirintoBuilder addStanzaMagica(String nomeStanzaAggiunta) {
		this.stanzaCheStoModificando = new StanzaMagica(nomeStanzaAggiunta);
		this.mapNome2Stanza.putIfAbsent(nomeStanzaAggiunta, stanzaCheStoModificando);
		return this;
	}
	
	
	public LabirintoBuilder addStanzaMagica(String nomeStanzaAggiunta, int sogliaMagica) {
		this.stanzaCheStoModificando = new StanzaMagica(nomeStanzaAggiunta, sogliaMagica);
		this.mapNome2Stanza.putIfAbsent(nomeStanzaAggiunta, stanzaCheStoModificando);
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
		this.mapNome2Stanza.putIfAbsent(nomeStanzaAggiunta, stanzaCheStoModificando);
		return this;
	}
	/**
	 * 
	 * @param nomeStanzaAggiunta
	 * @param nomeDirezioneBloccata
	 * @param nomeEnabler
	 * @return
	 */
	
	public LabirintoBuilder addStanzaBloccata(String nomeStanzaAggiunta, String nomeDirezioneBloccata, String nomeEnabler) {
		this.stanzaCheStoModificando = new StanzaBloccata(nomeStanzaAggiunta, nomeDirezioneBloccata, nomeEnabler);
		this.mapNome2Stanza.putIfAbsent(nomeStanzaAggiunta, stanzaCheStoModificando);
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
