package tda.src.model;

import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

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

	public Model() {
		super();
		// initialize program:
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

	public void parseFile(String xmlPath) {
		parser.parse(xmlPath);
	}

	public List<TestedClass> getTestedClassesFromTestRun(TestRun testRun) {
		List<TestedClass> classesOfOneRun = testRun.getTestedClasses();
		for (TestedClass testedClass : classesOfOneRun) {
			testedClass.setCurrentFailurePercentage(testRun);
		}
		
		return classesOfOneRun;
		
	}

}
