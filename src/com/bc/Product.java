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

	@Override
	public String toString() {
		return "Products [code=" + code + ", label=" + label + "]";
	}

}
