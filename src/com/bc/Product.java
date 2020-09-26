/**
 * Product class, extended by Rental, Repair, Towing, and Concession classes
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

public class Product {
	private String code;
	private String label;

	public Product(String code, String label) {
		super();
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

}
