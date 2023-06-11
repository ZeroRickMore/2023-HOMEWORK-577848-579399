package it.uniroma3.diadia.ambienti;
import it.uniroma3.diadia.attrezzi.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StanzaMagicaTest {
	private StanzaMagica stanza;
	private Attrezzo attrezzo;

	@BeforeEach
	void setUp() throws Exception {
		this.attrezzo = new Attrezzo("palla", 2);
		this.stanza = new StanzaMagica("magica", 2);
	}

	@Test
	void testAddAttrezzoNonMagico() {
		this.stanza.addAttrezzo(attrezzo);
		assertTrue(this.stanza.hasAttrezzo("palla"));
		assertFalse(this.stanza.hasAttrezzo("Falange"));
	}
	
	
	@Test
	void testAddAttrezzoMagico() {
		this.stanza.addAttrezzo(new Attrezzo("Topo", 5));
		this.stanza.addAttrezzo(new Attrezzo("Lale", 1));
		this.stanza.addAttrezzo(attrezzo);
		assertTrue(this.stanza.hasAttrezzo("allap"));
		assertFalse(this.stanza.hasAttrezzo("palla"));
	}

}
