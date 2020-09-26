package com.bc;

public class Repair extends Product {

	private char type = 'F';
	private double partsCost;
	private double hourlyLaborCost;

	public Repair(String code, String label, char type, double partsCost, double hourlyLaborCost) {
		super(code, label);
		this.type = type;
		this.partsCost = partsCost;
		this.hourlyLaborCost = hourlyLaborCost;
	}

	public char getType() {
		return type;
	}

	public double getPartsCost() {
		return partsCost;
	}

	public double getHourlyLaborCost() {
		return hourlyLaborCost;
	}

	@Override
	public String toString() {
		return "Repair [type=" + type + ", partsCost=" + partsCost + ", hourlyLaborCost=" + hourlyLaborCost
				+ ", toString()=" + super.toString() + "]";
	}

}
