package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;
import it.uniroma3.diadia.ambienti.StanzaMagica;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.personaggi.AbstractPersonaggio;
import it.uniroma3.personaggi.Cane;
import it.uniroma3.personaggi.Mago;
import it.uniroma3.personaggi.Strega;

class CaricatoreLabirintoTest {
	private CaricatoreLabirinto caricatoreLabirinto;
	private String simulaFileText;
	private String stanzeMarker = CaricatoreLabirinto.STANZE_MARKER;
	private String stanzaBuiaMarker = CaricatoreLabirinto.STANZE_BUIE_MARKER;
	private String stanzaBloccataMarker = CaricatoreLabirinto.STANZE_BLOCCATE_MARKER;
	private String stanzaMagicaMarker = CaricatoreLabirinto.STANZE_MAGICHE_MARKER;
	private String stanzaInizialeMarker = CaricatoreLabirinto.STANZA_INIZIALE_MARKER;
	private String stanzaVincenteMarker = CaricatoreLabirinto.STANZA_VINCENTE_MARKER;
	private String attrezziMarker = CaricatoreLabirinto.ATTREZZI_MARKER;
	private String usciteMarker = CaricatoreLabirinto.USCITE_MARKER;
	private String personaggiMarker = CaricatoreLabirinto.PERSONAGGI_MARKER;

	@Test
	void testCaricatoreLabirintoMonolocale() throws Exception {
		this.simulaFileText = this.stanzeMarker + "N10" + "\n" + this.stanzaMagicaMarker + "\n" + this.stanzaBloccataMarker + "\n" + this.stanzaBuiaMarker +
				"\n" + this.stanzaInizialeMarker + "N10" + "\n" +
				this.stanzaVincenteMarker + "N10" + "\n" + this.attrezziMarker + "\n" + this.usciteMarker + "\n" + this.personaggiMarker;
		this.caricatoreLabirinto = new CaricatoreLabirinto(true, simulaFileText);
		this.caricatoreLabirinto.carica();	
		assertEquals(1, this.caricatoreLabirinto.getLabirintoBuilder().getMapNome2Stanza().values().size());
	}
	
	/*
	 * Stanze: N10
	 * Inizio: N10
	 * Vincente: N10
	 * Attrezzi: 
	 * Uscite:
	 * Stanze magiche:
	 * Stanze bloccate:
	 * Stanze buie:
	 * Personaggi:
	 * */

	@Test
	void testCaricatoreLabirintoBilocale() throws Exception {
		this.simulaFileText = this.stanzeMarker + "N10, N12" +
				"\n" + this.stanzaMagicaMarker + "\n" + this.stanzaBloccataMarker + "\n" + this.stanzaBuiaMarker +
				"\n" + this.stanzaInizialeMarker + "N10" + "\n" +
				this.stanzaVincenteMarker + "N12" + "\n" + this.attrezziMarker + "\n" + this.usciteMarker + "N10 est N12" + "\n" + this.personaggiMarker;
		this.caricatoreLabirinto = new CaricatoreLabirinto(true, simulaFileText);
		this.caricatoreLabirinto.carica();	
		assertEquals(2, this.caricatoreLabirinto.getLabirintoBuilder().getMapNome2Stanza().values().size());
		Labirinto labirintoAttuale = this.caricatoreLabirinto.getLabirintoBuilder().getLabirinto();
		assertEquals("N10", labirintoAttuale.getStanzaIniziale().getNome());
		assertEquals("N12",  labirintoAttuale.getStanzaVincente().getNome());
		assertEquals("N12", labirintoAttuale.getStanzaIniziale().getStanzaAdiacente(Direzione.est).getNome());
		assertEquals("N10", labirintoAttuale.getStanzaVincente().getStanzaAdiacente(Direzione.ovest).getNome());
	}

