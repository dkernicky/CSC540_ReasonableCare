package MainCode;

import java.sql.SQLException;
import java.util.Scanner;


public class Receptionist {
	private static int id;
	
	public void runReceptionistScenario() {
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

	public void createStaffAccount() {
		// TODO Auto-generated method stub
		
	}

	public void updateStaffInformation() {
		// TODO Auto-generated method stub
		
	}
	
	public void createStudentAppointment() throws SQLException{
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		
		Student.runAppointmentScenario(studentID);
	}
	


	public void updateStudentInformation() {
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
			
			try {
			    ReasonableCare.updateStudent(studentID, name, age, genderChar, phone, address, dateOfBirth, ssn, vaccNum);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void createStudentAccount() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		
		System.out.println("Please enter the student's name:");
		String name = input.nextLine();
		
		System.out.println("Please enter a gender (M/F):");
		char genderChar = input.nextLine().charAt(0);
		
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
}
