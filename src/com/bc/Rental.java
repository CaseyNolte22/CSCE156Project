/**
 * Rental class, extends Product class
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

public class Rental extends Product {
	private char type = 'R';
	private double dailyCost;
	private double deposit;
	private double cleaningFee;
	private double daysRented;

	public Rental(String code, String label, char type, double dailyCost, double deposit, double cleaningFee) {
		super(code, label);
		this.type = type;
		this.dailyCost = dailyCost;
		this.deposit = deposit;
		this.cleaningFee = cleaningFee;
	}

	public Rental(Rental old, double daysRented) {
		super(old.getCode(), old.getLabel());
		this.dailyCost = old.getDailyCost();
		this.deposit = old.getDeposit();
		this.cleaningFee = old.getCleaningFee();
		this.daysRented = daysRented;
	}

	public char getType() {
		return type;
	}

	public double getDailyCost() {
		return dailyCost;
	}

	public double getDeposit() {
		return deposit;
	}

	public double getCleaningFee() {
		return cleaningFee;
	}

	public double getDaysRented() {
		return daysRented;
	}

	public double getPrice() {
		return ((dailyCost * daysRented) + cleaningFee - deposit);
	}

	@Override
	public String toString() {
		return "(" + daysRented + " days @ $" + dailyCost + "/day + $" + cleaningFee + " cleaning fee, -$" + deposit
				+ " refund)";
	}

}