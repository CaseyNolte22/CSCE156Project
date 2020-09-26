package com.bc;

import java.util.ArrayList;

public class Person {

	private String personCode;
	private String firstName;
	private String lastName;
	private Address personAddress;
	private ArrayList<String> emails;

	public Person(String personCode, String firstName, String lastName, Address personAddress,
			ArrayList<String> emails) {
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.personAddress = personAddress;
		this.emails = emails;

	}

	public String getPersonCode() {
		return personCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Address getPersonAddress() {
		return personAddress;
	}

	public ArrayList<String> getEmails() {
		return emails;
	}

	@Override
	public String toString() {
		return "Person [personCode=" + personCode + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", personAddress=" + personAddress + ", emails=" + emails + "]";
	}

}
