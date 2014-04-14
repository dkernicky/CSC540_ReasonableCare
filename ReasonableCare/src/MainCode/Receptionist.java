package MainCode;

import java.sql.SQLException;
import java.util.Scanner;


/**
 * This class encapsulates operations performed by the receptionist. 
 * It provides a command line interface for the following operations:
 * 
 * 1. Make appointment
 * 2. Cancel appointment
 * 3. Create student acct.
 * 4. Modify student acct.
 * 5. Create student ins. info
 * 6. Update student ins. info
 * 7. View student acct. holds
 * 8. View student bill
 * 9. Create staff acct.
 * 10. Update staff acct.
 * 11. Log out
 *
 */
public class Receptionist {
	private static int id;
	private enum Operation {CREATE, UPDATE};
	
	/**
	 * Command line interface for receptionist.
	 * 
	 * @return true if still logged in, false if logged out
	 * @throws SQLException
	 * 
	 */
	public static boolean runReceptionistScenario() throws SQLException {
		boolean loggedIn = true;
		
		// display menu
		System.out.println("Please select an option:");
		System.out.println("1.  Make an appointment");
		System.out.println("2.  Cancel an appointment");
		System.out.println("3.  Create a student account");
		System.out.println("4.  Update student information");
		System.out.println("5.  Create student insurance info");
		System.out.println("6.  Update student insurance info");
		System.out.println("7.  View student holds");
		System.out.println("8.  View student billing statement");
		System.out.println("9.  Create a staff account");
		System.out.println("10.  Update staff information");		
		System.out.println("11.  Log out");
		
		// take user selection
		Scanner input = new Scanner(System.in);		
		int choice = input.nextInt(); input.nextLine();
		
		// handle the user selection
		switch(choice) {
			case 1:	createStudentAppointment();
			break;
			case 2: cancelStudentAppointment();
			break;
			case 3:	createOrUpdateStudentAccount(Operation.CREATE);
			break;
			case 4:	createOrUpdateStudentAccount(Operation.UPDATE);
			break;
			case 5: createStudentInsuranceInfo(); 
			break;
			case 6: updateStudentInsuranceInfo(); 
			break;
			case 7: viewStudentHolds();
			break;
			case 8: viewStudentBillingStatement();
			break;
			case 9:	createStaffAccount();
			break;
			case 10:updateStaffInformation();
			break;
			case 11:loggedIn = false; // this is how we log out
			break;
			
		}
		return loggedIn;
	}

