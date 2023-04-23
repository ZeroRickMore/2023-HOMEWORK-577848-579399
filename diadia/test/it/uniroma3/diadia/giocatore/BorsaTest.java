package it.uniroma3.diadia.giocatore;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.uniroma3.diadia.attrezzi.*;


class BorsaTest {
	private Borsa borsa;
	private Attrezzo martello;
	private Attrezzo ascia;
	private Attrezzo palla;
	
	
	@BeforeEach
	void setUp() throws Exception {
		this.borsa = new Borsa();
		this.martello = new Attrezzo("Martello", 2);
		this.ascia = new Attrezzo("Ascia", 3);
		this.palla = new Attrezzo("Palla", 1);
		//avendo testato il funzionamento di addAttrezzo, lo usiamo nel setUp
		this.borsa.addAttrezzo(martello);
		this.borsa.addAttrezzo(ascia);
		this.borsa.addAttrezzo(palla);
	}

	
	/**
     * Test removeAttrezzoDaBorsa
     */
	
	@Test
	void testRemoveAttrezzoDaBorsaEsistente() {
		assertEquals(this.martello, this.borsa.removeAttrezzoDaBorsa("Martello"));
	}
	
	@Test
	void testRemoveAttrezzoDaBorsaNonEsistente() {
		assertNull(this.borsa.removeAttrezzoDaBorsa("Spada Di Tor San Lorenzo"));
	}
	
	/**
     * Test addAttrezzo
     */
	
	@Test
	void testAddAttrezzo() {
		assertTrue(this.borsa.addAttrezzo(new Attrezzo ("Bottiglia", 2)));
	}
	
	@Test
	void testAddAttrezzoFalse() {
		assertFalse(this.borsa.addAttrezzo(new Attrezzo ("Bottiglia", 7)));
	}
	
	/**
     * Test getAttrezzo
     */
	
	@Test
	void testGetAttrezzoTrue() {
		assertEquals(this.palla, this.borsa.getAttrezzo("Palla"));
	}
	
	@Test
	void testGetAttrezzoFalse() {
		assertNull(this.borsa.getAttrezzo("Pesce"));
	}

	/**
     * Test getPeso
     */
	
	@Test
	void testGetPeso() {
		assertEquals(6, this.borsa.getPeso());
	}
	
	/**
     * Test hasAttrezzo
     */
	
	@Test
	void testHasAttrezzoTrue() {
		assertTrue(this.borsa.hasAttrezzo("Palla"));
	}
	
	@Test
	void testHasAttrezzoFalse() {
		assertFalse(this.borsa.hasAttrezzo("Salmone affumicato di Pedaso"));
	}
	
	/**
     * Test isEmpty
     */
	
	@Test
	void testIsEmpty() {
		assertFalse(this.borsa.isEmpty());
	}
	

}
