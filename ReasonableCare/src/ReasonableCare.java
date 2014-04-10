import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReasonableCare 
{
	private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
	// Put your oracle ID and password here
	private static final String user = "djkernic";
	private static final String password = "001101409";

	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet result = null;

	public static void main(String[] args) 
	{
		initialize();

//		try 
//		{
//			//result = statement.executeQuery("SELECT * FROM STUDENT WHERE id = 1");
//			//while(result.next()) {
//			//	System.out.println(result.getInt("id"));
//			//}
//			//result = statement.executeQuery("SELECT * FROM user_tables;");
//			
//		}
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//		}
		close();
	}
	
	private static void initialize() 
	{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(jdbcURL, user, password);
			statement = connection.createStatement();

			try 
			{
				/* DROP TABLES */
				statement.executeUpdate("DROP TABLE billing_statement ");
				statement.executeUpdate("DROP TABLE appointment ");
				statement.executeUpdate("DROP TABLE student ");
				statement.executeUpdate("DROP TABLE nurse ");
				statement.executeUpdate("DROP TABLE doctor ");
				statement.executeUpdate("DROP TABLE medical_staff ");
				statement.executeUpdate("DROP TABLE staff ");
				statement.executeUpdate("DROP TABLE login ");
				statement.executeUpdate("DROP TABLE person ");
				statement.executeUpdate("DROP TABLE health_insurance ");

//				/* DROP SEQUENCES */
				statement.executeUpdate("DROP SEQUENCE person_seq");
				statement.executeUpdate("DROP SEQUENCE appointment_seq");
				statement.executeUpdate("DROP SEQUENCE billing_statement_seq");
			}
			catch (SQLException e) {
				printSQLException(e);
			}
			/* CREATE SEQUENCES */
			statement.executeUpdate("CREATE SEQUENCE person_seq START WITH 0 MINVALUE 0 INCREMENT BY 1");
			statement.executeUpdate("CREATE SEQUENCE appointment_seq START WITH 0 MINVALUE 0 INCREMENT BY 1");
			statement.executeUpdate("CREATE SEQUENCE billing_statement_seq START WITH 0 MINVALUE 0 INCREMENT BY 1");

			/* CREATE TABLES */
			statement.executeUpdate("CREATE TABLE health_insurance( name VARCHAR(100) PRIMARY KEY, address VARCHAR(100), phone_num VARCHAR(10))");
			statement.executeUpdate("CREATE TABLE person(id INT PRIMARY KEY, name VARCHAR(100),phone_num VARCHAR(10))");
			statement.executeUpdate("CREATE TABLE login(login_id VARCHAR(30) PRIMARY KEY, login_pwd VARCHAR(30) NOT NULL,person_id INT,FOREIGN KEY (person_id) REFERENCES person(id))");
			statement.executeUpdate("CREATE TABLE student( id INT PRIMARY KEY,date_of_birth DATE,ins_acct_id INT,insurance_name VARCHAR(30),FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE, FOREIGN KEY (insurance_name) REFERENCES health_insurance(name))");
			statement.executeUpdate("CREATE TABLE staff( id INT PRIMARY KEY,FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE)");		  
			statement.executeUpdate("CREATE TABLE medical_staff( id INT PRIMARY KEY,FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE)");		  
			statement.executeUpdate("CREATE TABLE nurse(id INT PRIMARY KEY, FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE)");	  
			statement.executeUpdate("CREATE TABLE doctor(id INT PRIMARY KEY, specialization VARCHAR(30), FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE)");	  
			statement.executeUpdate("CREATE TABLE appointment( id INT PRIMARY KEY,type CHAR(1), app_date DATE,reason VARCHAR(30), notes VARCHAR(1000), student_id INT, med_staff_id INT, FOREIGN KEY (student_id) REFERENCES student(id),FOREIGN KEY (med_staff_id) REFERENCES medical_staff(id))");
			statement.executeUpdate("CREATE TABLE billing_statement(id INT PRIMARY KEY,amt_owed FLOAT NOT NULL, amt_covered FLOAT NOT NULL, appointment_id INT,FOREIGN KEY (appointment_id) REFERENCES appointment(id) ON DELETE CASCADE)");

//			/* SEED DB WITH DATA */
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Bob Higgins', '1111111111')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'George Thompson', '1234567890')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Dan Walker', '3265497310')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Sophie Bell', '5861237940')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Elizabeth Edmunds', '9134876520')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Virginia Wright', '4567891230')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Jason Hudson', '4987561230')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Joe MacLeod', '1049587412')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Megan Wallace', '4978561251')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Bella Young', '4913658756')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Diane Baker', '9876543210')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Joseph Nolan', '6489751238')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Olivia Pullman', '1645528954')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Karen Walker', '8451239753')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Zoe Hill', '9163485205')");
//			statement.executeUpdate("INSERT INTO person(id, name, phone_num) VALUES (person_seq.nextval, 'Alexandra May', '7361594583')");
//
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user1', 16,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user2', 1,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user3', 2,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user4', 3,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user5', 4,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user6', 5,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user7', 6,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user8', 7,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user9', 8,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user10', 9,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user11', 10,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user12', 11,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user13', 12,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user14', 13,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user15', 14,'password')");
//			statement.executeUpdate("INSERT INTO login(login_id, person_id, login_pwd) VALUES ('user16', 15,'password')");
//
//			statement.executeUpdate("INSERT INTO health_insurance(name, address, phone_num) VALUES ('ACME', '121 Baker St', '8955556217')");
//			statement.executeUpdate("INSERT INTO health_insurance(name, address, phone_num) VALUES ('BCBS', '115 Cedar Ln', '6665559345')");
//			statement.executeUpdate("INSERT INTO health_insurance(name, address, phone_num) VALUES ('Primary Care', '105 Highland Ave', '2225557785')");
//			statement.executeUpdate("INSERT INTO health_insurance(name, address, phone_num) VALUES ('Alliance', '227 First St', '3695556217')");
//
//			statement.executeUpdate("INSERT INTO student(id, date_of_birth, ins_acct_id, insurance_name) VALUES (16, to_date('5-MAR-1995:12:00:00PM', 'DD-MON-YYYY:HH:MI:SSPM'), 12345, 'ACME')");
//			statement.executeUpdate("INSERT INTO student(id, date_of_birth, ins_acct_id, insurance_name) VALUES (1, to_date('18-JAN-1993:12:00:00PM', 'DD-MON-YYYY:HH:MI:SSPM'), 54321, 'Primary Care')");
//			statement.executeUpdate("INSERT INTO student(id, date_of_birth, ins_acct_id, insurance_name) VALUES (2, to_date('28-FEB-1993:12:00:00PM', 'DD-MON-YYYY:HH:MI:SSPM'), 98765, 'Alliance')");
//			statement.executeUpdate("INSERT INTO student(id, date_of_birth, ins_acct_id, insurance_name) VALUES (3, to_date('15-MAR-1994:12:00:00PM', 'DD-MON-YYYY:HH:MI:SSPM'), 56789, 'BCBS')");
//
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (4)");
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (5)");
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (6)");
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (7)");
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (8)");
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (9)");
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (10)");
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (11)");
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (12)");
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (13)");
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (14)");
//			statement.executeUpdate("INSERT INTO staff(id) VALUES (15)");
//
//			statement.executeUpdate("INSERT INTO medical_staff(id) VALUES (8)");
//			statement.executeUpdate("INSERT INTO medical_staff(id) VALUES (9)");
//			statement.executeUpdate("INSERT INTO medical_staff(id) VALUES (10)");
//			statement.executeUpdate("INSERT INTO medical_staff(id) VALUES (11)");
//			statement.executeUpdate("INSERT INTO medical_staff(id) VALUES (12)");
//			statement.executeUpdate("INSERT INTO medical_staff(id) VALUES (13)");
//			statement.executeUpdate("INSERT INTO medical_staff(id) VALUES (14)");
//			statement.executeUpdate("INSERT INTO medical_staff(id) VALUES (15)");
//
//			statement.executeUpdate("INSERT INTO nurse(id) VALUES (8)");
//			statement.executeUpdate("INSERT INTO nurse(id) VALUES (9)");
//			statement.executeUpdate("INSERT INTO nurse(id) VALUES (10)");
//			statement.executeUpdate("INSERT INTO nurse(id) VALUES (11)");
//
//			statement.executeUpdate("INSERT INTO doctor(id, specialization) VALUES (12, 'pediatrics')");
//			statement.executeUpdate("INSERT INTO doctor(id, specialization) VALUES (13, 'physician')");
//			statement.executeUpdate("INSERT INTO doctor(id, specialization) VALUES (14, 'orthopedics')");
//			statement.executeUpdate("INSERT INTO doctor(id, specialization) VALUES (15, 'pulmonology')");
//
//			statement.executeUpdate("INSERT INTO appointment(id, type, app_date, reason, notes, student_id, med_staff_id) VALUES (appointment_seq.nextval, 'P', to_date('13-MAR-2014:12:00:00PM', 'DD-MON-YYYY:HH:MI:SSPM'), 'checkup','passed', 16, 15)");
//			statement.executeUpdate("INSERT INTO appointment(id, type, app_date, reason, notes, student_id, med_staff_id) VALUES (appointment_seq.nextval, 'C', to_date('13-MAR-2014:01:00:00PM', 'DD-MON-YYYY:HH:MI:SSPM'), 'sickness','prescribing medication', 2, 15)");
//			statement.executeUpdate("INSERT INTO appointment(id, type, app_date, reason, notes, student_id, med_staff_id) VALUES (appointment_seq.nextval, 'V', to_date('13-MAR-2014:12:00:00PM', 'DD-MON-YYYY:HH:MI:SSPM'), 'measles', '', 3, 13)");
//			statement.executeUpdate("INSERT INTO appointment(id, type, app_date, reason, notes, student_id, med_staff_id) VALUES (appointment_seq.nextval, 'P', to_date('15-MAR-2014:12:00:00PM', 'DD-MON-YYYY:HH:MI:SSPM'), 'checkup', 'passed', 2, 14)");
//
//			statement.executeUpdate("INSERT INTO billing_statement(id, amt_owed, amt_covered, appointment_id) VALUES (billing_statement_seq.nextval, 20.00, 20.00, 4)");
//			statement.executeUpdate("INSERT INTO billing_statement(id, amt_owed, amt_covered, appointment_id) VALUES (billing_statement_seq.nextval, 30.00, 20.00, 1)");
//			statement.executeUpdate("INSERT INTO billing_statement(id, amt_owed, amt_covered, appointment_id) VALUES (billing_statement_seq.nextval, 50.00, 86.00, 2)");
//			statement.executeUpdate("INSERT INTO billing_statement(id, amt_owed, amt_covered, appointment_id) VALUES (billing_statement_seq.nextval, 30.00, 10.00, 3)");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
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
	            if (ignoreSQLException(
	                ((SQLException)e).
	                getSQLState()) == false) {

	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " +
	                    ((SQLException)e).getSQLState());

	                System.err.println("Error Code: " +
	                    ((SQLException)e).getErrorCode());

	                System.err.println("Message: " + e.getMessage());

	                Throwable t = ex.getCause();
	                while(t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }
	}

	


	private static void close() 
	{
		if(connection != null) 
		{
			try 
			{
				connection.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		if(statement != null) 
		{
			try 
			{
				statement.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		if(result != null) 
		{
			try 
			{
				result.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
}