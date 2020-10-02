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
	private double milesTowed;

	public Towing(String code, String label, char type, double costPerMile) {
		super(code, label);
		this.type = type;
		this.costPerMile = costPerMile;
	}

	public Towing(Towing old, double milesTowed) {
		super(old.getCode(), old.getLabel());
		this.milesTowed = milesTowed;
		this.costPerMile = old.getCostPerMile();
	}

	public char getType() {
		return type;
	}

	public double getCostPerMile() {
		return costPerMile;
	}

	public double getMilesTowed() {
		return milesTowed;
	}

	public double getPrice() {
		return (costPerMile * milesTowed);
	}

	@Override
	public String toString() {
		return "(" + milesTowed + " miles @ $" + costPerMile + "/mile)";
	}

}
