package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.attrezzi.*;
import it.uniroma3.diadia.*;
import it.uniroma3.diadia.ambienti.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComandoPosaTest {
	private Partita partita;
	private Stanza stanza;
	private Attrezzo attrezzoDaPosare;
	private ComandoPosa comandoPosa;
	private FabbricaDiComandi fabbricaDiComandi;
	private IO io;
	//private Giocatore giocatore;

	@BeforeEach
	void setUp() {
		this.stanza = new Stanza("Blibloteca");
		//this.giocatore = new Giocatore();
		this.partita = new Partita();
		this.io = new IOConsole();
		this.partita.setStanzaCorrente(stanza);
		this.attrezzoDaPosare = new Attrezzo("attrezzo",8);
		this.partita.getGiocatore().getBorsa().addAttrezzo(attrezzoDaPosare);
		this.fabbricaDiComandi = new FabbricaDiComandiFisarmonica();
		this.comandoPosa = (ComandoPosa) fabbricaDiComandi.costruisciComando("posa attrezzo");
	}

	@Test
	void testEsegui() {
		assertEquals("attrezzo", this.comandoPosa.getParametro());
		assertFalse(this.partita.getGiocatore().getBorsa().isEmpty());
		this.comandoPosa.esegui(partita, io);
		assertTrue(this.partita.getGiocatore().getBorsa().isEmpty());
		assertEquals(this.attrezzoDaPosare, this.stanza.getAttrezzi()[0]);
	}

	@Test
	void testSetParametro() {
		assertEquals("attrezzo", this.comandoPosa.getParametro());
	}
}
