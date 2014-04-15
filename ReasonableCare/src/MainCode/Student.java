package MainCode;
import java.sql.SQLException;
import java.util.Scanner;

public class Student {
	private static int id;
	private enum Operation {CREATE, UPDATE};
	
	// run the student scenario, providing choices and choosing appropriate responses based on user input
	public static boolean runStudentScenario(Scanner input, int s_id) throws SQLException {
		boolean loggedIn = true;
		id = s_id;
		
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
		System.out.println("10.  Log out");
		
		int choice = input.nextInt();
		input.nextLine();
		switch(choice) {
			case 1:	runAppointmentScenario(input, id);
			break;
			case 2:	viewUpcomingAppointments(id);
			break;
			case 3:	viewPastAppointments(id);
			break;
			case 4:	viewInsuranceInformation(id);
			break;
			case 5: cancelAppointment(input, id);
			break;
			case 6:	updateInsuranceInformation(input, id);
			break;
			case 7:	runDoctorSearchScenario(input);
			break;
			case 8:	ReasonableCare.showHolds(id);
			break;
			case 9:	viewBillingInfo(id);
			break;
			case 10: loggedIn = false; // this is how we log out
			break;
		}
		return loggedIn;
	}

	// ask the student to verify a date and start time for a past apppointment in order to cancel it
	public static void cancelAppointment(Scanner input, int studentID) {
		System.out.println("Please enter the date of the appointment you wish to cancel:");
		String date = input.nextLine();
		System.out.println("Please enter the start time of the appointment you wish to cancel:");
		String time = input.nextLine();
		ReasonableCare.cancelAppointment(studentID, date, time);
		//System.out.println("Your appointment was successfully cancelled.");

	}
	
	// display holds if the student's vaccinations have not been completed
	public static void viewHolds(int studentID) {
		ReasonableCare.showHolds(studentID);
	}
	
	// call method to prompt user for insurance information and create it accordingly
	public static void createInsuranceInformation(Scanner input, int studentID) {
		System.out.println("Creating insurance info.");
		createOrUpdateInsuranceInfo(input, studentID, Operation.CREATE);
	}	
	
	// call method to prompt user for insurance information and update it accordingly
	public static void updateInsuranceInformation(Scanner input, int studentID) {
		System.out.println("Updating insurance info.");
		createOrUpdateInsuranceInfo(input, studentID, Operation.UPDATE);
	}
	
	// based on operation type, prompt user for insurance information and create/update it accordingly
	private static void createOrUpdateInsuranceInfo(Scanner input, int studentID, Operation op){
		System.out.println("Please enter insurance company's name:");
		String insName = input.nextLine();
		
		System.out.println("Please enter policy number:");
		String policyNum = input.nextLine();
		
		System.out.println("Please enter policy start date (DD-MON-YYYY):");
		String start = input.nextLine();
		
		System.out.println("Please enter policy end date (DD-MON-YYYY):");
		String end = input.nextLine();
		
		System.out.println("Please enter student copayment:");
		float copayment = input.nextFloat();
		
		if (op == Operation.CREATE)
			ReasonableCare.createInsuranceInfo(studentID, insName, policyNum, start, end, copayment);
		else
			ReasonableCare.updateInsuranceInfo(studentID, insName, policyNum, start, end, copayment);
	}
	
	// guide the student through the appointment creation process
	public static void runAppointmentScenario(Scanner input, int studentID) {
		System.out.println("Enter a doctor's id (or 1 to return to the previous menu):");
		int dID = input.nextInt();
		input.nextLine();

		if (dID == 1)
			return;

		// get proposed appointment date from student and verify that the
		//   doctor is available
		String date = null;
		boolean dateIsAvailable = false;
		while(!dateIsAvailable){
			System.out.println("Enter a date (DD-MON-YYYY) or 1 to return to previous menu:");
			date = input.nextLine();
			if (date.equals("1")){
				return;
			}
			try {
				dateIsAvailable = ReasonableCare.doctorAvailable(dID, date);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			if(!dateIsAvailable) {
				System.out.println("The date you have entered is not available.");
			}
		}
		
		// get proposed appointment time from student and verify that the
		//  doctor is available (and with no conflicting appointments)
		String time = null;
		boolean timeIsAvailable = false;
		while (!timeIsAvailable){
			System.out.println("Enter a time (HH:MMPM) or 1 to return to previous menu:");	
			//TODO fix code to check for existence between times
			time = input.nextLine();
			if (time.equals("1")){
				return;
			}
			try {
				timeIsAvailable = ReasonableCare.timeAvailable(dID, date, time);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			if(!timeIsAvailable) {
				System.out.println("The time you have entered is not available.");
			}
		}
		
		System.out.println("Enter a reason for this appointment:");
		String reason = input.nextLine();
		
		//TODO get copay amount
		try {
			System.out.println("Your copay for this appointment is $" + ReasonableCare.getCopay(studentID) + ".");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		// get card info from the user
		System.out.println("Please enter your billing address:");
		String address = input.nextLine();
		System.out.println("Please enter your card company (VISA, MasterCard, etc.):");
		String company = input.nextLine();
		System.out.println("Is this a credit card? (Y/N)");
		String value = input.nextLine();
		String type = "c";
		if(value.equals("N")) {
			type = "d";
		}
		System.out.println("Please enter your card number:");
		String num = input.nextLine();
		System.out.println("Please enter your card expiration date:");
		String expDate = input.nextLine();
		// dummy method to verify payment
		boolean verified = ReasonableCare.verifyPayment(type, num, company, address, expDate);
		if(verified) {
			ReasonableCare.createAppointmentWithBilling(studentID, dID, reason, date, time, "", address, type, num, company);
		}
	}
	
	// get an appointment date and start time from the user to display their billing statement
	public static void viewBillingInfo(int studentID) {
		ReasonableCare.viewBillingInfo(studentID);
	}
	
	// display upcoming appointments for the student
	public static void viewUpcomingAppointments(int studentID) throws SQLException {
		System.out.println("Here are your current appointments:");
		ReasonableCare.viewUpcomingAppointmentInfo(studentID);
	}
	
	// display past appointments for the student
	public static void viewPastAppointments(int studentID) throws SQLException {
		System.out.println("Here are your past appointments:");
		ReasonableCare.viewPastAppointmentInfo(studentID);
	}
	
	// display insurance information for the student
	private static void viewInsuranceInformation(int studentID) {
		System.out.println("Your current insurance information is:");
		ReasonableCare.showInsuranceInfo(id);
	}
	
	// get information to initialize the doctor search
	private static void runDoctorSearchScenario(Scanner input) {
		System.out.println("Enter a specialization to search by:");
		String s = input.nextLine();
		System.out.println("Your search returned the following doctors:");
		ReasonableCare.searchForSpecialist(s);
	}
	
}
