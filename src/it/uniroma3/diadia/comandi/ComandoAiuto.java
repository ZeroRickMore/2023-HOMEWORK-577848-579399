package it.uniroma3.diadia.comandi;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

@SuppressWarnings("serial")
class ComandoAiutoException extends RuntimeException {
	String msg;	
	@Override
	public String getMessage() {
		return this.msg;
	}
	
	public ComandoAiutoException(String msg) {
		this.msg = msg;
	}
}

public class ComandoAiuto extends AbstractComando {

	static final private String fineElenco = "---Finito l'elenco dei comandi!---";

	@Override 
	public void esegui(Partita partita, IO io) {
		ClassLoader classLoader = ComandoAiuto.class.getClassLoader();
		String nomeDelPackage = ComandoAiuto.class.getPackage().getName();
		URL urlDelPackage = classLoader.getResource(nomeDelPackage.replace('.', '/'));
		if (urlDelPackage == null) {
			throw new ComandoAiutoException("Non è stato possibile ottenere il package" + nomeDelPackage + " come risorsa");
		}
		
		File cartellaDelPackage = new File(urlDelPackage.getFile());
		if (!cartellaDelPackage.exists() || !cartellaDelPackage.isDirectory()) {
			throw new ComandoAiutoException("Il file corrispondente al package " + nomeDelPackage + " non esiste oppure non è una cartella");
		}
		
		File[] filesNelPackage = cartellaDelPackage.listFiles();
		if (filesNelPackage == null) {
			throw new ComandoAiutoException("Un errore relativo all'I/O è avvenuto durante la lettura dei files contenuti nel package " + nomeDelPackage);
		}
		
		List<String> listaClassi = Arrays
				.asList(filesNelPackage)
				.stream()
				.map(file -> file.getName())
				.map(nomeFile -> nomeFile.split(".class")[0]) // rimuovi l'estensione .class
				.filter(nomeFile -> !nomeFile.endsWith("Test"))
				.filter(nomeFile -> !nomeFile.endsWith("Exception"))
				.filter(nomeFile -> nomeFile.startsWith("Comando"))
				.filter(nomeFile -> !nomeFile.equals("ComandoNonValido"))
				.filter(nomeFile -> !nomeFile.equals("ComandoFake"))
				.map(nomeFile -> nomeFile.split("Comando")[1])
				.map(nomeFile -> nomeFile.toLowerCase())
				.collect(Collectors.toList());
		
		for(String i : listaClassi) {
			io.mostraMessaggio(i);
		}
		io.mostraMessaggio(fineElenco);
	}

	public String getFineElenco() {
		return fineElenco;
	}



}
