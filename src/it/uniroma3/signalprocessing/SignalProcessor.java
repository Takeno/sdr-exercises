package it.uniroma3.signalprocessing;

import it.uniroma3.domain.*;
import it.uniroma3.utility.Utils;
import it.uniroma3.utility.DiscreteInterpolator;

public class SignalProcessor {
	public static double[] convoluzione(double[] v1, double[] v2) {
		int lunghezzaFinale = v1.length + v2.length - 1,
			upperBound = 0,
			lowerBound = 0,
			k, j;
		
		double[] result = new double[lunghezzaFinale];
		
		for(k = 0; k < lunghezzaFinale; k++){
			upperBound = Math.min(k, v2.length -1);
			lowerBound = Math.max(0, k - v1.length + 1);
			
			for(j = lowerBound; j <= upperBound; j++)
				result[k] += v1[k-j] * v2[j];
		}
		
		return result;
	}
	
	public static Complex[] convoluzione(Complex[] v1, Complex[] v2) {
		int lunghezzaFinale = v1.length + v2.length - 1,
			upperBound = 0,
			lowerBound = 0,
			k, j;
		
		Complex[] result = new Complex[lunghezzaFinale];
		
		for(k = 0; k < lunghezzaFinale; k++){
			upperBound = Math.min(k, v2.length -1);
			lowerBound = Math.max(0, k - v1.length + 1);
			result[k] = new Complex(0, 0);
			for(j = lowerBound; j <= upperBound; j++)
				result[k] = result[k].somma(v1[k-j].prodotto(v2[j]));
		}
		
		return result;
	}
	
	/**
	 * Restituisce l'n-esimo campione di una sinc di banda band
	 * 
	 * @param n
	 * @param band
	 * @return valore campione
	 */
	
	public static double sinc(double n, double band){
		if(n == 0D)
			return 1;
		
		double range = 1D/(2D * band);
		if (n % range == 0D)
			return 0D;
		
		double sincArgument = Math.PI * 2D * band * n;
		return Math.sin(sincArgument) / sincArgument;
	}


	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * con numero di campioni dati in input
	 * e lo interpola con un interpolatore
	 * 
	 * @param double band
	 * @param int numCampioni
	 * @param DiscreteInterpolator<Double> interpolator
	 * @return Segnale discreto
	 */
	
	public static Signal lowPassFilter(double band, int numCampioni, DiscreteInterpolator<Double> interpolator) {
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		for(int n = 0; n <= simmetria; n++){
			double realval = 2 * band * SignalProcessor.sinc(n, band);
			realval = interpolator.calc(realval, n);
			values[n + simmetria] = new Complex(realval);
			values[-n + simmetria] = new Complex(realval);
		}
		
		return new Signal(values);
	}
	
	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * con numero di campioni dati in input
	 * 
	 * @param band
	 * @param numCampioni
	 * @return Segnale discreto
	 */
	
	public static Signal lowPassFilter(double band, int numCampioni) {
		return SignalProcessor.lowPassFilter(band, numCampioni,
				new DiscreteInterpolator<Double>() {
					public Double calc(Double oldValue, int index) {
						return oldValue;
					}
				});
	}

	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * con numero di campioni calcolati
	 * 
	 * @param band
	 * @return Segnale discreto
	 */
	
	public static Signal lowPassFilter(double band) {
		double ampiezzaConsiderata = 5D / (2D * band);
		int numCampioni = ((int)ampiezzaConsiderata)*2 + 1;
		
		return SignalProcessor.lowPassFilter(band, numCampioni);
	}
	
	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * con numero di campioni calcolati e interpolato secondo un DiscreteInterpolator
	 * 
	 * @param band
	 * @param DiscreteInterpolator<Double> interpolator
	 * @return Segnale discreto
	 */
	
	public static Signal lowPassFilter(double band, DiscreteInterpolator<Double> interpolator) {
		double ampiezzaConsiderata = 5D / (2D * band);
		int numCampioni = ((int)ampiezzaConsiderata)*2 + 1;
		
		return SignalProcessor.lowPassFilter(band, numCampioni, interpolator);
	}
	
	public static Signal bandPassFilter (double band, final double portante) {
		double ampiezzaConsiderata = 5D / (2D * band);
		int numCampioni = ((int)ampiezzaConsiderata)*2 +1;
		
		return SignalProcessor.lowPassFilter(band, numCampioni,
				new DiscreteInterpolator<Double>() {
					public Double calc(Double oldValue, int index) {
						return oldValue * 2 * Math.cos(2 * Math.PI * portante * index);
					}
				});
	}

