package it.uniroma3.diadia.comandi;

import java.util.Scanner;

public class FabbricaDiComandiRiflessiva implements FabbricaDiComandi {

	@SuppressWarnings("deprecation")
	@Override
	public AbstractComando costruisciComando(String istruzione) {
		Scanner scannerDiParole = new Scanner(istruzione);
		try {
			String nomeComando = null;
			String parametro = null;
			AbstractComando comando = null;
			if (scannerDiParole.hasNext())
				nomeComando = scannerDiParole.next();//prima parola: nome del comando
			if (scannerDiParole.hasNext())
				parametro = scannerDiParole.next();//seconda parola: eventuale parametro
			try {
				String nomeClasse = "it.uniroma3.diadia.comandi.Comando";
				nomeClasse += Character.toUpperCase(nomeComando.charAt(0));
				nomeClasse += nomeComando.substring(1);
				comando = (AbstractComando)Class.forName(nomeClasse).newInstance();
				comando.setParametro(parametro);
			} catch (Exception e) {
				comando = new ComandoNonValido();
			}
			return comando; 
		} finally { scannerDiParole.close(); }
	}
}
