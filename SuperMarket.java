package my.supermarket;

import java.util.Scanner;
public class SuperMarket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner SP = new Scanner(System.in);

		System.out.println("How many items? ");
		int numstd= SP.nextInt();

		String[] itemNames = new String[numstd];
		double[] prices = new double[numstd];
		int[] quantities = new int[numstd];
		double[] subtotals = new double[numstd];

		double grandTotal = 0;


		for (int i = 0; i < numstd; i++) {
			System.out.println(" Enter item details :  " + (i + 1));
			System.out.println(" Item name: ");
			itemNames[i] = SP.next();
			System.out.println(" Price per unit: ");
			prices[i] = SP.nextDouble();
			System.out.println("Quantity purchased: ");
			quantities[i] = SP.nextInt();

			subtotals[i] = prices[i] * quantities[i];
			grandTotal += subtotals[i];
		}

		double discount = 0;
		if (grandTotal > 50000) {
			discount = grandTotal * 0.05;
		}
		double finalAmount = grandTotal - discount;

		System.out.println("---SUPERMARKET RECEIPT ---");
		System.out.println("--------------------------");
		for (int i = 0; i < numstd; i++) {
			System.out.println("Item: " + itemNames[i]);
			System.out.println("Quantity: " + quantities[i]);
			System.out.println("Price per unit: " + prices[i]);
			System.out.println("Subtotal: " + subtotals[i]);
			System.out.println("-----------------------");
		}
		System.out.println("Grand Total: " + grandTotal);
		System.out.println("Discount: " + discount);
		System.out.println("Final Amount Payable: " + finalAmount);
		System.out.println("--------------------------");

		SP.close();
	}


}
