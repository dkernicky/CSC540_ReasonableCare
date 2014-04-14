package MainCode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ReasonableCare {
	private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
	// Put your oracle ID and password here
	private static final String user = "shwhite3";
	private static final String password = "200025411";
//	private static final String user = "djkernic";
//	private static final String password = "001101409";
	
	
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet result = null;

	public static void main(String[] args) throws SQLException {
		//initialize the database
		initialize();
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the Reasonable Care Database System");
		//get login information from the user
		System.out.println("Please enter your login id:");
		int loginID = input.nextInt();
		input.nextLine();
		System.out.println("Please enter your password:");
		String loginPwd = input.nextLine();
		char role = getLoginInfo(loginID, loginPwd);
		
		boolean loggedIn = true;
		//assign view based on the user's permissions
		switch (role){
			case 'R':
				while(loggedIn){
					loggedIn = Receptionist.runReceptionistScenario(input, loginID);
				}
				break;
			case 'D':
				while(loggedIn){
					loggedIn = DoctorNurse.runDoctorNurseScenario(input, loginID);
				}
				break;
			case 'S':
				while(loggedIn){
					Student.runStudentScenario(input, loginID);
				}
			default:
				System.out.println("Login failed.");
		}
		//close the database
		close();
		input.close();
	}
	

	
	// **********************************************************
	// ** Manage User Accounts **
	
	//verify login info entered by the user
	public static char getLoginInfo(int id, String pwd){
		try{
			String query = "SELECT * FROM login WHERE id=" + id + " AND pwd='" + pwd + "'";
			result = statement.executeQuery(query);
			if(result.next()){
				return result.getString("permissions").charAt(0);
			}
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return 'I';
	}

	// print out all billing information for a student
	public static void viewBillingInfo(int id) {
		String query = "SELECT appt_date, start_time, amt_billed, billing_addr, card_type, card_num, card_company from billing_info INNER " +
				"JOIN appointment ON appointment.id = billing_info.appt_id WHERE billing_info.s_id = " + id + "";
		try {
			//TODO: add more (copay)
			result = statement.executeQuery(query);
			System.out.println("Billing Statements for Student ID " + id);
			System.out.println("------------------------------------------------------");
			System.out.println();
			//print out the results found in the database
			while(result.next()){
				String date = result.getString("appt_date");
				String time = result.getString("start_time");
				float amount = result.getFloat("amt_billed");
				String address = result.getString("billing_addr");
				String type = result.getString("card_type");
				String company = result.getString("card_company");
				String num = result.getString("card_num");
				System.out.println("Date: " + date.substring(0, 10));
				System.out.println("Start time: " + time.substring(11, 16));
				System.out.println("Amount billed: " + amount);
				System.out.println("Billing Address: " + address);
				System.out.println("Card Type: " + type);
				System.out.println("Card Number: " + num);
				System.out.println("Card Company: " + company);

				System.out.println();
				System.out.println("------------------------------------------------------");
				System.out.println();
				
			}
		} catch (SQLException e) {			
			System.out.println(e.getMessage()); }
	}
	
	// create a person in the database
	public static int createPerson(int id, String name, int age, char gender, String phone,
			String address, char permissions) {
		try {
			int rows = statement.executeUpdate("INSERT INTO person(id, name, age, gender, "
					+ "phone_num, address) VALUES (" + id + ", " + name + ", " + age + ", " +
					gender + ", " + phone + ", " + address +")");
			if(rows == 0){
				System.out.println("The person could not be created using the information provided.");
			}
			else{
				statement.executeUpdate("INSERT INTO login(id, pwd, permissions) VALUES ("
						+ id + ", 'password', '" + permissions + "')");
			}
			return rows;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
	
	// create a student in the database
	public static void createStudent(int id, String name, int age, char gender, String phone, String address, String dateOfBirth, String ssn, int vacc) {
		try {
			connection.setAutoCommit(false);
		
			int status = createPerson(id, name, age, gender, phone, address, 'S');

			int rows = statement.executeUpdate("INSERT INTO student(id, date_of_birth, ssn, vacc)"
					+ " VALUES (" + id + ", to_date(" + dateOfBirth + ", 'DD-MON-YYYY'), " +ssn +
					"," + vacc + ")");
			if(rows == 1 && status == 1) {
				connection.commit();
				System.out.println("The student entry has been created in the database.");

			}
			else {
				System.out.println("The student could not be created using the information provided.");
				connection.rollback();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// create a staff member in the database
	public static void createStaff(int id, String name, int age, char gender, char jobTitle, String department, String phone, String address) {
		try {
			if(jobTitle == 'R')
				createPerson(id, name, age, gender, phone, address, 'R');
			else
				createPerson(id, name, age, gender, phone, address, 'D');
			int rows = statement.executeUpdate("INSERT INTO staff(id, job_title, department) "
					+ "VALUES (" + 10001 + ", " + jobTitle + "," + department + ")");
			if(rows == 0){
				System.out.println("The staff member could not be entered using the information provided.");
			}
			else{
				System.out.println("The staff member entry has been created in the database.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// create a doctor in the database
	public static void createDoctor(int id, String name, int age, char gender, char jobTitle, String profTitle, String department, String phone, String address) {
		try {
			createStaff(id, name, age, gender, jobTitle, department, phone, address);
			int rows = statement.executeUpdate("INSERT INTO doctor(id, professional_title) VALUES"
					+ " (" + id + ", " + profTitle + ")");
			if(rows == 0){
				System.out.println("The doctor could not be created using the information provided.");
			}
			else{
				System.out.println("The doctor entry has been created in the database.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//add student insurance information into the database
	public static void createInsuranceInfo(int studentID, String insName, String policyNum, String start, String end, float copayment){
		try{
			int rows = statement.executeUpdate("INSERT INTO health_insurance(s_id, ins_name, "
					+ "policy_num, start_date, end_date, copayment) VALUES (" + studentID + ", "
					+ insName + ", " + policyNum + ",  to_date(" + start + ", 'DD-MON-YYYY'), "
					+ "to_date(" + end + ", 'DD-MON-YYYY'), " + copayment + ")");
			if(rows == 0){
				System.out.println("This insurance information could not be entered.");
			}
			else{
				System.out.println("The insurance information was properly created.");
			}
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	//create a billing statement for the student when they generate an appointment
	public static void createBillingStatement(int s_id, String appt_date, String appt_time, String addr, String type, String num, String company) {
		try{
			String query = "SELECT id FROM appointment WHERE s_id=" + s_id + " AND appt_date=to_date('" + appt_date + "', 'DD-MON-YYYY') AND " +
					"start_time=to_date('" + appt_time + "', 'HH:MIPM')";
			result = statement.executeQuery(query);
			if(result.next()){
				int appt_id = result.getInt("id");
				String insert = "INSERT INTO billing_info(s_id, appt_id, billing_addr, card_type, card_num, card_company) VALUES ( " +
						s_id + ", " + appt_id + ", '" + addr + "', '" + type + "', '" + num + "', '" + company + "')";
				int rows = statement.executeUpdate(insert);
				if(rows == 0){
					System.out.println("There was an issue entering the given information.");
				}
			}
			else{
				System.out.println("Some of the information provided was invalid.");
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// create an appointment with billing info
	public static void createAppointmentWithBilling(int studentID, int staffID, String reason, String date, String start, String notes, String addr, String type, String num, String company) {
		try {
			//create the appointment entry
			float amtBilled = 50;
			int rows = statement.executeUpdate("INSERT INTO appointment(id, s_id, staff_id, "
					+ "reason, appt_date, start_time, end_time, amt_billed, notes) VALUES "
					+ "(appointment_seq.nextVal, " + studentID + ", " + staffID + ", '" + reason
					+ "', to_date('" + date + "', 'DD-MON-YYYY'), to_date('" + start + "', "
					+ "'HH:MIPM'), to_date('" + start +"', 'HH:MIPM') + 1/24, " + amtBilled +
					", '" + notes + "')");
			if(rows == 0){
				System.out.println("The appointment could not be created with the information provided.");
			}
			else{
				System.out.println("The appointment has been created.");
			}
			//get the appointment entry that was created
			String query = "SELECT id FROM appointment WHERE s_id=" + studentID + " AND appt_date=to_date('" + date + "', 'DD-MON-YYYY') AND " +
					"start_time=to_date('" + start + "', 'HH:MIPM')";
			result = statement.executeQuery(query);
			if(result.next()){
				int appt_id = result.getInt("id");
				//create the billing statement
				String insert = "INSERT INTO billing_info(s_id, appt_id, billing_addr, card_type, card_num, card_company) VALUES ( " +
						studentID + ", " + appt_id + ", '" + addr + "', '" + type + "', '" + num + "', '" + company + "')";
				
				rows = statement.executeUpdate(insert);
				if(rows == 0){
					System.out.println("There was an issue entering the given information.");
				}
			}
			else{
				System.out.println("Some of the information provided was invalid.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// create an appointment
	public static void createAppointment(int studentID, int staffID, String reason, String date, String start, String notes) {
		try {
			float amtBilled = 50;
			int rows = statement.executeUpdate("INSERT INTO appointment(id, s_id, staff_id, "
					+ "reason, appt_date, start_time, end_time, amt_billed, notes) VALUES "
					+ "(appointment_seq.nextVal, " + studentID + ", " + staffID + ", '" + reason
					+ "', to_date('" + date + "', 'DD-MON-YYYY'), to_date('" + start + "', "
					+ "'HH:MIPM'), to_date('" + start +"', 'HH:MIPM') + 1/24, " + amtBilled +
					", '" + notes + "')");
			if(rows == 0){
				System.out.println("The appointment could not be created with the information provided.");
			}
			else{
				System.out.println("The appointment has been created.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//update a student entry in the database
	public static void updateStudent(int id, String name, int age, char gender, String phone, String address, String dateOfBirth, String ssn, int vacc){
		try{
			updatePerson(id, name, age, gender, phone, address);
			int rows = statement.executeUpdate("UPDATE student set date_of_birth=to_date('" +
					dateOfBirth + "', 'DD-MON-YYYY'), ssn='" + ssn + "', vacc=" + vacc + " WHERE "
					+ "id=" + id);
			if(rows == 0){
				System.out.println("Invalid information provided.");
			}
			else{
				System.out.println("Information updated.");
			}
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	//update a person entry in the database
	public static int updatePerson(int id, String name, int age, char gender, String phone,
			String address){
		try{
			int rows = statement.executeUpdate("UPDATE person SET name='" + name + "', age = " +
					age + ", gender='" + gender + "', phone_num='" + phone + "', address='" +
					address + "' WHERE id=" + id);
			return rows;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
	
	//update a staff entry in the database
	public static void updateStaff(int id, String name, int age, char gender, String phone,
			String address, char jobTitle, String department){
		try{
			connection.setAutoCommit(false);
			int status = updatePerson(id, name, age, gender, phone, address);
			int rows = statement.executeUpdate("UPDATE staff set job_title='" + jobTitle + 
					"', department='" + department + "' WHERE id=" + id);
			if(rows == 1 && status == 1) {
				System.out.println("Information updated.");
				connection.commit();
			}
			else {
				System.out.println("Invalid information provided.");
				connection.rollback();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//update a doctor entry in the database
	public static void updateDoctor(int id, String name, int age, char gender, String phone,
			String address, char jobTitle, String department, String profTitle){
		try{
			updateStaff(id, name, age, gender, phone, address, jobTitle, department);
			int rows = statement.executeUpdate("UPDATE doctor SET professional_title='" + profTitle
					+ "' WHERE id=" + id);
			if(rows == 0){
				System.out.println("Invalid information provided.");
			}
			else{
				System.out.println("Information updated.");
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//update insurance information for a student
	public static void updateInsuranceInfo(int studentID, String insName, String policyNum, String start, String end, float copayment){
		try{
			if(start == null || start.isEmpty()) 
				start = "13-APR-2014";
			if(end == null || end.isEmpty()) 
				end = "15-APR-2016";
			if(copayment == 0)
				copayment = 30;
			
			int rows = statement.executeUpdate("UPDATE health_insurance set ins_name='" + 
					insName + "', policy_num='" + policyNum + "', start_date=to_date('" +
					start + "', 'DD-MON-YYYY'), end_date=to_date('" + end + "', 'DD-MON-YYYY'), "
					+ "copayment=" + copayment + " WHERE s_id=" + studentID);
			if(rows == 0){
				System.out.println("Invalid insurance information provided.");
			}
			else{
				System.out.println("Insurance information updated.");
			}
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	//update appointment information in the database
	public void updateAppointment(int id, int studentID, int staffID, String reason, String date,
			String start, String end, float amt, String notes) {
		try{
			int rows = statement.executeUpdate("UPDATE appointment set reason='" + reason + "', "
					+ "appt_date=to_date('" + date + "', 'DD-MON-YYYY'), start_time=to_date('"
					+ start + "', 'HH:MIPM'), end_time=to_date('" + end + "', 'HH:MIPM'), amt="
					+ amt + ", notes='" + notes + "' WHERE id=" + id );
			System.out.println();
			if(rows == 0){
				System.out.println("Invalid appointment information provided.");
			}
			else{
				System.out.println("The appointment has been successfully updated.");
			}
			System.out.println();
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	//method for viewing records of a student's past appointments
	public static void viewPastAppointmentInfo(int studentID) throws SQLException {
		String query = "SELECT * from appointment WHERE s_id = " + studentID + " AND sysdate > appt_date";
		result = statement.executeQuery(query);
		System.out.println("Past appointments for Student ID " + studentID);
		System.out.println("------------------------------------------------------");
		System.out.println();
		//printing out the results of the query
		while(result.next()){
			int staff_id = result.getInt("staff_id");
			String reason = result.getString("reason");
			String appt_date = result.getString("appt_date");
			String start_time = result.getString("start_time");
			String end_time = result.getString("end_time");
			float amt_billed = result.getFloat("amt_billed");
			String notes = result.getString("notes");
			System.out.println("Staff ID: " + staff_id);
			System.out.println("Reason for visit: " + reason);
			System.out.println("Date: " + appt_date.substring(0, 10));
			System.out.println("Start time: " + start_time.substring(11, 16));
			System.out.println("End time: " + end_time.substring(11, 16));
			System.out.println("Amount billed: " + amt_billed);
			System.out.println("Notes: " + notes);
			System.out.println();
			System.out.println("------------------------------------------------------");
			System.out.println();
		}
	}
	
	//method for viewing upcoming appointments for a student
	public static void viewUpcomingAppointmentInfo(int studentID) throws SQLException {
		String query = "SELECT * from appointment WHERE s_id = " + studentID + " AND sysdate <= appt_date";
		result = statement.executeQuery(query);
		System.out.println("Upcoming appointments for Student ID " + studentID);
		System.out.println("------------------------------------------------------");
		System.out.println();
		//printing out results from the query
		while(result.next()){
			int staff_id = result.getInt("staff_id");
			String reason = result.getString("reason");
			String appt_date = result.getString("appt_date");
			String start_time = result.getString("start_time");
			String end_time = result.getString("end_time");
			float amt_billed = result.getFloat("amt_billed");
			String notes = result.getString("notes");
			System.out.println("Staff ID: " + staff_id);
			System.out.println("Reason for visit: " + reason);
			System.out.println("Date: " + appt_date.substring(0, 10));
			System.out.println("Start time: " + start_time.substring(11, 16));
			System.out.println("End time: " + end_time.substring(11, 16));
			System.out.println("Amount billed: " + amt_billed);
			System.out.println("Notes: " + notes);
			System.out.println();
			System.out.println("------------------------------------------------------");
			System.out.println();
		}
	}
	
	// determine if all vaccinations have been completed
	public static void showHolds(int studentID) throws SQLException {
		result = statement.executeQuery("SELECT * FROM STUDENT WHERE id = " + studentID);
		
		if(result.next()) {
			System.out.println("------------------------------------------------------");

			int num = result.getInt("vacc");
			if(num < 3) {
				System.out.println("Hold: You have only " + num + " of 3 vaccinations" );
			}
			else {
				System.out.println("You have no holds.");
			}
			System.out.println("------------------------------------------------------");

		}
	}
	
	//method for showing a student's insurance information
	public static void showInsuranceInfo(int id) {
		try{
			String query = "SELECT * FROM health_insurance WHERE s_id=" + id;
			result = statement.executeQuery(query);
			//s_id, ins_name, policy_num, start_date, end_date, copayment
			System.out.println();
			//printing results from the query
			if(result.next()) {
				String ins_name = result.getString("ins_name");
				String policy_num = result.getString("policy_num");
				String start_date = result.getString("start_date");
				String end_date = result.getString("end_date");
				float copay = result.getFloat("copayment");
				System.out.println("Insurance information for Student ID " + id);
				System.out.println("------------------------------------------------------");
				System.out.println();
				System.out.println("Insurance company: " + ins_name);
				System.out.println("Policy number: " + policy_num);
				System.out.println("Start date: " + start_date.substring(0, 10));
				System.out.println("End date: " + end_date.substring(0, 10));
				System.out.println("Copayment: " + copay);
				System.out.println();
				System.out.println("------------------------------------------------------");
				System.out.println();
			}
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	// take a number value (as returned by the SQL 'D' to_date format) and assign it a day-of-the-week char value
	public static char toDayAbbrev(int value) {
		switch(value) {
			case 2: return 'M';
			case 3: return 'T';
			case 4: return 'W';
			case 5: return 'R';
			case 6: return 'F';
			default: return 'S';
		}
	} 
	
	// given a date, retrieve the day-of-week number value through SQL
	public static char getDayOfWeek(String date) throws SQLException {
		String query = "SELECT to_char(to_date('"+ date +"', 'DD-MON-YYYY'), 'D') AS Day from dual";
		result = statement.executeQuery(query);
		if(result.next()) {
			int dayNum = result.getInt("Day");
			char c = toDayAbbrev(dayNum);
			return c;
		}
		return 'S';
	}
	
	// check a doctor's schedule to see if the requested day-of-week char is in the doctor's schedule
	public static boolean doctorAvailable(int id, String date) throws SQLException {
		try{
			char day = getDayOfWeek(date);
			String query = "SELECT * FROM doctor_schedule WHERE d_id=" + id + " AND days_available LIKE '%" + day + "%'";
			result = statement.executeQuery(query);
			if(result.next()){
				return true;
			}
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	// convert a time string into the appropriate SQL format for comparison
	public static String convertToSQLTime(String time) throws SQLException {
		String query = "SELECT to_char(to_date(' "+ time +" ', 'HH:MIPM'), 'HH:MIPM') AS time FROM dual";
		result = statement.executeQuery(query);
		if(result.next()){
			time = result.getString("time");
			return time;
		}
		return "";
	}
	
	// check if a given time is between two other times
	public static boolean betweenTime(String time, String start, String end) {
		String query = "SELECT to_date('"+ time +"', 'HH:MIPM') - to_date('"+ start +"', 'HH:MIPM') AS one, to_date('"+ time +"', 'HH:MIPM') - to_date('"+ end +"', 'HH:MIPM') AS two FROM dual";
		try {
		result = statement.executeQuery(query);
			if(result.next()) {
				// retrieve the difference between the queried time and the start time
				float result1 = result.getFloat("one");
				
				// retrieve the difference between the queried time and the end time
				float result2 = result.getFloat("two");
				
				// a negative result indicated that the time lies between the two others
				float result = result1*result2;
				return (result < 0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// check whether a doctor a) is available at a given time based on his/her schedule and
	//   b) has a time conflict with any other appointments for a given time
	public static boolean timeAvailable(int id, String date, String startTime) throws SQLException {
		startTime = convertToSQLTime(startTime);
		String query = "SELECT to_char(start_time, 'HH:MIPM') AS \"start\", to_char(end_time, 'HH:MIPM') AS \"end\" FROM appointment WHERE staff_id=" + id + " AND appt_date = to_date('" + date + "', 'DD-MON-YYYY')";
		result = statement.executeQuery(query);
			if(result.next()){
				String start = result.getString("start");
				String end = result.getString("end");
				if(betweenTime(startTime, start, end)) {
					return false;
				}
			}
		return true;
	}
	
	// retrieve copay information from a student's health insurance info
	public static float getCopay(int id) throws SQLException {
		String query = "SELECT copayment FROM health_insurance WHERE s_id = " + id;
		result = statement.executeQuery(query);
		if(result.next()) {
			return result.getFloat("copayment");
		}
		return -1.0f;
	}
	
	// a dummy method for the card company's api to verify the payment
	public static boolean verifyPayment(String type, String number, String company, String address, String exp) {
		return true;
	}
	
	
	//methods for making or canceling appointments with doctors
	
	//method for finding a specialist a student wants to see
	public static void searchForSpecialist(String specialization){
		try{
			String query = "";
			if(specialization.equals("")) {
				// constructing SQL statement to return all doctors if specialization is not supplied
				query = "SELECT person.name as name, staff.id as staff_id, staff.department "
						+ "as specialization, person.phone_num as phone, doctor_schedule.days_available"
						+ " as availability, doctor_schedule.start_time as start_time, doctor_schedule.end_time"
						+ " as end_time FROM staff INNER JOIN person ON staff.id=person.id INNER JOIN "
						+ "doctor_schedule ON staff.id=doctor_schedule.d_id";
			}
			else {
				// constructing SQL statement to return doctors whose departments match spcialization string
				query = "SELECT person.name as name, staff.id as staff_id, staff.department "
						+ "as specialization, person.phone_num as phone, doctor_schedule.days_available"
						+ " as availability, doctor_schedule.start_time as start_time, doctor_schedule.end_time"
						+ " as end_time FROM staff INNER JOIN person ON staff.id=person.id INNER JOIN "
						+ "doctor_schedule ON staff.id=doctor_schedule.d_id WHERE department='"
						+ specialization + "'";
			}
			result = statement.executeQuery(query);
			System.out.println();
			System.out.println("------------------------------------------------------");
			System.out.println();
			//print results of the search query
			while(result.next()){
				String name = result.getString("name");
				int staff_id = result.getInt("staff_id");
				String phone = result.getString("phone");
				String availability = result.getString("availability");
				String start_time = result.getString("start_time");
				String end_time = result.getString("end_time");
				System.out.println("Name: " + name);
				System.out.println("Staff ID: " + staff_id);
				System.out.println("Specialization: " + specialization);
				System.out.println("Phone: " + phone);
				System.out.println("Days available: " + availability);
				String hours = start_time.substring(11, 16) + "-" + end_time.substring(11, 16);
				System.out.println("Work hours: " + hours);
				System.out.println();
				System.out.println("------------------------------------------------------");
				System.out.println();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//method for viewing all past and upcoming appointments for a student
	public void viewStudentAppointments(int student_id){
		try{
			String query = "SELECT * FROM appointment WHERE student_id=" + student_id + ";";
			result = statement.executeQuery(query);
			System.out.println();
			System.out.println("Appointments for Student ID " + student_id);
			System.out.println("------------------------------------------------------");
			System.out.println();
			while(result.next()){
				int staff_id = result.getInt("staff_id");
				String reason = result.getString("reason");
				String appt_date = result.getString("appt_date");
				String start_time = result.getString("start_time");
				String end_time = result.getString("end_time");
				float amt_billed = result.getFloat("amt_owed");
				String notes = result.getString("notes");
				System.out.println("Staff ID: " + staff_id);
				System.out.println("Reason for visit: " + reason);
				System.out.println("Date: " + appt_date.substring(0, 10));
				System.out.println("Start time: " + start_time.substring(11, 16));
				System.out.println("End time: " + end_time.substring(11, 16));
				System.out.println("Amount billed: " + amt_billed);
				System.out.println("Notes: " + notes);
				System.out.println();
				System.out.println("------------------------------------------------------");
				System.out.println();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//method for viewing a doctor's appointments
	public static void viewDoctorAppointments(int doctor_id){
		try{
			String query = "SELECT * FROM appointment WHERE staff_id=" + doctor_id;
			result = statement.executeQuery(query);
			System.out.println();
			System.out.println("Appointments for Staff ID " + doctor_id);
			System.out.println("------------------------------------------------------");
			System.out.println();
			//printing out results of the query
			while(result.next()){
				int s_id = result.getInt("s_id");
				String reason = result.getString("reason");
				String appt_date = result.getString("appt_date");
				String start_time = result.getString("start_time");
				String end_time = result.getString("end_time");
				String notes = result.getString("notes");
				System.out.println("Student ID: " + s_id);
				System.out.println("Reason for visit: " + reason);
				System.out.println("Date: " + appt_date.substring(0, 10));
				System.out.println("Start time: " + start_time.substring(11, 16));
				System.out.println("End time: " + end_time.substring(11, 16));
				System.out.println("Notes: " + notes);
				System.out.println();
				System.out.println("------------------------------------------------------");
				System.out.println();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//method for deleting an appointment's corresponding billing statement
	public static void deleteBillingStatement(int s_id, String date, String time) {
		try {
			String query = "SELECT id FROM appointment WHERE s_id=" + s_id + " AND appt_date=to_date('" + date + "', 'DD-MON-YYYY') AND " +
				"start_time=to_date('" + time + "', 'HH:MIPM')";
			result = statement.executeQuery(query);

			if(result.next()){
				int appt_id = result.getInt("id");
				String update = "DELETE FROM billing_info WHERE appt_id = " + appt_id;
				int rows = statement.executeUpdate(update);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//method for cancelling an upcoming appointment
	public static void cancelAppointment(int student_id, String date, String start_time){
		try{
			deleteBillingStatement(student_id, date, start_time);
			String update = "DELETE FROM appointment WHERE s_id=" + student_id +
					" AND appt_date=to_date('" + date + "', 'DD-MON-YYYY') AND start_time=" +
					"to_date('" + start_time + "', 'HH:MIPM') AND to_date(sysdate,"
					+ " 'DD-MON-YYYY:HH:MIPM')" + "<to_date('" + date + ":" + start_time + "',"
					+ " 'DD-MON-YYYY:HH:MIPM')";
			int rows = statement.executeUpdate(update);
			if(rows == 0){
				System.out.println("There were no appointments scheduled for the given time.");
			}
			else{
				System.out.println("Your appointment was cancelled.");
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//method for displaying a student's medical records
	public static void displayMedicalRecords(int student_id){
		try{
			//first get the medical history
			String query = "SELECT * FROM medical_record WHERE s_id=" + student_id;
			result = statement.executeQuery(query);
			System.out.println();
			System.out.println("Prescriptions for Student ID " + student_id);
			System.out.println("------------------------------------------------------");
			System.out.println();
			//printing results from the query
			while(result.next()){
				String start_date = result.getString("start_date");
				String end_date = result.getString("end_date");
				String prescription = result.getString("prescription");
				String diagnosis = result.getString("diagnosis");
				int d_id = result.getInt("d_id");
				System.out.println("Responsible doctor: " + d_id);
				System.out.println("Diagnosis: " + diagnosis);
				System.out.println("Prescription: " + prescription);
				System.out.println("Start date: " + start_date.substring(0, 10));
				if(end_date != null){
					end_date = end_date.substring(0, 10);
				}
				else{
					end_date = "N/A";
				}
				System.out.println("End date: " + end_date);
				System.out.println();
				System.out.println("------------------------------------------------------");
				System.out.println();
			}
			
			//then get appointment history
			query = "SELECT * FROM appointment WHERE s_id=" + student_id;
			result = statement.executeQuery(query);
			System.out.println("Past Appointments for Student ID " + student_id);
			System.out.println("------------------------------------------------------");
			System.out.println();
			//printing out results from the query
			while(result.next()){
				int staff_id = result.getInt("staff_id");
				String reason = result.getString("reason");
				String appt_date = result.getString("appt_date");
				String start_time = result.getString("start_time");
				String end_time = result.getString("end_time");
				String notes = result.getString("notes");
				System.out.println("Staff ID: " + staff_id);
				System.out.println("Reason: " + reason);
				System.out.println("Appt date: " + appt_date.substring(0, 10));
				System.out.println("Start time: " + start_time.substring(11, 16));
				System.out.println("End time: " + end_time.substring(11, 16));
				if(notes == null){
					notes = "N/A";
				}
				System.out.println("Notes: " + notes);
				System.out.println();
				System.out.println("------------------------------------------------------");
				System.out.println();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//method for adding notes to an existing appointment
	public static void addNoteToAppointment(int s_id, int staff_id, String date, String time,
			String note){
		try{
			String update = "UPDATE appointment SET notes='" + note + "' WHERE s_id=" + s_id +
					" AND staff_id=" + staff_id + " AND appt_date=to_date('" + date +
					"', 'DD-MON-YYYY') AND start_time=to_date('" + time + "', 'HH:MIPM')";
			int rows = statement.executeUpdate(update);
			if(rows == 0){
				System.out.println("The appointment information provided was invalid.");
				System.out.println();
			}
			else{
				System.out.println("Your note has been added.");
				System.out.println();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//method for generating a new medical record for a student
	public static void createMedicalRecord(int s_id, int staff_id, String appt_date,
			String appt_time, String start_date, String end_date, String prescription,
			String diagnosis){
		try{
			//get the appointment id this medical record corresponds to
			String query = "SELECT id FROM appointment WHERE s_id=" + s_id + " AND staff_id=" +
					staff_id + " AND appt_date=to_date('" + appt_date + "', 'DD-MON-YYYY') AND " +
					"start_time=to_date('" + appt_time + "', 'HH:MIPM')";
			result = statement.executeQuery(query);
			//if the appointment information was valid, insert the provided medical record info
			if(result.next()){
				int appt_id = result.getInt("id");
				String insert = "INSERT INTO medical_record(appt_id, s_id, d_id, start_date, " +
						"end_date, prescription, diagnosis) VALUES (" + appt_id + ", " + s_id +
						", " + staff_id + ", to_date('" + start_date + "', 'DD-MON-YYYY'), "
						+ "to_date('" + end_date + "', 'DD-MON-YYYY'), '" + prescription +
						"', '" + diagnosis + "')";
				int rows = statement.executeUpdate(insert);
				if(rows == 0){
					System.out.println("There was an issue entering the given information.");
				}
			}
			else{
				System.out.println("Some of the information provided was invalid.");
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//method for viewing information about doctors a student has seen in the past
	public static void viewPastDoctorInfo(int s_id){
		try{
			String query = "SELECT DISTINCT person.name AS name, staff.department AS dept, "+
					"person.phone_num AS phone FROM person INNER JOIN staff ON " +
					"person.id=staff.id INNER JOIN appointment ON staff.id=appointment.staff_id " +
					"WHERE appointment.s_id=" + s_id;
			result = statement.executeQuery(query);
			System.out.println();
			System.out.println("------------------------------------------------------");
			System.out.println();
			//printing out results from the query
			while(result.next()){
				String name = result.getString("name");
				String dept = result.getString("dept");
				String phone = result.getString("phone");
				System.out.println("NAME: " + name);
				System.out.println("DEPT: " + dept);
				System.out.println("PHONE: " + phone);
				System.out.println();
				System.out.println("------------------------------------------------------");
				System.out.println();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//method for initializing the database when the program is run
	public static void initialize() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(jdbcURL, user, password);
			statement = connection.createStatement();

			//dropping existing tables
			try {
				statement.executeUpdate("DROP TABLE medical_record ");
			} catch (SQLException e) {}
			try {
				statement.executeUpdate("DROP TABLE doctor_schedule ");
			} catch (SQLException e) {}
			try {
				statement.executeUpdate("DROP TABLE billing_info ");
			} catch (SQLException e) {}
			try {
				statement.executeUpdate("DROP TABLE health_insurance ");
			} catch (SQLException e) {}
			try {
				statement.executeUpdate("DROP TABLE appointment ");
			} catch (SQLException e) {}
			try {
				statement.executeUpdate("DROP TABLE login");
			} catch (SQLException e) {}
			try {
				statement.executeUpdate("DROP TABLE doctor ");
			} catch (SQLException e) {}
			try {
				statement.executeUpdate("DROP TABLE staff ");
			} catch (SQLException e) {}
			try {
				statement.executeUpdate("DROP TABLE student ");
			} catch (SQLException e) {}
			try {
				statement.executeUpdate("DROP TABLE person ");
			} catch (SQLException e) {}
			try {
				statement.executeUpdate("DROP SEQUENCE appointment_seq");
			} catch (SQLException e) {}

			//System.out.println("Dropping Complete");
			
			/* CREATE SEQUENCES */
			statement.executeUpdate("CREATE SEQUENCE appointment_seq START WITH 16 MINVALUE 0 INCREMENT BY 1");

			//creating tables
			statement.executeUpdate("CREATE TABLE person(id INT PRIMARY KEY, name VARCHAR(100) NOT NULL, age INT NOT NULL, gender VARCHAR(1) NOT NULL, phone_num VARCHAR(20) NOT NULL, address VARCHAR(100) NOT NULL)");
			statement.executeUpdate("CREATE TABLE staff(id INT PRIMARY KEY, job_title VARCHAR(1) NOT NULL, department VARCHAR(30), FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE)");
			statement.executeUpdate("CREATE TABLE doctor(id INT PRIMARY KEY, professional_title VARCHAR(30) NOT NULL, FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE)");
			statement.executeUpdate("CREATE TABLE student(id INT PRIMARY KEY, date_of_birth DATE NOT NULL, ssn VARCHAR(11), vacc INT NOT NULL, FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE)");
			statement.executeUpdate("CREATE TABLE appointment(id INT PRIMARY KEY, s_id INT, staff_id INT, reason VARCHAR(30) NOT NULL, appt_date DATE NOT NULL, start_time DATE NOT NULL, end_time DATE NOT NULL, amt_billed FLOAT NOT NULL, notes VARCHAR(100), FOREIGN KEY (s_id) REFERENCES student(id), FOREIGN KEY (staff_id) REFERENCES staff(id))");
			statement.executeUpdate("CREATE TABLE medical_record(appt_id INT PRIMARY KEY, s_id INT, d_id INT, start_date DATE NOT NULL, end_date DATE, prescription VARCHAR(30) NOT NULL, diagnosis VARCHAR(30) NOT NULL, FOREIGN KEY (appt_id) REFERENCES appointment(id) ON DELETE CASCADE, FOREIGN KEY (s_id) REFERENCES student(id) ON DELETE CASCADE, FOREIGN KEY (d_id) REFERENCES doctor(id) ON DELETE CASCADE)");
			statement.executeUpdate("CREATE TABLE billing_info(s_id INT, appt_id INT PRIMARY KEY, billing_addr VARCHAR(100) NOT NULL, card_type VARCHAR(1) NOT NULL, card_num VARCHAR(19) NOT NULL, card_company VARCHAR(30) NOT NULL, FOREIGN KEY(s_id) REFERENCES student(id), FOREIGN KEY (appt_id) REFERENCES appointment(id))");
			statement.executeUpdate("CREATE TABLE health_insurance(s_id INT PRIMARY KEY, ins_name VARCHAR(30) NOT NULL, policy_num VARCHAR(30) NOT NULL, start_date DATE NOT NULL, end_date DATE NOT NULL, copayment FLOAT NOT NULL, FOREIGN KEY (s_id) REFERENCES student(id))");
			statement.executeUpdate("CREATE TABLE doctor_schedule(d_id INT PRIMARY KEY, days_available VARCHAR(5) NOT NULL, start_time DATE NOT NULL, end_time DATE NOT NULL, FOREIGN KEY (d_id) REFERENCES doctor(id))");
			statement.executeUpdate("CREATE TABLE login(id INT PRIMARY KEY, pwd VARCHAR(10) NOT NULL, permissions VARCHAR(1) NOT NULL, FOREIGN KEY (id) REFERENCES person(id))");
			
			//inserting entries into person table
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (10001, 'John Terry', 48, 'M', '919-100-2101', '106 Cloverdale Ct, Raleigh, NC 27607')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (10501, 'Mary Jobs', 30, 'F', '919-500-1212', '106 RiverDale Ct, Raleigh, NC 27807')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (20001, 'Rebecca Johnston', 36, 'F', '919-853-2744', '1048 Avent Ferry Rd, Raleigh NC 27606')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (20501, 'Davy Jacobs', 28, 'M', '919-902-6198', '1048 Glenwood Ave, Raleigh NC 27706')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (30001, 'Michael Smith', 41, 'M', '866-452-9100', '2734 Timber Dr, Maitland, FL')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (1102140001, 'Jason Hunter', 23, 'M', '919-232-1122', '101 Dormant Dr. Cary, NC')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (1102140501, 'Dale Steyn', 30, 'M', '919-456-7890', '150 Rapid Dr. Cary, NC')");
			
			//inserting entries into staff table
			statement.executeUpdate("INSERT INTO staff(id, job_title, department) VALUES (10001, 'D', 'Oncological Surgery')");
			statement.executeUpdate("INSERT INTO staff(id, job_title, department) VALUES (10501, 'D', 'Oncological Surgery')");
			statement.executeUpdate("INSERT INTO staff(id, job_title, department) VALUES (20001, 'N', 'Senior Nurse')");
			statement.executeUpdate("INSERT INTO staff(id, job_title, department) VALUES (20501, 'N', 'Nurse')");
			statement.executeUpdate("INSERT INTO staff(id, job_title, department) VALUES (30001, 'R', '')");

			//inserting entries into doctor table
			statement.executeUpdate("INSERT INTO doctor(id, professional_title) VALUES (10001, 'Senior Surgeon')");
			statement.executeUpdate("INSERT INTO doctor(id, professional_title) VALUES (10501, 'Surgeon')");

			//inserting entries into student table
			statement.executeUpdate("INSERT INTO student(id, date_of_birth, ssn, vacc) VALUES (1102140001, to_date('23-MAY-1990', 'DD-MON-YYYY'), '677-22-1134', 0)");
			statement.executeUpdate("INSERT INTO student(id, date_of_birth, ssn, vacc) VALUES (1102140501, to_date('27-JUN-1983', 'DD-MON-YYYY'), '707-12-4531', 3)");
			
			//inserting entries into login table
			statement.executeUpdate("INSERT INTO login(id, pwd, permissions) VALUES (10001, 'password', 'D')");
			statement.executeUpdate("INSERT INTO login(id, pwd, permissions) VALUES (10501, 'password', 'D')");
			statement.executeUpdate("INSERT INTO login(id, pwd, permissions) VALUES (20001, 'password', 'D')");
			statement.executeUpdate("INSERT INTO login(id, pwd, permissions) VALUES (20501, 'password', 'D')");
			statement.executeUpdate("INSERT INTO login(id, pwd, permissions) VALUES (30001, 'password', 'R')");
			statement.executeUpdate("INSERT INTO login(id, pwd, permissions) VALUES (1102140001, 'password', 'S')");
			statement.executeUpdate("INSERT INTO login(id, pwd, permissions) VALUES (1102140501, 'password', 'S')");
			
			//inserting entries into appointment table
			statement.executeUpdate("INSERT INTO appointment(id, s_id, staff_id, reason, appt_date, start_time, end_time, amt_billed, notes) VALUES (15, 1102140001, 10001, 'Vaccination', to_date('21-APR-2014', 'DD-MON-YYYY'), to_date('4:00PM', 'HH:MIPM'), to_date('5:00PM', 'HH:MIPM'), 100.0, '')");
			
			//inserting entries into medical record table
			statement.executeUpdate("INSERT INTO medical_record(appt_id, s_id, d_id, start_date, end_date, prescription, diagnosis) VALUES (15, 1102140001, 10001, to_date('15-MAR-2011', 'DD-MON-YYYY'), '', 'Pain Killer', 'Broken Bone')");
			
			//inserting entries into billing info table
			statement.executeUpdate("INSERT INTO billing_info(s_id, appt_id, billing_addr, card_type, card_num, card_company) VALUES (1102140001, 15, '101 Dormant Dr. Cary, NC', 'C', '1111-1022-2222-1023', 'VISA')");
			
			//inserting entries into health insurance table
			statement.executeUpdate("INSERT INTO health_insurance(s_id, ins_name, policy_num, start_date, end_date, copayment) VALUES (1102140001, 'Acme', '123456', to_date('15-MAR-2014', 'DD-MON-YYYY'), to_date('14-MAR-2015', 'DD-MON-YYYY'), 30.0)");
			
			//inserting entries into doctor schedule table
			statement.executeUpdate("INSERT INTO doctor_schedule(d_id, days_available, start_time, end_time) VALUES (10001, 'MTWF', to_date('3:00PM', 'HH:MIPM'), to_date('6:00PM', 'HH:MIPM'))");
			statement.executeUpdate("INSERT INTO doctor_schedule(d_id, days_available, start_time, end_time) VALUES (10501, 'TF', to_date('10:00AM', 'HH:MIPM'), to_date('1:00PM', 'HH:MIPM'))");

			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//closes the database
	public static void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}