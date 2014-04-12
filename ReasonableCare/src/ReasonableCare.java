import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReasonableCare {
	private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
	// Put your oracle ID and password here
	private static final String user = "djkernic";
	private static final String password = "001101409";

	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet result = null;

	public static void main(String[] args) {
		initialize();

		close();
	}

	private void createPerson(int id, String name, int age, char gender, String phone, String address) {
		try {
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (" + id + ", " + name + ", " + age + ", " + gender + ", " + phone + ", " + address +")");
		} catch (SQLException e) {}

	}
	private void createStaff() {
		// abcde
	}
	private void createStudent(int id, String name, int age, char gender, String phone, String address, String dateOfBirth, String ssn, int vacc) {
		try {
			createPerson(id, name, age, gender, phone, address);
			statement.executeUpdate("INSERT INTO student(id, date_of_birth, ssn, vacc) VALUES (" + id + ", to_date(" + dateOfBirth + ", 'DD-MON-YYYY'), " +ssn + "," + vacc + ")");
		} catch (SQLException e) {}
	}
	
	private static void initialize() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(jdbcURL, user, password);
			statement = connection.createStatement();

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

			statement.executeUpdate("CREATE TABLE person(id INT PRIMARY KEY, name VARCHAR(100) NOT NULL, age INT NOT NULL, gender VARCHAR(1) NOT NULL, phone_num VARCHAR(20) NOT NULL, address VARCHAR(100) NOT NULL)");
			statement.executeUpdate("CREATE TABLE staff(id INT PRIMARY KEY, job_title VARCHAR(1) NOT NULL, department VARCHAR(30), FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE)");
			statement.executeUpdate("CREATE TABLE doctor(id INT PRIMARY KEY, professional_title VARCHAR(30) NOT NULL, FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE)");
			statement.executeUpdate("CREATE TABLE student(id INT PRIMARY KEY, date_of_birth DATE NOT NULL, ssn VARCHAR(11), vacc INT NOT NULL, FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE)");
			statement.executeUpdate("CREATE TABLE appointment(id INT PRIMARY KEY, s_id INT, staff_id INT, reason VARCHAR(30) NOT NULL, appt_date DATE NOT NULL, start_time DATE NOT NULL, end_time DATE NOT NULL, amt_billed FLOAT NOT NULL, notes VARCHAR(100), FOREIGN KEY (s_id) REFERENCES student(id), FOREIGN KEY (staff_id) REFERENCES staff(id))");
			statement.executeUpdate("CREATE TABLE medical_record(appt_id INT PRIMARY KEY, s_id INT, d_id INT, start_date DATE NOT NULL, end_date DATE, prescription VARCHAR(30) NOT NULL, diagnosis VARCHAR(30) NOT NULL, FOREIGN KEY (appt_id) REFERENCES appointment(id) ON DELETE CASCADE, FOREIGN KEY (s_id) REFERENCES student(id) ON DELETE CASCADE, FOREIGN KEY (d_id) REFERENCES doctor(id) ON DELETE CASCADE)");
			statement.executeUpdate("CREATE TABLE billing_info(s_id INT PRIMARY KEY, appt_id INT, billing_addr VARCHAR(100) NOT NULL, card_type VARCHAR(1) NOT NULL, card_num VARCHAR(19) NOT NULL, card_company VARCHAR(30) NOT NULL, FOREIGN KEY(s_id) REFERENCES student(id), FOREIGN KEY (appt_id) REFERENCES appointment(id))");
			statement.executeUpdate("CREATE TABLE health_insurance(s_id INT PRIMARY KEY, ins_name VARCHAR(30) NOT NULL, policy_num VARCHAR(30) NOT NULL, start_date DATE NOT NULL, end_date DATE NOT NULL, copayment FLOAT NOT NULL, FOREIGN KEY (s_id) REFERENCES student(id))");
			statement.executeUpdate("CREATE TABLE doctor_schedule(d_id INT PRIMARY KEY, days_available VARCHAR(5) NOT NULL, start_time DATE NOT NULL, end_time DATE NOT NULL, FOREIGN KEY (d_id) REFERENCES doctor(id))");
			
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (10001, 'John Terry', 48, 'M', '919-100-2101', '106 Cloverdale Ct, Raleigh, NC 27607')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (10501, 'Mary Jobs', 30, 'F', '919-500-1212', '106 RiverDale Ct, Raleigh, NC 27807')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (20001, 'Rebecca Johnston', 36, 'F', '919-853-2744', '1048 Avent Ferry Rd, Raleigh NC 27606')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (20501, 'Davy Jacobs', 28, 'M', '919-902-6198', '1048 Glenwood Ave, Raleigh NC 27706')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (30001, 'Michael Smith', 41, 'M', '866-452-9100', '2734 Timber Dr, Maitland, FL')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (1102140001, 'Jason Hunter', 23, 'M', '919-232-1122', '101 Dormant Dr. Cary, NC')");
			statement.executeUpdate("INSERT INTO person(id, name, age, gender, phone_num, address) VALUES (1102140501, 'Dale Steyn', 30, 'M', '919-456-7890', '150 Rapid Dr. Cary, NC')");
			
			statement.executeUpdate("INSERT INTO staff(id, job_title, department) VALUES (10001, 'D', 'Oncological Surgery')");
			statement.executeUpdate("INSERT INTO staff(id, job_title, department) VALUES (10501, 'D', 'Oncological Surgery')");
			statement.executeUpdate("INSERT INTO staff(id, job_title, department) VALUES (20001, 'N', 'Senior Nurse')");
			statement.executeUpdate("INSERT INTO staff(id, job_title, department) VALUES (20501, 'N', 'Nurse')");
			statement.executeUpdate("INSERT INTO staff(id, job_title, department) VALUES (30001, 'R', '')");

			statement.executeUpdate("INSERT INTO doctor(id, professional_title) VALUES (10001, 'Senior Surgeon')");
			statement.executeUpdate("INSERT INTO doctor(id, professional_title) VALUES (10501, 'Surgeon')");

			statement.executeUpdate("INSERT INTO student(id, date_of_birth, ssn, vacc) VALUES (1102140001, to_date('23-MAY-1990', 'DD-MON-YYYY'), '677-22-1134', 0)");
			statement.executeUpdate("INSERT INTO student(id, date_of_birth, ssn, vacc) VALUES (1102140501, to_date('27-JUN-1983', 'DD-MON-YYYY'), '707-12-4531', 3)");
			
			statement.executeUpdate("INSERT INTO appointment(id, s_id, staff_id, reason, appt_date, start_time, end_time, amt_billed, notes) VALUES (15, 1102140001, 10001, 'Vaccination', to_date('21-APR-2014', 'DD-MON-YYYY'), to_date('4:00PM', 'HH:MIPM'), to_date('5:00PM', 'HH:MIPM'), 100.0, '')");
			
			statement.executeUpdate("INSERT INTO medical_record(appt_id, s_id, d_id, start_date, end_date, prescription, diagnosis) VALUES (15, 1102140001, 10001, to_date('15-MAR-2011', 'DD-MON-YYYY'), '', 'Pain Killer', 'Broken Bone')");
			
			statement.executeUpdate("INSERT INTO billing_info(s_id, appt_id, billing_addr, card_type, card_num, card_company) VALUES (1102140001, 15, '101 Dormant Dr. Cary, NC', 'C', '1111-1022-2222-1023', 'VISA')");
			
			statement.executeUpdate("INSERT INTO health_insurance(s_id, ins_name, policy_num, start_date, end_date, copayment) VALUES (1102140001, 'Acme', '123456', to_date('15-MAR-2014', 'DD-MON-YYYY'), to_date('14-MAR-2015', 'DD-MON-YYYY'), 30.0)");
			
			statement.executeUpdate("INSERT INTO doctor_schedule(d_id, days_available, start_time, end_time) VALUES (10001, 'MTWF', to_date('3:00PM', 'HH:MIPM'), to_date('6:00PM', 'HH:MIPM'))");
			statement.executeUpdate("INSERT INTO doctor_schedule(d_id, days_available, start_time, end_time) VALUES (10501, 'TF', to_date('10:00AM', 'HH:MIPM'), to_date('1:00PM', 'HH:MIPM'))");

			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public static boolean ignoreSQLException(String sqlState) {

		if (sqlState == null) {
			System.out.println("The SQL state is not defined!");
			return false;
		}

		// X0Y32: Jar file already exists in schema
		if (sqlState.equalsIgnoreCase("X0Y32"))
			return true;

		// 42Y55: Table already exists in schema
		if (sqlState.equalsIgnoreCase("42Y55"))
			return true;

		return false;
	}

	public static void printSQLException(SQLException ex) {

		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {

					e.printStackTrace(System.err);
					System.err.println("SQLState: "
							+ ((SQLException) e).getSQLState());

					System.err.println("Error Code: "
							+ ((SQLException) e).getErrorCode());

					System.err.println("Message: " + e.getMessage());

					Throwable t = ex.getCause();
					while (t != null) {
						System.out.println("Cause: " + t);
						t = t.getCause();
					}
				}
			}
		}
	}

	private static void close() {
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