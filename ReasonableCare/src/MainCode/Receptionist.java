package MainCode;

import java.sql.SQLException;
import java.util.Scanner;


public class Receptionist {
	private static int id;
	private enum Operation {CREATE, UPDATE};
	
	public static boolean runReceptionistScenario() throws SQLException {
		boolean loggedIn = true;
		
		Scanner input = new Scanner(System.in);		
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
		int choice = input.nextInt();
		
// ADD INSURANCE INFO FOR STUDENT
		
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
			case 11:loggedIn = false;
			break;
			
		}
		return loggedIn;
	}

	private static void viewStudentHolds() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		Student.viewHolds(studentID);
	}

	private static void viewStudentBillingStatement() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		
		Student.viewBillingInfo(studentID);
	}

	private static void cancelStudentAppointment() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		
		Student.cancelAppointment(studentID);
	}

	private static void createStudentInsuranceInfo() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		
		Student.createInsuranceInformation(studentID);
	}
	
	private static void updateStudentInsuranceInfo() {
		
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		
		Student.updateInsuranceInformation(studentID);
	}


	public static void createStaffAccount() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the staff ID:");
		int staffID = input.nextInt();
		
		System.out.println("Please enter the staff name:");
		String name = input.nextLine();
		
		System.out.println("Please enter a gender (M/F):");
		char gender = input.nextLine().toUpperCase().charAt(0);
		
		System.out.println("Please enter the staff age:");
		int age = input.nextInt();
		
		System.out.println("Please enter the staff address:");
		String address = input.nextLine();

		System.out.println("Please enter the staff phone number (10 digits):");
		String phone = input.nextLine();

		System.out.println("Please enter the job title (Doctor, Nurse, or Receptionist):");
		char jobTitle = input.nextLine().toUpperCase().charAt(0);
		
		String department = "";
		if(jobTitle != 'R') { // doctor or nurse
			System.out.println("Please enter the department:");
			department = input.nextLine();
		}
		
		if(jobTitle == 'D'){ // doctor
			System.out.println("Please enter professional title:");
			String profTitle = input.nextLine();
			//createDoctor(int id, String name, int age, char gender, char jobTitle, String profTitle, String department, String phone, String address)
			ReasonableCare.createDoctor(staffID, name, age, gender, jobTitle, profTitle, department, phone, address);
		}
		else { // nurse or receptionist
			//createStaff(int id, String name, int age, char gender, char jobTitle, String department, String phone, String address)
			ReasonableCare.createStaff(staffID, name, age, gender, jobTitle, department, phone, address);
		}
	}

	public static void updateStaffInformation() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the staff ID:");
		int id = input.nextInt();

		// NO DEFAULT VLAUES, NO DATABASE LOOKUP, REENTER EVERYTHING.

		System.out.println("Please enter staff name:");
		String name = input.nextLine();

		System.out.println("Please enter staff gender (M/F):");
		char gender = input.nextLine().charAt(0);

		System.out.println("Please enter staff age:");
		int age = input.nextInt();

		System.out.println("Please enter staff address:");
		String address = input.nextLine();
		
		System.out.println("Please enter staff phone number (10 digits):");
		String phone = input.nextLine();

		System.out.println("Please enter staff job title:");
		char jobTitle = input.nextLine().toUpperCase().charAt(0);

		String department = "";
		if(jobTitle != 'R'){
			System.out.println("Please enter staff department:");
			department = input.nextLine();
		}

		if(jobTitle == 'D'){
			System.out.println("Please enter the new professional title:");
			String profTitle = input.nextLine();
			ReasonableCare.updateDoctor(id, name, age, gender, phone, address, jobTitle, department, profTitle);
		}
		else
			ReasonableCare.updateStaff(id, name, age, gender, phone, address, jobTitle, department);
	}
	
	public static void createStudentAppointment(){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		
		Student.runAppointmentScenario(studentID);
		input.close();
	}

	public static void createOrUpdateStudentAccount(Operation op){
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		
		System.out.println("Please enter the student's name:");
		String name = input.nextLine();
		
		System.out.println("Please enter a gender (M/F):");
		char gender = input.nextLine().toUpperCase().charAt(0);
		
		System.out.println("Please enter the student's DOB (DD-MMM-YYYY):");
		String dateOfBirth = input.nextLine();
		
		System.out.println("Please enter the student's age:");
		int age = input.nextInt();
		
		System.out.println("Please enter the student's SSN:");
		String ssn = input.nextLine();
		
		System.out.println("Please enter the student's address:");
		String address = input.nextLine();

		System.out.println("Please enter the student's phone number (10 digits):");
		String phone = input.nextLine();

		System.out.println("Please enter the number of vaccinations completed:");
		int vaccNum = input.nextInt();
		
		ReasonableCare.createStudent(studentID, name, age, gender, phone, address, dateOfBirth, ssn, vaccNum);
		input.close();
	}
	
	public static void updateStudentInformation() {
		Scanner input = new Scanner(System.in);
		// updateStudent(int id, String name, int age, char gender, String phone, String address, String dateOfBirth, String ssn, int vacc)
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		boolean saveAndExitLoop = false;
		
			
			/*
			 * Get current student information from DB into local variables,
			 * then ask receptionist which values to change inside a loop.
			 * after exiting the loop, update the student record
			 */
		
		//name, age, genderChar, phone, address, dateOfBirth, ssn, vaccNum
		String name = null;
		char genderChar = '\0';
		String dateOfBirth = null;
		int age = 0;
		String ssn = null;
		String address = null;
		String phone = null;
		int vaccNum = 0;
		
		while (!saveAndExitLoop){
			// display menu
			System.out.println("UPDATING STUDENT INFORMATION");
			System.out.println("Please select an option:");
			System.out.println("1.  Update the student's name");
			System.out.println("2.  Update the student's gender");
			System.out.println("3.  Update the student's DOB");
			System.out.println("4.  Update the student's age");
			System.out.println("5.  Update the student's SSN");
			System.out.println("6.  Update the student's address");
			System.out.println("7.  Update the student's phone");
			System.out.println("8.  Update the student's vaccinations");
			System.out.println("9.  Save student data and exit");
			
			int choice = input.nextInt();
			switch(choice) {
			case 1:	// name
				System.out.println("Please enter the new name:");
				name = input.nextLine();
			break;
			case 2:	// gender
				System.out.println("Please enter the new gender (M/F):");
				genderChar = input.nextLine().charAt(0);
			break;
			case 3:	// DOB
				System.out.println("Please enter the new DOB (DD-MMM-YYYY):");
				dateOfBirth = input.nextLine();
			break;
			case 4:	// age
				System.out.println("Please enter the new age:");
				age = input.nextInt();
			break;
			case 5:	// ssn
				System.out.println("Please enter the new SSN:");
				ssn = input.nextLine();
			break;
			case 6: // address
				System.out.println("Please enter the new address:");
				address = input.nextLine();
			break;
			case 7: // phone
				System.out.println("Please enter the student's phone number (10 digits):");
				phone = input.nextLine();
			break;
			case 8: // vacc
				System.out.println("Please enter the number of vaccinations completed:");
				vaccNum = input.nextInt();
			break;
			case 9: // save and exit
				saveAndExitLoop = true;
			break;
			}
		} // end loop
		ReasonableCare.updateStudent(studentID, name, age, genderChar, phone, address, dateOfBirth, ssn, vaccNum);

		if(op == Operation.CREATE)
			ReasonableCare.createStudent(studentID, name, age, genderChar, phone, address, dateOfBirth, ssn, vaccNum);
		else
			ReasonableCare.updateStudent(studentID, name, age, genderChar, phone, address, dateOfBirth, ssn, vaccNum);
	}
}
