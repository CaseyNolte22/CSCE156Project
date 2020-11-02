package com.bc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseAccess {

	/**
	 * Fetches all persons from an SQL database and creates an array of Person
	 * objects
	 * 
	 * @return ArrayList of Person
	 */
	public static ArrayList<Person> personRetrieve() {

		ArrayList<Person> persons = new ArrayList<Person>();

		Connection conn = Connector.establishConnection();
		String personQuery = "SELECT p.personCode, p.firstName, p.lastName, "
				+ "a.street, a.city, a.state, a.zip, a.country " + "FROM Person p "
				+ "JOIN Address a ON p.addressId = a.addressId";

		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;

		try {
			ps = conn.prepareStatement(personQuery);
			rs = ps.executeQuery();

			while (rs.next()) {
				String personCode = rs.getString("personCode");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zip = rs.getString("zip");
				String country = rs.getString("country");
				Address personAddress = new Address(street, city, state, zip, country);

				String emailQuery = "SELECT e.email " + "FROM Email e JOIN Person p "
						+ "WHERE e.personId = p.PersonId AND p.personCode = '" + personCode + "'";
				ArrayList<String> emails = new ArrayList<String>();

				try {
					ps2 = conn.prepareStatement(emailQuery);
					rs2 = ps2.executeQuery();
					while (rs2.next()) {
						String emailAddress = rs2.getString("email");
						emails.add(emailAddress);
					}

					rs2.close();
					ps2.close();
				} catch (SQLException e) {
					System.out.println("SQLException: ");
					e.printStackTrace();
					throw new RuntimeException(e);
				}

				Person p = new Person(personCode, firstName, lastName, personAddress, emails);
				persons.add(p);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps2 != null && !ps2.isClosed())
				ps2.close();
			if (rs2 != null && !rs2.isClosed())
				rs2.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return persons;
	}

	/**
	 * Fetches all customers from an SQL database and writes them to an array
	 * 
	 * @param persons
	 * @return ArrayList of Customers
	 */
	public static ArrayList<Customer> customerRetrieve(ArrayList<Person> persons) {
		ArrayList<Customer> customers = new ArrayList<Customer>();

		Connection conn = Connector.establishConnection();
		String customerQuery = "SELECT c.customerCode, c.customerType, c.name, p.personCode, "
				+ "a.street, a.city, a.state, a.zip, a.country FROM Customer c "
				+ "JOIN Person p ON p.personId = c.personId " + "JOIN Address a ON c.addressId = a.addressId;";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(customerQuery);
			rs = ps.executeQuery();

			while (rs.next()) {
				String customerCode = rs.getString("customerCode");
				String customerType = rs.getString("customerType");
				String name = rs.getString("name");
				String personCode = rs.getString("personCode");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zip = rs.getString("zip");
				String country = rs.getString("country");
				Address customerAddress = new Address(street, city, state, zip, country);

				Person contact = null;
				for (Person item : persons) {
					if (item.getPersonCode().equals(personCode)) {
						contact = item;
					}
				}

				Customer c = new Customer(customerCode, customerType, name, contact, customerAddress);
				customers.add(c);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (rs != null && !rs.isClosed())
				rs.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return customers;
	}

	/**
	 * Fetches all products from an SQL database
	 * 
	 * @return ArrayList of Products
	 */
	public static ArrayList<Product> productRetrieve() {
		ArrayList<Product> products = new ArrayList<Product>();

		Connection conn = Connector.establishConnection();
		String productQuery = "SELECT * FROM Product";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(productQuery);
			rs = ps.executeQuery();

			while (rs.next()) {
				String productCode = rs.getString("productCode");
				String productTypeAsString = rs.getString("productType");
				String productLabel = rs.getString("label");
				char productType;

				switch (productTypeAsString) {

				case "R":
					productType = 'R';
					double dailyCost = rs.getDouble("dailyCost");
					double deposit = rs.getDouble("deposit");
					double cleaningFee = rs.getDouble("cleaningFee");

					Product e1 = new Rental(productCode, productLabel, productType, dailyCost, deposit, cleaningFee);
					products.add(e1);
					break;

				case "F":
					productType = 'F';
					double partsCost = rs.getDouble("partsCost");
					double hourlyLaborCost = rs.getDouble("hourlyLaborCost");

					Product e2 = new Repair(productCode, productLabel, productType, partsCost, hourlyLaborCost);
					products.add(e2);
					break;

				case "C":
					productType = 'C';
					double unitCost = rs.getDouble("unitCost");

					Product e3 = new Concession(productCode, productLabel, productType, unitCost);
					products.add(e3);
					break;

				case "T":
					productType = 'T';
					double costPerMile = rs.getDouble("costperMile");

					Product e4 = new Towing(productCode, productLabel, productType, costPerMile);
					products.add(e4);
					break;

				}

			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (rs != null && !rs.isClosed())
				rs.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return products;
	}

	/**
	 * Fetches all invoices from an SQL Database
	 * 
	 * @param persons
	 * @param customers
	 * @param products
	 * @return ArrayList of Invoices
	 */
	public static ArrayList<Invoice> invoiceRetrieve(ArrayList<Person> persons, ArrayList<Customer> customers,
			ArrayList<Product> products) {

		ArrayList<Invoice> invoices = new ArrayList<Invoice>();

		Connection conn = Connector.establishConnection();
		String invoiceQuery = "SELECT i.invoiceId, i.invoiceCode, p.personCode, c.CustomerCode "
				+ "FROM Invoice i JOIN Person p " + "ON i.personId = p.personId "
				+ "JOIN Customer c ON c.customerId = i.customerId;";

		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;

		try {
			ps = conn.prepareStatement(invoiceQuery);
			rs = ps.executeQuery();

			while (rs.next()) {
				int invoiceId = rs.getInt("invoiceId");
				String invoiceCode = rs.getString("invoiceCode");
				String personCode = rs.getString("personCode");
				String customerCode = rs.getString("customerCode");
				Person personContact = null;
				Customer customerContact = null;

				for (Person item : persons) {
					if (personCode.equals(item.getPersonCode())) {
						personContact = item;
					}
				}
				for (Customer item : customers) {
					if (customerCode.equals(item.getCustomerCode())) {
						customerContact = item;
					}
				}

				String invoiceProductQuery = "SELECT * FROM InvoiceProduct ip " + "JOIN Product p "
						+ "ON ip.productId = p.productId WHERE invoiceId = '" + invoiceId + "'";
				ArrayList<Product> productList = new ArrayList<Product>();

				try {
					ps2 = conn.prepareStatement(invoiceProductQuery);
					rs2 = ps2.executeQuery();
					while (rs2.next()) {

						String productCode = rs2.getString("productCode");
						Product currentProduct = null;
						for (Product item : products) {
							if (productCode.equals(item.getCode())) {
								currentProduct = item;
							}
						}
						if (currentProduct instanceof Rental) {
							double daysRented = rs2.getDouble("daysRented");
							Product p = new Rental((Rental) currentProduct, daysRented);
							productList.add(p);
						} else if (currentProduct instanceof Repair) {
							double hoursWorked = rs2.getDouble("hoursWorked");
							Product p = new Repair((Repair) currentProduct, hoursWorked);
							productList.add(p);
						} else if (currentProduct instanceof Towing) {
							double milesTowed = rs2.getDouble("milesTowed");
							Product p = new Towing((Towing) currentProduct, milesTowed);
							productList.add(p);
						} else if (currentProduct instanceof Concession) {
							int quantity = rs2.getInt("quantity");
							Product associatedRepair = null;
							String repairCode;
							repairCode = rs2.getString("associatedRepair");

							for (Product item : products) {
								if (repairCode != null) {
									if (repairCode.equals(item.getCode())) {
										associatedRepair = item;
									}
								}
							}
							Product p = new Concession((Concession) currentProduct, quantity, associatedRepair);
							productList.add(p);
						}

					}

					rs2.close();
					ps2.close();
				} catch (SQLException e) {
					System.out.println("SQLException: ");
					e.printStackTrace();
					throw new RuntimeException(e);
				}

				Invoice inv = new Invoice(invoiceCode, personContact, customerContact, productList);
				invoices.add(inv);
			}
			rs.close();
			ps.close();
		} catch (

		SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps2 != null && !ps2.isClosed())
				ps2.close();
			if (rs2 != null && !rs2.isClosed())
				rs2.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return invoices;
	}
}
