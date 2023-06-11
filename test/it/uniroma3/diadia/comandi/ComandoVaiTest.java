package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.*;
import it.uniroma3.diadia.ambienti.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComandoVaiTest {
	
	private ComandoVai comandoVai;
	private FabbricaDiComandi fabbricaDiComandi;
	private IO io;
	private Labirinto labirinto;
	private DiaDia diaDia;
	
	@BeforeEach
	void setUp(){
		this.io = new IOConsole(new Scanner(System.in));
		this.fabbricaDiComandi = new FabbricaDiComandiRiflessiva();
		this.comandoVai = (ComandoVai) fabbricaDiComandi.costruisciComando("vai nord");	
		this.labirinto = new Labirinto.LabirintoBuilder()
				.addStanzaIniziale("Blibloteca")
				.addStanza("RGB Room")
				.addAdiacenza("Blibloteca", "RGB Room", Direzione.nord)
				.getLabirinto();
		this.diaDia = new DiaDia(this.labirinto, io);
	}

	@Test
	void testEsegui() {
		this.comandoVai.esegui(this.diaDia.getPartita(), this.io);
		assertEquals("RGB Room", this.diaDia.getPartita().getStanzaCorrente().getNome());
	}

	@Test
	void testSetParametro() {
		assertEquals("nord", this.comandoVai.getParametro());
	}
	
	@Test
	void testGetNome() {
		assertEquals("vai", this.comandoVai.getNome());
	}

}
