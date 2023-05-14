package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.uniroma3.diadia.attrezzi.*;

class StanzaTest {
	private Labirinto labirinto;

	@BeforeEach
	void setUp(){	
		this.labirinto = new LabirintoBuilder()
				.addStanza("Stanza test")
				.addAttrezzo("Martello", 8)
				.addAttrezzo("Ascia", 1)
				.addAttrezzo("Mouse", 9)
				.setComeStanzaIniziale("Stanza test")
				.addStanza("Stanza nord")
				.addStanza("Stanza sud")
				.addStanza("Stanza est")
				.addStanza("Stanza ovest")
				.addAdiacenza("Stanza test", "Stanza nord", "nord")
				.addAdiacenza("Stanza test", "Stanza sud", "sud")
				.addAdiacenza("Stanza test", "Stanza est", "est")
				.addAdiacenza("Stanza test", "Stanza ovest", "ovest")
				.getLabirinto();
	}

	/**
	 * Test getStanzaAdiacente
	 */

	@Test
	void testGetStanzaAdiacenteNord() { 
		assertEquals("Stanza nord", this.labirinto.getStanzaIniziale().getStanzaAdiacente("nord").getNome());
	} 

	@Test
	void testGetStanzaAdiacenteSud() { 
		assertEquals("Stanza sud", this.labirinto.getStanzaIniziale().getStanzaAdiacente("sud").getNome());
	}

	@Test
	void testGetStanzaAdiacenteEst() { 
		assertEquals("Stanza est", this.labirinto.getStanzaIniziale().getStanzaAdiacente("est").getNome());
	}

	@Test
	void testGetStanzaAdiacenteOvest() { 
		assertEquals("Stanza ovest", this.labirinto.getStanzaIniziale().getStanzaAdiacente("ovest").getNome());
	}

	@Test
	void testGetStanzaAdiacenteNonValida() {
		assertNull(this.labirinto.getStanzaIniziale().getStanzaAdiacente("nordovest"));
	}

	/**
	 * Test hasAttrezzo    
	 */


	@Test
	void testHasAttrezzoTrue() {
		assertTrue(this.labirinto.getStanzaIniziale().hasAttrezzo("Martello"));
		assertTrue(this.labirinto.getStanzaIniziale().hasAttrezzo("Ascia"));
		assertTrue(this.labirinto.getStanzaIniziale().hasAttrezzo("Mouse"));
	}

	@Test
	void testHasAttrezzoFalse() {
		assertFalse(this.labirinto.getStanzaIniziale().hasAttrezzo("Iphone 150xs-pro-max-air-13 pollici"));
	}

	/**
	 * Test getAttrezzo    
	 */

	@Test
	void testGetAttrezzoEsistente() {
		assertEquals("Martello", this.labirinto.getStanzaIniziale().getAttrezzo("Martello").getNome());
		assertEquals("Ascia", this.labirinto.getStanzaIniziale().getAttrezzo("Ascia").getNome());
		assertEquals("Mouse", this.labirinto.getStanzaIniziale().getAttrezzo("Mouse").getNome());
	}

	@Test
	void testGetAttrezzoNonEsistente() {
		assertNull(this.labirinto.getStanzaIniziale().getAttrezzo("Spada"));
		assertNull(this.labirinto.getStanzaIniziale().getAttrezzo("Resilienza"));
		assertNull(this.labirinto.getStanzaIniziale().getAttrezzo("Dominio"));
	}


	/**
	 * Test removeAttrezzo    
	 */

	@Test
	void testRemoveAttrezzoTrovato() {
		assertFalse(this.labirinto.getStanzaIniziale().getAttrezzi().isEmpty());
		assertTrue(this.labirinto.getStanzaIniziale().removeAttrezzoDaStanza(new Attrezzo("Martello", 8)));
		assertTrue(this.labirinto.getStanzaIniziale().removeAttrezzoDaStanza(new Attrezzo("Ascia", 1)));
		assertTrue(this.labirinto.getStanzaIniziale().removeAttrezzoDaStanza(new Attrezzo("Mouse", 9)));
		assertTrue(this.labirinto.getStanzaIniziale().getAttrezzi().isEmpty());
	}

	@Test
	void testRemoveAttrezzoNonTrovato() {
		assertFalse(this.labirinto.getStanzaIniziale().removeAttrezzoDaStanza(new Attrezzo("pugnale del vicoletto", 4)));
	}

	/**
	 * Test addAttrezzo    
	 */

	@Test
	void testAddAttrezzoTrue() {
		assertTrue(this.labirinto.getStanzaIniziale().addAttrezzo(new Attrezzo ("Galeforce", 6)));
	}

	@Test
	void testAddAttrezzoFalse() {
		assertFalse(this.labirinto.getStanzaIniziale().addAttrezzo(new Attrezzo("Martello", 8)));
		assertFalse(this.labirinto.getStanzaIniziale().addAttrezzo(new Attrezzo("Martello", 1)));
		assertFalse(this.labirinto.getStanzaIniziale().addAttrezzo(null));
	}

	@Test
	void testAddAttrezzoFalseStanzaPiena() {
		this.labirinto.getStanzaIniziale().addAttrezzo(new Attrezzo ("Galeforce", 6));
		this.labirinto.getStanzaIniziale().addAttrezzo(new Attrezzo ("Baron Nashor", 4));
		this.labirinto.getStanzaIniziale().addAttrezzo(new Attrezzo ("Immortal Cringebow", 6));
		this.labirinto.getStanzaIniziale().addAttrezzo(new Attrezzo ("Rabadon", 6));
		this.labirinto.getStanzaIniziale().addAttrezzo(new Attrezzo ("Rylai", 6));
		this.labirinto.getStanzaIniziale().addAttrezzo(new Attrezzo ("Collector", 6));
		this.labirinto.getStanzaIniziale().addAttrezzo(new Attrezzo ("Infinity Edge", 6));
		assertTrue(this.labirinto.getStanzaIniziale().addAttrezzo(new Attrezzo ("Piastrella", 4)));
	}

}
