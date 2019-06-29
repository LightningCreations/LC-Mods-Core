package github.chorman0773.gac14.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public interface Sets {
	/**
	 * Computes the Union of 2 sets.
	 * The result is a Set which is the Union of s1 and s2. <br/>
	 * The set is immutable. It is unspecified if modifying either s1 or s2 after this operation will be reflected in the result. <br/>
	 * <br/>
	 * If s1 is a subset of s2, then the result equals s2.<br/>
	 * If s2 is a subset of s1, then the result equals s1.<br/>
	 * If both sets are the empty set, then the result is the empty set.<br/>
	 */
	public static <E> Set<E> union(final Set<? extends E> s1,final Set<? extends E> s2){
		if(s1.isEmpty()&&s2.isEmpty())
			return Collections.emptySet();
		else if(s1.isEmpty())
			return Collections.unmodifiableSet(s2);
		else if(s2.isEmpty())
			return Collections.unmodifiableSet(s1);
		else
			return new AbstractSet<E>() {
				private Stream<E> stream = Stream.concat(s1.stream(), s2.stream()).distinct();
				@Override
				public Iterator<E> iterator() {
					// TODO Auto-generated method stub
					return stream.iterator();
				}
	
				@Override
				public int size() {
					// TODO Auto-generated method stub
					return (int)stream.count();
				}
	
				@Override
				public boolean isEmpty() {
					// TODO Auto-generated method stub
					return false;
				}
	
				@Override
				public boolean contains(Object o) {
					// TODO Auto-generated method stub
					return s1.contains(o)||s2.contains(o);
				}
	
				@Override
				public boolean containsAll(Collection<?> c) {
					if(c.isEmpty())
						return true;
					return !c.stream().anyMatch(e->!this.contains(e));
				}
				
			};
	}
	
	/**
	 * Computes the intersection between s1 and s2. <br/>
	 * The result is an immutable set. It is unspecified if modifying s1 or s2 will affect the result set<br/>
	 * <br/>
	 * If s1 and s2 are equal, the result is equal to both<br/>
	 * If s1 is a subset of s2, then the result is equal to s1.<br/>
	 * If s2 is a subset of s1, then the result is equal to s2.<br/>
	 * If s1 is neither a subset nor superset of s2, the result is the empty set <br/>
	 */
	public static <E> Set<E> intersection(Set<? extends E> s1,Set<? extends E> s2){
		return new AbstractSet<E>() {
			private Stream<E> stream = Stream.concat(s1.stream(), s2.stream()).filter(e->s1.contains(e)&&s2.contains(e));
			@Override
			public Iterator<E> iterator() {
				// TODO Auto-generated method stub
				return stream.iterator();
			}

			@Override
			public int size() {
				// TODO Auto-generated method stub
				return (int)stream.count();
			}

			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return stream.anyMatch(p->true);
			}

			@Override
			public boolean contains(Object o) {
				// TODO Auto-generated method stub
				return s1.contains(o)&&s2.contains(o);
			}

			@Override
			public boolean containsAll(Collection<?> c) {
				if(c.isEmpty())
					return true;
				return !c.stream().anyMatch(e->!this.contains(e));
			}
		};
	}
	
	/**
	 * Computes the Disjunction or Symmetric Difference between s1 and s2. <br/>
	 * The result is an immutable set. It is unspecified as to whether modifying s1 or s2 affects the result set. <br/>
	 * <br/>
	 * If s1 is equal to s2, then the result is the empty set.<br/>
	 * If s1 is a subset of s2, then the result is the strict subset of s2, r, which contains no elements of s1, such that union(s1,r) equals s2.
	 * If s2 is a subset of s1, then the result is the strict subset of s1, r, which contains no elements of s2, such that union(s2,r) equals s2.
	 * If s1 is not a subset or superset of s2, then the result is the same as union(s1,s2)<br/>
	 * The result of disjunction(s1,s2) is r, such that disjunction(s1,r) is s2 and disjunction(s2,r) is s1<br/>
	 */
	public static <E> Set<E> disjunction(Set<? extends E> s1,Set<? extends E> s2){
		return new AbstractSet<E>() {
			private Stream<E> stream = Stream.concat(s1.stream(), s2.stream()).filter(e->!(s1.contains(e)&&s2.contains(e)));
			@Override
			public Iterator<E> iterator() {
				// TODO Auto-generated method stub
				return stream.iterator();
			}

			@Override
			public int size() {
				// TODO Auto-generated method stub
				return (int)stream.count();
			}

			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return stream.anyMatch(p->true);
			}

			@Override
			public boolean contains(Object o) {
				// TODO Auto-generated method stub
				return (s1.contains(o)&&!s2.contains(o))||(!s1.contains(o)&&s2.contains(o));
			}

			@Override
			public boolean containsAll(Collection<?> c) {
				if(c.isEmpty())
					return true;
				return !c.stream().anyMatch(e->!this.contains(e));
			}
		};
	}
}
