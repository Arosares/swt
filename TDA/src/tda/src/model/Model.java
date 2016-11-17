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

	public Model(Controller controller) {
		this.controller = controller;

		// initialize program:
		testData = TestData.getInstance();
		parser = new StAXParser();
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
