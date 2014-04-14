package MainCode;

import java.sql.SQLException;
import java.util.Scanner;


public class Receptionist {
	private static int id;
	
	public static void runReceptionistScenario() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please select an option:");
		
		int choice = input.nextInt();
		System.out.println("Please select an option:");
		System.out.println("1.  Make an appointment");
		System.out.println("2.  Create a student account");
		System.out.println("3.  Update student information");
		System.out.println("4.  Create a staff account");
		System.out.println("5.  Update staff information");
		

		switch(choice) {
			case 1:	try {
				createStudentAppointment();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			case 2:	createStudentAccount();
			break;
			case 3:	updateStudentInformation();
			break;
			case 4:	createStaffAccount();
			break;
			case 5:	updateStaffInformation();
			break;
		}
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
		
		//updateStaff(int id, String name, int age, char gender, String phone, String address, char jobTitle, String department)
		//updateDoctor(int id, String name, int age, char gender, String phone, String address, char jobTitle, String department, String profTitle)
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the staff ID:");
		int id = input.nextInt();
		boolean saveAndExitLoop = false;
		
			
			/*
			 * Get current student information from DB into local variables,
			 * then ask receptionist which values to change inside a loop.
			 * after exiting the loop, update the student record
			 */
		
// NEED ACTUAL DATA HERE!!!!!!!!!!!!!!!!
		String name = null;
		char gender = '\0';
		int age = 0;
		String address = null;
		String phone = null;
		char jobTitle = '\0';
		String department = null;
		String profTitle = null;
		
		while (!saveAndExitLoop){
			// display menu
			System.out.println("UPDATING STAFF INFORMATION");
			System.out.println("Please select an option:");
			System.out.println("1.  Update the staff name");
			System.out.println("2.  Update the staff gender");
			System.out.println("3.  Update the staff age");
			System.out.println("4.  Update the staff address");
			System.out.println("5.  Update the staff phone");
			System.out.println("6.  Update the staff job title");
			System.out.println("7.  Update the staff department");
			System.out.println("8.  Update the professional title");
			System.out.println("9.  Save staff data and exit");
			
			int choice = input.nextInt();
			switch(choice) {
			case 1:	// name
				System.out.println("Please enter the new name:");
				name = input.nextLine();
			break;
			case 2:	// gender
				System.out.println("Please enter the new gender (M/F):");
				gender = input.nextLine().charAt(0);
			break;
			case 3:	// age
				System.out.println("Please enter the new age:");
				age = input.nextInt();
			break;
			case 4: // address
				System.out.println("Please enter the new address:");
				address = input.nextLine();
			break;
			case 5: // phone
				System.out.println("Please enter the new phone number (10 digits):");
				phone = input.nextLine();
			break;
			case 6: // job title
				System.out.println("Please enter the new job title:");
				jobTitle = input.nextLine().toUpperCase().charAt(0);
			break;
			case 7: // department
				System.out.println("Please enter the new department:");
				department = input.nextLine();
			break;
			case 8:	
				System.out.println("Please enter the new professional title:");
				profTitle = input.nextLine();
			break;
			case 9:	
				saveAndExitLoop = true;
			break;
			}
			if(jobTitle == 'D')
				ReasonableCare.updateDoctor(id, name, age, gender, phone, address, jobTitle, department, profTitle);
			
			//updateStaff(int id, String name, int age, char gender, String phone, String address, char jobTitle, String department)

		} // end loop
	
	}
	
	public static void createStudentAppointment() throws SQLException{
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		
		Student.runAppointmentScenario(studentID);
	}

	public static void createStudentAccount() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		
		System.out.println("Please enter the student's name:");
		String name = input.nextLine();
		
		System.out.println("Please enter a gender (M/F):");
		char genderChar = input.nextLine().toUpperCase().charAt(0);
		
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
		
		ReasonableCare.createStudent(studentID, name, age, genderChar, phone, address, dateOfBirth, ssn, vaccNum);
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
		
// NEED ACTUAL DATA HERE!!!!!!!!!!!!!!!!
		
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

	}
}
