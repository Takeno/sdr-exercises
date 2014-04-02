package it.uniroma3.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SignalTest {
	
	private Signal segnaleVuoto;
	private Signal segnale;
	
	private Complex valoreUno;
	private Complex valoreDue;
	
	@Before
	public void setUp() throws Exception {

		this.valoreUno = new Complex(10, 0);
		this.valoreDue = new Complex(0, 10);
		
		this.segnaleVuoto = new Signal(new Complex[0]);
		this.segnale = new Signal(new Complex[]{ this.valoreUno, this.valoreDue });
	}

	@Test
	public void lengthTest() {
		assertEquals(0, this.segnaleVuoto.getLength());
		assertEquals(2, this.segnale.getLength());
	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void valuesTest() {
		assertEquals(this.valoreUno, this.segnale.getValues()[0]);
		assertEquals(this.valoreDue, this.segnale.getValues()[2]);
		
		// Check exception
		assertEquals(null, this.segnale.getValues()[3]);
	}
	
	@Test
	public void setValuesTest() {
		Complex c = new Complex (20, 0);
		assertEquals(this.valoreUno, this.segnale.getValues()[0]);
		Complex[] arr = this.segnale.getValues();
		arr[0] = c;
		this.segnale.setValues(arr);
		assertEquals(c, this.segnale.getValues()[0]);
	}

}
