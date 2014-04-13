package MainCode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DoctorNurse {
	private int id;
	
	public void runDoctorNurseScenario(int login_id){
		id = login_id;
		Scanner input = new Scanner(System.in);
		System.out.println("Please select an option:");
		System.out.println("1. Find student medical records");
		System.out.println("2. Add notes to existing appointment");
		System.out.println("3. Create consulation");
		System.out.println("4. Generate prescription");
		System.out.println("5. View past doctors for student");
		System.out.println("6. Update personal information");
		System.out.println("7. Logout");
		
		int choice = input.nextInt();
		switch(choice){
		case 1:
			getMedicalRecords();
			break;
		case 2:
			addNotesToAppointment();
			break;
		case 3:
			createConsultation();
			break;
		case 4:
			generatePrescription();
			break;
		case 5:
			viewPastDoctors();
			break;
		case 6:
			updatePersonalInfo();
			break;
		case 7:
			break;
		default:
			break;
		}
	}
	
	public void getMedicalRecords(){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the student's ID for whom you wish to get medical records:");
		int s_id = input.nextInt();
		ReasonableCare.displayMedicalRecords(s_id);
	}
	
	public void addNotesToAppointment(){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the ID of the student this appointment was for:");
		int s_id = input.nextInt();
		System.out.println("Please enter the date of the appointment (as DD-MON-YYYY):");
		String date = input.nextLine();
		System.out.println("Please enter the time of the appointment (as HH:MIPM):");
		String time = input.nextLine();
		System.out.println("Please enter the note that should be added:");
		String note = input.nextLine();
		ReasonableCare.addNoteToAppointment(s_id, id, date, time, note);
	}
	
	//public void createAppointment(int studentID, int staffID, String reason, String date, String start, String end, float amt, String notes) {
	public void createConsultation(){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the ID of the student this consultation was for:");
		int s_id = input.nextInt();
		System.out.println("Please enter the date of the consultation (as DD-MON-YYYY):");
		String date = input.nextLine();
		System.out.println("Please enter the start time of the consultation (as HH:MIPM):");
		String s_time = input.nextLine();
		System.out.println("Please enter the end time of the consultation (as HH:MIPM):");
		String e_time = input.nextLine();
		System.out.println("Please enter any notes for the consultation:");
		String note = input.nextLine();
		ReasonableCare.createAppointment(s_id, id, "Consultation", date, s_time, e_time, 0, note);
	}
	
	public void generatePrescription(){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the ID of the student this prescription is for:");
		int s_id = input.nextInt();
		System.out.println("Please enter the date of the appointment leading to this prescription:");
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
	
	public void viewPastDoctors(){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the ID of the student for whom you would like to view past doctors:");
		int s_id = input.nextInt();
		ReasonableCare.viewPastDoctorInfo(s_id);
	}
	
	//int id, String name, int age, char gender, String phone, String address, char jobTitle, String department, String profTitle
	public void updatePersonalInfo(){
		//TODO make this do something
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter your name:");
		String name = input.nextLine();
		System.out.println("Please enter your age:");
		int age = input.nextInt();
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
}