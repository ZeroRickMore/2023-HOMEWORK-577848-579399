package it.uniroma3.diadia.ambienti;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StanzaBuiaTest {
	
	private StanzaBuia stanzaBuia;
	private Attrezzo enabler;
	

	@BeforeEach
	void setUp() {
		this.stanzaBuia = new StanzaBuia("tokoyami dark room", "Dark Shadow");
		this.enabler = new Attrezzo("Dark Shadow", 2);
	}

	@Test
	void testGetDescrizioneNoAttrezzo() {
		assertEquals("Qui c'è buio pesto...", this.stanzaBuia.getDescrizione());
	}
	
	@Test
	void testGetDescrizioneConAttrezzo() {
		this.stanzaBuia.addAttrezzo(enabler);
		assertEquals(this.stanzaBuia.toString(), this.stanzaBuia.getDescrizione());
	}

}
