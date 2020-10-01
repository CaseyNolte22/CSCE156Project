/**
 * Invoice class
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

import java.util.ArrayList;

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

	@Override
	public String toString() {
		return "Invoice [invoiceCode=" + invoiceCode + ", ownerCode=" + ownerCode + ", customerCode=" + customerCode
				+ ", productList=" + productList + "]";
	}

}
