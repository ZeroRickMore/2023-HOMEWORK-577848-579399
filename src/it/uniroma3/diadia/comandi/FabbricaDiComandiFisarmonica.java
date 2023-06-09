package it.uniroma3.diadia.comandi;

import java.util.Scanner;

	public class FabbricaDiComandiFisarmonica implements FabbricaDiComandi {
		@Override
		public AbstractComando costruisciComando(String istruzione) {
			Scanner scannerDiParole = new Scanner(istruzione);
			try {
			String nomeComando = null;
			String parametro = null;
			AbstractComando comando = null;
			
			if (scannerDiParole.hasNext())
				nomeComando = scannerDiParole.next();// prima parola: nome del comando
			if (scannerDiParole.hasNext())
				parametro = scannerDiParole.next(); // seconda parola: eventuale param.
			
			if (nomeComando == null)
				comando = new ComandoNonValido();
			else if (nomeComando.equals("vai"))
				comando = new ComandoVai();
			else if (nomeComando.equals("prendi"))
				comando = new ComandoPrendi();
			else if (nomeComando.equals("posa"))
				comando = new ComandoPosa();
			else if (nomeComando.equals("aiuto"))
				comando = new ComandoAiuto();
			else if (nomeComando.equals("fine"))
				comando = new ComandoFine();
			else if (nomeComando.equals("guarda"))
				comando = new ComandoGuarda();
			else comando = new ComandoNonValido();
			//Sai che comando è e hai creato la classe opportuna
			
			comando.setParametro(parametro);
			
			return comando;
			}
			finally { scannerDiParole.close(); }
		}
	}
