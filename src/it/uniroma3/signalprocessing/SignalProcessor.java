package it.uniroma3.signalprocessing;

import it.uniroma3.domain.*;

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
	
	public static double sinc(double n, double band){
		if(n == 0.0)
			return 1;
		double range = 1.0/(2.0*band);
		if (n%range == 0.0)
			return 0;
		
		double sincArgument = Math.PI * band * n;
		return Math.sin(sincArgument) / sincArgument;
	}
	
	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * NOTA BENE: il numero di campioni che deve essere passato deve essere dispari
	 * Ottimizzare il metodo come richiesto nell'homework
	 * 
	 * @param band
	 * @param numCampioni
	 * @return Segnale discreto
	 */
	
	
	
	public static Signal lowPassFilter(double band) {
		int numCampioni =  ((int)(5.0 / (2.0*band)))*2 +1;
		
		Complex[] values = new Complex[numCampioni];
		int simmetria = numCampioni / 2;
		
		for(int n = -simmetria; n <= simmetria; n++){
			double realval = 2 * band * sinc(n, band);
			values[n + simmetria] = new Complex(realval, 0);
		}
		Signal lpf = new Signal(values);
		return lpf;
	}
	
	public Signal bandFilter (double band, double portante){
		int numCampioni = ((int)(5.0 / (2.0*band)))*2 +1;
		
		Complex[] values = new Complex[numCampioni];
		int simmetria = numCampioni / 2;
		int lowerLimit = (int) (portante) - simmetria;
		int upperLimit = (int) (portante) + simmetria;
		
		for(int n = lowerLimit; n <= upperLimit; n++)
		
		return bf;
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
}