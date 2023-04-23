package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FabbricaDiComandiFisarmonicaTest {
	
	private FabbricaDiComandiFisarmonica factory;
	private Comando comandoGenerico;

	@BeforeEach
	void setUp() {
		this.factory = new FabbricaDiComandiFisarmonica();
	}

	@Test
	void testCostruisciComandoAiuto() {
		this.comandoGenerico = factory.costruisciComando("aiuto");
		assertEquals("aiuto", this.comandoGenerico.getNome());
		assertNull(this.comandoGenerico.getParametro());
	}
	
	@Test
	void testCostruisciComandoPosa() {
		this.comandoGenerico = factory.costruisciComando("posa attrezzo");
		assertEquals("posa", this.comandoGenerico.getNome());
		assertEquals("attrezzo", this.comandoGenerico.getParametro());
	}
	
	@Test
	void testCostruisciComandoPrendi() {
		this.comandoGenerico = factory.costruisciComando("prendi attrezzo");
		assertEquals("prendi", this.comandoGenerico.getNome());
		assertEquals("attrezzo", this.comandoGenerico.getParametro());
	}
	
	@Test
	void testCostruisciComandoNonValido() {
		this.comandoGenerico = factory.costruisciComando("");
		assertEquals("non valido", this.comandoGenerico.getNome());
		assertNull(this.comandoGenerico.getParametro());
	}
	
	@Test
	void testCostruisciComandoGuarda() {
		this.comandoGenerico = factory.costruisciComando("guarda");
		assertEquals("guarda", this.comandoGenerico.getNome());
		assertNull(this.comandoGenerico.getParametro());
	}
	
	@Test
	void testCostruisciComandoFine() {
		this.comandoGenerico = factory.costruisciComando("fine");
		assertEquals("fine", this.comandoGenerico.getNome());
		assertNull(this.comandoGenerico.getParametro());
	}
	
	@Test
	void testCostruisciComandoVai() {
		this.comandoGenerico = factory.costruisciComando("vai nord");
		assertEquals("vai", this.comandoGenerico.getNome());
		assertEquals("nord", this.comandoGenerico.getParametro());
	}
}
