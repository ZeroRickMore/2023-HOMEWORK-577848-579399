package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AbstractComandoTest {

	@Test
	void testGetNome() {
		assertEquals("fake", (new ComandoFake()).getNome());
	}

	@Test
	void testSetEGetParametro() {
		ComandoFake comando = new ComandoFake();
		comando.setParametro("Param");
		assertEquals("Param", comando.getParametro());
	}
}
