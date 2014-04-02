package it.uniroma3.domain;

public class Complex {
	private double reale;
	private double immaginaria;
	
	public Complex(double reale, double immaginaria) {
		super();
		this.reale = reale;
		this.immaginaria = immaginaria;
	}
	public double getReale() {
		return reale;
	}
	public void setReale(double reale) {
		this.reale = reale;
	}
	public double getImmaginaria() {
		return immaginaria;
	}
	public void setImmaginaria(double immaginaria) {
		this.immaginaria = immaginaria;
	}
	
	public String toString(){
		String complex = "";

		if(this.immaginaria == 0)
			complex += this.reale;
		else if(this.immaginaria < 0 && this.reale!=0) 
			complex = this.reale +" "+ this.immaginaria+" j";
		else if(this.reale==0)
			complex = this.immaginaria+" j";
		else
			complex = this.reale+" + "+ this.immaginaria+" j";

		return complex;
	}
	
	public double abs(){
		return Math.hypot(this.reale, this.immaginaria);
	}
	
	public Complex coniugato(){
		return new Complex(this.reale, - this.immaginaria);
	}
	
	public Complex somma(Complex b){
		Complex result = new Complex(
			this.reale + b.getReale(),
			this.immaginaria + b.getImmaginaria()
		);
		return result;
	}
	
	public Complex differenza(Complex b) {
		Complex result = new Complex(
			this.reale -  b.getReale(), 
			this.immaginaria - b.getImmaginaria()
		);
		return result;
	}
	
	
	public Complex prodotto(Complex b){
		Complex result = new Complex(
				this.reale * b.getReale() - this.immaginaria * b.getImmaginaria(),
				this.reale * b.getImmaginaria() + b.getReale() * this.immaginaria
		);
		return result;
	}
	
	public Complex prodottoScalare(double s){
		Complex result = new Complex(
			this.reale * s,
			this.immaginaria * s
		);
		return result;
	}
	
	
	public Complex reciproco(){
		double scalare = Math.pow(this.reale,2) + Math.pow(this.immaginaria,2);
		Complex result = new Complex(this.reale/scalare , this.immaginaria/scalare);
		return result;
	}
	
	public Complex rapporto(Complex b){
		return this.prodotto(b.reciproco());
	}
	
	
	@Override
	public int hashCode() {
		return (int) (this.reale + this.immaginaria);
	}

	@Override
	public boolean equals(Object o) {

		Complex c = (Complex) o;

		return this.reale == c.getReale() && this.immaginaria == c.getImmaginaria();
	}	 
}
