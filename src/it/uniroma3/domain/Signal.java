package it.uniroma3.domain;

public class Signal {
	public Complex[] values;

	public Signal(Complex[] values) {
		this.values = values;
	}

	public Complex[] getValues() {
		return values;
	}

	public void setValues(Complex[] values) {
		this.values = values;
	}

	public String toString(){
		String s = "";
		for(int i=0; i<this.getLength(); i++)
			s+=this.values[i].toString()+", ";
		return s;
	}
	
	public int getLength(){
		return this.values.length;
	}
	
	public int hashCode() {
		return this.values.hashCode();
	}

	
	public boolean equals(Object o) {
		Signal s = (Signal)o;

		if(!(this.values.length == s.getLength()))
			return false;

		for(int i=0; i<this.values.length; i++ )
			if(!this.values[i].equals(s.getValues()[i]))
				return false;

		return true;

	}
}
