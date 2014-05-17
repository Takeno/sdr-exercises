package it.uniroma3.utility;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ScrittoreTest {

	private Scrittore scrittore;
	private final String path1 = "tests/it/uniroma3/utility/campioni1.txt";
	private Double[] campioni;
	private BufferedReader reader;
	private File file;
    String[] lines = new String[3];
	
	@Before
	public void setUp(){

		this.file = new File(this.path1);
		this.scrittore = new Scrittore(this.path1);
		this.campioni = new Double[] {12.3D, 6D, 2.1D};
	}
	
	@Test
	public void testScriviCampioni() {
		scrittore.scriviCampioni(this.campioni);
		scrittore.close();
		
		try{
			this.reader = new BufferedReader(new FileReader(file));
			for(int i = 0; i < 3; i++)
				lines[i] = this.reader.readLine();
		}catch(Exception e){}
		
		assertEquals(true, file.isFile());
		assertEquals(this.campioni[0].doubleValue(), Double.parseDouble(lines[0]), 0);
		assertEquals(this.campioni[1].doubleValue(), Double.parseDouble(lines[1]), 0);
		assertEquals(this.campioni[2].doubleValue(), Double.parseDouble(lines[2]), 0);
		
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
