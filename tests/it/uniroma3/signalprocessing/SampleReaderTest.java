package it.uniroma3.signalprocessing;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import it.uniroma3.domain.Complex;
import java.io.FileNotFoundException;



public class SampleReaderTest {

	private String unexistsFile;
	private String malformedFile;
	private String wellformedFile;


	@Before
	public void setUp() throws Exception {
		this.unexistsFile = "test/404.txt";
		this.malformedFile = "test/500.txt";
		this.wellformedFile = "test/samples.txt";
	}

	@Test(expected=FileNotFoundException.class)
	public void fileNotFound() throws FileNotFoundException {
		SampleReader sReader = new SampleReader(this.unexistsFile);
	}

	@Test
	public void malformedFile() throws FileNotFoundException {
		SampleReader sReader = new SampleReader(this.malformedFile);
		assertNull(sReader.readSample());
	}

	@Test
	public void wellformedFile() throws FileNotFoundException {
		SampleReader sReader = new SampleReader(this.wellformedFile);
		Complex firtSample = sReader.readSample();
		Complex secondSample = sReader.readSample();
		Complex thirdSample = sReader.readSample();

		assertEquals(10, firtSample.getReale(), 0);
		assertEquals(25, firtSample.getImmaginaria(), 0);

		assertEquals(1.4, secondSample.getReale(), 0);
		assertEquals(2.3, secondSample.getImmaginaria(), 0);

		assertNull(thirdSample);
	}
}