package tda.src.logic;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class StAXParser implements Parser {

	private String xmlPath = "/afs/swt.wiai.uni-bamberg.de/users/home.swt-041097/XML_Files/testRun_1.xml";
	private List<UnitTest> unitTests;
	
	public void parse() {
		boolean waitForStdOut = false;
		boolean readingStdOut = false;
		
		String unitTestID;
		String testName;
		String executionID;
		String className;
		String methodName;
		String runID;
		String runName;
		String runUser;
		String creationTime;
		String finishTime;
		String queuingTime;
		String startTime;
		String outcome;
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
		String stdOutContent;
		
		
		
		
		try {

			// creating inputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// create InputStream
			XMLStreamReader reader = inputFactory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(xmlPath));
			
			while (reader.hasNext()) {
				int event = reader.next();
				
				switch (event) {
				case XMLStreamConstants.START_ELEMENT:
					if ("UnitTest".equals(reader.getLocalName())) {
						unitTestID = reader.getAttributeValue(0);
						testName = reader.getAttributeValue(1);
						//TODO: Create TestedClass Object
					}
					
					if ("Execution".equals(reader.getLocalName())) {
						if(reader.getAttributeCount() == 1){
							// To avoid wrong 'execution' start element
							executionID = reader.getAttributeValue(0);
						}
					}
					if ("TestMethod".equals(reader.getLocalName())) {
						className = reader.getAttributeValue(1);
						methodName = reader.getAttributeValue(3);
						//TODO: Create UnitTest Object
					}
					if ("TestRun".equals(reader.getLocalName())) {
						runID = reader.getAttributeValue(1);
						runName = reader.getAttributeValue(2);
						runUser = reader.getAttributeValue(3);

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
					break;
				
				default:
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Set<TestRun> getTestRun() {
		// TODO Auto-generated method stub
		return null;
	}

}
