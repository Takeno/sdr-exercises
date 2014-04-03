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
		if(n == 0D)
			return 1;
		
		double range = 1D/(2D * band);
		if (n % range == 0D)
			return 0D;
		
		double sincArgument = Math.PI * 2 * band * n;
		return Math.sin(sincArgument) / sincArgument;
	}
	
	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * 
	 * @param band
	 * @param numCampioni
	 * @return Segnale discreto
	 */
	public static Signal lowPassFilter(double band, int numCampioni) {
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		for(int n = - simmetria; n <= simmetria; n++){
			double realval = 2 * band * SignalProcessor.sinc(n, band);
			values[n + simmetria] = new Complex(realval);
		}
		
		return new Signal(values);
	}

	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * 
	 * @param band
	 * @return Segnale discreto
	 */
	public static Signal lowPassFilter(double band) {
		double ampiezzaConsiderata = 5D / (2D * band);
		int numCampioni = ((int)ampiezzaConsiderata)*2 +1;
		
		return SignalProcessor.lowPassFilter(band, numCampioni);
	}
	
	//	public Signal bandFilter (double band, double portante){
	//	int numCampioni = ((int)(5.0 / (2.0*band)))*2 +1;
	//	
	//	Complex[] values = new Complex[numCampioni];
	//	int simmetria = numCampioni / 2;
	//	int lowerLimit = (int) (portante) - simmetria;
	//	int upperLimit = (int) (portante) + simmetria;
	//	
	//	for(int n = lowerLimit; n <= upperLimit; n++)
	//	
	//	return bf;
	//}
	
	public static Signal bandpassFilter (double band, double portante) {
		Complex[] filterValues = SignalProcessor.lowPassFilter(band).getValues();
		Complex expTranslazione;
		
		int simmetria = filterValues.length / 2;
		
		for(int n = -simmetria; n < simmetria; n++) {
			expTranslazione = (new Complex(0, 2 * portante * n)).exp();
			filterValues[n + simmetria] = filterValues[n + simmetria].prodotto(expTranslazione);
		}
		
		return new Signal(filterValues);
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