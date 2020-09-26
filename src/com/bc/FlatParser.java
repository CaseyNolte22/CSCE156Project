//Contains parsing stuff



package com.bc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FlatParser {

	public static ArrayList<Person> personParse(File file) {
		Scanner p;
		try {
			p = new Scanner(file);
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}
		int entries1 = p.nextInt();
		ArrayList<Person> persons = new ArrayList<Person>();
		p.nextLine();
		for (int i = 0; i < entries1; i++) {
			String line = p.nextLine();
			String tokens[] = line.split(";");
			String personsCode = tokens[0];
			String nameTokens[] = tokens[1].split(",");
			String firstName = nameTokens[1];
			String lastName = nameTokens[0];
			String addressTokens[] = tokens[2].split(",");
			Address address = new Address(addressTokens[0], addressTokens[1], addressTokens[2], addressTokens[3],
					addressTokens[4]);
			String emailTokens[] = tokens[3].split(",");
			ArrayList<String> emails = new ArrayList<String>();
			for (int j = 0; j < emailTokens.length; j++) {
				emails.add(emailTokens[j]);
			}
			Person e = new Person(personsCode, firstName, lastName, address, emails);
			persons.add(e);
		}
		p.close();
		return persons;

	}

	public static ArrayList<Customer> customerParse(File file, ArrayList<Person> persons) {
		Scanner s;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}

		// Parsing Customers
		int entries = s.nextInt();
		ArrayList<Customer> customers = new ArrayList<Customer>();
		s.nextLine();
		for (int i = 0; i < entries; i++) {
			String line = s.nextLine();
			String tokens[] = line.split(";");
			String customerCode = tokens[0];
			String customerType = tokens[1];
			String customerName = tokens[2];
			String primaryContactCode = tokens[3];
			String addressTokens[] = tokens[4].split(",");
			Address address = new Address(addressTokens[0], addressTokens[1], addressTokens[2], addressTokens[3],
					addressTokens[4]);
			Person contact = null;
			for (Person item : persons) {
				if (primaryContactCode.equals(item.getPersonCode())) {
					contact = item;
				}

			}
			Customer e = new Customer(customerCode, customerType, customerName, contact, address);
			customers.add(e);
		}
		s.close();
		return customers;
	}
	
	public static ArrayList<Product> productParse(File file) {
		Scanner r;
		try {
			r = new Scanner(file);
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}

		// Parsing Products
		int entries2 = r.nextInt();
		ArrayList<Product> products = new ArrayList<Product>();
		r.nextLine();
		for (int i = 0; i < entries2; i++) {
			String line = r.nextLine();
			String tokens[] = line.split(";");
			String productCode = tokens[0];
			String productLabel = tokens[2];

			char productType;
			switch ((tokens[1])) {

			case "R":
				productType = 'R';
				double dailyCost = Double.parseDouble(tokens[3]);
				double deposit = Double.parseDouble(tokens[4]);
				double cleaningFee = Double.parseDouble(tokens[5]);

				Product e1 = new Rental(productCode, productLabel, productType, dailyCost, deposit, cleaningFee);
				products.add(e1);
				break;

			case "F":

				productType = 'F';
				double partsCost = Double.parseDouble(tokens[3]);
				double hourlyLaborCost = Double.parseDouble(tokens[4]);

				Product e2 = new Repair(productCode, productLabel, productType, partsCost, hourlyLaborCost);
				products.add(e2);
				break;

			case "C":
				productType = 'C';
				double unitCost = Double.parseDouble(tokens[3]);

				Product e3 = new Concession(productCode, productLabel, productType, unitCost);
				products.add(e3);
				break;

			case "T":
				productType = 'T';
				double costPerMile = Double.parseDouble(tokens[3]);

				Product e4 = new Towing(productCode, productLabel, productType, costPerMile);
				products.add(e4);
				break;

			}

		}
		r.close();
		return products;
	}
}
