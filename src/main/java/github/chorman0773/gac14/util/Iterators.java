package github.chorman0773.gac14.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface Iterators {
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
