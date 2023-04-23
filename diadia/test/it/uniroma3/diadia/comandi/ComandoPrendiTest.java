package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.uniroma3.diadia.*;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class ComandoPrendiTest {
	private Partita partita;
	private Attrezzo attrezzoDaPrendere;
	private ComandoPrendi comandoPrendi;
	private FabbricaDiComandi fabbricaDiComandi;
	private IO io;
	
	@BeforeEach
	void setUp() {
		this.partita = new Partita();
		this.io = new IOConsole();
		this.attrezzoDaPrendere = new Attrezzo("Sasso", 2);
		this.partita.getStanzaCorrente().addAttrezzo(attrezzoDaPrendere);
		this.fabbricaDiComandi = new FabbricaDiComandiFisarmonica();
		this.comandoPrendi = (ComandoPrendi)fabbricaDiComandi.costruisciComando("prendi Sasso");
	}

	@Test
	void testEsegui() {
		assertTrue(this.partita.getStanzaCorrente().hasAttrezzo("Sasso"));
		this.comandoPrendi.esegui(partita, io);
		assertEquals(attrezzoDaPrendere, this.partita.getGiocatore().getBorsa().getAttrezzo("Sasso"));
		assertFalse(this.partita.getStanzaCorrente().hasAttrezzo("Sasso"));
	}
	
	@Test
	void testSetParametro() {
		assertEquals("Sasso", this.comandoPrendi.getParametro());
	}
}
