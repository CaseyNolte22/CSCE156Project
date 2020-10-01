/**
 * Contains a main method to generate Json files from flat data files
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */

package com.bc;

import java.io.File;
import java.util.ArrayList;

public class DataConverter {

	public static void main(String[] args) {

		File customerFile = new File("data/Customers.dat");
		File personsFile = new File("data/Persons.dat");
		File productsFile = new File("data/Products.dat");

		ArrayList<Person> persons = new ArrayList<Person>();
		persons = FlatParser.personParse(personsFile);

		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers = FlatParser.customerParse(customerFile, persons);

		ArrayList<Product> products = new ArrayList<Product>();
		products = FlatParser.productParse(productsFile);

		System.out.print(products.get(0).getClass());
		
		JsonWrite.printJSON("data/Persons.json", persons, "persons");
		JsonWrite.printJSON("data/Customers.json", customers, "customers");
		JsonWrite.printJSON("data/Products.json", products, "products");
	}

}
