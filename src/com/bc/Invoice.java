/**
 * Invoice class
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

import java.util.*;

public class Invoice {

	private String invoiceCode;
	private Person ownerCode;
	private Customer customerCode;
	private ArrayList<Product> productList;

	public Invoice(String invoiceCode, Person ownerCode, Customer customerCode, ArrayList<Product> productList) {
		this.invoiceCode = invoiceCode;
		this.ownerCode = ownerCode;
		this.customerCode = customerCode;
		this.productList = productList;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public Person getOwnerCode() {
		return ownerCode;
	}

	public Customer getCustomerCode() {
		return customerCode;
	}

	public ArrayList<Product> getProductList() {
		return productList;
	}

	public double getSubtotal() {
		double sum = 0;
		for (Product item : productList) {
			sum += item.getPrice();
		}
		return sum;
	}

	public double getFees() {
		if (customerCode.getCustomerType().equals("B")) {
			return 75.5;
		} else {
			return 0.0;
		}
	}

	public double getTax(double total) {
		double taxRate;
		if (customerCode.getCustomerType().equals("B")) {
			taxRate = .0425;
		} else {
			taxRate = .08;
		}
		double tax = total * taxRate;
		return tax;
	}

	public double getTowingDiscount() {
		double discountTotal = 0;
		Set<String> types = new HashSet<String>();
		Set<String> typeCheck = new HashSet<String>();
		typeCheck.add("R");
		typeCheck.add("F");
		typeCheck.add("T");
		for (Product item : productList) {
			char typeChar = item.getType();
			types.add(Character.toString(typeChar));
		}
		if (types.containsAll(typeCheck)) {
			for (Product item : productList) {
				if (item instanceof Towing) {
					discountTotal -= item.getPrice();
				}
			}
		}
		return discountTotal;
	}

	public double getConcessionDiscount() {
		double discountTotal = 0;
		for (Product item : productList) {
			if (item instanceof Concession) {
				if (((Concession) item).getAssociatedRepair() != null) {
					discountTotal -= (.1 * item.getPrice());
				}
			}
		}
		return discountTotal;
	}

	public double getPostTaxDiscount(double total) {
		double discountTotal = 0;
		if ((customerCode.getContact().getEmails().size() >= 2) && (customerCode.getCustomerType().equals("P"))) {
			discountTotal -= total * (.05);
		}
		return discountTotal;
	}

	@Override
	public String toString() {
		return "Invoice [invoiceCode=" + invoiceCode + ", ownerCode=" + ownerCode + ", customerCode=" + customerCode
				+ ", productList=" + productList + "]";
	}

}
