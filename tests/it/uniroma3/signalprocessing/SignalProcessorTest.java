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
		// http://www.wolframalpha.com/input/?i=sinc%280.42+*+0.25+*+pi%29
		assertEquals(0.9819, SignalProcessor.sinc(0.42, band), 0.0001);
	}
	
	@Test
	public void costruzioneFiltroPassaBasso() {
		Signal filter = SignalProcessor.lowPassFilter(0.25);
		for(Complex c : filter.getValues())
			System.out.println(c);
//		assertEquals()
	}
}
