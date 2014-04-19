package it.uniroma3.utility;

public abstract class Utils {

	/**
	 * metodo per il calcolo del massimo comun divisore di due numeri interi
	 * 
	 * @param int x1
	 * @param int x2
	 * @return int massimo comun divisore
	 */
	public static final int gcd(int x1, int x2) {
		if (x1 < 1 || x2 < 1) {
			throw new IllegalArgumentException(
					"I parametri non possono essere "
							+ "essere negativi o uguali a zero");
		}
		int a, b, k, z;

		if (x1 > x2) {
			a = x1;
			b = x2;
		} else {
			a = x2;
			b = x1;
		}

		k = b;
		while (k != 0) {
			z = a % k;
			a = k;
			k = z;
		}
		return a;
	}
}
