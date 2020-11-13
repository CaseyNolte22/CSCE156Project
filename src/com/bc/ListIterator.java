/**
 * Sorted Linked List Iterator Implementation
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

import java.util.Iterator;

public class ListIterator<T> implements Iterator<T> {
	Node<T> current;

	/**
	 * Initializes the start of the iterator
	 * 
	 * @param list
	 */
	public ListIterator(LinkedList<T> list) {
		current = list.getHead();
	}

	/**
	 * Returns true if list has another element, false if not
	 */
	public boolean hasNext() {
		return current != null;
	}

	/**
	 * Returns the current element
	 */
	public T next() {
		T item = current.getItem();
		current = current.getNext();
		return item;
	}

}
