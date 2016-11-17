package tda.src.model;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import tda.src.controller.Controller;
import tda.src.logic.Parser;
import tda.src.logic.StAXParser;
import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;

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
	final private Controller controller;

	public TestData getTestDataInstance() {
		return testData;
	}

	public Model(Controller controller) {
		this.controller = controller;

		// initialize program:
		testData = new TestData();
		Parser parser = new StAXParser(this);
		parser.parse();
		System.out.println(testData.getTestedClassList().size());

		parser.parse();
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

	public void parseFile(Path xmlPath) {
		// TODO Auto-generated method stub
		parser.parse(xmlPath.toString());
	}

}
