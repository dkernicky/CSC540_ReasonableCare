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
	
	public void createStudentAppointment_old() {
		// TODO Auto-generated method stub
		// createAppointment(int studentID, int staffID, String reason, String date, String start, String end, float amt, String notes)
		
		/* 
		 * Process:
		 * 1. Find the doctor
		 * 2. Check the doctor's schedule by date (to find available date and time)
		 * 3. Enter all the required data, one by one
		 * Enter studentID, staffID, reason, date, start and end times.
		 * 
		 * Don't bother to check if the appointment has already been taken.
		 * If we did that, we would have to lock the doctor's schedule
		 * until all appointment data is submitted and confirmation generated.
		 * Instead, we check 
		 */
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the student ID:");
		boolean exitLoop = false;
		
		
		int studentID = input.nextInt();
		int doctorID = -1; // initial value meaning that no doctor has been selected
		String apptDate = null;
		String apptStartTime = null;
		String apptEndTime = null;
		String reason = null;
		
		double amt = 0.0; //HANDE THIS PLEASE!
		
		while (!exitLoop){
			// display menu
			System.out.println("CREATING APPOINTMENT");
			System.out.println("Please select an option:");
			System.out.println("1.  Search for a specialist");
			System.out.println("2.  Enter doctor ID");
			System.out.println("3.  Enter desired apointment date");
			System.out.println("4.  Check the doctor's schedule for selected date");
			System.out.println("5.  Enter apointment start time");
			System.out.println("6.  Enter appointment end time");
			System.out.println("7.  Enter reason for appointment");
			System.out.println("8.  Save appointment");
			System.out.println("9.  Exit to previous menu");
			
			int choice = input.nextInt();
			switch(choice) {
			case 1:	// specialist
				System.out.println("Please enter the specialization:");
				String specialization = input.nextLine();
				ReasonableCare.searchForSpecialist(specialization);
			break;
			case 2:	// select doctor
				System.out.println("Please enter the doctor's ID:");
				doctorID = input.nextLine().charAt(0);
			break;
			case 3:	// enter date
				System.out.println("Please enter desired appointment date:");
				apptDate = input.nextLine();
			break;
			case 4:	// check schedule
				if(doctorID>0 && apptDate!=null)
					ReasonableCare.viewDoctorAppointmentsByDate(doctorID, apptDate);
				else
					System.out.println("Please enter a valid doctor ID and appointment date before you can see the schedule.");
			break;
			case 5:	// start time
				System.out.println("Please enter the appointment start time:");
				apptStartTime = input.nextLine();
			break;
			case 6: // end time
				System.out.println("Please enter the appointment end time:");
				apptEndTime = input.nextLine();
			break;
			case 7: // reason
				System.out.println("Please enter the reason for appointment:");
				reason = input.nextLine();
			break;
			case 8: // ssave
				if(reason!=null && apptDate != null && apptStartTime != null && apptEndTime != null){
					ReasonableCare.createAppointment(studentID, doctorID, reason, apptDate, apptStartTime, apptEndTime, (float) amt, "none");
					System.out.println("Your appointment has been scheduled.");
				}
				else{
					System.out.println("You must provide a valid date, start and end times, and reason for this appointment.");
				}
			break;
			case 9: // exit
				exitLoop = true;
			break;
			}
		} // end loop
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
