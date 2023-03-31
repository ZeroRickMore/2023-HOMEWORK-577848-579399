package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.uniroma3.diadia.attrezzi.*;

class StanzaTest {
	private Stanza centro;
	private Stanza nord;
	private Stanza sud;
	private Stanza est;
	private Stanza ovest;
	private Attrezzo attrezzo;
	private Attrezzo attrezzo2;
	private Attrezzo attrezzo3;

	@BeforeEach
	void setUp(){
		this.centro = new Stanza("Stanza test");
		this.nord = new Stanza("Stanza nord");
		this.sud = new Stanza("Stanza sud");
		this.est = new Stanza("Stanza est");
		this.ovest = new Stanza("Stanza ovest");
		this.attrezzo = new Attrezzo("Martello", 8);
		this.attrezzo2 = new Attrezzo("Ascia", 1);
		this.attrezzo3 = new Attrezzo("Mouse", 9);
		//avendo testato il funzionamento del setter impostaStanzaAdiacente, lo usiamo nel setUp
		this.centro.impostaStanzaAdiacente("nord", this.nord);
		this.centro.impostaStanzaAdiacente("sud", this.sud);
		this.centro.impostaStanzaAdiacente("est", this.est);
		this.centro.impostaStanzaAdiacente("ovest", this.ovest);
		//avendo testato il funzionamento di addAttrezzo, lo usiamo nel setUp
		this.centro.addAttrezzo(this.attrezzo);
		this.centro.addAttrezzo(this.attrezzo2);
		this.centro.addAttrezzo(this.attrezzo3);	 
	}


	/**
	 * Test impostaStanzaAdiacente
	 */

	@Test
	void testImpostaStanzaAdiacenteNord () {
		Stanza stanzaNordTest = new Stanza ("Stanza nord test");
		this.centro.impostaStanzaAdiacente("nord", stanzaNordTest);
		assertEquals(stanzaNordTest, this.centro.getStanzaAdiacente("nord"));
	}

	@Test
	void testImpostaStanzaAdiacenteSud () {
		Stanza stanzaSudTest = new Stanza ("Stanza sud test");
		this.centro.impostaStanzaAdiacente("sud", stanzaSudTest);
		assertEquals(stanzaSudTest, this.centro.getStanzaAdiacente("sud"));
	}

	@Test
	void testImpostaStanzaAdiacenteEst () {
		Stanza stanzaEstTest = new Stanza ("Stanza est test");
		this.centro.impostaStanzaAdiacente("est", stanzaEstTest);
		assertEquals(stanzaEstTest, this.centro.getStanzaAdiacente("est"));
	}

	@Test
	void testImpostaStanzaAdiacenteOvest () {
		Stanza stanzaOvestTest = new Stanza ("Stanza ovest test");
		this.centro.impostaStanzaAdiacente("ovest", stanzaOvestTest);
		assertEquals(stanzaOvestTest, this.centro.getStanzaAdiacente("ovest"));
	}

	@Test
	// verifico che non crei nuove direzioni
	void testImpostaStanzaAdiacenteDirezioneNonValida () {
		Stanza stanzaNonValidaTest = new Stanza ("Stanza non valida test");
		this.centro.impostaStanzaAdiacente("nordovest", stanzaNonValidaTest);
		assertNull(this.centro.getStanzaAdiacente("nordovest"));
	}

	/**
	 * Test getStanzaAdiacente
	 */

	@Test
	void testGetStanzaAdiacenteNord() { 
		assertEquals(this.nord, this.centro.getStanzaAdiacente("nord"));
	} 

	@Test
	void testGetStanzaAdiacenteSud() { 
		assertEquals(this.sud, this.centro.getStanzaAdiacente("sud"));
	}

	@Test
	void testGetStanzaAdiacenteEst() { 
		assertEquals(this.est, this.centro.getStanzaAdiacente("est"));
	}

	@Test
	void testGetStanzaAdiacenteOvest() { 
		assertEquals(this.ovest, this.centro.getStanzaAdiacente("ovest"));
	}

	@Test
	void testGetStanzaAdiacenteNonValida() {
		assertNull(this.centro.getStanzaAdiacente("nordovest"));
	}

	/**
	 * Test hasAttrezzo    
	 */


	@Test
	void testHasAttrezzoTrue() {
		assertTrue(this.centro.hasAttrezzo("Martello"));
	}

	@Test
	void testHasAttrezzoFalse() {
		assertFalse(this.centro.hasAttrezzo("Iphone 150xs-pro-max-air-13 pollici"));
	}

	/**
	 * Test getAttrezzo    
	 */

	@Test
	void testGetAttrezzoEsistente() {
		assertEquals(attrezzo, this.centro.getAttrezzo("Martello"));
		assertEquals(attrezzo2, this.centro.getAttrezzo("Ascia"));
		assertEquals(attrezzo3, this.centro.getAttrezzo("Mouse"));
	}

	@Test
	void testGetAttrezzoNull() {
		assertNull(this.centro.getAttrezzo("Spada"));
		assertNull(this.centro.getAttrezzo("Resilienza"));
		assertNull(this.centro.getAttrezzo("Predominio"));
	}


	/**
	 * Test removeAttrezzo    
	 */

	@Test
	void testRemoveAttrezzoTrovato() {
		assertTrue(this.centro.removeAttrezzoDaStanza(attrezzo2));
		assertTrue(this.centro.removeAttrezzoDaStanza(attrezzo));
		assertTrue(this.centro.removeAttrezzoDaStanza(attrezzo3));
	}

	@Test
	void testRemoveAttrezzoNonTrovato() {
		assertFalse(this.centro.removeAttrezzoDaStanza(new Attrezzo("pugnale del vicoletto", 4)));
	}

	/**
	 * Test addAttrezzo    
	 */

	@Test
	void testAddAttrezzoTrue() {
		assertTrue(this.centro.addAttrezzo(new Attrezzo ("Galeforce", 6)));
	}

	@Test
	void testAddAttrezzoFalse() {
		assertFalse(this.centro.addAttrezzo(null));
	}

	@Test
	void testAddAttrezzoFalseStanzaPiena() {
		this.centro.addAttrezzo(new Attrezzo ("Galeforce", 6));
		this.centro.addAttrezzo(new Attrezzo ("Baron Nashor", 4));
		this.centro.addAttrezzo(new Attrezzo ("Immortal Cringebow", 6));
		this.centro.addAttrezzo(new Attrezzo ("Rabadon", 6));
		this.centro.addAttrezzo(new Attrezzo ("Rylai", 6));
		this.centro.addAttrezzo(new Attrezzo ("Collector", 6));
		this.centro.addAttrezzo(new Attrezzo ("Infinity Edge", 6));
		assertFalse(this.centro.addAttrezzo(new Attrezzo ("Piastrella", 4)));
	}

	/**
	 * Test getDirezioni   
	 */

	@Test
	void testGetDirezioni() {
		assertEquals("nord", this.centro.getDirezioni()[0]); 
		assertEquals("sud", this.centro.getDirezioni()[1]);
		assertEquals("est", this.centro.getDirezioni()[2]);
		assertEquals("ovest", this.centro.getDirezioni()[3]);
	}

}
