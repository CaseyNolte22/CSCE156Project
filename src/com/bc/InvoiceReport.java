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
		ArrayList<Invoice> invoices = DatabaseAccess.invoiceRetrieve(persons, customers, products);

		InvoiceWriter.invoicePrint(invoices);
	}

}
