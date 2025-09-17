package my.attendance;

import java.util.Scanner;

public class AttendanceTracker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		        Scanner TR = new Scanner(System.in);

		      		 System.out.print("Enter total number of students in the class: ");
		                int totalStudents = TR.nextInt();

		                int[] attendance = new int[30];
		                int day = 0;
		                String choice;

		                do {
		                    day++;
		                    System.out.print("Enter number of students present on day " + day + ": ");
		                    attendance[day - 1] = TR.nextInt();

		                    System.out.print("Do you want to enter attendance for another day? (yes/no): ");
		                    choice = TR.next().toLowerCase();
		                    switch (choice) {
		                        case "yes":
		                            break;
		                        case "no":
		                            break;
		                        default:
		                            System.out.println("Invalid input. Assuming 'no'.");
		                            choice = "no";
		                            break;
		                    }

		                } while (choice.equals("yes") && day < 30);

		                int sum = 0, lowDays = 0;

		                System.out.println("          ATTENDANCE LIST           ");
		                System.out.println("______________________________________");
		                for (int i = 0; i < day; i++) {
		                    System.out.println("Day " + (i + 1) + " → " + attendance[i] + " present");
		                    sum += attendance[i];
		                    if (attendance[i] < totalStudents / 2.0) lowDays++;
		                }

		                System.out.println("\nAverage attendance: " + (sum / (double) day));
		                System.out.println("Days with low attendance (<50%): " + lowDays + 
		                                   " (" + (lowDays * 100.0 / day) + "%)");

		                TR.close();
		            }
		        }
