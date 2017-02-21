import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tda.src.datastructure.Counters;
import tda.src.datastructure.TestData;
import tda.src.datastructure.TestRun;
import tda.src.datastructure.TestedClass;
import tda.src.datastructure.UnitTest;

public class CountersTests { 
	
	private Counters counters; 
	
	@Before
	public void setUp() throws Exception {
		
		counters = new Counters("testSumAborted", "testSumCompleted", "testSumDisconnected", "testSumError", "testSumExecuted", 
				"testSumFailed", "testSumInProgress", "testSumInconclusive", "testSumNotExecuted", "testSumNotRunnable",
				"testSumPassed", "testSumPassedButRunAborted", "testSumPending", "testSumTimeOut", "testSumTotal", "testSumWarning"); 
	}
	
	@After 
	public void tearDown() throws Exception {
		counters = null; 
	}
	
	@Test
	public void testGetSumAbortedSuccess() {
		assertTrue(counters.getSumAborted().equals("testSumAborted")); 
	}
	
	@Test
	public void testSetSumAbortedSuccess() {
		String changedSumAborted = "changedSumAborted"; 
		counters.setSumAborted(changedSumAborted);
		assertTrue(counters.getSumAborted().equals(changedSumAborted)); 
	}
	
	@Test
	public void testGetSumCompletedSuccess() {
		assertTrue(counters.getSumCompleted().equals("testSumCompleted")); 
	}
	
	@Test
	public void testSetSumCompletedSuccess() {
		String changedSumCompleted = "changedSumCompleted"; 
		counters.setSumCompleted(changedSumCompleted);
		assertTrue(counters.getSumCompleted().equals(changedSumCompleted)); 
	}
	
	@Test 
	public void testGetSumDisconnectedSuccess() {
		assertTrue(counters.getSumDisconnected().equals("testSumDisconnected")); 
	}
	
	@Test
	public void testSetSumDisconnectedSuccess() {
		String changedSumDisconnected = "changedSumDisconnected"; 
		counters.setSumDisconnected(changedSumDisconnected);
		assertTrue(counters.getSumDisconnected().equals(changedSumDisconnected)); 
	}
	
	@Test 
	public void testGetSumErrorSuccess() {
		assertTrue(counters.getSumError().equals("testSumError")); 
	}
	
	@Test
	public void testSetSumErrorSuccess() {
		String changedSumError = "changedSumError"; 
		counters.setSumError(changedSumError);
		assertTrue(counters.getSumError().equals(changedSumError)); 
	}

	@Test 
	public void testGetSumExecutedSuccess() {
		assertTrue(counters.getSumExecuted().equals("testSumExecuted")); 
	}

	@Test
	public void testSetSumExecutedSuccess() {
		String changedSumExecuted = "changedSumExecuted"; 
		counters.setSumExecuted(changedSumExecuted);
		assertTrue(counters.getSumExecuted().equals(changedSumExecuted)); 
	}

	@Test 
	public void testGetSumFailedSuccess() {
		assertTrue(counters.getSumFailed().equals("testSumFailed")); 
	}

	@Test 
	public void testSetSumFailedSuccess() {
		String changedSumFailed = "changedSumFailed"; 
		counters.setSumFailed(changedSumFailed);
		assertTrue(counters.getSumFailed().equals(changedSumFailed)); 
	}

	@Test 
	public void testGetSumInProgressSuccess() {
		assertTrue(counters.getSumInProgress().equals("testSumInProgress")); 
	}

	@Test 
	public void testSetSumInProgressSuccess() {
		String changedSumInProgress = "changedSumInProgress"; 
		counters.setSumInProgress(changedSumInProgress);
		assertTrue(counters.getSumInProgress().equals(changedSumInProgress)); 
	}

	@Test 
	public void testGetSumInconclusiveSuccess() {
		assertTrue(counters.getSumInconclusive().equals("testSumInconclusive")); 
	}

	@Test 
	public void testSetSumInconclusiveSuccess() {
		String changedSumInconclusive = "changedSumInconclusive"; 
		counters.setSumInconclusive(changedSumInconclusive);
		assertTrue(counters.getSumInconclusive().equals(changedSumInconclusive)); 
	}

	@Test 
	public void testGetSumNotExecutedSuccess() {
		assertTrue(counters.getSumNotExecuted().equals("testSumNotExecuted")); 
	}

	@Test 
	public void testSetSumNotExecutedSuccess() {
		String changedSumNotExecuted = "changedSumNotExecuted"; 
		counters.setSumNotExecuted(changedSumNotExecuted);
		assertTrue(counters.getSumNotExecuted().equals(changedSumNotExecuted)); 
	}

	@Test 
	public void testGetSumNotRunnableSuccess() {
		assertTrue(counters.getSumNotRunnable().equals("testSumNotRunnable")); 
	}

	@Test
	public void testSetSumNotRunnableSuccess() {
		String changedSumNotRunnable = "changedSumNotRunnable"; 
		counters.setSumNotRunnable(changedSumNotRunnable);
		assertTrue(counters.getSumNotRunnable().equals(changedSumNotRunnable)); 
	}

	@Test 
	public void testGetSumPassedSuccess() {
		assertTrue(counters.getSumPassed().equals("testSumPassed")); 
	}

	@Test 
	public void testSetSumPassedSuccess() {
		String changedSumPassed = "changedSumPassed"; 
		counters.setSumPassed(changedSumPassed);
		assertTrue(counters.getSumPassed().equals(changedSumPassed)); 
	}

	@Test 
	public void testGetSumPassedButRunAbortedSuccess() {
		assertTrue(counters.getSumPassedButRunAborted().equals("testSumPassedButRunAborted")); 
	}

	@Test 
	public void testSetSumPassedButRunAbortedSuccess() {
		String changedSumPassedButRunAborted = "changedSumPassedButRunAborted"; 
		counters.setSumPassedButRunAborted(changedSumPassedButRunAborted);
		assertTrue(counters.getSumPassedButRunAborted().equals(changedSumPassedButRunAborted)); 
	}

	@Test 
	public void testGetSumPendingSuccess() {
		assertTrue(counters.getSumPending().equals("testSumPending")); 
	}

	@Test 
	public void testSetSumPendingSuccess() {
		String changedSumPending = "changedSumPending"; 
		counters.setSumPending(changedSumPending);
		assertTrue(counters.getSumPending().equals(changedSumPending)); 
	}

	@Test 
	public void testGetSumTimeOutSuccess() {
		assertTrue(counters.getSumTimeOut().equals("testSumTimeOut")); 
	}

	@Test 
	public void testSetSumTimeOutSuccess() {
		String changedSumTimeOut = "changedSumTimeOut"; 
		counters.setSumTimeOut(changedSumTimeOut);
		assertTrue(counters.getSumTimeOut().equals(changedSumTimeOut)); 
	}

	@Test 
	public void testGetSumTotalSuccess() {
		assertTrue(counters.getSumTotal().equals("testSumTotal")); 
	}

	@Test 
	public void testSetSumTotalSuccess() {
		String changedSumTotal = "changedSumTotal"; 
		counters.setSumTotal(changedSumTotal);
		assertTrue(counters.getSumTotal().equals(changedSumTotal)); 
	}

	@Test 
	public void testGetSumWarningSuccess() {
		assertTrue(counters.getSumWarning().equals("testSumWarning")); 
	}

	@Test 
	public void testSetSumWarningSuccess() {
		String changedSumWarning = "changedSumWarning"; 
		counters.setSumWarning(changedSumWarning);
		assertTrue(counters.getSumWarning().equals(changedSumWarning)); 
	}


}
