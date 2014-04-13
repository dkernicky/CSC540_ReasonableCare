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
			case 2:	viewUpcomingAppointments();
			break;
			case 3:	viewPastAppointments();
			break;
			case 4:	viewInsuranceInformation();
			break;
			case 5: cancelAppointment();
			break;
			case 6:	updateInsuranceInformation();
			break;
			case 7:	runDoctorSearchScenario();
			break;
			case 8:	viewHolds();
			break;
			case 9:	viewBillingInfo();
			break;
		}
	}

	private static void cancelAppointment() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the date of the appointment you wish to cancel:");
		String date = input.nextLine();
		System.out.println("Please enter the time of the appointment you wish to cancel:");
		String time = input.nextLine();
		System.out.println("Your appointment was successfully cancelled.");

	}
	
	private static void viewHolds() {
		System.out.println("You have a hold on your account, lacking the required vaccinations.");
		System.out.println("You have no holds on your account.");
	}
	
	private static void updateInsuranceInformation() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter your insurance company's name:");
		String name = input.nextLine();
		System.out.println("Please enter your policy number:");
		String num = input.nextLine();
	}
	
	private static void runAppointmentScenario() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a doctor's name (or 1 to return to the previous menu):");
		String name = input.nextLine();
		System.out.println("Enter a date (DD-MON-YYYY):");
		String date = input.nextLine();
		System.out.println("Enter a time (HH:MMPM):");
		//TODO if time or date invalid, request a new one

		String time = input.nextLine();
		//TODO get copay amount
		System.out.println("Your copay for this appointment is ");
		System.out.println("Please enter your billing address:");
		String address = input.nextLine();
		System.out.println("Please enter your card company (VISA, MasterCard, etc.):");
		String company = input.nextLine();
		System.out.println("Is this a credit card? (Y/N)");
		String value = input.nextLine();
		System.out.println("Please enter your card number:");
		String num = input.nextLine();
		System.out.println("Please enter your card expiration date:");
		String expDate = input.nextLine();
		//TODO  dummy method to verify payment
		System.out.println("Your appointment was successfully saved.");

	}
	
	private static void viewBillingInfo() {
		
	}
	
	private static void viewUpcomingAppointments() {
		System.out.println("Here are your current appointments:");
	}
	
	private static void viewPastAppointments() {
		System.out.println("Here are your past appointments:");
	}
	
	private static void viewInsuranceInformation() {
		System.out.println("Your current insurance information is:");
	}
	
	private static void runDoctorSearchScenario() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a specialization to search by:");
		String s = input.nextLine();
		System.out.println("Your search returned the following doctors:");
		ReasonableCare.studentSearchForSpecialist(s);
	}
	
}