	/**
	 * Displays student holds, if any. Provides a reason,
	 *  such as insufficient number of vaccinations.
	 */
	private static void viewStudentHolds() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt(); input.nextLine();
		Student.viewHolds(studentID);
	}

	/**
	 * Displays a bill for an student's appointment
	 */
	private static void viewStudentBillingStatement() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt(); input.nextLine();
		
		Student.viewBillingInfo(studentID);
	}

	/**
	 * Cancels a student's appointment
	 */
	private static void cancelStudentAppointment() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt(); input.nextLine();
		
		Student.cancelAppointment(studentID);
	}

	/**
	 * Adds insurance info associated with student acct
	 */
	private static void createStudentInsuranceInfo() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt(); input.nextLine();
		
		Student.createInsuranceInformation(studentID);
	}
	
	/**
	 * Updates insurance info associated with student acct
	 */
	private static void updateStudentInsuranceInfo() {		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt(); input.nextLine();
		
		Student.updateInsuranceInformation(studentID);
	}


	/**
	 * Creates an account for Receptionist, Nurse, or Doctor
	 */
	public static void createStaffAccount() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the staff ID:");
		int staffID = input.nextInt(); input.nextLine();
		
		System.out.println("Please enter the staff name:");
		String name = input.nextLine();
		
		System.out.println("Please enter a gender (M/F):");
		char gender = input.nextLine().toUpperCase().charAt(0);
		
		System.out.println("Please enter the staff age:");
		int age = input.nextInt(); input.nextLine();
		
		System.out.println("Please enter the staff address:");
		String address = input.nextLine();

		System.out.println("Please enter the staff phone number (10 digits):");
		String phone = input.nextLine();

		System.out.println("Please enter the job title (Doctor, Nurse, or Receptionist):");
		char jobTitle = input.nextLine().toUpperCase().charAt(0);
		
		String department = "";
		
		// enter department for doctor or nurse (not receptionist)
		if(jobTitle != 'R') { 
			System.out.println("Please enter the department:");
			department = input.nextLine();
		}
		
		
		if(jobTitle == 'D'){ 
			// enter professional title for doctor
			System.out.println("Please enter professional title:");
			String profTitle = input.nextLine();
			
			//create doctor
			ReasonableCare.createDoctor(staffID, name, age, gender, jobTitle, profTitle, department, phone, address);
		}
		else { 
			// create nurse or receptionist			
			ReasonableCare.createStaff(staffID, name, age, gender, jobTitle, department, phone, address);
		}
	}

	/**
	 * Updates account info for Doctors, Nurses, and Receptionists
	 */
	public static void updateStaffInformation() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the staff ID:");
		int id = input.nextInt(); input.nextLine();

		// NO DEFAULT VLAUES, NO DATABASE LOOKUP, REENTER EVERYTHING.

		System.out.println("Please enter staff name:");
		String name = input.nextLine();

		System.out.println("Please enter staff gender (M/F):");
		char gender = input.nextLine().charAt(0);

		System.out.println("Please enter staff age:");
		int age = input.nextInt(); input.nextLine();

		System.out.println("Please enter staff address:");
		String address = input.nextLine();
		
		System.out.println("Please enter staff phone number (10 digits):");
		String phone = input.nextLine();

		System.out.println("Please enter staff job title:");
		char jobTitle = input.nextLine().toUpperCase().charAt(0);

		String department = "";
		
		// doctors and nurses need department (not receptionists)
		if(jobTitle != 'R'){
			System.out.println("Please enter staff department:");
			department = input.nextLine();
		}

		// doctors need professional title
		if(jobTitle == 'D'){
			System.out.println("Please enter the new professional title:");
			String profTitle = input.nextLine();
			ReasonableCare.updateDoctor(id, name, age, gender, phone, address, jobTitle, department, profTitle);
		}
		else
			ReasonableCare.updateStaff(id, name, age, gender, phone, address, jobTitle, department);
		
	}
	
	/**
	 * Creates a student appointment
	 */
	public static void createStudentAppointment(){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt(); input.nextLine();
		
		Student.runAppointmentScenario(studentID);
		input.close();
	}

	/**
	 * Takes input for creating or updating student acct
	 * 
	 * @param op specifies whether this operation is create or update
	 */
	public static void createOrUpdateStudentAccount(Operation op){
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt(); input.nextLine();
		
		System.out.println("Please enter the student's name:");
		String name = input.nextLine();
		
		System.out.println("Please enter a gender (M/F):");
		char gender = input.nextLine().toUpperCase().charAt(0);
		
		System.out.println("Please enter the student's DOB (DD-MMM-YYYY):");
		String dateOfBirth = input.nextLine();
		
		System.out.println("Please enter the student's age:");
		int age = input.nextInt(); input.nextLine();
		
		System.out.println("Please enter the student's SSN:");
		String ssn = input.nextLine();
		
		System.out.println("Please enter the student's address:");
		String address = input.nextLine();

		System.out.println("Please enter the student's phone number (10 digits):");
		String phone = input.nextLine();

		System.out.println("Please enter the number of vaccinations completed:");
		int vaccNum = input.nextInt(); input.nextLine();
		
		if(op == Operation.CREATE)
			ReasonableCare.createStudent(studentID, name, age, gender, phone, address, dateOfBirth, ssn, vaccNum);
		else
			ReasonableCare.updateStudent(studentID, name, age, gender, phone, address, dateOfBirth, ssn, vaccNum);
		
		input.close();
	}
}