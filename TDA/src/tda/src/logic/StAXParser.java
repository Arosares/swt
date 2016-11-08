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
						String executionID = reader.getAttributeValue(0);
					
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
