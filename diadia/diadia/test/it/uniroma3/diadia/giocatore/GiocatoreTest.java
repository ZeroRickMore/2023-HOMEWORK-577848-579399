package it.uniroma3.diadia.giocatore;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GiocatoreTest {
	static final private int CFU_INIZIALI = 20;
	private Giocatore giocatore;
	
	@BeforeEach
	void setUp() {
		this.giocatore = new Giocatore();
	}
	
	
	@Test
	void testGetBorsa() {
		assertEquals(0, this.giocatore.getBorsa().getPeso()); //se la borsa è vuota, ha funzionato
	}
	
	@Test
	void testGetCfu() {
		assertEquals(CFU_INIZIALI, this.giocatore.getCfu());
	}
	
	@Test
	void testGetNome() {
		this.giocatore.setNome("Francolo");
		assertEquals("Francolo", this.giocatore.getNome());
	}

}
