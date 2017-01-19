import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tda.src.logic.TestRunStartTime;

public class TestRunStartTimeTests {
	
	private TestRunStartTime time1; 
	private TestRunStartTime time2; 
	
	@Before
	public void setUp() throws Exception {
		time1 = new TestRunStartTime(2017, 1, 12, 19, 5, 23); 
		time2 = new TestRunStartTime(2017, 1, 12, 19, 5, 25); 		 
	}
	
	@After 
	public void tearDown() throws Exception {
		time1 = null; 
		time2 = null; 
	}
	
	@Test
	public void getYearSuccess() {
		int year = time1.getYear(); 
		assertEquals(2017, year); 
	}

	@Test
	public void getMonthSuccess() {
		int month = time1.getMonth(); 
		assertEquals(1, month); 
	}

	@Test
	public void getDaySuccess() {
		int day = time1.getDay(); 
		assertEquals(12, day); 
	}

	@Test
	public void getHourSuccess() {
		int hour = time1.getHour(); 
		assertEquals(19, hour); 
	}

	@Test
	public void getMinSuccess() {
		int minute = time1.getMin(); 
		assertEquals(5, minute); 
	}

	@Test
	public void getSecSuccess() {
		int second = time1.getSec(); 
		assertEquals(second, 23); 
	}
	
	@Test
	public void toStringSuccess() {
		String string = time1.toString(); 
		assertTrue(("TestRunStartTime " + "[" + 2017 + "-" + 1 + "-" + 12 + " " + 19 + ":" + 5 + ":" + 23 + "]").equals(string));
	}
	
	@Test 
	public void compareToSuccess(){
		int comparison = time1.compareTo(time2); 
		System.out.println(comparison);
		assertEquals(1, comparison); 
	}

}
