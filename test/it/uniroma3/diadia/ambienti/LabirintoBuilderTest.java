package it.uniroma3.diadia.ambienti;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class LabirintoBuilderTest {
	private Labirinto.LabirintoBuilder labirintoBuilder;
	private String nomeStanzaIniziale = "Atrio";
	private String nomeStanzaVincente = "Uscita";

	@Before
	public void setUp() throws Exception {
		labirintoBuilder = new Labirinto.LabirintoBuilder();
	}

	@Test
	public void testMonolocale() {
		Labirinto monolocale = labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale)
				.addStanzaVincente(nomeStanzaIniziale)
				.getLabirinto();
	assertEquals(nomeStanzaIniziale,monolocale.getStanzaIniziale().getNome());
	assertEquals(nomeStanzaIniziale,monolocale.getStanzaVincente().getNome());
	}
	
	@Test
	public void testMonolocaleConAttrezzo() {
		Labirinto monolocale = labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale).addAttrezzo("spada",1)
				.addStanzaVincente(nomeStanzaIniziale).addAttrezzo("spadina", 3)
				.getLabirinto();
		assertEquals(nomeStanzaIniziale,monolocale.getStanzaIniziale().getNome());
		assertEquals(nomeStanzaIniziale,monolocale.getStanzaVincente().getNome());
		assertEquals("spada",monolocale.getStanzaIniziale().getAttrezzo("spada").getNome());
		assertEquals("spadina",monolocale.getStanzaVincente().getAttrezzo("spadina").getNome());
	}
	
	@Test
	public void testMonolocaleConAttrezzoSingoloDuplicato() {
		Labirinto monolocale = labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale)
				.addAttrezzo("spada",1)
				.addAttrezzo("spada",1)
				.getLabirinto();
		int size = monolocale.getStanzaIniziale().getAttrezzi().size();
		assertTrue(size==1);
		assertEquals("{spada=spada (1kg)}",monolocale.getStanzaIniziale().getAttrezzi().toString());	
	}
	
	@Test
	public void testBilocale() {
		Labirinto bilocale = labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale)
				.addStanzaVincente(nomeStanzaVincente)
				.addAdiacenza(nomeStanzaIniziale, nomeStanzaVincente, Direzione.nord)
				.addAdiacenza(nomeStanzaVincente, nomeStanzaIniziale, Direzione.sud)
				.getLabirinto();
		assertEquals(bilocale.getStanzaVincente(),bilocale.getStanzaIniziale().getStanzaAdiacente(Direzione.nord));
		assertEquals(Collections.singleton(Direzione.nord),bilocale.getStanzaIniziale().getDirezioni());
		assertEquals(Collections.singleton(Direzione.sud),bilocale.getStanzaVincente().getDirezioni()); 
		assertEquals(nomeStanzaVincente, bilocale.getStanzaIniziale().getStanzaAdiacente(Direzione.nord).getNome());
	}
	
	@Test
	public void testTrilocale(){
		Labirinto trilocale = labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale).addAttrezzo("sedia", 1)
				.addStanza("biblioteca")
				.addAdiacenza(nomeStanzaIniziale, "biblioteca", Direzione.sud)
				.addAdiacenza("biblioteca", nomeStanzaIniziale, Direzione.nord)
				.addAttrezzo("libro antico", 5)
				.addStanzaVincente(nomeStanzaVincente)
				.addAdiacenza("biblioteca", nomeStanzaVincente, Direzione.est)
				.addAdiacenza(nomeStanzaVincente,"biblioteca" , Direzione.ovest)
				.getLabirinto();	
		assertEquals(nomeStanzaIniziale, trilocale.getStanzaIniziale().getNome());
		assertEquals(nomeStanzaVincente, trilocale.getStanzaVincente().getNome());
		assertEquals("biblioteca",trilocale.getStanzaIniziale().getStanzaAdiacente(Direzione.sud).getNome());
	}
	
	@Test
	public void testTrilocaleConStanzaDuplicata() {
				labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale)
				.addStanza("stanza generica")
				.addStanza("stanza generica")
				.addAdiacenza(nomeStanzaIniziale, "stanza generica", Direzione.nord)
				.getLabirinto();
		assertTrue(labirintoBuilder.getMapNome2Stanza().size()<=2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testPiuDiQuattroAdiacenti() {
		Labirinto maze = labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale)
				.addStanza("stanza 1")
				.addStanza("stanza 2")
				.addStanza("stanza 3")
				.addStanza("stanza 4")
				.addStanza("stanza 5")
				.addAdiacenza(nomeStanzaIniziale, "stanza 1", Direzione.nord)
				.addAdiacenza(nomeStanzaIniziale, "stanza 2", Direzione.ovest)
				.addAdiacenza(nomeStanzaIniziale, "stanza 3", Direzione.sud)
				.addAdiacenza(nomeStanzaIniziale, "stanza 4", Direzione.est)
				.addAdiacenza(nomeStanzaIniziale, "stanza 5", Direzione.valueOf("nord-ovest")) // non dovrebbe essere aggiunta
				.getLabirinto();
				Stanza test = new Stanza("stanza 5");
		assertNull(maze.getStanzaIniziale().getStanzaAdiacente(Direzione.valueOf("nord-ovest")));
		assertTrue(maze.getStanzaIniziale().getStanzeAdiacenti().size()<=4);
		assertTrue(!maze.getStanzaIniziale().getStanzeAdiacenti().containsValue(test));
		Map<Direzione,Stanza> mappa = new HashMap<>();
		mappa.put(Direzione.nord, new Stanza("stanza 1"));
		mappa.put(Direzione.ovest, new Stanza("stanza 2"));
		mappa.put(Direzione.sud, new Stanza("stanza 3"));
		mappa.put(Direzione.est, new Stanza("stanza 4"));
		assertEquals(mappa,maze.getStanzaIniziale().getStanzeAdiacenti());
	}
	
	@Test
	public void testImpostaStanzaInizialeCambiandola() {
		Labirinto maze = labirintoBuilder
				.addStanzaIniziale(this.nomeStanzaIniziale)
				.addStanza("nuova iniziale")
				.addStanzaIniziale("nuova iniziale")
				.getLabirinto();
		assertEquals("nuova iniziale",maze.getStanzaIniziale().getNome());
	}
	
	@Test
	public void testAggiuntaAttrezziAStanze_Iniziale() {
		String nomeAttrezzo = "attrezzo";
		int peso = 1;
		Labirinto maze = this.labirintoBuilder
				.addStanzaIniziale(this.nomeStanzaIniziale)
				.addAttrezzo(nomeAttrezzo, peso)
				.getLabirinto();
		Attrezzo attrezzo = new Attrezzo(nomeAttrezzo,peso);
		assertEquals(attrezzo,maze.getStanzaIniziale().getAttrezzo(nomeAttrezzo));
	}
	
	@Test
	public void testAggiuntaAttrezziAStanze_AppenaAggiunte() {
		String nomeAttrezzo = "attrezzo";
		int peso = 1;
		String nomeStanza = "stanza 1";
		labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale)
				.addStanza(nomeStanza)
				.addAttrezzo(nomeAttrezzo, peso)
				.getLabirinto();
		assertTrue(this.labirintoBuilder.getMapNome2Stanza().get(nomeStanza).getAttrezzi().containsKey(nomeAttrezzo));
		assertEquals(new Attrezzo(nomeAttrezzo,peso),this.labirintoBuilder.getMapNome2Stanza().get(nomeStanza).getAttrezzo(nomeAttrezzo));
	}
	
	@Test
	public void testAggiuntaAttrezzoAStanze_AppenaAggiunteMultiple() {
		String nomeAttrezzo = "attrezzo";
		int peso = 1;
		String nomeStanza = "stanza 1";
		this.labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale)
				.addStanza(nomeStanza)
				.addAttrezzo(nomeAttrezzo, peso)
				.getLabirinto();
		Attrezzo attrezzo = new Attrezzo(nomeAttrezzo,peso);
		assertEquals(attrezzo,labirintoBuilder.getMapNome2Stanza().get(nomeStanza).getAttrezzi().get(nomeAttrezzo));
	}
	
	@Test
	public void testAggiuntaAttrezzoAStanze_MoltepliciAttrezziStessaStanza() {
		String nomeAttrezzo1 = "attrezzo 1";
		String nomeAttrezzo2 = "attrezzo 2";
		int peso1 = 1;
		int peso2 = 2;
		String nomeStanza1 = "Stanza 1";
		this.labirintoBuilder
		.addStanza(nomeStanza1)
		.addAttrezzo(nomeAttrezzo1, peso1)
		.addAttrezzo(nomeAttrezzo2, peso2);
		assertEquals(new Attrezzo(nomeAttrezzo2,peso2),labirintoBuilder.getMapNome2Stanza().get(nomeStanza1).getAttrezzo(nomeAttrezzo2));
		assertEquals(new Attrezzo(nomeAttrezzo1,peso1),labirintoBuilder.getMapNome2Stanza().get(nomeStanza1).getAttrezzo(nomeAttrezzo1));
	}
	
	
	@Test  //verifico che gli attrezzi vengano aggiunti all'ultima stanza aggiunta
	public void testAggiuntaAttrezzoAStanze_AttrezzoAggiuntoAllaSecondaStanza() {
		String nomeAttrezzo1 = "attrezzo 1";
		String nomeAttrezzo2 = "attrezzo 2";
		int peso1 = 1;
		int peso2 = 2;
		String nomeStanza1 = "Stanza 1";
		String nomeStanza2 = "Stanza 2";
		this.labirintoBuilder
		.addStanza(nomeStanza1)
		.addStanza(nomeStanza2)
		.addAttrezzo(nomeAttrezzo1, peso1)
		.addAttrezzo(nomeAttrezzo2, peso2);
		assertEquals(new Attrezzo(nomeAttrezzo1,peso1),labirintoBuilder.getMapNome2Stanza().get(nomeStanza2).getAttrezzo(nomeAttrezzo1));
		assertEquals(new Attrezzo(nomeAttrezzo2,peso2),labirintoBuilder.getMapNome2Stanza().get(nomeStanza2).getAttrezzo(nomeAttrezzo2));
	}
	
	@Test
	public void testAggiuntaAttrezzoAStanze_PrimoAttrezzoInUnaStanzaSecondoAttrezzoSecondaStanza() {
		String nomeAttrezzo1 = "attrezzo 1";
		String nomeAttrezzo2 = "attrezzo 2";
		int peso1 = 1;
		int peso2 = 2;
		String nomeStanza1 = "Stanza 1";
		String nomeStanza2 = "Stanza 2";
		this.labirintoBuilder
		.addStanza(nomeStanza1)
		.addAttrezzo(nomeAttrezzo1, peso1)
		.addStanza(nomeStanza2)
		.addAttrezzo(nomeAttrezzo2, peso2);
		assertEquals(new Attrezzo(nomeAttrezzo1,peso1),labirintoBuilder.getMapNome2Stanza().get(nomeStanza1).getAttrezzo(nomeAttrezzo1));
		assertEquals(new Attrezzo(nomeAttrezzo2,peso2),labirintoBuilder.getMapNome2Stanza().get(nomeStanza2).getAttrezzo(nomeAttrezzo2));
	}
	
	@Test
	public void testLabirintoConStanzaMagica() {
		int sogliaMagica = 1;
		String nomeStanzaMagica = "Stanza Magica";
		this.labirintoBuilder
		.addStanzaMagica(nomeStanzaMagica, sogliaMagica);
		StanzaMagica stanzaMagica = (StanzaMagica)labirintoBuilder.getMapNome2Stanza().get(nomeStanzaMagica);
		assertTrue(stanzaMagica.isMagica());
	}
	
	@Test
	public void testLabirintoConStanzaMagica_AggiuntaElementoOltreSogliaMagica() {
		String nomeAttrezzo1 = "attrezzo 1";
		String nomeAttrezzo2 = "attrezzo 2";
		String nomeAttrezzo2Inv = "2 ozzertta";
		int sogliaMagica = 1;
		int peso1 = 1;
		int peso2 = 2;
		int peso2_x2 = peso2*2;
		String nomeStanzaMagica = "Stanza Magica";
		this.labirintoBuilder
		.addStanzaMagica(nomeStanzaMagica, sogliaMagica)
		.addAttrezzo(nomeAttrezzo1, peso1)
		.addAttrezzo(nomeAttrezzo2, peso2);
		assertEquals(new Attrezzo(nomeAttrezzo2Inv,peso2_x2), labirintoBuilder.getMapNome2Stanza().get(nomeStanzaMagica).getAttrezzo(nomeAttrezzo2Inv));
		assertEquals(new Attrezzo(nomeAttrezzo1,peso1), labirintoBuilder.getMapNome2Stanza().get(nomeStanzaMagica).getAttrezzo(nomeAttrezzo1));
	}
	
	
	@Test
	public void testLabirintoConStanzaBloccata_ConPassepartout() {
		this.labirintoBuilder
		.addStanzaIniziale(nomeStanzaIniziale)
		.addStanzaBloccata("stanza bloccata", Direzione.nord, "chiave").addAttrezzo("chiave", 1)
		.addAdiacenza(nomeStanzaIniziale, "stanza bloccata", Direzione.nord)
		.addAdiacenza("stanza bloccata", nomeStanzaIniziale, Direzione.sud)
		.addStanzaVincente(nomeStanzaVincente)
		.addAdiacenza("stanza bloccata", nomeStanzaVincente, Direzione.nord)
		.addAdiacenza(nomeStanzaVincente, "stanza bloccata", Direzione.sud);
		Stanza stanzaVincente = new Stanza(nomeStanzaVincente);
		//Asserisce che in presenza di passepartout, invocato il metodo getStanzaAdiacente(), la stanza bloccata ritorna la corretta adiacenza
		assertEquals(stanzaVincente,labirintoBuilder.getMapNome2Stanza().get("stanza bloccata").getStanzaAdiacente(Direzione.nord));	
	}
	
	@Test
	public void testLabirintoConStanzaBloccata_SenzaPassepartout() {
		this.labirintoBuilder
		.addStanzaIniziale(nomeStanzaIniziale)
		.addStanzaBloccata("stanza bloccata", Direzione.nord, "chiave")
		.addAdiacenza(nomeStanzaIniziale, "stanza bloccata", Direzione.nord)
		.addAdiacenza("stanza bloccata", nomeStanzaIniziale, Direzione.sud)
		.addStanzaVincente(nomeStanzaVincente)
		.addAdiacenza("stanza bloccata", nomeStanzaVincente, Direzione.nord)
		.addAdiacenza(nomeStanzaVincente, "stanza bloccata", Direzione.sud);
		Stanza stanzaBloccata = new StanzaBloccata("stanza bloccata", Direzione.nord, "chiave");
		//Asserisce che in caso di mancanza di passepartout, invocato il metodo getStanzaAdiacente(), la stanza bloccata ritorna se stessa
		assertEquals(stanzaBloccata,labirintoBuilder.getMapNome2Stanza().get("stanza bloccata").getStanzaAdiacente(Direzione.nord));
	}
	
	@Test
	public void testLabirintoCompletoConTutteLeStanze() {
		
		Labirinto labirintoCompleto = this.labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale)
				.addStanzaVincente(nomeStanzaVincente)
				.addStanza("corridoio")
				.addAttrezzo("chiave", 1)
				.addAttrezzo("lanterna", 1)
				.addStanzaBloccata("corridoio bloccato",Direzione.nord,"chiave")
				.addStanzaMagica("stanza magica", 1)
				.addStanzaBuia("stanza buia","lanterna")
				.addStanza("Aula 1")
				.addAdiacenza(nomeStanzaIniziale, "corridoio", Direzione.nord)
				.addAdiacenza("corridoio", nomeStanzaIniziale, Direzione.sud)
				.addAdiacenza("corridoio", "corridoio bloccato", Direzione.nord)
				.addAdiacenza("corridoio bloccato", "corridoio", Direzione.sud)
				.addAdiacenza("corridoio bloccato", "Aula 1", Direzione.nord)
				.addAdiacenza("Aula 1", "corridoio bloccato", Direzione.sud)
				.addAdiacenza("Aula 1", nomeStanzaVincente,Direzione.nord)
				.addAdiacenza(nomeStanzaVincente, "Aula 1", Direzione.sud)
				.addAdiacenza("corridoio", "stanza magica", Direzione.est)
				.addAdiacenza("stanza magica", "corridoio", Direzione.ovest)
				.addAdiacenza("corridoio", "stanza buia", Direzione.ovest)
				.addAdiacenza("stanza buia", "corridoio", Direzione.est)
				.getLabirinto();
		assertEquals(nomeStanzaIniziale, labirintoCompleto.getStanzaIniziale().getNome());
		assertEquals(nomeStanzaVincente, labirintoCompleto.getStanzaVincente().getNome());
		Stanza corridoio = labirintoCompleto.getStanzaIniziale().getStanzaAdiacente(Direzione.nord);
		assertEquals("corridoio",corridoio.getNome());
		assertTrue(corridoio.getDirezioni().containsAll(Arrays.asList(Direzione.ovest,Direzione.est,Direzione.nord,Direzione.sud)));
		Map<Direzione,Stanza> mapAdiacenti = new HashMap<>();
		mapAdiacenti.put(Direzione.nord,new Stanza("corridoio bloccato"));
		mapAdiacenti.put(Direzione.sud,new Stanza(nomeStanzaIniziale));
		mapAdiacenti.put(Direzione.est,new Stanza("stanza magica"));
		mapAdiacenti.put(Direzione.ovest,new Stanza("stanza buia"));
		assertEquals(mapAdiacenti,corridoio.getStanzeAdiacenti());
		assertEquals("{chiave=chiave (1kg), lanterna=lanterna (1kg)}",corridoio.getAttrezzi().toString());
	}
}