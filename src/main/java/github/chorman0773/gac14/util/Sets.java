package github.chorman0773.gac14.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public interface Sets {
	public static <E> Set<E> union(final Set<? extends E> s1,final Set<? extends E> s2){
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
				return s1.isEmpty()||s2.isEmpty();
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
