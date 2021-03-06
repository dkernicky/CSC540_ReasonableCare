package MainCode;
import java.util.Scanner;

//class governing the database permissions of doctors and nurses
public class DoctorNurse {
	private static int id;
	
	//menu method that displays a list of actions the doctor/nurse can take
	public static boolean runDoctorNurseScenario(Scanner input, int login_id){
		int choice = 0;
		id = login_id;
		System.out.println("Please select an option:");
		System.out.println("1. Find student medical records");
		System.out.println("2. Add notes to existing appointment");
		System.out.println("3. Create consulation");
		System.out.println("4. Generate prescription");
		System.out.println("5. View past doctors for student");
		System.out.println("6. Update personal information");
		System.out.println("7. View my appointments");
		System.out.println("8. Logout");
		
		choice = input.nextInt();
		input.nextLine();
		//determine action based off the user's input for desired action
		switch(choice){
		case 1:
			getMedicalRecords(input);
			break;
		case 2:
			addNotesToAppointment(input);
			break;
		case 3:
			createConsultation(input);
			break;
		case 4:
			generatePrescription(input);
			break;
		case 5:
			viewPastDoctors(input);
			break;
		case 6:
			updatePersonalInfo(input);
			break;
		case 7:
			viewMyAppointments();
			break;
		//return false if user wishes to log out
		case 8:
			return false;
		default:
			System.out.println("That is an invalid choice.");
			break;
		}
		return true;
	}
	
	/*
	 * The methods below correspond to the various actions doctors and nurses can take according
	 * to their database permissions. For each action, relevant information is asked for by
	 * the program and provided by the user so the corresponding SQL query can be executed.
	 */
	
	//method for displaying a student's medical records
	public static void getMedicalRecords(Scanner input){
		System.out.println("Please enter the student's ID for whom you wish to get medical records:");
		int s_id = input.nextInt();
		input.nextLine();
		ReasonableCare.displayMedicalRecords(s_id);
	}
	
	//method for adding notes to an existing appointment
	public static void addNotesToAppointment(Scanner input){
		System.out.println("Please enter the ID of the student this appointment was for:");
		int s_id = input.nextInt();
		input.nextLine();
		System.out.println("Please enter the date of the appointment (as DD-MON-YYYY):");
		String date = input.nextLine();
		System.out.println("Please enter the time of the appointment (as HH:MIPM):");
		String time = input.nextLine();
		System.out.println("Please enter the note that should be added:");
		String note = input.nextLine();
		ReasonableCare.addNoteToAppointment(s_id, id, date, time, note);
	}
	
	//method for creating a consultation appointment with a student
	public static void createConsultation(Scanner input){
		System.out.println("Please enter the ID of the student this consultation was for:");
		int s_id = input.nextInt();
		input.nextLine();
		System.out.println("Please enter the date of the consultation (as DD-MON-YYYY):");
		String date = input.nextLine();
		System.out.println("Please enter the start time of the consultation (as HH:MIPM):");
		String s_time = input.nextLine();
		System.out.println("Please enter any notes for the consultation:");
		String note = input.nextLine();
		ReasonableCare.createAppointment(s_id, id, "Consultation", date, s_time, note);
	}
	
	//method for inserting information about a prescription to a student's medical record
	public static void generatePrescription(Scanner input){
		System.out.println("Please enter the ID of the student this prescription is for:");
		int s_id = input.nextInt();
		input.nextLine();
		System.out.println("Please enter the date of the appointment leading to this prescription (as DD-MON-YYYY):");
		String appt_date = input.nextLine();
		System.out.println("Please enter the time as which the appointment was held (as HH:MIPM):");
		String appt_time = input.nextLine();
		System.out.println("Please enter the start date for the prescription (as DD-MON-YYYY):");
		String s_date = input.nextLine();
		System.out.println("Please enter the end date for the prescription (as DD-MON-YYYY):");
		String e_date = input.nextLine();
		System.out.println("Please enter the medication you are prescribing to the student:");
		String prescription = input.nextLine();
		System.out.println("Please enter the diagnosis leading to this prescription:");
		String diagnosis = input.nextLine();
		ReasonableCare.createMedicalRecord(s_id, id, appt_date, appt_time, s_date, e_date, prescription, diagnosis);
	}
	
	//method for viewing information about past doctors a student has seen according to their
	//medical records
	public static void viewPastDoctors(Scanner input){
		System.out.println("Please enter the ID of the student for whom you would like to view past doctors:");
		int s_id = input.nextInt();
		input.nextLine();
		ReasonableCare.viewPastDoctorInfo(s_id);
	}
	
	//method for updating the user's personal information
	public static void updatePersonalInfo(Scanner input){
		System.out.println("Please enter your name:");
		String name = input.nextLine();
		System.out.println("Please enter your age:");
		int age = input.nextInt();
		input.nextLine();
		System.out.println("Please enter your gender (M for male, F for female):");
		String genderChar = input.nextLine();
		char gender = genderChar.charAt(0);
		System.out.println("Please enter your phone number:");
		String phone = input.nextLine();
		System.out.println("Please enter your address:");
		String address = input.nextLine();
		System.out.println("Please enter your job title (N for nurse, D for doctor):");
		String titleChar = input.nextLine();
		char jobTitle = titleChar.charAt(0);
		System.out.println("Please enter your department:");
		String department = input.nextLine();
		System.out.println("Please enter your professional title:");
		String profTitle = input.nextLine();
		ReasonableCare.updateDoctor(id, name, age, gender, phone, address, jobTitle, department, profTitle);
	}
	
	public static void viewMyAppointments(){
		ReasonableCare.viewDoctorAppointments(id);
	}
}
