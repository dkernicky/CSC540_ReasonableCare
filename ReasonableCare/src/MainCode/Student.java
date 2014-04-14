package MainCode;
import java.sql.SQLException;
import java.util.Scanner;

public class Student {
	private static int id;
	
	public static void runStudentScenario(int s_id) throws SQLException {
		Scanner input = new Scanner(System.in);
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
		System.out.println("10. Update billing information");
		
		int choice = input.nextInt();
		switch(choice) {
			case 1:	runAppointmentScenario(id);
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
		System.out.println("Please enter the start time of the appointment you wish to cancel:");
		String time = input.nextLine();
		ReasonableCare.cancelAppointment(id, date, time);
		System.out.println("Your appointment was successfully cancelled.");

	}
	
	private static void viewHolds() {
		System.out.println("You have a hold on your account, lacking the required vaccinations.");
		System.out.println("You have no holds on your account.");
	}
	
	private static void updateInsuranceInformation() throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter your insurance company's name:");
		String name = input.nextLine();
		System.out.println("Please enter your policy number:");
		String num = input.nextLine();
		ReasonableCare.updateInsuranceInfo(id, name, num);
	}
	
	public static void runAppointmentScenario(int studentID) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a doctor's name (or 1 to return to the previous menu):");
		String name = input.nextLine();
		
		if (name.equals("1"))
			return;
		int dID = -1;
		dID = ReasonableCare.searchForSpecialistByName(name);
		
		String date = null;
		boolean dateIsAvailable = false;
		while(!dateIsAvailable){
			System.out.println("Enter a date (DD-MON-YYYY) or 1 to return to previous menu:");
			date = input.nextLine();
			if (date.equals("1"))
				return;
			dateIsAvailable = ReasonableCare.doctorAvailable(dID, date);
			if(!dateIsAvailable) {
				System.out.println("The date you have entered is not available.");
			}
		}
		
		String time = null;
		boolean timeIsAvailable = false;
		while (!timeIsAvailable){
			System.out.println("Enter a time (HH:MMPM) or 1 to return to previous menu:");	
			//TODO fix code to check for existence between times
			time = input.nextLine();
			if (time.equals("1"))
				return;
			timeIsAvailable = ReasonableCare.timeAvailable(dID, date, time);
			if(!timeIsAvailable) {
				System.out.println("The time you have entered is not available.");
			}
		}
		//TODO get copay amount
		System.out.println("Your copay for this appointment is $" + ReasonableCare.getCopay(studentID) + ".");

		System.out.println("Please enter your billing address:");
		String address = input.nextLine();
		System.out.println("Please enter your card company (VISA, MasterCard, etc.):");
		String company = input.nextLine();
		System.out.println("Is this a credit card? (Y/N)");
		String value = input.nextLine();
		String type = "credit";
		if(value.equals("N")) {
			type = "debit";
		}
		System.out.println("Please enter your card number:");
		String num = input.nextLine();
		System.out.println("Please enter your card expiration date:");
		String expDate = input.nextLine();
		// dummy method to verify payment
		boolean verified = ReasonableCare.verifyPayment(type, num, company, address, expDate);
		if(verified) {
			//ReasonableCare.createAppointment(id, dID, reason, date, time, "N/A", amt, "");
			System.out.println("Your appointment was successfully saved.");
		}
	}
	
	private static void viewBillingInfo() {
		
	}
	
	private static void viewUpcomingAppointments() throws SQLException {
		System.out.println("Here are your current appointments:");
		ReasonableCare.viewUpcomingAppointmentInfo(id);
	}
	
	private static void viewPastAppointments() throws SQLException {
		System.out.println("Here are your past appointments:");
		ReasonableCare.viewPastAppointmentInfo(id);
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
