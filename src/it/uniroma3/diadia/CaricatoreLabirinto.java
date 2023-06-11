package it.uniroma3.diadia;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.personaggi.AbstractPersonaggio;

public class CaricatoreLabirinto {

	/* prefisso di una singola riga di testo contenente tutti i nomi delle stanze */
	protected static final String STANZE_MARKER = "Stanze:";             

	/* prefisso di una singola riga di testo contenente tutti i nomi delle stanze magiche -> <nome> <sogliaMagica> */
	protected static final String STANZE_MAGICHE_MARKER = "Stanze magiche:";

	/* prefisso di una singola riga di testo contenente tutti i nomi delle stanze bloccate -> <nome> <nomeDirezioneBloccata> <nomeAttrezzo> */
	protected static final String STANZE_BLOCCATE_MARKER = "Stanze bloccate:";

	/* prefisso di una singola riga di testo contenente tutti i nomi delle stanze buie -> <nome> <nomeAttrezzo> */
	protected static final String STANZE_BUIE_MARKER = "Stanze buie:";

	/* prefisso di una singola riga contenente il nome della stanza iniziale */
	protected static final String STANZA_INIZIALE_MARKER = "Inizio:";    

	/* prefisso della riga contenente il nome stanza vincente */
	protected static final String STANZA_VINCENTE_MARKER = "Vincente:";  

	/* prefisso della riga contenente le specifiche degli attrezzi da collocare nel formato <nomeAttrezzo> <peso> <nomeStanza> */
	protected static final String ATTREZZI_MARKER = "Attrezzi:";

	/* prefisso della riga contenente le specifiche dei collegamenti tra stanza nel formato <nomeStanzaDa> <direzione> <nomeStanzaA> */
	protected static final String USCITE_MARKER = "Uscite:";

	/* prefisso della riga contenente le specifiche dei personaggi da collocare nel formato <tipoPersonaggio> <nomePersonaggio> <presentazione %%%> <Attrezzo> <PesoAttrezzo> <StanzaDoveMetterlo> */
	protected static final String PERSONAGGI_MARKER = "Personaggi:";


	/*
	 *  Esempio di un possibile file di specifica di un labirinto (vedi POO-26-eccezioni-file.pdf)

		Stanze: biblioteca, N10, N11
		Inizio: N10
		Vincente: N11
		Attrezzi: martello 10 biblioteca, pinza 2 N10
		Uscite: biblioteca nord N10, biblioteca sud N11

	 */
	private LineNumberReader reader;
	private Labirinto.LabirintoBuilder labirintoBuilder;

	public CaricatoreLabirinto(boolean iWantAStringReader, String toBeRead) throws FileNotFoundException, FormatoFileNonValidoException {
		if(!iWantAStringReader) throw new IllegalArgumentException("Inserisci true nel costruttore");
		this.labirintoBuilder = new Labirinto.LabirintoBuilder();
		this.reader = new LineNumberReader(new StringReader(toBeRead));
	}

	public Labirinto.LabirintoBuilder getLabirintoBuilder() {
		return labirintoBuilder;
	}

	public void setReader(LineNumberReader reader) { //Utile per la fase di testing, per inserirci uno StringReader
		this.reader = reader;
	}

	public CaricatoreLabirinto(String nomeFile) throws FileNotFoundException, FormatoFileNonValidoException {
		this.labirintoBuilder = new Labirinto.LabirintoBuilder();
		this.reader = new LineNumberReader(new FileReader(nomeFile));
	}

