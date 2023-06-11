package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;
import it.uniroma3.diadia.ambienti.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartitaTest {
	private Partita partita;
	
	
	@BeforeEach
	void setUp() {
		this.partita = new Partita();
	}
	
	 /**
     * Test vinta
     */	
	
	@Test
	void testNonVintaCambiandoStanzaCorrenteInNonVincente() {
		Stanza sbagliata = new Stanza("Sbagliata");
		this.partita.setStanzaCorrente(sbagliata);
		assertFalse(this.partita.vinta());
	}
	
	
	@Test
	void testVintaCambiandoStanzaCorrenteInVincente() {
		this.partita.setStanzaCorrente(this.partita.getLabirinto().getStanzaVincente());
		assertTrue(this.partita.vinta());
	}
	
}
