/**
 * Repair class, extends Product class
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

public class Repair extends Product {

	private char type = 'F';
	private double partsCost;
	private double hourlyLaborCost;
	private double hoursWorked;

	public Repair(String code, String label, char type, double partsCost, double hourlyLaborCost) {
		super(code, label);
		this.type = type;
		this.partsCost = partsCost;
		this.hourlyLaborCost = hourlyLaborCost;
	}

	public Repair(Repair old, double hoursWorked) {
		super(old.getCode(), old.getLabel());
		this.hoursWorked = hoursWorked;
		this.partsCost = old.getPartsCost();
		this.hourlyLaborCost = old.getHourlyLaborCost();
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

	public double getHoursWorked() {
		return hoursWorked;
	}

	public double getPrice() {
		return (partsCost + (hourlyLaborCost * hoursWorked));
	}

	@Override
	public String toString() {
		return "(" + hoursWorked + " hours of labor @ $" + hourlyLaborCost + "/hour + $" + partsCost + " for parts)";
	}

}
