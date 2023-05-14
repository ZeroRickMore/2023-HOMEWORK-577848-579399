package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.attrezzi.*;
import it.uniroma3.diadia.*;
import it.uniroma3.diadia.ambienti.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComandoPosaTest {
	
	private Attrezzo attrezzoDaPosare;
	private ComandoPosa comandoPosa;
	private FabbricaDiComandi fabbricaDiComandi;
	private IO io;
	private Labirinto labirinto;
	private DiaDia diaDia;

	@BeforeEach
	void setUp() {
		this.io = new IOConsole();
		this.attrezzoDaPosare = new Attrezzo("attrezzo",8);
		this.fabbricaDiComandi = new FabbricaDiComandiFisarmonica();
		this.comandoPosa = (ComandoPosa) fabbricaDiComandi.costruisciComando("posa attrezzo");
		
		this.labirinto = new LabirintoBuilder()
				.addStanzaIniziale("Blibloteca")
				.getLabirinto();
		this.diaDia = new DiaDia(this.labirinto, io);
		this.diaDia.getPartita().getGiocatore().getBorsa().addAttrezzo(attrezzoDaPosare);
	}

	@Test
	void testEsegui() {
		assertEquals("attrezzo", this.comandoPosa.getParametro());
		assertFalse(this.diaDia.getPartita().getGiocatore().getBorsa().isEmpty());
		this.comandoPosa.esegui(this.diaDia.getPartita(), io);
		assertTrue(this.diaDia.getPartita().getGiocatore().getBorsa().isEmpty());
		assertTrue(this.labirinto.getStanzaIniziale().hasAttrezzo("attrezzo"));
	}

	@Test
	void testSetParametro() {
		assertEquals("attrezzo", this.comandoPosa.getParametro());
	}
}
