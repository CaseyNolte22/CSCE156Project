/**
 * Towing class, extension of Product class
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

public class Towing extends Product {

	private char type = 'T';
	private double costPerMile;

	public Towing(String code, String label, char type, double costPerMile) {
		super(code, label);
		this.type = type;
		this.costPerMile = costPerMile;
	}

	public char getType() {
		return type;
	}

	public double getCostPerMile() {
		return costPerMile;
	}

}
