package tda.src.model;

import java.util.Set;

import tda.src.logic.Parser;
import tda.src.logic.StAXParser;
import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.logic.UnitTest;

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
	
	public TestData getTestDataInstance() {
		return testData;
	}


	public Model() {
		super();
		//initialize program:
		testData = new TestData();
		Parser parser = new StAXParser(this);
		parser.parse();
		
		System.out.println(testData.getTestedClassList());
		
		
		parser.parse();
		System.out.println(testData.getTestedClassList());
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
