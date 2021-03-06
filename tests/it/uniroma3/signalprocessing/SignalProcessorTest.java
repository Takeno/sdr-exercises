package it.uniroma3.signalprocessing;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import it.uniroma3.domain.*;

public class SignalProcessorTest {
	
	private double[] vettoreRealeUno;
	private double[] vettoreRealeDue;
	
	private Complex[] vettoreComplessoUno;
	private Complex[] vettoreComplessoDue;
	private Complex[] vettoreComplessoTre;
	private Complex[] vettoreComplessoQuattro;
	
	private Signal segnaleUno;	
	private Signal segnaleDue;
	private Signal segnaleTre;
	private Signal segnaleQuattro;
	
	private Signal filtroPassaBasso;

	@Before
	public void setUp() throws Exception {
		this.vettoreRealeUno = new double[] {3,2,1};
		this.vettoreRealeDue = new double[] {1,1,2,1};
		
		this.vettoreComplessoUno = new Complex[] {
			new Complex(3,0),
			new Complex(2,0),
			new Complex(1,0)
		};
		
		this.vettoreComplessoDue = new Complex[] {
			new Complex(1,0),
			new Complex(1,0),
			new Complex(2,0),
			new Complex(1,0)
		};
		
		this.vettoreComplessoTre = new Complex[]{
				new Complex(1,0),
				new Complex(0,0),
				new Complex(2,0),
				new Complex(0,0),
				new Complex(1,0),
				new Complex(0,0)
			};
		
		this.vettoreComplessoQuattro = new Complex[]{
				new Complex(3,0),
				new Complex(0,0)
		};
		
		this.segnaleUno     = new Signal(this.vettoreComplessoUno);	
		this.segnaleDue     = new Signal(this.vettoreComplessoDue);	
		this.segnaleTre     = new Signal(this.vettoreComplessoTre);
		this.segnaleQuattro = new Signal(this.vettoreComplessoQuattro);
	}

	
	@Test
	public void convoluzioneReale() {
		double[] vettoreRisultato = SignalProcessor.convoluzione(this.vettoreRealeUno, this.vettoreRealeDue);
		
		assertEquals(3, vettoreRisultato[0], 0);
		assertEquals(5, vettoreRisultato[1], 0);
		assertEquals(9, vettoreRisultato[2], 0);
		assertEquals(8, vettoreRisultato[3], 0);
		assertEquals(4, vettoreRisultato[4], 0);
		assertEquals(1, vettoreRisultato[5], 0);
	}
	
	@Test
	public void convoluzioneComplessa() {
		Complex[] vettoreRisultato = SignalProcessor.convoluzione(this.vettoreComplessoUno, this.vettoreComplessoDue);
		
		assertEquals(3, vettoreRisultato[0].getReale(), 0);
		assertEquals(5, vettoreRisultato[1].getReale(), 0);
		assertEquals(9, vettoreRisultato[2].getReale(), 0);
		assertEquals(8, vettoreRisultato[3].getReale(), 0);
		assertEquals(4, vettoreRisultato[4].getReale(), 0);
		assertEquals(1, vettoreRisultato[5].getReale(), 0);
	}
	
	@Test
	public void sinc() {
		double band = 0.25;
		assertEquals(1, SignalProcessor.sinc(0, 20), 0);
		assertEquals(0, SignalProcessor.sinc(1.0/(2.0*band), band), 0);

		// Me lo ha detto Wolfram
		// http://www.wolframalpha.com/input/?i=sinc%280.42+*+0.5+*+pi%29
		assertEquals(0.9290, SignalProcessor.sinc(0.42, band), 0.0001);
	}
	
	@Test
	public void lowPassFilter() {
		double band = 0.25;
		Signal filter = SignalProcessor.lowPassFilter(band);
		int simmetria = filter.getValues().length / 2;
		
		assertEquals(0, filter.getValues()[simmetria-2].abs(), 0);
		assertEquals(0.3183, filter.getValues()[simmetria-1].abs(), 0.0001);
		assertEquals(0.5, filter.getValues()[simmetria].abs(), 0);
		assertEquals(0.3183, filter.getValues()[simmetria+1].abs(), 0.0001);
		assertEquals(0, filter.getValues()[simmetria+2].abs(), 0);

	}
	
	@Test
	public void bandPassFilter() {
		double band = 0.25,
			   portante = 3;
		
		Signal filter = SignalProcessor.bandPassFilter(band, portante);
		int simmetria = filter.getValues().length / 2;
		
		// 2 * B * sinc(2*pi*B*n) * 2 * cos(2 * pi * f0 * n)

		assertEquals(0, filter.getValues()[simmetria-2].getReale(), 0);
		assertEquals(0.6366, filter.getValues()[simmetria-1].getReale(), 0.0001);
		assertEquals(4*band, filter.getValues()[simmetria].getReale(), 0);
		assertEquals(0.6366, filter.getValues()[simmetria+1].getReale(), 0.0001);
		assertEquals(0, filter.getValues()[simmetria+2].getReale(), 0);
	}
	
	
	@Test
	public void decimatoreFattoreDue() {
		int fattore = 2;
		Signal decimato = SignalProcessor.decimatore(this.segnaleDue, fattore);
		assertEquals(this.segnaleDue.getValues().length / fattore, decimato.getValues().length);
		
		assertEquals(this.segnaleDue.getValues()[0], decimato.values[0]);
		assertEquals(this.segnaleDue.getValues()[2], decimato.values[1]);
	}
	
