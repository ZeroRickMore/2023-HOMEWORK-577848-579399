package it.uniroma3.diadia;
import java.util.Scanner;

public class IOConsole implements IO{
	
	/**
	 * Mostra il messaggio inserito come parametro.
	 * 
	 * @param Messaggio da mostrare
	 */
	
	public void mostraMessaggio(String msg) {
		System.out.println(msg);
	}
	
	/**
	 * Legge la riga inserita in input
	 */
	
	public String leggiRiga() {
		Scanner scannerDiLinee = new Scanner(System.in);
		String riga = scannerDiLinee.nextLine();
		//scannerDiLinee.close(); //"Omettere questa riga, ci torneremo sopra"
		return riga;
	}

}
