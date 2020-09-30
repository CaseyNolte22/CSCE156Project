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
	private int quantity;
	private Repair associatedRepair;

	public Concession(String code, String label, char type, double unitCost) {
		super(code, label);
		this.type = type;
		this.unitCost = unitCost;
	}

	public Concession(Concession old, int quantity, Repair associatedRepair) {
		super(old.getCode(), old.getLabel());
		this.associatedRepair = associatedRepair;
		this.quantity = quantity;
	}

	public char getType() {

		return type;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public int getQuantity() {
		return quantity;
	}

	public Repair getAssociatedRepair() {
		return associatedRepair;
	}

}
