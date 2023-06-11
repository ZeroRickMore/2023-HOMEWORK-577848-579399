package it.uniroma3.diadia;
import java.util.Scanner;

public class IOConsole implements IO{

	private Scanner sc;

	/**
	 * Mostra il messaggio inserito come parametro.
	 * 
	 * @param Messaggio da mostrare
	 */
	public IOConsole(Scanner sc) {
		this.sc = sc;
	}
	
	
	public void mostraMessaggio(String msg) {
		System.out.println(msg);
	}

	/**
	 * Legge la riga inserita in input
	 */

	public String leggiRiga() {
			String riga = sc.nextLine();
			return riga; 
	}

}
