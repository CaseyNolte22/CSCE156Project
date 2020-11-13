package com.bc.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.bc.Connector;
import com.bc.model.Concession;
import com.bc.model.Customer;
import com.bc.model.Invoice;
import com.bc.model.Person;
import com.bc.model.Product;
import com.bc.model.Rental;
import com.bc.model.Repair;
import com.bc.model.Towing;

import unl.cse.albums.Album;
import unl.cse.albums.Band;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application. 16 methods in
 * total, add more if required. Do not change any method signatures or the
 * package name.
 * 
 * Adapted from Dr. Hasan's original version of this file
 * 
 * @author Chloe
 *
 */
public class InvoiceData {

	/**
	 * 1. Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		removeAllCusomters();

		String emailQuery = "DELETE FROM Email";
		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(emailQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String personQuery = "DELETE FROM Person";

		ps = null;

		try {
			ps = conn.prepareStatement(personQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 2. Method to add a person record to the database with the provided data.
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country) {
		Connection conn = Connector.establishConnection();
		
		if (street.isEmpty()) {
			street = "empty";
		}
		if (city.isEmpty()) {
			city = "empty";
		}
		if (state.isEmpty()) {
			state = "empty";
		}
		if (zip.isEmpty()) {
			zip = "00000";
		}
		if (country.isEmpty()) {
			country = "empty";
		}
		
		String retrieveAddressQuery = "SELECT addressId FROM Address WHERE street = '" + street + "'";
		String newAddressQuery = "INSERT INTO Address (street, city, state, zip, country) "
				+ "VALUES ('"+ street + "','" + city + "','" + state + "','" + zip + "','" + country + "')";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String addressId;
		try {
			ps = conn.prepareStatement(retrieveAddressQuery);
			rs = ps.executeQuery();
			if (rs.next()) {
				addressId = rs.getString("addressId");
				rs.close();
				ps.close();
			} else {

				try {
					ps.close();
					ps = null;
					ps = conn.prepareStatement(newAddressQuery);
					ps.executeUpdate(newAddressQuery, Statement.RETURN_GENERATED_KEYS);
					ResultSet keys = ps.getGeneratedKeys();
					keys.next();
					int key = keys.getInt(1);
					addressId = Integer.toString(key);
					rs.close();
					ps.close();

				} catch (SQLException e) {
					System.out.println("SQLException: ");
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String newPersonQuery = "INSERT INTO Person (personCode, lastName, firstName, addressId) "
				+ "SELECT '" + personCode + "', '" + lastName + "', '" + firstName + "', " + addressId;
		ps = null;

		try {
			ps = conn.prepareStatement(newPersonQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {

		if (personCode.isBlank() | email.isBlank()) {
			System.out.println("Invalid Input. All fields must be filled");
			System.exit(0);
		}

		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;
		String emailQuery = "INSERT INTO Email" + "(email, personId) SELECT '" + email
				+ "', p.personId FROM Person p WHERE " + "p.personcode = '" + personCode + "'";

		try {
			ps = conn.prepareStatement(emailQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	public static void removeAllCusomters() {
		Connection conn = Connector.establishConnection();

		String invoiceProductQuery = "DELETE FROM InvoiceProduct";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(invoiceProductQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String invoiceQuery = "DELETE FROM Invoice";

		ps = null;

		try {
			ps = conn.prepareStatement(invoiceQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String customerQuery = "DELETE FROM Customer";

		ps = null;

		try {
			ps = conn.prepareStatement(customerQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 5. Method to add a customer record to the database with the provided data
	 * 
	 * @param customerCode
	 * @param customerType
	 * @param primaryContactPersonCode
	 * @param name
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,
			String name, String street, String city, String state, String zip, String country) {

		Connection conn = Connector.establishConnection();
		
		name = name.replaceAll("'","");

		if (street.isEmpty()) {
			street = "empty";
		}
		if (city.isEmpty()) {
			city = "empty";
		}
		if (state.isEmpty()) {
			state = "empty";
		}
		if (zip.isEmpty()) {
			zip = "00000";
		}
		if (country.isEmpty()) {
			country = "empty";
		}
		
		String retrieveAddressQuery = "SELECT addressId FROM Address WHERE street = '" + street + "'";
		String newAddressQuery = "INSERT INTO Address (street, city, state, zip, country) " + "SELECT '" + street
				+ "','" + city + "','" + state + "','" + zip + "','" + country + "'";

		PreparedStatement ps = null;
		ResultSet rs = null;
		String addressId;
		try {
			ps = conn.prepareStatement(retrieveAddressQuery);
			rs = ps.executeQuery();
			if (rs.next()) {
				addressId = rs.getString("addressId");
				rs.close();
				ps.close();
			} else {

				try {
					ps.close();
					ps = null;
					ps = conn.prepareStatement(newAddressQuery);
					ps.executeUpdate(newAddressQuery, Statement.RETURN_GENERATED_KEYS);
					ResultSet keys = ps.getGeneratedKeys();
					keys.next();
					int key = keys.getInt(1);
					addressId = Integer.toString(key);
					rs.close();
					ps.close();

				} catch (SQLException e) {
					System.out.println("SQLException: ");
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String newCustomerQuery = "INSERT INTO Customer (customerCode, customerType, name, personId, addressId) "
				+ "SELECT '" + customerCode + "', '" + customerType + "', '" + name + "', p.personId, " + addressId
				+ "\r\n" + " FROM Person p WHERE p.personCode = '" + primaryContactPersonCode + "'";
		
		ps = null;

		try {
			ps = conn.prepareStatement(newCustomerQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			return;
		}
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * 6. Removes all product records from the database
	 */
	public static void removeAllProducts() {
		String invoiceProductQuery = "DELETE FROM InvoiceProduct";
		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(invoiceProductQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String productQuery = "DELETE FROM Product";

		ps = null;

		try {
			ps = conn.prepareStatement(productQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 7. Adds a concession record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param productLabel
	 * @param unitCost
	 */
	public static void addConcession(String productCode, String productLabel, double unitCost) {

		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		String concessionQuery = "INSERT INTO Product (productCode, productType, label, unitCost) " + "VALUES ('"
				+ productCode + "','C','" + productLabel + "'," + unitCost + ")";

		try {
			ps = conn.prepareStatement(concessionQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * 8. Adds a repair record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param proudctLabel
	 * @param partsCost
	 * @param laborRate
	 */
	public static void addRepair(String productCode, String productLabel, double partsCost, double laborRate) {
		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		String repairQuery = "INSERT INTO Product (productCode, productType, label, partsCost, hourlyLaborCost) "
				+ "VALUES ('" + productCode + "','F','" + productLabel + "'," + partsCost + "," + laborRate + ")";

		try {
			ps = conn.prepareStatement(repairQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 9. Adds a towing record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param productLabel
	 * @param costPerMile
	 */
	public static void addTowing(String productCode, String productLabel, double costPerMile) {

		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		String towingQuery = "INSERT INTO Product (productCode, productType, label, costPerMile) " + "VALUES ('"
				+ productCode + "','T','" + productLabel + "'," + costPerMile + ")";

		try {
			ps = conn.prepareStatement(towingQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * 10. Adds a rental record to the database with the provided data.
	 * 
	 * @param productCode
	 * @param productLabel
	 * @param dailyCost
	 * @param deposit
	 * @param cleaningFee
	 */
	public static void addRental(String productCode, String productLabel, double dailyCost, double deposit,
			double cleaningFee) {
		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		String rentalQuery = "INSERT INTO Product (productCode, productType, label, dailyCost, deposit, cleaningFee) "
				+ "VALUES ('" + productCode + "','R','" + productLabel + "'," + dailyCost + "," + deposit + ","
				+ cleaningFee + ")";

		try {
			ps = conn.prepareStatement(rentalQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 11. Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {

		String invoiceProductQuery = "DELETE FROM InvoiceProduct";
		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(invoiceProductQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String invoiceQuery = "DELETE FROM Invoice";

		ps = null;

		try {
			ps = conn.prepareStatement(invoiceQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 12. Adds an invoice record to the database with the given data.
	 * 
	 * @param invoiceCode
	 * @param ownerCode
	 * @param customertCode
	 */
	public static void addInvoice(String invoiceCode, String ownerCode, String customerCode) {
		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		String invoiceQuery = "INSERT INTO Invoice (invoiceCode, customerId, personId) " + "SELECT '" + invoiceCode
				+ "', c.customerId, p.personId FROM Customer c JOIN Person p " + "WHERE p.personCode = '" + ownerCode
				+ "' AND c.customerCode = '" + customerCode + "'";

		try {
			ps = conn.prepareStatement(invoiceQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 13. Adds a particular Towing (corresponding to <code>productCode</code> to an
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of miles towed
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param milesTowed
	 */
	public static void addTowingToInvoice(String invoiceCode, String productCode, double milesTowed) {

		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		String invoiceQuery = "INSERT INTO InvoiceProduct (productId, invoiceId, milesTowed) "
				+ "SELECT p.productId, i.invoiceId, '" + milesTowed + "' FROM Product p " + "JOIN Invoice i "
				+ "WHERE p.productCode = '" + productCode + "' AND i.invoiceCode = '" + invoiceCode + "'";

		try {
			ps = conn.prepareStatement(invoiceQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 14. Adds a particular Repair (corresponding to <code>productCode</code> to an
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of hours worked
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param hoursWorked
	 */
	public static void addRepairToInvoice(String invoiceCode, String productCode, double hoursWorked) {

		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		String invoiceQuery = "INSERT INTO InvoiceProduct (productId, invoiceId, hoursWorked) "
				+ "SELECT p.productId, i.invoiceId, '" + hoursWorked + "' FROM Product p " + "JOIN Invoice i "
				+ "WHERE p.productCode = '" + productCode + "' AND i.invoiceCode = '" + invoiceCode + "'";

		try {
			ps = conn.prepareStatement(invoiceQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 15. Adds a particular Concession (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with the
	 * given number of quantity. NOTE: repairCode may be null
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param quantity
	 * @param repairCode
	 */
	public static void addConcessionToInvoice(String invoiceCode, String productCode, int quantity, String repairCode) {
		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		String concessionQuery = "INSERT INTO InvoiceProduct (productId, invoiceId, quantity, associatedRepair) "
				+ "SELECT p.productId, i.invoiceId, '" + quantity + "', '" + repairCode + "' FROM Product p "
				+ "JOIN Invoice i WHERE p.productCode = '" + productCode + "' AND i.invoiceCode = '" + invoiceCode
				+ "'";

		try {
			ps = conn.prepareStatement(concessionQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 16. Adds a particular Rental (corresponding to <code>productCode</code> to an
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of days rented.
	 * 
	 * @param invoiceCode
	 * @param productCode
	 * @param daysRented
	 */
	public static void addRentalToInvoice(String invoiceCode, String productCode, double daysRented) {

		Connection conn = Connector.establishConnection();
		PreparedStatement ps = null;

		String invoiceQuery = "INSERT INTO InvoiceProduct (productId, invoiceId, daysRented) "
				+ "SELECT p.productId, i.invoiceId, '" + daysRented + "' FROM Product p " + "JOIN Invoice i "
				+ "WHERE p.productCode = '" + productCode + "' AND i.invoiceCode = '" + invoiceCode + "'";

		try {
			ps = conn.prepareStatement(invoiceQuery);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}