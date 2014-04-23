package it.uniroma3.utility;

public interface DiscreteInterpolator<T> {
	public T calc(T oldValue, int index);
}
