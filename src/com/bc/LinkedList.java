/**
 * Sorted Linked List Implementation
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */

package com.bc;

import java.util.Comparator;
import java.util.Iterator;

public class LinkedList<T> implements Iterable<T>, Comparator<Invoice> {

	private Node<T> head;
	private int size;

	public LinkedList() {
		this.head = null;
		this.size = 0;
	}

	/**
	 * Takes an invoice as an argument and places it in the linked list at the
	 * correct sorted location
	 * 
	 * @param item
	 */
	@SuppressWarnings("unchecked")
	public void insertItem(Invoice item) {
		if (size == 0) {
			insertAtStart((T) item);
			return;
		}
		for (int i = 0; i < this.size; i++) {
			int check = compare(item, (Invoice) retrieveItemAtIndex(i));
			if (check == -1) {
				insertAtIndex(i, (T) item);
				return;
			}
		}
		return;
	}

	/**
	 * Inserts an element to the list at the start
	 * 
	 * @param item
	 */
	private void insertAtStart(T item) {
		Node<T> temp = new Node<T>(item);
		temp.setNext(this.head);
		this.head = temp;
		this.size++;
	}

	public int getsize() {
		return this.size;
	}

	/**
	 * Returns element at supplied index
	 * 
	 * @param index
	 * @return element at given index
	 */
	public T retrieveItemAtIndex(int index) {
		return retrieveNodeAtIndex(index).getItem();
	}

	/**
	 * Returns the node object at a given index
	 * 
	 * @param index
	 * @return node at given index
	 */
	private Node<T> retrieveNodeAtIndex(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index is not valuid!!");
		}
		Node<T> curr = this.head;
		for (int i = 0; i < index; i++) {
			curr = curr.getNext();
		}
		return curr;
	}

	/**
	 * Inserts element into linked list at given index
	 * 
	 * @param index
	 * @param item
	 */
	private void insertAtIndex(int index, T item) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index is not valuid!!");
		}
		if (index == 0) {
			this.insertAtStart(item);
			return;
		}
		Node<T> curr = this.retrieveNodeAtIndex(index - 1);
		Node<T> newNode = new Node<T>(item);
		newNode.setNext(curr.getNext());
		curr.setNext(newNode);
		size++;
	}

	/**
	 * Removes element from linked list at given index
	 * 
	 * @param index
	 * @return
	 */
	public T removeAtIndex(int index) {
		if (this.head == null) {
			throw new IllegalArgumentException("No element to remove!!!");
		}
		if (index == 0) {
			return this.removeFromStart();
		}
		Node<T> prev = this.retrieveNodeAtIndex(index - 1);
		Node<T> curr = prev.getNext();
		prev.setNext(curr.getNext());
		this.size--;
		return curr.getItem();
	}

	public String toString() {
		if (this.head == null) {
			return "Empty List!!";
		}
		StringBuilder sb = new StringBuilder();
		Node<T> curr = this.head;
		while (curr != null) {
			sb.append(curr.getItem().toString() + ", ");
			curr = curr.getNext();
		}
		return sb.toString();
	}

	private boolean empty() {
		return this.size == 0;
	}

	/**
	 * Inserts element at end of list. It's private and unused because the user shouldn't need this function in 
	 * the invoicing system, but is a useful linked list function
	 * 
	 * @param item
	 */
	private void insertAtEnd(T item) {
		if (this.size == 0) {
			this.insertAtStart(item);
			return;
		}
		Node<T> curr = this.head;
		while (curr.getNext() != null) {
			curr = curr.getNext();
		}
		Node<T> newEnd = new Node<T>(item);
		curr.setNext(newEnd);
		this.size++;
	}

	/**
	 * Removes element from beginning of linked list
	 * 
	 * @return
	 */
	public T removeFromStart() {
		if (this.empty()) {
			throw new IllegalStateException("You are removing from an empty list!!!");
		}
		T item = this.head.getItem();
		this.head = this.head.getNext();
		this.size--;
		return item;
	}

	public Node<T> getHead() {
		return head;
	}

	/**
	 * Implements the iterator 
	 */
	@Override
	public Iterator<T> iterator() {
		return new ListIterator<T>(this);
	}

	/**
	 * Implements the comparator for invoices
	 */
	@Override
	public int compare(Invoice one, Invoice two) {
		if (one.getTotal() == two.getTotal()) {
			return 0;
		} else if (one.getTotal() < two.getTotal()) {
			return 1;
		} else {
			return -1;
		}
	}
}