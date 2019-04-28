package github.chorman0773.gac14.util;

import java.util.Comparator;
import java.util.function.Function;

public interface Comparators {
	public static <T,U> Comparator<U> with(Comparator<? super T> comp,Function<? super U,? extends T> func){
		return (a,b)->comp.compare(func.apply(a), func.apply(b));
	}
}
