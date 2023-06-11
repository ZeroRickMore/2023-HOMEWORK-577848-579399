package it.uniroma3.diadia.ambienti;

public enum Direzione {
	
	nord, est, sud, ovest;
	
	public Direzione getDirezioneOpposta() {
		switch (this) {
		case nord : return sud;
		case est : return ovest;
		case ovest : return est;
		case sud : return nord;
		default: throw new IllegalArgumentException("Direzione non valida");
		}
	}
	
}
