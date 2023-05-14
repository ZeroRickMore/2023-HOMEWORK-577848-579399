package it.uniroma3.diadia.giocatore;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

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
		assertFalse(this.borsa.addAttrezzo(ascia));
		assertFalse(this.borsa.addAttrezzo(new Attrezzo("Martello", 1)));
		assertFalse(this.borsa.addAttrezzo(new Attrezzo("Martello", 2)));
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
	
	@Test
	void testGetContenutoOrdinatoPerPeso() {
		Borsa borsaPeso30 = new Borsa(30);
		Attrezzo piombo = new Attrezzo("piombo", 10);
		borsaPeso30.addAttrezzo(piombo);
		Attrezzo ps = new Attrezzo("ps",5);
		borsaPeso30.addAttrezzo(ps);
		Attrezzo piuma = new Attrezzo("piuma", 1);
		borsaPeso30.addAttrezzo(piuma);
		Attrezzo libro = new Attrezzo("libro",5);
		borsaPeso30.addAttrezzo(libro);
//		System.out.println(borsaPeso30);
//		System.out.println(borsaPeso30.getContenutoOrdinatoPerPeso());
		List<Attrezzo> contenutoOrdinatoPerPeso = borsaPeso30.getContenutoOrdinatoPerPeso();
		assertEquals(piuma, contenutoOrdinatoPerPeso.get(0));
		assertEquals(libro, contenutoOrdinatoPerPeso.get(1));
		assertEquals(ps, contenutoOrdinatoPerPeso.get(2));
		assertEquals(piombo, contenutoOrdinatoPerPeso.get(3));
	}
	
	@Test
	void testGetContenutoOrdinatoPerNome() {
		Borsa borsaPeso30 = new Borsa(30);
		Attrezzo piombo = new Attrezzo("piombo", 10);
		borsaPeso30.addAttrezzo(piombo);
		Attrezzo ps = new Attrezzo("ps",5);
		borsaPeso30.addAttrezzo(ps);
		Attrezzo piuma = new Attrezzo("piuma", 1);
		borsaPeso30.addAttrezzo(piuma);
		Attrezzo libro = new Attrezzo("libro",5);
		borsaPeso30.addAttrezzo(libro);
		SortedSet<Attrezzo> contenutoOrdinatoPerNome = borsaPeso30.getContenutoOrdinatoPerNome();
		assertEquals(libro, contenutoOrdinatoPerNome.toArray()[0]);
		assertEquals(piombo, contenutoOrdinatoPerNome.toArray()[1]);
		assertEquals(piuma, contenutoOrdinatoPerNome.toArray()[2]);
		assertEquals(ps, contenutoOrdinatoPerNome.toArray()[3]);
	//	System.out.println(borsaPeso30.getContenutoOrdinatoPerNome());
	}
	
	@Test
	void testGetContenutoRaggruppatoPerPeso() {
		Borsa borsaPeso30 = new Borsa(30);
		Attrezzo piombo = new Attrezzo("piombo", 10);
		borsaPeso30.addAttrezzo(piombo);
		Attrezzo ps = new Attrezzo("ps",5);
		borsaPeso30.addAttrezzo(ps);
		Attrezzo piuma = new Attrezzo("piuma", 1);
		borsaPeso30.addAttrezzo(piuma);
		Attrezzo libro = new Attrezzo("libro",5);
		borsaPeso30.addAttrezzo(libro);
		Map<Integer, Set<Attrezzo>> contenutoRaggruppatoPerPeso = borsaPeso30.getContenutoRaggruppatoPerPeso();
		assertEquals(libro, contenutoRaggruppatoPerPeso.get(5).toArray()[0]);
		assertEquals(piombo, contenutoRaggruppatoPerPeso.get(10).toArray()[0]);
		assertEquals(piuma, contenutoRaggruppatoPerPeso.get(1).toArray()[0]);
		assertEquals(ps, contenutoRaggruppatoPerPeso.get(5).toArray()[1]);
// 		System.out.println(borsaPeso30.getContenutoRaggruppatoPerPeso());
	}
	
	@Test
	void testGetSortedSetOrdinatoPerPeso() {
		Attrezzo giavellotto = new Attrezzo("Giavellotto", 2);
		this.borsa.addAttrezzo(giavellotto);
		Attrezzo tradimento = new Attrezzo("Tradimento", 2);
		this.borsa.addAttrezzo(tradimento);
		SortedSet<Attrezzo> sortedSetOrdinatoPerPeso = this.borsa.getSortedSetOrdinatoPerPeso();
		assertEquals(palla, sortedSetOrdinatoPerPeso.toArray()[0]);
		assertEquals(giavellotto, sortedSetOrdinatoPerPeso.toArray()[1]);
		assertEquals(martello, sortedSetOrdinatoPerPeso.toArray()[2]);
		assertEquals(tradimento, sortedSetOrdinatoPerPeso.toArray()[3]);
		assertEquals(ascia, sortedSetOrdinatoPerPeso.toArray()[4]);
	//	System.out.println(sortedSetOrdinatoPerPeso); 
	}
	

}
