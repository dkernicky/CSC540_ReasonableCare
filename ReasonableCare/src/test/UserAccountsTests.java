package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import MainCode.*;

public class UserAccountsTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	// Creating a valid student should update the database accordingly
	public void createStudentTest() {
		fail("Not yet implemented");
	}
	
	@Test
	// Creating a valid doctor should update the database accordingly
	public void createDoctorTest() {
		fail("Not yet implemented");
	}
	
	@Test
	// Creating a valid staff member should update the database accordingly
	public void createStaffTest() {
		fail("Not yet implemented");
	}	
	
	@Test
	// Creating a valid appointment should update the database accordingly
	public void createAppointmentTest() {
		fail("Not yet implemented");
	}
	
	@Test
	// Querying an existing appointment should return the appropriate values
	public void viewExistingAppointmentTest() {
		fail("Not yet implemented");
	}
	
	@Test
	// Updating an appointment with valid attributes should update the database
	public void updateAppointmentTest() {
		fail("Not yet implemented");
	}	
	
	@Test
	// A student with all vaccinations completed should have no holds
	public void verifyNoHoldsTest() {
		fail("Not yet implemented");
	}
	
	@Test
	// A student without all vaccinations completed should have holds
	public void verifyHoldsTest() {
		fail("Not yet implemented");
	}
}
