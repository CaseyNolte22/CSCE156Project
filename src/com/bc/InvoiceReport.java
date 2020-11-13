/**
 * Contains a main method to generates the invoice output file
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

import java.util.ArrayList;

public class InvoiceReport {

	public static void main(String[] args) {
		
		ArrayList<Person> persons = DatabaseAccess.personRetrieve();
		ArrayList<Customer> customers = DatabaseAccess.customerRetrieve(persons);
		ArrayList<Product> products = DatabaseAccess.productRetrieve();
		ArrayList<Invoice> arrayInvoices = DatabaseAccess.invoiceRetrieve(persons, customers, products);
		
		// Because it is simpler to leave the database access system unchanged, we are still first
		// loading from the database to an ArrayList. We then write this UNSORTED ArrayList into our insertItem function
		// that is part of the LinkedList class. The invoicePrint function was practically unmodified. The 
		// only change was changing the input parameter from ArrayList to LinkedList.
		
		LinkedList<Invoice> linkedInvoices = new LinkedList<Invoice>();
		for (Invoice item : arrayInvoices) {
			linkedInvoices.insertItem(item);
		}

		InvoiceWriter.invoicePrint(linkedInvoices);
	}

}
