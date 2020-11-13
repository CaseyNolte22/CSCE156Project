package com.bc;

import java.util.ArrayList;

public class Demo {

	public static void main(String[] args) {
		
		LinkedList<Invoice> linkedInvoices = new LinkedList<Invoice>();
		
		ArrayList<Person> persons = DatabaseAccess.personRetrieve();
		ArrayList<Customer> customers = DatabaseAccess.customerRetrieve(persons);
		ArrayList<Product> products = DatabaseAccess.productRetrieve();
		ArrayList<Invoice> invoices = DatabaseAccess.invoiceRetrieve(persons, customers, products);
		
		for (Invoice item : invoices) {
			linkedInvoices.insertItem(item);
			System.out.println(item.getInvoiceCode());
			System.out.println(item.getTotal());
		}
		
		for (Invoice item : linkedInvoices) {
			System.out.println(item.getTotal());
			System.out.println(item.getInvoiceCode());
		}
	}

}
