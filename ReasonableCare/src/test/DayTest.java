package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import MainCode.*;

public class DayTest {

	@Before
	public void setUp() throws Exception {
		ReasonableCare.initialize();
	}

	@After
	public void tearDown() throws Exception {
		ReasonableCare.close();
	}

	@Test
	public void dateAvailableTest() throws SQLException {
		String date = "14-APR-2014";
		//System.out.println("Testing");
		boolean avail = ReasonableCare.doctorAvailable(10001, date);
		//char c = ReasonableCare.getDayOfWeek(date);
		assertTrue(avail);
		
		date  = "17-APR-2014";
		avail = ReasonableCare.doctorAvailable(10001, date);
		assertTrue(!avail);
	}
	
	@Test
	public void timeAvailableTest() throws SQLException {
		String date = "21-APR-2014";
		String time = "4:00PM";
		boolean avail = ReasonableCare.timeAvailable(10001, date, time);
		assertTrue(!avail);
		
		date = "22-APR-2014";
		avail = ReasonableCare.timeAvailable(10001, date, time);
		assertTrue(avail);
		
	}

}
