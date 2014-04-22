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
	
	private Signal filtroPassaBasso;
	private Signal segnaleProva;

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
		
		this.segnaleProva = new Signal(vettoreComplessoTre);
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
	public void filtroInterpolatore(){
		int fattore = 2;
		Signal interpolato = SignalProcessor.interpolazione(this.segnaleProva, fattore);
		assertEquals(this.segnaleProva.getValues().length, interpolato.getValues().length);
		
		
		Complex zero = new Complex();
		
		assertEquals(this.segnaleProva.getValues()[0], interpolato.values[0]);
		assertFalse(interpolato.getValues()[1].equals(zero));
		assertEquals(this.segnaleProva.getValues()[2], interpolato.values[2]);
		assertFalse(interpolato.getValues()[3].equals(zero));
		assertEquals(this.segnaleProva.getValues()[4], interpolato.values[4]);
		assertFalse(interpolato.getValues()[5].equals(zero));
	}
}
