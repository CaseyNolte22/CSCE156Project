/**
 * Concession class, extended from Product class
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

public class Concession extends Product {

	private char type = 'C';
	private double unitCost;

	public Concession(String code, String label, char type, double unitCost) {
		super(code, label);
		this.type = type;
		this.unitCost = unitCost;
	}

	public char getType() {
		return type;
	}

	public double getUnitCost() {
		return unitCost;
	}

}
