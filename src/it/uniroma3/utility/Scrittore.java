package it.uniroma3.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Scrittore {
	private Writer out;
	private OutputStreamWriter osw;
	private FileOutputStream fos;
	private File file;	
	
	public Scrittore(String pathIn){
		try{	
			file = new File(pathIn);
			fos  = new FileOutputStream(file);
			osw  = new OutputStreamWriter(fos);
			out  = new BufferedWriter(osw);
		} catch (Exception e){
			System.err.println("Si e' verificato un problema nella creazione dell'oggetto Scrittore");
			e.printStackTrace();
		}
	}
	
	public void stampa (Double[] campioni){
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.err.println("Si e' verificato un errore nella creazione del file");
			e.printStackTrace();
		}
		try{
			for(Double campione : campioni)
				out.write(campione.toString()+'\n');
		} catch (IOException ie){
				System.err.println("Si e' verificato un errore nella scrittura dei campioni");
				ie.printStackTrace();
		}
	}
	
	public void close(){
		try{
			this.out.close();
		} catch(IOException ie){
			System.err.println("Si e' verificato un errore nella chiusura dell'oggetto Scrittore");
			ie.printStackTrace();
		}
	}
	

}