	@Test
	void testCaricatoreLabirintoCompleto() throws Exception {
		/* 
	    Stanze: inizio, mezzo, fine
	    Stanze magiche: magica1 , magica2 10
		Stanze bloccate: block1 nord attrblock1
		Stanze buie: dark1 attrDark1
		Inizio: inizio
		Vincente: fine
		Attrezzi: attrInizio 10 inizio, attrMezzo 2 mezzo
		Uscite: inizio nord mezzo, mezzo est fine
		Personaggi: mago mago1 ciao sono mago1 %%% attrMago1 5 inizio, cane cane1 bau wooof %%% attrCane 3 mezzo, strega strega1 ciao sono strega1 %%% attrStrega1 8 fine
		 
		 */


		this.simulaFileText = 	stanzeMarker + "inizio, mezzo, fine\n" + //solo stanze normali
								stanzaMagicaMarker + "magica1 , magica2 10\n" +
								stanzaBloccataMarker + "block1 nord attrblock1\n" +
								stanzaBuiaMarker + "dark1 attrDark1\n" +
								stanzaInizialeMarker + "inizio\n" +
								stanzaVincenteMarker + "fine\n" +
								attrezziMarker + "attrInizio 10 inizio, attrMezzo 2 mezzo\n" +
								usciteMarker + "inizio nord mezzo, mezzo est fine, inizio est dark1, inizio sud magica1, magica1 sud magica2, inizio ovest block1\n" +
								/*			 MEZZO     FINE
								 * BLOCK1   INIZIO	  DARK1
								 *     		MAGICA1
								 *     		MAGICA2
								 * */
								personaggiMarker + "Mago mago1 ciao sono mago1 %%% attrMago1 5 inizio, " +
								"Cane cane1 bau wooof %%% attrCane 3 mezzo, Strega strega1 ciao sono strega1 %%% attrStrega1 8 fine\n";
		
//		System.out.println(simulaFileText);

		this.caricatoreLabirinto = new CaricatoreLabirinto(true, simulaFileText);
		this.caricatoreLabirinto.carica();	
		//

		assertFalse(this.caricatoreLabirinto.getLabirintoBuilder().getMapNome2Stanza().isEmpty());
		Labirinto labirintoAttuale = this.caricatoreLabirinto.getLabirintoBuilder().getLabirinto();
		
		//TEST ADIACENZE
		Stanza inizio = labirintoAttuale.getStanzaIniziale();
		assertEquals("inizio", inizio.getNome());
		Stanza fine = labirintoAttuale.getStanzaVincente();
		assertEquals("fine", fine.getNome());
		Stanza mezzo = inizio.getStanzaAdiacente(Direzione.nord);
		assertEquals("mezzo" , mezzo.getNome());
		assertEquals("fine" , mezzo.getStanzaAdiacente(Direzione.est).getNome());
		Stanza block1 = inizio.getStanzaAdiacente(Direzione.ovest);
		assertEquals("block1", block1.getNome());
		Stanza dark1 = inizio.getStanzaAdiacente(Direzione.est);
		assertEquals("dark1", dark1.getNome());
		Stanza magica1 = inizio.getStanzaAdiacente(Direzione.sud);
		assertEquals("magica1", magica1.getNome());
		Stanza magica2 = magica1.getStanzaAdiacente(Direzione.sud);
		assertEquals("magica2", magica2.getNome());

		//TEST DEGLI ATTREZZI
		assertTrue(inizio.hasAttrezzo("attrInizio"));
		assertEquals(10, inizio.getAttrezzo("attrInizio").getPeso());
		
		assertTrue(mezzo.hasAttrezzo("attrMezzo"));
		assertEquals(2, mezzo.getAttrezzo("attrMezzo").getPeso());
		
		//TEST ISTANZE CORRETTE
		assertTrue(block1 instanceof StanzaBloccata);
		assertTrue(magica1 instanceof StanzaMagica);
		assertTrue(magica2 instanceof StanzaMagica);

		//TEST PERSONAGGI ->
		//mago mago1 ciao sono mago1 %%% attrMago1 5 inizio, cane cane1 bau wooof %%% attrCane 3 mezzo, 
		//strega strega1 ciao sono strega1 %%% attrStrega1 8 fine

		//TEST NELLA STANZA GIUSTA
		AbstractPersonaggio mago1 = inizio.getPersonaggio();
		assertTrue(mago1 instanceof Mago);
		AbstractPersonaggio cane1 = mezzo.getPersonaggio();
		assertTrue(cane1 instanceof Cane);
		AbstractPersonaggio strega1 = fine.getPersonaggio();
		assertTrue(strega1 instanceof Strega);
		//TEST NOME GIUSTO
		assertEquals("mago1", mago1.getNome());
		assertEquals("cane1", cane1.getNome());
		assertEquals("strega1", strega1.getNome());
		//TEST PRESENTAZIONE GIUSTA
		assertEquals("ciao sono mago1", mago1.getPresentazione());
		assertEquals("bau wooof", cane1.getPresentazione());
		assertEquals("ciao sono strega1", strega1.getPresentazione());
		//TEST ATTREZZO GIUSTO
		assertEquals(new Attrezzo("attrMago1", 5), mago1.getAttrezzo());
		assertEquals(new Attrezzo("attrCane", 3), cane1.getAttrezzo());
		assertEquals(new Attrezzo("attrStrega1", 8), strega1.getAttrezzo());
	}




}