	@Test
	public void decimatoreFattoreUno() {
		int fattore = 1;
		Signal decimato = SignalProcessor.decimatore(this.segnaleDue, fattore);
		assertEquals(this.segnaleDue.getValues().length, decimato.getValues().length);
		
		assertEquals(this.segnaleDue.getValues()[0], decimato.values[0]);
		assertEquals(this.segnaleDue.getValues()[1], decimato.values[1]);
		assertEquals(this.segnaleDue.getValues()[2], decimato.values[2]);
		assertEquals(this.segnaleDue.getValues()[3], decimato.values[3]);
	}
	
	@Test
	public void getParameters(){
		int tArray[] = {18,24};
		int fArray[] = SignalProcessor.getParameters(tArray[0], tArray[1]);
		assertEquals(3, fArray[0]);
		assertEquals(4, fArray[1]);
	}	
	@Test
	public void espansoreFattoreDue() {
		int fattore = 2;
		Signal espanso = SignalProcessor.espansione(this.segnaleUno, fattore);
		assertEquals(this.segnaleUno.getValues().length * fattore, espanso.getValues().length);
		
		Complex zero = new Complex();
		
		assertEquals(this.segnaleUno.getValues()[0], espanso.values[0]);
		assertEquals(zero, espanso.getValues()[1]);
		
		assertEquals(this.segnaleUno.getValues()[1], espanso.values[2]);
		assertEquals(zero, espanso.getValues()[3]);
		
		assertEquals(this.segnaleUno.getValues()[2], espanso.values[4]);
		assertEquals(zero, espanso.getValues()[5]);
	}
	
	@Test
	public void espansoreFattoreUno() {
		int fattore = 1;
		Signal espanso = SignalProcessor.espansione(this.segnaleUno, fattore);
		assertEquals(this.segnaleUno.getValues().length, espanso.getValues().length);
		
		assertEquals(this.segnaleUno.getValues()[0], espanso.values[0]);
		assertEquals(this.segnaleUno.getValues()[1], espanso.values[1]);
		assertEquals(this.segnaleUno.getValues()[2], espanso.values[2]);
	}
	
	@Test
	public void interpolatoreFattoreUno(){
		int fattore = 1;
		Signal interpolato = SignalProcessor.interpolazione(this.segnaleTre, fattore);
		
		assertEquals(this.segnaleTre.getValues().length, interpolato.getValues().length);
		
		assertEquals(this.segnaleTre.getValues()[0], interpolato.values[0]);
		assertEquals(this.segnaleTre.getValues()[1], interpolato.values[1]);
		assertEquals(this.segnaleTre.getValues()[2], interpolato.values[2]);
		assertEquals(this.segnaleTre.getValues()[3], interpolato.values[3]);
		assertEquals(this.segnaleTre.getValues()[4], interpolato.values[4]);
		assertEquals(this.segnaleTre.getValues()[5], interpolato.values[5]);
		
	}
	
	@Test
	public void interpolatoreFattoreDue(){
		int fattore = 2;
		Signal interpolato = SignalProcessor.interpolazione(this.segnaleTre, fattore);
		
		assertEquals(this.segnaleTre.getValues().length, interpolato.getValues().length);
		
		
		assertEquals(this.segnaleTre.getValues()[0], interpolato.values[0]);
		assertEquals(1.6976, interpolato.values[1].getReale(), 0.0001);
		assertEquals(this.segnaleTre.getValues()[2], interpolato.values[2]);
        assertEquals(1.6976, interpolato.values[3].getReale(), 0.0001);
		assertEquals(this.segnaleTre.getValues()[4], interpolato.values[4]);
        assertEquals(0.3395, interpolato.values[5].getReale(), 0.0001);
	}
	
	@Test
	public void cambioTassoFattoriUno(){
		int T1 = 1, T2 = 1;
		Signal segnaleNuovoTasso = SignalProcessor.cambioTassoCampionamento(T1, T2, this.segnaleUno);
		
		assertEquals(this.segnaleUno.getValues().length, segnaleNuovoTasso.getValues().length);
		
		assertEquals(this.segnaleUno.getValues()[0], segnaleNuovoTasso.getValues()[0]);
		assertEquals(this.segnaleUno.getValues()[1], segnaleNuovoTasso.getValues()[1]);
		assertEquals(this.segnaleUno.getValues()[2], segnaleNuovoTasso.getValues()[2]);
	}
	
	@Test
	public void cambioTassoFattoriNonUno(){
		int T1 = 10, T2 = 15;
		Signal segnaleNuovoTasso = SignalProcessor.cambioTassoCampionamento(T1, T2, this.segnaleUno);
		
		assertEquals(this.segnaleQuattro.getValues().length, segnaleNuovoTasso.getValues().length);
		
		assertEquals(this.segnaleQuattro.getValues()[0], segnaleNuovoTasso.getValues()[0]);
		assertEquals(1.2732, segnaleNuovoTasso.getValues()[1].getReale(), 0.0001);		
	}
}
