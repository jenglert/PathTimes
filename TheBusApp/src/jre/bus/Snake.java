package jre.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Snake<T extends Comparable<T>, X> implements Comparable<Snake<T, X>> {
	
	private T head;
	
	private List<X> contents;
	
	public Snake() {
		contents = new ArrayList<X>();
	}
	
	public Snake(T head, X... contents) {
		this.head = head;
		this.contents = new ArrayList<X>(Arrays.asList(contents));
	}
	
	public Snake(T head, Collection<X> collection) {
		this.head = head;
		this.contents = new ArrayList<X>(collection);
	}
	
	public T getFirst() {
		return head;
	}
	
	public X getSecond() {
		if (contents.size() < 1) {
			throw new RuntimeException("Unable to get second, list has fewer than 2 items.");
		}
		
		return contents.get(0);
	}
	
	public X getThird() {
		if (contents.size() < 2) {
			throw new RuntimeException("Unable to get third, list has fewer than 3 items.");
		}
		
		return contents.get(1);
	}
	
	public X getLast() {
		if (contents.size() == 0) {
			throw new RuntimeException("Unable to get last, list has no items.");
		}
		
		return contents.get(contents.size() - 1);
	}

	@Override
	public int compareTo(Snake<T, X> rhs) {
		return head.compareTo(rhs.getFirst());
	}

	@Override
	public boolean equals(Object o) {
		@SuppressWarnings("unchecked")
		Snake<T, X> rhs = (Snake<T, X>) o;
		
		return head.equals(rhs.getFirst());
	}
}
