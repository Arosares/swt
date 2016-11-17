package tda.src.model;

import java.util.Set;

import tda.src.logic.Parser;
import tda.src.logic.StAXParser;
import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.logic.UnitTest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Observable;

public class Model extends Observable {
	/**
	 * <pre>
	 *           1..1     1..1
	 * Model ------------------------> Parser
	 *           model        &gt;       parser
	 * </pre>
	 */
	private Parser parser;
	final private TestData testData;
	
	public Model() {
		super();
		//initialize program:
		testData = TestData.getInstance();
		Parser parser = new StAXParser();
		
		String path = "/afs/swt.wiai.uni-bamberg.de/users/home.swt-041097/XML_Files/testRun_1.xml";
		parser.parse(path);
		System.out.println(testData.getTestedClassList().size());

		parser.parse(path);
		System.out.println(testData.getTestedClassList().size());
		for (TestedClass testedClass : testData.getTestedClassList()) {
			System.out.println(testedClass);
		}
	}
	
	
	public void setParser(Parser value) {
		this.parser = value;
	}

	public Parser getParser() {
		return this.parser;
	}

	/**
	 * <pre>
	 *           1..1     0..*
	 * Model ------------------------> TestRun
	 *           model        &gt;       testRun
	 * </pre>
	 */
	private Set<TestRun> testRun;

	public Set<TestRun> getTestRun() {
		if (this.testRun == null) {
			this.testRun = new HashSet<TestRun>();
		}
		return this.testRun;
	}

}
