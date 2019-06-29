package github.chorman0773.gac14.util;

import java.util.function.Function;

/**
 * Provides various helper methods.
 * @author chorm
 *
 */
public interface Helpers {
	/**
	 * Returns a Function which casts a value of 1 type to another. 
	 * Note that the cast is unchecked here, and its sole intended purpose is as an argument for Stream.map.<br/>
	 * @param <T> The parameter type of the Function
	 * @param <U> The result type of the function
	 * @param cl A parameter solely used for inferring &lt;U&gt;.  
	 * @return A Function which performs an unchecked cast from T-&gt;U
	 */
	@SuppressWarnings("unchecked")
	public static <T,U> Function<T,U> castTo(Class<U> cl){
		return t->(U)t;
	}
}
