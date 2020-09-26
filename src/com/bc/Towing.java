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

	@Override
	public String toString() {
		return "Towing [type=" + type + ", costPerMile=" + costPerMile + ", toString()=" + super.toString() + "]";
	}

}
