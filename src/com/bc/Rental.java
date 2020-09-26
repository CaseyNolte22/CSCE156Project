package com.bc;

public class Rental extends Product {
	private char type = 'R';
	private double dailyCost;
	private double deposit;
	private double cleaningFee;

	public Rental(String code, String label, char type, double dailyCost, double deposit, double cleaningFee) {
		super(code, label);
		this.type = type;
		this.dailyCost = dailyCost;
		this.deposit = deposit;
		this.cleaningFee = cleaningFee;
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

	@Override
	public String toString() {
		return "Rental [type=" + type + ", dailyCost=" + dailyCost + ", deposit=" + deposit + ", cleaningFee="
				+ cleaningFee + ", toString()=" + super.toString() + "]";
	}

}