	public void carica() throws FormatoFileNonValidoException {
		try {
			this.leggiECreaStanze();
			this.leggiECreaStanzeMagiche();
			this.leggiECreaStanzeBloccate();
			this.leggiECreaStanzeBuie();
			this.leggiInizialeEvincente();
			this.leggiECollocaAttrezzi();
			this.leggiEImpostaUscite();
			this.leggiECollocaPersonaggi();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}


	private String leggiRigaCheCominciaPer(String marker) throws FormatoFileNonValidoException {
		try {
			String riga = this.reader.readLine();

			if(riga==null) throw new IllegalArgumentException("Riga è null!");
			check(riga.startsWith(marker),"era attesa una riga che cominciasse per "+marker);
			return riga.substring(marker.length());

		} catch (IOException e) {
			throw new FormatoFileNonValidoException(e.getMessage());
		}
	}

	private void leggiECreaStanze() throws FormatoFileNonValidoException  {
		String nomiStanze = this.leggiRigaCheCominciaPer(STANZE_MARKER);
		List<String> listaStanze = separaStringheAlleVirgole(nomiStanze);
		for(String nomeStanza : listaStanze) {
			this.labirintoBuilder.addStanza(nomeStanza);
		}
	}

	private void leggiECreaStanzeBuie() throws FormatoFileNonValidoException {
		String nomiStanzeBuie = this.leggiRigaCheCominciaPer(STANZE_BUIE_MARKER);
		List<String> listaStanzeBuie = separaStringheAlleVirgole(nomiStanzeBuie);
		for(String iteratore : listaStanzeBuie) {
			String nomeStanza = null;
			String attrezzoEnabler = null;
			try (Scanner sc = new Scanner(iteratore)) {
				check(sc.hasNext(), "Nome stanza non valido");
				nomeStanza = sc.next();
				check(sc.hasNext(), "Nome attrezzo enabler non valido");
				attrezzoEnabler = sc.next();
			}
			this.labirintoBuilder.addStanzaBuia(nomeStanza, attrezzoEnabler);
		}
	}

	private void leggiECreaStanzeMagiche() throws FormatoFileNonValidoException {
		String nomiStanzeMagiche = this.leggiRigaCheCominciaPer(STANZE_MAGICHE_MARKER);
		List<String> listaStanzeMagiche = separaStringheAlleVirgole(nomiStanzeMagiche);
		for(String iteratore : listaStanzeMagiche) {
			try(Scanner sc = new Scanner(iteratore)) {
				String nomeStanza = null;
				check(sc.hasNext(), "Nome stanza non valido");
				nomeStanza = sc.next();
				if(!sc.hasNext()) {
					this.labirintoBuilder.addStanzaMagica(nomeStanza);
				}
				else {
					try {
						int sogliaMagica = Integer.parseInt(sc.next());
						this.labirintoBuilder.addStanzaMagica(nomeStanza, sogliaMagica);
					}
					catch (NumberFormatException e) {
						check(false, "Soglia magica non valida");
					}

				}
			}
		}
	}
	
	private void leggiECreaStanzeBloccate() throws FormatoFileNonValidoException {
		String nomiStanzeBloccate = this.leggiRigaCheCominciaPer(STANZE_BLOCCATE_MARKER);
		List<String> listaStanzeBloccate = separaStringheAlleVirgole(nomiStanzeBloccate);
		for(String iteratore : listaStanzeBloccate) {
			String nomeStanza = null;
			String direzioneBloccata = null;
			String attrezzoEnabler = null;
			try (Scanner sc = new Scanner(iteratore)) {
				check(sc.hasNext(), "Nome stanza non valido");
				nomeStanza = sc.next();
				check(sc.hasNext(), "Nome direzione bloccata non valido");
				direzioneBloccata = sc.next();
				check(sc.hasNext(), "Nome attrezzo enabler non valido");
				attrezzoEnabler = sc.next();
			}
			this.labirintoBuilder.addStanzaBloccata(nomeStanza, Direzione.valueOf(direzioneBloccata), attrezzoEnabler);
		}
	}

	private List<String> separaStringheAlleVirgole(String string) {
		List<String> result = new LinkedList<>();
		Scanner scanner = new Scanner(string);
		scanner.useDelimiter(", ");
		try (Scanner scannerDiParole = scanner) {
			while(scannerDiParole.hasNext()) 
				result.add(scannerDiParole.next());
		}
		return result;
	}


	private void leggiInizialeEvincente() throws FormatoFileNonValidoException {
		String nomeStanzaIniziale = this.leggiRigaCheCominciaPer(STANZA_INIZIALE_MARKER);
		check(this.isStanzaValida(nomeStanzaIniziale), nomeStanzaIniziale +" non definita");
		String nomeStanzaVincente = this.leggiRigaCheCominciaPer(STANZA_VINCENTE_MARKER);
		check(this.isStanzaValida(nomeStanzaVincente), nomeStanzaVincente + " non definita");
		this.labirintoBuilder.setComeStanzaIniziale(nomeStanzaIniziale);
		this.labirintoBuilder.setComeStanzaVincente(nomeStanzaVincente);
	}

	private void leggiECollocaAttrezzi() throws FormatoFileNonValidoException {
		String specificheAttrezzi = this.leggiRigaCheCominciaPer(ATTREZZI_MARKER);

		for(String specificaAttrezzo : separaStringheAlleVirgole(specificheAttrezzi)) {
			String nomeAttrezzo = null;
			String pesoAttrezzo = null;
			String nomeStanza = null; 
			try (Scanner scannerLinea = new Scanner(specificaAttrezzo)) {
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome di un attrezzo."));
				nomeAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il peso dell'attrezzo "+nomeAttrezzo+"."));
				pesoAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome della stanza in cui collocare l'attrezzo "+nomeAttrezzo+"."));
				nomeStanza = scannerLinea.next();
			}				
			posaAttrezzo(nomeAttrezzo, pesoAttrezzo, nomeStanza);
		}
	}

	private void leggiECollocaPersonaggi() throws FormatoFileNonValidoException {
		String specifichePersonaggi = this.leggiRigaCheCominciaPer(PERSONAGGI_MARKER);

		for(String iteratore: separaStringheAlleVirgole(specifichePersonaggi)) {
			String tipoPersonaggio = null;
			String nomePersonaggio = null;
			String presentazione = null;
			String nomeAttrezzo = null; 
			String pesoAttrezzo = null;
			String nomeStanza = null;
			try (Scanner scannerLinea = new Scanner(iteratore)) {
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il tipo di un personaggio."));
				tipoPersonaggio = scannerLinea.next();	
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome di un personaggio."));
				nomePersonaggio = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("la presentazione del personaggio"));
				while(scannerLinea.hasNext()) {
					
					String next = scannerLinea.next();
					if(next.equals("%%%")) {
						break;
					}
					if(presentazione==null)
						presentazione = next;
					else
					presentazione += " " + next;	
				}
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome dell'attrezzo da dare al personaggio"));
				nomeAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il peso dell'attrezzo da dare al personaggio"));
				pesoAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("la stanza dove collocare il personaggio"));
				nomeStanza = scannerLinea.next();

			}				
			collocaPersonaggio(tipoPersonaggio ,nomePersonaggio, presentazione, nomeAttrezzo, pesoAttrezzo, nomeStanza);
		}

	}


	private void posaAttrezzo(String nomeAttrezzo, String pesoAttrezzo, String nomeStanza) throws FormatoFileNonValidoException {
		int peso;
		try {
			peso = Integer.parseInt(pesoAttrezzo);
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			check(isStanzaValida(nomeStanza),"Attrezzo "+ nomeAttrezzo+" non collocabile: stanza " +nomeStanza+" inesistente");
			this.labirintoBuilder.getMapNome2Stanza().get(nomeStanza).addAttrezzo(attrezzo);
		}
		catch (NumberFormatException e) {
			check(false, "Peso attrezzo "+nomeAttrezzo+" non valido");
		}
	}

	@SuppressWarnings("deprecation")
	private void collocaPersonaggio(String tipoPersonaggio, String nomePersonaggio, String presentazione, String nomeAttrezzo, String pesoAttrezzo, String nomeStanza) throws FormatoFileNonValidoException {
		int peso;
		try {
			peso = Integer.parseInt(pesoAttrezzo);
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			check(isStanzaValida(nomeStanza),"Personaggio "+ nomePersonaggio+" non collocabile: stanza " +nomeStanza+" inesistente");
			AbstractPersonaggio personaggio = null;
			try {
				String nomeClasse = "it.uniroma3.personaggi.";
				nomeClasse += Character.toUpperCase(tipoPersonaggio.charAt(0));
				nomeClasse += tipoPersonaggio.substring(1);
				personaggio = (AbstractPersonaggio) Class.forName(nomeClasse).newInstance();
				personaggio.setNome(nomePersonaggio);
				personaggio.setPresentazione(presentazione);
				personaggio.setAttrezzo(attrezzo);
				personaggio.setHaSalutato(false);
			}
			catch(ClassNotFoundException e) {
				check(false, "Personaggio "+ nomePersonaggio+" non collocabile: tipo "+tipoPersonaggio+" inesistente");
				e.printStackTrace();
			} catch (InstantiationException e) {
				check(false, "Personaggio "+ nomePersonaggio+" Istanziabile: tipo "+tipoPersonaggio+" Costruttore no args inesistente");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
			this.labirintoBuilder.getMapNome2Stanza().get(nomeStanza).setPersonaggio(personaggio);
		}
		catch (NumberFormatException e) {
			check(false, "Peso attrezzo "+nomeAttrezzo+" non valido");
		}
	}



	private boolean isStanzaValida(String nomeStanza) {
		return this.labirintoBuilder.getMapNome2Stanza().containsKey(nomeStanza);
	}

	private void leggiEImpostaUscite() throws FormatoFileNonValidoException {
		String specificheUscite = this.leggiRigaCheCominciaPer(USCITE_MARKER);
		for(String iterator : separaStringheAlleVirgole(specificheUscite)) {
			String stanzaPartenza = null;
			String dir = null;
			String stanzaDestinazione = null;

			try (Scanner scannerDiLinea = new Scanner(iterator)) {			

				while (scannerDiLinea.hasNext()) {
					check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("le uscite di una stanza."));
					stanzaPartenza = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la direzione di una uscita della stanza "+stanzaPartenza));
					dir = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la destinazione di una uscita della stanza "+stanzaPartenza+" nella direzione "+dir));
					stanzaDestinazione = scannerDiLinea.next();

					impostaUscita(stanzaPartenza, dir, stanzaDestinazione);
				}
			}
		} 
	}

	private String msgTerminazionePrecoce(String msg) {
		return "Terminazione precoce del file prima di leggere "+msg;
	}

	private void impostaUscita(String stanzaDa, String direzione, String nomeA) throws FormatoFileNonValidoException {
		check(isStanzaValida(stanzaDa),"Stanza di partenza sconosciuta "+ stanzaDa);
		check(isStanzaValida(nomeA),"Stanza di destinazione sconosciuta "+ nomeA);
		this.labirintoBuilder.addAdiacenza(stanzaDa, nomeA, Direzione.valueOf(direzione.toLowerCase()));
	}


	final private void check(boolean condizioneCheDeveEsseraVera, String messaggioErrore) throws FormatoFileNonValidoException {
		if (!condizioneCheDeveEsseraVera)
			throw new FormatoFileNonValidoException("Formato file non valido [" + this.reader.getLineNumber() + "] "+messaggioErrore);		
	}

	public Stanza getStanzaIniziale() {
		return this.labirintoBuilder.getLabirinto().getStanzaIniziale();
	}

	public Stanza getStanzaVincente() {
		return this.labirintoBuilder.getLabirinto().getStanzaVincente();
	}

}
