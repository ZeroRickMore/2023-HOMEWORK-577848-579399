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
		assertEquals("palla", this.stanza.getAttrezzi()[0].getNome());
		assertEquals(2, this.stanza.getAttrezzi()[0].getPeso());
	}
	
	
	@Test
	void testAddAttrezzoMagico() {
		this.stanza.addAttrezzo(attrezzo);
		this.stanza.addAttrezzo(attrezzo);
		this.stanza.addAttrezzo(attrezzo);
		assertEquals("allap", this.stanza.getAttrezzi()[2].getNome());
		assertEquals(4, this.stanza.getAttrezzi()[2].getPeso());
	}

}
