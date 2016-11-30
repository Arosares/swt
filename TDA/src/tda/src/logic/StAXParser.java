package tda.src.logic;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class StAXParser implements Parser {

	private final TestData testData;

	public StAXParser() {
		testData = TestData.getInstance();
	}

	public void parse(String path) {
		System.out.println("Starting to parse");
		List<UnitTest> unitTestsOfOneRun = new LinkedList<>();
		boolean waitForStdOut = false;
		boolean readingStdOut = false;

		TestRun testRun = null;
		// UnitTest Data
		String unitTestID = "", unitTestName = "", unitTestExecutionID = "", testMethodName = "";
		// UnitTestResult Data
		String resultUnitTestID;
		String unitTestOutcome;
		// Tested Class Data
		String testedClassName = "";
		// TestRun Data
		String runID, runName, runUser;
		// TestRunTimeD
		String creationTime, finishTime, queuingTime, startTime;
		String outcome;

		// Counters
		String sumAborted;
		String sumCompleted;
		String sumDisconnected;
		String sumError;
		String sumExecuted;
		String sumFailed;
		String sumInProgress;
		String sumInconclusive;
		String sumNotExecuted;
		String sumNotRunnable;
		String sumPassed;
		String sumPassedButRunAborted;
		String sumPending;
		String sumTimeOut;
		String sumTotal;
		String sumWarning;

		// StandardOuts
		String stdOutContent;

		UnitTestsToTestRunMapper mapper = null;

		try {
			// creating inputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// create InputStream
			XMLStreamReader reader = inputFactory.createXMLStreamReader(new FileInputStream(path));

			while (reader.hasNext()) {
				int event = reader.next();
				switch (event) {
				case XMLStreamConstants.START_ELEMENT:
					if ("UnitTest".equals(reader.getLocalName())) {
						unitTestID = reader.getAttributeValue(0);
						unitTestName = reader.getAttributeValue(1);
						// TODO: Create TestedClass Object
					}
					if ("UnitTestResult".equals(reader.getLocalName())) {
						resultUnitTestID = reader.getAttributeValue(8);
						unitTestOutcome = reader.getAttributeValue(5);

						for (UnitTest unitTest : unitTestsOfOneRun) {
							if (unitTest.getUnitTestID().equals(resultUnitTestID)) {
								unitTest.setOutcome(unitTestOutcome);
							}
						}
					}

					if ("Execution".equals(reader.getLocalName())) {
						if (reader.getAttributeCount() == 1) {
							// To avoid wrong 'execution' start element
							unitTestExecutionID = reader.getAttributeValue(0);
						}
					}
					if ("TestMethod".equals(reader.getLocalName())) {
						testedClassName = reader.getAttributeValue(1);
						testMethodName = reader.getAttributeValue(3);
						// TODO: Create UnitTest Object
					}
					if ("TestRun".equals(reader.getLocalName())) {

						runID = reader.getAttributeValue(1);
						runName = reader.getAttributeValue(2);
						runUser = reader.getAttributeValue(3);

						testRun = new TestRun(runID, runName, runUser);
						testRun.setPath(path);
						testData.addNewTestRun(testRun);

					}
					if ("Times".equals(reader.getLocalName())) {
						creationTime = reader.getAttributeValue(0);
						finishTime = reader.getAttributeValue(1);
						queuingTime = reader.getAttributeValue(2);
						startTime = reader.getAttributeValue(3);

					}
					if ("ResultSummary".equals(reader.getLocalName())) {
						outcome = reader.getAttributeValue(0);
						waitForStdOut = true;
					}
					if ("Counters".equals(reader.getLocalName())) {
						sumAborted = reader.getAttributeValue(0);
						sumCompleted = reader.getAttributeValue(1);
						sumDisconnected = reader.getAttributeValue(2);
						sumError = reader.getAttributeValue(3);
						sumExecuted = reader.getAttributeValue(4);
						sumFailed = reader.getAttributeValue(5);
						sumInProgress = reader.getAttributeValue(6);
						sumInconclusive = reader.getAttributeValue(7);
						sumNotExecuted = reader.getAttributeValue(8);
						sumNotRunnable = reader.getAttributeValue(9);
						sumPassed = reader.getAttributeValue(10);
						sumPassedButRunAborted = reader.getAttributeValue(11);
						sumPending = reader.getAttributeValue(12);
						sumTimeOut = reader.getAttributeValue(13);
						sumTotal = reader.getAttributeValue(14);
						sumWarning = reader.getAttributeValue(15);

						// Create Counters Class
						Counters counter = new Counters(sumAborted, sumCompleted, sumDisconnected, sumError,
								sumExecuted, sumFailed, sumInProgress, sumInconclusive, sumNotExecuted, sumNotRunnable,
								sumPassed, sumPassedButRunAborted, sumPending, sumTimeOut, sumTotal, sumWarning);
						testRun.setResultSummary(counter);
					}
					if ("StdOut".equals(reader.getLocalName()) && waitForStdOut) {
						readingStdOut = true;
					}
					break;

				case XMLStreamConstants.CHARACTERS:
					if (readingStdOut) {
						stdOutContent = reader.getText().trim();
					}
					break;

				case XMLStreamConstants.END_ELEMENT:
					if ("UnitTest".equals(reader.getLocalName())) {

						UnitTest unitTest = new UnitTest(testRun, unitTestID, unitTestName, unitTestExecutionID,
								testMethodName);
						TestedClass testedClass = new TestedClass(testedClassName, unitTest);

						testData.addNewTestedClass(testedClass);
						testData.addNewUnitTest(unitTest);
						unitTestsOfOneRun.add(unitTest);

						testRun.addTestedClassToTestRun(testedClass);

					}
					if ("TestRun".equals(reader.getLocalName())) {
						System.out.println("Finished TestRun: " + testRun.getRunID());
					}
					break;

				default:
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}

	}
}