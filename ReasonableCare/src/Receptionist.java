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
			case 1:	//runAppointmentScenario();
			break;
			case 2:	createStudentAccount();
			break;
			case 3:	updateStudentInformation();
			break;
			case 4:	
			break;
			case 5:	
			break;
		}
	}

	private static void updateStudentInformation() {
		Scanner input = new Scanner(System.in);
		
		// INSERT INTO person(id, name, age, gender, phone_num, address)
				System.out.println("Please enter the student ID:");
				int studentID = input.nextInt();
				
				System.out.println("Please enter the student name:");
				String name = input.nextLine();
				
				System.out.println("Please enter a gender (M/F):");
				String gender = input.nextLine();
				
				System.out.println("Please enter phone number (10 digits):");
				String phone = input.nextLine();
				
				System.out.println("Please enter address:");
				String address = input.nextLine();
				
				//TODO call createStudent();		
	}

	private static void createStudentAccount() {
		
		Scanner input = new Scanner(System.in);
		
// INSERT INTO person(id, name, age, gender, phone_num, address)
// INSERT INTO student(id, date_of_birth, ssn, vacc)
		System.out.println("Please enter the student ID:");
		int studentID = input.nextInt();
		
		System.out.println("Please enter the student name:");
		String name = input.nextLine();
		
		System.out.println("Please enter address:");
		String address = input.nextLine();

		System.out.println("Please enter a gender (M/F):");
		String gender = input.nextLine();
		
		System.out.println("Please enter the student DOB (DD-MMM-YYYY):");
		String DOB = input.nextLine();
		
		System.out.println("Please enter the student SSN:");
		String SSN = input.nextLine();
		
		System.out.println("Please enter phone number (10 digits):");
		String phone = input.nextLine();

		System.out.println("Please enter number of vaccinations:");
		int vacc = input.nextInt();
		
		//TODO call createStudent();
	}
}