	/**
	 * Operazione di convoluzione fra segnali:
	 * implementa un'operazione di convoluzione discreta fra due segnali passati come parametro.
	 * Presuppone che il segnale d'ingresso abbia parte reale e immaginaria non nulle 
	 * e che il filtro abbia solo parte reale.
	 * @param segnaleIn
	 * @param rispImpulsivaFiltro
	 * @return
	 */
	
	public static Signal convoluzione(Signal segnaleIn, Signal rispImpulsivaFiltro){
		
		Complex[] values = convoluzione(segnaleIn.getValues(), rispImpulsivaFiltro.getValues());
		Signal signal = new Signal(values);
		
		return signal;
	}

	/**
	 * dati T1 e T2 vengono calcolati i valori dei parametri F1 ed F2
	 * @param T1
	 * @param T2
	 * @return array contenente F1 ed F2
	 */
	
	public static int[] getParameters(int t1, int t2){
		int gcd = Utils.gcd(t1, t2);
		int[] fArray = {t1/gcd, t2/gcd};
		return fArray;
	}

	/**
	 * Operazione di espansione di un segnale
	 * dato un segnale e un fattore di espansione viene creato un nuovo segnale
	 * espanso con valori nulli.
	 *
	 * @param Signal segnaleIn
	 * @param int fattore
	 * @return Signal espanso
	 */

	public static Signal espansione(Signal segnaleIn, int fattore) {

		// TODO: verificare side effect
		// sto tornando lo stesso oggetto e non un oggetto uguale ma diverso
		// mi va bene?
		if(fattore == 1)
			return segnaleIn;

		// array valori attuali
		Complex[] values = segnaleIn.getValues();
		// array per i nuovi valori
		Complex[] newValues = new Complex[ values.length * fattore ];
		// indice di iterazione su values
		int j = 0;

		for(int i = 0; i < newValues.length; i++)
			if(i % fattore == 0)
				newValues[i] = values[j++];
			else
				newValues[i] = new Complex();

		return new Signal(newValues);
	}
	
	/**
	 * Filtro interpolatore eseguito su una sequenza espansa, in base al parametro F1
	 * @param segnaleIn
	 * @param F1
	 * @return segnale interpolato
	 */
	
	public static Signal interpolazione(Signal segnaleIn, final int F1){
		if(F1 == 1) return segnaleIn;
		
		double band = 1D/ (2D * F1);
		
		Signal lpf = SignalProcessor.lowPassFilter(band, new DiscreteInterpolator<Double>() {
			public Double calc(Double oldValue, int index) {
				return oldValue * F1;
			}
		});
				
		
		int n = (lpf.getLength() -1)/2, j = 0;
		Complex[] val = new Complex[segnaleIn.getLength()];
		
		Signal si = SignalProcessor.convoluzione(segnaleIn, lpf);
		
		for(int i = n; i < si.getLength() - n; i++){
			val[j] = si.getValues()[i];
			j++;
		}
		
		
		return new Signal(val);
	}

	/**
	 * Operazione di decimazione di un segnale
	 * dato un segnale e un fattore di decimazione viene creato un nuovo segnale
	 * decimato.
	 *
	 * @param Signal segnaleIn
	 * @param int fattore
	 * @return Signal decimato
	 */

	public static Signal decimatore(Signal segnaleIn, int fattore) {

		// TODO: controllare side effect
		if(fattore == 1)
			return segnaleIn;

		// valori attuali
		Complex[] values = segnaleIn.getValues();
		// array nuovi valori
		Complex[] newValues = new Complex[ values.length / fattore ];
		// indice per iterare sui nuovi valori
		int j = 0;


		/*
			Non ho bisogno di iterare su tutti i valori,
			devo prendere solo quelli tali che (i % fattore == 0).
			Ciò significa, partendo da i = 0, i+= fattore.
		 */
		for(int i = 0; i < values.length; i += fattore)
			newValues[j++] = values[i]; 

		return new Signal(newValues);
	}
	
	/** Operazione per il cambio di tasso di campionamento
	 *  @param T1
	 *  @param T2
	 *  @param signaleIn
	 *  @return segnale campionato con tasso T2
	 */
	
	public static Signal cambioTasso (int T1, int T2, Signal signalIn){
		int[] fattori = SignalProcessor.getParameters(T1, T2);
		Signal newSignal;
		
		newSignal = SignalProcessor.espansione(signalIn, fattori[0]);
		newSignal = SignalProcessor.interpolazione(newSignal, fattori[0]);
		newSignal = SignalProcessor.decimatore(newSignal, fattori[1]);
		
		return newSignal;
	}
}