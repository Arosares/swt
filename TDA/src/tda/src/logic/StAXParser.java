package tda.src.logic;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import tda.src.exceptions.WrongXMLAttributeException;

public class StAXParser implements Parser {

	private final TestData testData;

	public StAXParser() {
		testData = TestData.getInstance();
	}

	public void parse(String path) throws WrongXMLAttributeException {
		// System.out.println("Starting to parse");
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
		String runID, runName;
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
						if (reader.getAttributeLocalName(0).equals("id")
								&& reader.getAttributeLocalName(1).equals("name")) {
							unitTestID = reader.getAttributeValue(0);
							unitTestName = reader.getAttributeValue(1);
						} else {
							throw new WrongXMLAttributeException("Expected another attribute in tag " 
						+ reader.getLocalName());
						}

					}
					if ("UnitTestResult".equals(reader.getLocalName())) {
						if(reader.getAttributeLocalName(8).equals("testId") 
								&& reader.getAttributeLocalName(5).equals("outcome")){
							resultUnitTestID = reader.getAttributeValue(8);
							unitTestOutcome = reader.getAttributeValue(5);

							for (UnitTest unitTest : unitTestsOfOneRun) {
								if (unitTest.getUnitTestID().equals(resultUnitTestID)) {
									unitTest.setOutcome(unitTestOutcome);
								}
							}
						}else {
							throw new WrongXMLAttributeException("Expected another attribute in tag " 
						+ reader.getLocalName());
						}
						
					}

					if ("Execution".equals(reader.getLocalName()) 
							&& reader.getAttributeCount() == 1) {
						if (reader.getAttributeLocalName(0).equals("id")) {
							// To avoid wrong 'execution' start element
							unitTestExecutionID = reader.getAttributeValue(0);
						}else {
							throw new WrongXMLAttributeException("Expected another attribute in tag " 
						+ reader.getLocalName());
						}
					}
					if ("TestMethod".equals(reader.getLocalName())) {
						if(reader.getAttributeLocalName(1).equals("className") 
								&& reader.getAttributeLocalName(3).equals("name")){
							testedClassName = reader.getAttributeValue(1);
							testMethodName = reader.getAttributeValue(3);
							// TODO: Create UnitTest Object
						}else {
							throw new WrongXMLAttributeException("Expected another attribute in tag " 
						+ reader.getLocalName());
						}
					}
					if ("TestRun".equals(reader.getLocalName())) { 
						if(reader.getAttributeLocalName(0).equals("id") 
								&& reader.getAttributeLocalName(1).equals("name")){

							runID = reader.getAttributeValue(0);
							runName = reader.getAttributeValue(1);

							testRun = new TestRun(runID, runName);
							testRun.setPath(path);

						} else {
							throw new WrongXMLAttributeException("Expected another attribute in tag " 
						+ reader.getLocalName());
						}
					}
					if ("Times".equals(reader.getLocalName())) {
						if(reader.getAttributeLocalName(0).equals("creation") 
								&& reader.getAttributeLocalName(1).equals("finish") 
								&& reader.getAttributeLocalName(2).equals("queuing") 
								&& reader.getAttributeLocalName(3).equals("start")){ 
							creationTime = reader.getAttributeValue(0);
							finishTime = reader.getAttributeValue(1);
							queuingTime = reader.getAttributeValue(2);
							startTime = reader.getAttributeValue(3);
							
							testRun.setStartTime(startTime);
							testData.addNewTestRun(testRun);
						} else {
							throw new WrongXMLAttributeException("Expected another attribute in tag " 
						+ reader.getLocalName());
						}
					}
					if ("ResultSummary".equals(reader.getLocalName())) {
						if(reader.getAttributeLocalName(0).equals("outcome")){
							outcome = reader.getAttributeValue(0);
							waitForStdOut = true;
						}else {
							throw new WrongXMLAttributeException("Expected another attribute in tag " 
						+ reader.getLocalName());
						}
					}
					if ("Counters".equals(reader.getLocalName())) {
						if(reader.getAttributeLocalName(0).equals("aborted") 
								&& reader.getAttributeLocalName(1).equals("completed") 
								&& reader.getAttributeLocalName(2).equals("disconnected")
								&& reader.getAttributeLocalName(3).equals("error")
								&& reader.getAttributeLocalName(4).equals("executed")
								&& reader.getAttributeLocalName(5).equals("failed")
								&& reader.getAttributeLocalName(6).equals("inProgress")
								&& reader.getAttributeLocalName(7).equals("inconclusive")
								&& reader.getAttributeLocalName(8).equals("notExecuted")
								&& reader.getAttributeLocalName(9).equals("notRunnable") 
								&& reader.getAttributeLocalName(10).equals("passed")
								&& reader.getAttributeLocalName(11).equals("passedButRunAborted")
								&& reader.getAttributeLocalName(12).equals("pending")
								&& reader.getAttributeLocalName(13).equals("timeout")
								&& reader.getAttributeLocalName(14).equals("total")
								&& reader.getAttributeLocalName(15).equals("warning")) {
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
						}else {
							throw new WrongXMLAttributeException("Expected another attribute in tag " 
						+ reader.getLocalName());
						}
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

						testData.addNewTestedClassToTree(testedClass);
						testData.addNewUnitTest(unitTest);
						unitTestsOfOneRun.add(unitTest);

						testRun.addTestedClassToTestRun(testData.getClass(testedClass));
					}
					if ("TestRun".equals(reader.getLocalName())) {
//						System.out.println("Finished TestRun: " + testRun.getRunID());
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