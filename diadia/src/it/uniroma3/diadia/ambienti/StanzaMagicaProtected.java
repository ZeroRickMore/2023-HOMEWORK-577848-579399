package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.*;

public class StanzaMagicaProtected extends StanzaProtected {
	final static protected int SOGLIA_MAGICA_DEFAULT = 3;
	protected int contatoreAttrezziPosati;
	protected int sogliaMagica;

	public StanzaMagicaProtected(String nome) {
		this(nome, SOGLIA_MAGICA_DEFAULT);		//chiamo il costruttore con parametri nome e soglia
	}

	public StanzaMagicaProtected(String nome, int soglia) { 
		super(nome);
		this.contatoreAttrezziPosati = 0;
		this.sogliaMagica = soglia;
	}

	private Attrezzo modificaAttrezzo(Attrezzo attrezzo) {		//modifica il nome dell'attrezzo invertendolo e ne raddoppia il peso
		StringBuilder nomeInvertito;
		int pesoX2 = attrezzo.getPeso()*2;
		nomeInvertito = new StringBuilder(attrezzo.getNome());
		nomeInvertito = nomeInvertito.reverse();
		attrezzo = new Attrezzo(nomeInvertito.toString(), pesoX2);
		return attrezzo;
	}

	@Override
	public boolean addAttrezzo(Attrezzo attrezzo) {
		this.contatoreAttrezziPosati++;
		if (this.contatoreAttrezziPosati>this.sogliaMagica)
			attrezzo = this.modificaAttrezzo(attrezzo);
		if(attrezzo==null)
			return false;
		return this.attrezzi.putIfAbsent(attrezzo.getNome(), attrezzo)==null;
	}

}
