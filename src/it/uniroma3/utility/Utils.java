package it.uniroma3.utility;
/**
 * metodo per il calcolo del massimo comun divisore di due numeri interi
 * @param x1
 * @param x2
 * @return massimo comun divisore
 */
public abstract class Utils {
	 public static final int gcd(int x1,int x2) {
	      if(x1<1 || x2<1) {
	          throw new IllegalArgumentException("I parametri non possono essere "
	        		  							  + "essere negativi o uguali a zero");
	      }
	      int a,b,k,z;

	      if(x1>x2) {
	          a = x1;
	          b = x2;
	      } else {
	          a = x2;
	          b = x1;
	      }

	      k = b;
	      while (k!=0) {
	          z= a%k;
	          a = k;
	          k = z;
	      }
	      return a;
	  }
}
