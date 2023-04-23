package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.*;
import it.uniroma3.diadia.ambienti.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComandoVaiTest {
	private Partita partita;
	private Stanza stanzaCorrente;
	private Stanza stanzaSuccessiva;
	private ComandoVai comandoVai;
	private FabbricaDiComandi fabbricaDiComandi;
	private IO io;
	
	@BeforeEach
	void setUp(){
		this.stanzaCorrente = new Stanza("Blibloteca");
		this.stanzaSuccessiva = new Stanza("RGB Room");
		this.partita = new Partita();
		this.io = new IOConsole();
		this.partita.setStanzaCorrente(stanzaCorrente);
		this.stanzaCorrente.impostaStanzaAdiacente("nord", this.stanzaSuccessiva);
		this.fabbricaDiComandi = new FabbricaDiComandiFisarmonica();
		this.comandoVai = (ComandoVai) fabbricaDiComandi.costruisciComando("vai nord");
		
	}

	@Test
	void testEsegui() {
		this.comandoVai.esegui(this.partita, this.io);
		assertEquals(this.stanzaSuccessiva, this.partita.getStanzaCorrente());
	}

	@Test
	void testSetParametro() {
		assertEquals("nord", this.comandoVai.getParametro());
	}

}
