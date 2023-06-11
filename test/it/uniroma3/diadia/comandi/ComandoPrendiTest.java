package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.ambienti.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.uniroma3.diadia.*;
import it.uniroma3.diadia.IOConsole;

class ComandoPrendiTest {
	
	private ComandoPrendi comandoPrendi;
	private FabbricaDiComandi fabbricaDiComandi;
	private IO io;
	private Labirinto labirinto;
	private DiaDia diaDia;
	
	@BeforeEach
	void setUp() {
		this.io = new IOConsole(new Scanner(System.in));
		this.fabbricaDiComandi = new FabbricaDiComandiRiflessiva();
		this.comandoPrendi = (ComandoPrendi)fabbricaDiComandi.costruisciComando("prendi Kiodo");
		this.labirinto = new Labirinto.LabirintoBuilder()
				.addStanzaIniziale("A")
				.addAttrezzo("Kiodo", 1)
				.getLabirinto();
		this.diaDia = new DiaDia(labirinto, io);
	}

	@Test
	void testEsegui() {
		assertTrue(this.labirinto.getStanzaIniziale().hasAttrezzo("Kiodo"));
		this.comandoPrendi.esegui(this.diaDia.getPartita(), io);
		assertEquals("Kiodo", this.diaDia.getPartita().getGiocatore().getBorsa().getAttrezzo("Kiodo").getNome());
		assertFalse(this.diaDia.getPartita().getStanzaCorrente().hasAttrezzo("Kiodo"));
	}
	
	@Test
	void testSetParametro() {
		assertEquals("Kiodo", this.comandoPrendi.getParametro());
	}
	
	@Test
	void testGetNome() {
		assertEquals("prendi", this.comandoPrendi.getNome());
	}
}
