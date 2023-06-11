package it.uniroma3.diadia.ambienti;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class LabirintoTest {
	private Labirinto labirinto;
	private Stanza stanzaIniziale;
	private Stanza stanzaVincente;

	@BeforeEach
	void setUp() {
		this.labirinto = new Labirinto();
		this.labirinto.creaStanzeDefault();
		this.stanzaIniziale = new Stanza("Atrio");
		this.stanzaVincente = new Stanza("Biblioteca");
	}
	
	
	@Test
	void testGetStanzaIniziale() {
		assertEquals(this.stanzaIniziale.getNome(), this.labirinto.getStanzaIniziale().getNome());
	}
	
	@Test
	void testGetStanzaVincente() {
		assertEquals(this.stanzaVincente.getNome(), this.labirinto.getStanzaVincente().getNome());
	}
	
}