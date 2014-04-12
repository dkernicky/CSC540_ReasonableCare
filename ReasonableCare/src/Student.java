import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Student {
	private static int id;
	
	public static void runStudentScenario() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please select an option:");
		System.out.println("1.  Make an appointment");
		System.out.println("2.  View upcoming appointments");
		System.out.println("3.  View past appointments");
		System.out.println("4.  View insurance information");
		System.out.println("5.  Cancel appointment");
		System.out.println("6.  Update insurance information");
		System.out.println("7.  Search for a doctor");
		System.out.println("8.  View holds");
		System.out.println("9.  View billing information");
		System.out.println("10. Update billing information");
		
		int choice = input.nextInt();
		switch(choice) {
			case 1:	runAppointmentScenario();
			break;
			case 2:	
			break;
			case 3:	
			break;
			case 4:	
			break;
			case 5:	
			break;
			case 6:	
			break;
			case 7:	
			break;
			case 8:	
			break;
			case 9:	
			break;
			case 10:	
			break;
		}
	}
	
	private static void runAppointmentScenario() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a doctor's name (or 1 to return to the previous menu):");
		String name = input.nextLine();
		System.out.println("Enter a day");
		//TODO if time or date invalid, request a new one
	}
	
	private static void viewUpcomingAppointments() {
		
	}
	
}
