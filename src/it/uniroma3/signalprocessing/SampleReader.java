package it.uniroma3.signalprocessing;

import it.uniroma3.domain.Complex;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class SampleReader {
	private BufferedReader reader;

	public SampleReader(String filename) throws FileNotFoundException {
		FileReader file = new FileReader(filename);
		this.reader = new BufferedReader(file);
	}

	public Complex readSample() {
		try {
			String currentLine = this.reader.readLine();
			if(currentLine == null)
				return null;

			String[] parts = currentLine.split("\t");

			return new Complex(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
		} catch(NumberFormatException e) {
			return null;
		} catch(IOException e) {
			return null;
		}
	}
}