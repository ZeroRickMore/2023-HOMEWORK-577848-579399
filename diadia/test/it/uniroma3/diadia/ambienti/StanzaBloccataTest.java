package it.uniroma3.diadia.ambienti;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StanzaBloccataTest {
	private StanzaBloccata stanzaBloccata;
	private String direzioneBloccata;
	private String nomeAttrezzoEnabler;
	private Attrezzo attrezzoEnabler;
	private Stanza stanzaSud;
	

	@BeforeEach
	void setUp() throws Exception {
		this.stanzaBloccata = new StanzaBloccata("Blocked", "sud", "Alohomora");
		this.direzioneBloccata = "sud";
		this.nomeAttrezzoEnabler="Alohomora";
		this.attrezzoEnabler = new Attrezzo("Alohomora", 1);
		this.stanzaSud = new Stanza("Stanza Sud");
		this.stanzaBloccata.impostaStanzaAdiacente(direzioneBloccata, stanzaSud);
	}

	@Test
	void testGetStanzaAdiacenteSenzaAttrezzo() {
		assertEquals(this.stanzaBloccata, this.stanzaBloccata.getStanzaAdiacente(direzioneBloccata));
	}
	
	@Test
	void testGetStanzaAdiacenteConAttrezzo() {
		this.stanzaBloccata.addAttrezzo(attrezzoEnabler);
		assertEquals(this.stanzaSud, this.stanzaBloccata.getStanzaAdiacente(direzioneBloccata));
	}
	
	@Test
	void testGetDescrizione() {
		String expected = "Ti trovi in: " + this.stanzaBloccata.toString() + "\nPer andare in direzione " + this.direzioneBloccata + 
				"\nnella stanza deve essere presente l'attrezzo " + this.nomeAttrezzoEnabler;
		assertEquals(expected, this.stanzaBloccata.getDescrizione());
	}

}
