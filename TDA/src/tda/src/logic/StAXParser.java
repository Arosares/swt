package tda.src.logic;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class StAXParser implements Parser {

	private String xmlPath = "/afs/swt.wiai.uni-bamberg.de/users/home.swt-041097/XML_Files/testRun_1.xml";
	private List<Test> unitTests;
	
	public void parse() {
		boolean waitForStdOut = false;
		boolean readingStdOut = false;
		
		
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
						String unitTestID = reader.getAttributeValue(0);
						String name = reader.getAttributeValue(1);
						//TODO: Create TestedClass Object
					}
					
					if ("Execution".equals(reader.getLocalName())) {
						if(reader.getAttributeCount() == 1){
							// To avoid wrong 'execution' start element
							String executionID = reader.getAttributeValue(0);
						}
					}
					if ("TestMethod".equals(reader.getLocalName())) {
						String className = reader.getAttributeValue(1);
						String methodName = reader.getAttributeValue(3);
						//TODO: Create TestedClass Object
					}
					if ("TestRun".equals(reader.getLocalName())) {
						String runID = reader.getAttributeValue(1);
						String runName = reader.getAttributeValue(2);
						String runUser = reader.getAttributeValue(3);

					}
					if ("Times".equals(reader.getLocalName())) {
						String creation = reader.getAttributeValue(0);
						String finish = reader.getAttributeValue(1);
						String queuing = reader.getAttributeValue(2);
						String start = reader.getAttributeValue(3);

					}
					if ("ResultSummary".equals(reader.getLocalName())) {
						String outcome = reader.getAttributeValue(0);
						waitForStdOut = true;
					}
					if ("Counters".equals(reader.getLocalName())) {
						String sumAborted = reader.getAttributeValue(0);
						String sumCompleted = reader.getAttributeValue(1);
						String sumDisconnected = reader.getAttributeValue(2);
						String sumError = reader.getAttributeValue(3);
						String sumFailed = reader.getAttributeValue(4);
						String sumInProgress = reader.getAttributeValue(5);
						String sumInconclusive = reader.getAttributeValue(6);
						String sumNotExecuted = reader.getAttributeValue(7);
						String sumNotRunnable = reader.getAttributeValue(8);
						String sumPassed = reader.getAttributeValue(9);
						String sumPassedButRunAborted = reader.getAttributeValue(10);
						String sumPending = reader.getAttributeValue(11);
						String sumTimeOut = reader.getAttributeValue(12);
						String sumTotal = reader.getAttributeValue(13);
						String sumWarning = reader.getAttributeValue(14);
						//TODO: Create Counters Class
					}
					if ("StdOut".equals(reader.getLocalName()) && waitForStdOut) {
						readingStdOut = true;
					}
					break;
				
				case XMLStreamConstants.CHARACTERS:
					if(readingStdOut){
						String stdOutContent = reader.getText().trim();
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
