package com.bc;

public class Customer {
	private String customerCode;
	private String customerType;
	private String name;
	private Person contact;
	private Address customerAddress;

	public Customer(String customerCode, String customerType, String name, Person contact, Address customerAddress) {
		this.customerCode = customerCode;
		this.customerType = customerType;
		this.name = name;
		this.contact = contact;
		this.customerAddress = customerAddress;
	}

	public String getCustomerType() {
		return customerType;
	}

	public Person getContact() {
		return contact;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getName() {
		return name;
	}

	public Address getCustomerAddress() {
		return customerAddress;
	}

}