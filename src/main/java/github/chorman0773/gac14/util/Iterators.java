package github.chorman0773.gac14.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface Iterators {
	/**
	 * Returns an Empty Iterator.
	 * the hasNext() method always returns false and next throws a NoSuchElementException uncondtionally.<br/>
	 * It is unspecified if each instance of this call returns the same iterator or a distinct one.
	 * @param <E> The element type of the iterator.
	 */
	public static <E> Iterator<E> empty(){
		return new Iterator<E>() {

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public E next() {
				throw new NoSuchElementException("Empty Iterator");
			}
			
		};
	}
}
