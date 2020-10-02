/** 
 * Contains method for outputting a formatted file of invoice data
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

import java.io.File;
import java.util.ArrayList;

public class InvoiceWriter {
	/**
	 * Uses printwriter to generate the invoice output.
	 * 
	 * 
	 * @param file
	 * @param invoices
	 */
	public static void invoicePrint(File file, ArrayList<Invoice> invoices) {
		/**
		 * This section generates the overall report
		 */
		System.out.println("Executive Summary Report:");
		System.out.println("");
		System.out.println(String.format("%-10s %-20s %-30s %-12s %-12s %-12s %-12s %-12s", "Code", "Owner",
				"Customer Account", "Subtotal", "Discounts", "Fees", "Taxes", "Total"));
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------");
		double totalSubtotal = 0;
		double totalDiscount = 0;
		double totalFees = 0;
		double totalTaxes = 0;
		double totalTotal = 0;
		for (Invoice item : invoices) {

			String code = item.getInvoiceCode();
			String owner = (item.getOwnerCode()).getLastName() + "," + (item.getOwnerCode()).getFirstName();
			String customerAccount = (item.getCustomerCode()).getName();
			double subtotal = item.getSubtotal();
			double fees = item.getFees();
			double discount = item.getConcessionDiscount() + item.getTowingDiscount();
			double preTotal = (subtotal + discount);
			double taxes = item.getTax(preTotal);
			preTotal = (subtotal + discount + fees + taxes);
			double postTotal = preTotal + item.getPostTaxDiscount(preTotal);
			String formattedSubtotal = String.format("%-1s %.2f", "$", item.getSubtotal());
			String formattedFees = String.format("%-1s %.2f", "$", item.getFees());
			String formattedDiscount = String.format("%-1s %.2f", "$", (discount + item.getPostTaxDiscount(preTotal)));
			String formattedTaxes = String.format("%-1s %.2f", "$", item.getTax(subtotal));
			String formattedTotal = String.format("%-1s %.2f", "$", postTotal);
			System.out.println(String.format("%-10s %-20s %-30s %-12s %-12s %-12s %-12s %-12s", code, owner, customerAccount,
					formattedSubtotal, formattedDiscount, formattedFees, formattedTaxes, formattedTotal));
			totalSubtotal += subtotal;
			totalDiscount += discount + item.getPostTaxDiscount(preTotal);
			totalFees += fees;
			totalTaxes += taxes;
			totalTotal += postTotal;
		}
		System.out.println(
				"============================================================================================================================");
		String formattedTotalSubtotal = String.format("%-1s %.2f", "$", totalSubtotal);
		String formattedTotalDiscount = String.format("%-1s %.2f", "$", totalDiscount);
		String formattedTotalFees = String.format("%-1s %.2f", "$", totalFees);
		String formattedTotalTaxes = String.format("%-1s %.2f", "$", totalTaxes);
		String formattedTotalTotal = String.format("%-1s %.2f", "$", totalTotal);
		System.out.println(String.format("%-10s %-20s %-30s %-12s %-12s %-12s %-12s %-12s", "TOTALS", "", "",
				formattedTotalSubtotal, formattedTotalDiscount, formattedTotalFees, formattedTotalTaxes,
				formattedTotalTotal));
		System.out.println();
		System.out.println();
		System.out.println();
		/**
		 * This section generates an invoice detail for every invoice provided
		 */
		System.out.println("Invoice Details");
		for (Invoice item : invoices) {
			System.out.println(
					"=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
			System.out.println(item.getInvoiceCode());
			System.out.println("------------------------------------------");
			System.out.println("Owner:");
			System.out.println("	" + item.getOwnerCode().getLastName() + "," + item.getOwnerCode().getFirstName());
			System.out.println("	" + item.getOwnerCode().getEmails());
			System.out.println("	" + item.getOwnerCode().getPersonAddress());
			System.out.println("Customer:");
			System.out.println("	" + item.getCustomerCode().getName());
			System.out.println("	" + item.getCustomerCode().getCustomerAddress());
			System.out.println("Products:");
			System.out.println(String.format("%-2s %-10s %-75s %-12s %-12s %-12s %-12s", "", "Code", "Description", "Subtotal",
					"Discount", "Taxes", "Total"));
			System.out.println(
					"  ---------------------------------------------------------------------------------------------------------------------------------------");
			totalSubtotal = 0;
			totalDiscount = 0;
			totalTaxes = 0;
			totalTotal = 0;
			for (Product product : item.getProductList()) {
				String code = product.getCode();
				String description = product.getLabel();
				String descriptionExtended = product.toString();
				double subtotal = product.getPrice();
				double discount = 0;
				if (product instanceof Towing) {
					discount = item.getTowingDiscount();
				}
				if (product instanceof Concession) {
					discount = item.getConcessionDiscount();
				}
				double taxes = item.getTax((subtotal + discount));
				double total = (subtotal + discount + taxes);
				String formattedSubtotal = String.format("%-1s %.2f", "$", subtotal);
				String formattedDiscount = String.format("%-1s %.2f", "$", discount);
				String formattedTaxes = String.format("%-1s %.2f", "$", taxes);
				String formattedTotal = String.format("%-1s %.2f", "$", total);
				System.out.println(String.format("%-2s %-10s %-75s %-12s %-12s %-12s %-12s", "", code, description,
						formattedSubtotal, formattedDiscount, formattedTaxes, formattedTotal));
				System.out.println(String.format("%-2s %-10s %-75s %-12s %-12s %-12s %-12s", "", "", descriptionExtended, "",
						"", "", ""));
				totalSubtotal += subtotal;
				totalDiscount += discount;
				totalTaxes += taxes;
				totalTotal += total;
			}
			String formattedTotalSubtotalSpecific = String.format("%-1s %.2f", "$", totalSubtotal);
			String formattedTotalDiscountSpecific = String.format("%-1s %.2f", "$", totalDiscount);
			String formattedTotalTaxesSpecific = String.format("%-1s %.2f", "$", totalTaxes);
			String formattedTotalTotalSpecific = String.format("%-1s %.2f", "$", totalTotal);
			System.out.println(
					"=========================================================================================================================================");
			System.out.println(
					String.format("%s %-77s %-12s %-12s %-12s %-12s", "ITEM TOTALS", "", formattedTotalSubtotalSpecific,
							formattedTotalDiscountSpecific, formattedTotalTaxesSpecific, formattedTotalTotalSpecific));
			double fee = 0;
			if (item.getCustomerCode().getCustomerType().equals("B")) {
				fee = 75.50;
				String formattedFee = String.format("%-1s %.2f", "$", fee);
				System.out.println(String.format("%s %107s %s", "BUSINESS ACCOUNT FEE", "", formattedFee));
			}
			double loyalDiscount = item.getPostTaxDiscount(totalTotal);
			if (loyalDiscount != 0) {
				String formattedLoyalDiscount = String.format("%-1s %.2f", "$", loyalDiscount);
				System.out.println(String.format("%s %104s %s", "LOYAL CUSTOMER DISCOUNT", "", formattedLoyalDiscount));
			}
			double grandTotal = fee + totalTotal + loyalDiscount;
			String formattedGrandTotal = String.format("%-1s %.2f", "$", grandTotal);
			System.out.println(String.format("%s %116s %s", "GRAND TOTAL", "", formattedGrandTotal));
			System.out.println();
			System.out.println();
			System.out.println("THANK YOU FOR DOING BUSINESS WITH US!");
			System.out.println();
		}
	}
}
