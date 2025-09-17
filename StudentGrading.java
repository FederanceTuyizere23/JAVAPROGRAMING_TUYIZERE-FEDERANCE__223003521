package my.gradingly;

import java.util.Scanner;

public class StudentGrading {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		        Scanner ST= new Scanner(System.in);

		                int totalStudents = 0;
		                int passCount = 0;
		                String choice = "Y";

		                while (choice.equalsIgnoreCase("Y")) {
		                    totalStudents++;
		                    System.out.print("Enter student marks (0-100): ");
		                    int marks = ST.nextInt();
		                    if (marks >= 80) {
		                        System.out.println("Grade: A");
		                        passCount++;
		                    } else if (marks >= 70) {
		                        System.out.println("Grade: B");
		                        passCount++;
		                    } else if (marks >= 60) {
		                        System.out.println("Grade: C");
		                        passCount++;
		                    } else if (marks >= 50) {
		                        System.out.println("Grade: D");
		                        passCount++;
		                    } else {
		                        System.out.println("Grade: F");
		                    }
		                    System.out.print("Do you want to enter another student? (Y/N): ");
		                    choice = ST.next();
		                }
		                System.out.println("\nTotal students: " + totalStudents);
		                System.out.println("Passed: " + passCount);
		                System.out.println("Failed: " + (totalStudents - passCount));

		                ST.close();
		            }
		        }
