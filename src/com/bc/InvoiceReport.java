/**
 * Contains a main method to generates the invoice output file
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

import java.io.*;
import java.util.ArrayList;

public class InvoiceReport {

	public static void main(String[] args) {
		File customerFile = new File("data/Customers.dat");
		File personsFile = new File("data/Persons.dat");
		File productsFile = new File("data/Products.dat");
		File invoiceFile = new File("data/Invoices.dat");
		File invoiceOutput = new File("data/output.txt");

		ArrayList<Person> persons = new ArrayList<Person>();
		persons = FlatParser.personParse(personsFile);

		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers = FlatParser.customerParse(customerFile, persons);

		ArrayList<Product> products = new ArrayList<Product>();
		products = FlatParser.productParse(productsFile);

		ArrayList<Invoice> invoices = new ArrayList<Invoice>();
		invoices = FlatParser.invoiceParse(invoiceFile, persons, customers, products);

		InvoiceWriter.invoicePrint(invoiceOutput, invoices);
	}

}
