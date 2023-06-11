package it.uniroma3.diadia;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IOSimulator implements IO {

	private Map<String, String> rigaLetta2msgProdotti;
	private List<String> comandiDaIniettare;
	private List<String> comandiStampati;
	private int indiceAttuale;
	private String comandoAppenaLetto;

	public IOSimulator () {
		this.rigaLetta2msgProdotti = new HashMap<>();	
		this.comandiDaIniettare = new ArrayList<>();
		this.indiceAttuale = 0;
		this.comandoAppenaLetto = null;
		this.comandiStampati = new ArrayList<>();
	}

	@Override
	public void mostraMessaggio(String messaggio) {
		this.rigaLetta2msgProdotti.put(comandoAppenaLetto, messaggio);
		this.comandiStampati.add(messaggio);
	}

	@Override
	public String leggiRiga() {
		this.comandoAppenaLetto = comandiDaIniettare.get(this.indiceAttuale);
		Scanner scannerDiLinee = new Scanner(this.comandoAppenaLetto);
		try {
		String riga = scannerDiLinee.nextLine();
		return riga;
		} finally { scannerDiLinee.close(); }
	}

	public Map<String, String> getRigaLetta2msgProdotti() {
		return rigaLetta2msgProdotti;
	}

	public List<String> getComandiDaIniettare() {
		return comandiDaIniettare;
	}

	public List<String> getComandiStampati() {
		return comandiStampati;
	}

	public int getIndiceAttuale() {
		return indiceAttuale;
	}

	public String getComandoAppenaLetto() {
		return comandoAppenaLetto;
	}

	public void setRigaLetta2msgProdotti(Map<String, String> rigaLetta2msgProdotti) {
		this.rigaLetta2msgProdotti = rigaLetta2msgProdotti;
	}

	public void setComandiDaIniettare(List<String> comandiDaIniettare) {
		this.comandiDaIniettare = comandiDaIniettare;
	}

	public void setComandiStampati(List<String> comandiStampati) {
		this.comandiStampati = comandiStampati;
	}

	public void setIndiceAttuale(int indiceAttuale) {
		this.indiceAttuale = indiceAttuale;
	}

	public void setComandoAppenaLetto(String comandoAppenaLetto) {
		this.comandoAppenaLetto = comandoAppenaLetto;
	}
}
