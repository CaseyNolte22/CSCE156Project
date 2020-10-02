/** 
 * Contains method for outputting a formatted file of invoice data
 * 
 * @author Casey Nolte
 * @author Jack Kieny
 */
package com.bc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
		PrintWriter out = null;
		try {
			out = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/**
		 * This section generates the overall report
		 */
		out.println("Executive Summary Report:");
		out.println("");
		out.println(String.format("%-10s %-20s %-30s %-12s %-12s %-12s %-12s %-12s", "Code", "Owner",
				"Customer Account", "Subtotal", "Discounts", "Fees", "Taxes", "Total"));
		out.println(
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
			out.println(String.format("%-10s %-20s %-30s %-12s %-12s %-12s %-12s %-12s", code, owner, customerAccount,
					formattedSubtotal, formattedDiscount, formattedFees, formattedTaxes, formattedTotal));
			totalSubtotal += subtotal;
			totalDiscount += discount + item.getPostTaxDiscount(preTotal);
			totalFees += fees;
			totalTaxes += taxes;
			totalTotal += postTotal;
		}
		out.println(
				"============================================================================================================================");
		String formattedTotalSubtotal = String.format("%-1s %.2f", "$", totalSubtotal);
		String formattedTotalDiscount = String.format("%-1s %.2f", "$", totalDiscount);
		String formattedTotalFees = String.format("%-1s %.2f", "$", totalFees);
		String formattedTotalTaxes = String.format("%-1s %.2f", "$", totalTaxes);
		String formattedTotalTotal = String.format("%-1s %.2f", "$", totalTotal);
		out.println(String.format("%-10s %-20s %-30s %-12s %-12s %-12s %-12s %-12s", "TOTALS", "", "",
				formattedTotalSubtotal, formattedTotalDiscount, formattedTotalFees, formattedTotalTaxes,
				formattedTotalTotal));
		out.println();
		out.println();
		out.println();
		/**
		 * This section generates an invoice detail for every invoice provided
		 */
		out.println("Invoice Details");
		for (Invoice item : invoices) {
			out.println(
					"=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
			out.println(item.getInvoiceCode());
			out.println("------------------------------------------");
			out.println("Owner:");
			out.println("	" + item.getOwnerCode().getLastName() + "," + item.getOwnerCode().getFirstName());
			out.println("	" + item.getOwnerCode().getEmails());
			out.println("	" + item.getOwnerCode().getPersonAddress());
			out.println("Customer:");
			out.println("	" + item.getCustomerCode().getName());
			out.println("	" + item.getCustomerCode().getCustomerAddress());
			out.println("Products:");
			out.println(String.format("%-2s %-10s %-75s %-12s %-12s %-12s %-12s", "", "Code", "Description", "Subtotal",
					"Discount", "Taxes", "Total"));
			out.println(
					"  ---------------------------------------------------------------------------------------------------------------------------------------");
			totalSubtotal = 0;
			totalDiscount = 0;
			totalFees = 0;
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
				out.println(String.format("%-2s %-10s %-75s %-12s %-12s %-12s %-12s", "", code, description,
						formattedSubtotal, formattedDiscount, formattedTaxes, formattedTotal));
				out.println(String.format("%-2s %-10s %-75s %-12s %-12s %-12s %-12s", "", "", descriptionExtended, "",
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
			out.println(
					"=========================================================================================================================================");
			out.println(
					String.format("%s %-77s %-12s %-12s %-12s %-12s", "ITEM TOTALS", "", formattedTotalSubtotalSpecific,
							formattedTotalDiscountSpecific, formattedTotalTaxesSpecific, formattedTotalTotalSpecific));
			double fee = 0;
			if (item.getCustomerCode().getCustomerType().equals("B")) {
				fee = 75.50;
				String formattedFee = String.format("%-1s %.2f", "$", fee);
				out.println(String.format("%s %107s %s", "BUSINESS ACCOUNT FEE", "", formattedFee));
			}
			double loyalDiscount = item.getPostTaxDiscount(totalTotal);
			if (loyalDiscount != 0) {
				String formattedLoyalDiscount = String.format("%-1s %.2f", "$", loyalDiscount);
				out.println(String.format("%s %104s %s", "LOYAL CUSTOMER DISCOUNT", "", formattedLoyalDiscount));
			}
			double grandTotal = fee + totalTotal + loyalDiscount;
			String formattedGrandTotal = String.format("%-1s %.2f", "$", grandTotal);
			out.println(String.format("%s %116s %s", "GRAND TOTAL", "", formattedGrandTotal));
			out.println();
			out.println();
			out.println("THANK YOU FOR DOING BUSINESS WITH US!");
			out.println();
		}
		out.close();
	}
}
