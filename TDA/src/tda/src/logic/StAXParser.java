package tda.src.logic;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;


public class StAXParser implements Parser {
	
	private final TestData testData;
	public StAXParser() {
		testData = TestData.getInstance();
	}

	public void parse(String path) {
		System.out.println("Starting to parse");
		boolean waitForStdOut = false;
		boolean readingStdOut = false;
		
		
		TestRun testRun = null;
		//UnitTest Data
		String unitTestID = "", unitTestName = "", unitTestExecutionID = "", testMethodName = "";
		//Tested Class Data
		String testedClassName = "";
		//TestRun Data
		String runID, runName, runUser;
		//TestRunTimeD
		String creationTime, finishTime, queuingTime, startTime;
		String outcome;
		
		//Counters
		String sumAborted;
		String sumCompleted;
		String sumDisconnected;
		String sumError;
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
		
		//StandardOuts
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
						//TODO: Create TestedClass Object
					}
					
					if ("Execution".equals(reader.getLocalName())) {
						if(reader.getAttributeCount() == 1){
							// To avoid wrong 'execution' start element
							unitTestExecutionID = reader.getAttributeValue(0);
						}
					}
					if ("TestMethod".equals(reader.getLocalName())) {
						testedClassName = reader.getAttributeValue(1);
						testMethodName = reader.getAttributeValue(3);
						//TODO: Create UnitTest Object
					}
					if ("TestRun".equals(reader.getLocalName())) {
						
						runID = reader.getAttributeValue(1);
						runName = reader.getAttributeValue(2);
						runUser = reader.getAttributeValue(3);
						
						testRun = new TestRun(runID, runName, runUser);
						
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
						sumFailed = reader.getAttributeValue(4);
						sumInProgress = reader.getAttributeValue(5);
						sumInconclusive = reader.getAttributeValue(6);
						sumNotExecuted = reader.getAttributeValue(7);
						sumNotRunnable = reader.getAttributeValue(8);
						sumPassed = reader.getAttributeValue(9);
						sumPassedButRunAborted = reader.getAttributeValue(10);
						sumPending = reader.getAttributeValue(11);
						sumTimeOut = reader.getAttributeValue(12);
						sumTotal = reader.getAttributeValue(13);
						sumWarning = reader.getAttributeValue(14);
						//TODO: Create Counters Class
					}
					if ("StdOut".equals(reader.getLocalName()) && waitForStdOut) {
						readingStdOut = true;
					}
					break;
				
				case XMLStreamConstants.CHARACTERS:
					if(readingStdOut){
						stdOutContent = reader.getText().trim();
					}
					break;
				
				case XMLStreamConstants.END_ELEMENT:
					if ("UnitTest".equals(reader.getLocalName())) {
						
						UnitTest unitTest = new UnitTest(testRun, unitTestID, unitTestName, unitTestExecutionID, testMethodName);
						TestedClass testedClass = new TestedClass(testedClassName, unitTest);
						
						testData.addNewTestedClass(testedClass);
						testData.addNewUnitTest(unitTest);
						

						
						
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