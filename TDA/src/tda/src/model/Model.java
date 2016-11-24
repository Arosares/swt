package tda.src.model;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import tda.src.logic.Parser;
import tda.src.logic.StAXParser;
import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.logic.UnitTestsToTestRunMapper;

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
		// initialize program:
		testData = TestData.getInstance();
		parser = new StAXParser();

		// debug(parser);
	}

	private void debug(Parser parser) {
		String path = "/afs/swt.wiai.uni-bamberg.de/users/home.swt-041097/XML_Files/testRun_1.xml";
		parser.parse(path);

		path = "/afs/swt.wiai.uni-bamberg.de/users/home.swt-041097/XML_Files/testRun_2.xml";
		parser.parse(path);

		path = "/afs/swt.wiai.uni-bamberg.de/users/home.swt-041097/XML_Files/testRun_3.xml";
		parser.parse(path);

		path = "/afs/swt.wiai.uni-bamberg.de/users/home.swt-041097/XML_Files/testRun_4.xml";
		parser.parse(path);

		path = "/afs/swt.wiai.uni-bamberg.de/users/home.swt-041097/XML_Files/testRun_5.xml";
		parser.parse(path);

		List<TestRun> runs = TestData.getInstance().getTestRunList();
		// for (TestRun testRun : runs) {
		// System.out.println(testRun.getTestedClasses().size());
		// }
		List<TestedClass> classes = TestData.getInstance().getTestedClassList();

		System.out.println(classes.size());
		for (TestedClass testedClass2 : classes) {
			System.out.println(testedClass2.getClassName());
			List<UnitTestsToTestRunMapper> log = testedClass2.getClassLog();
			System.out.println("This class was tested in " + log.size() + " different testRuns");
			for (UnitTestsToTestRunMapper mapper : log) {
				System.out.println(mapper.getFailurePercentage());
			}
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

	// Create Lists from Classnames
	public List<TestedClass> getTestedClassesFromTestRun(String runID) {
		TestRun testrun = getTestRunFromID(runID);
		List<TestedClass> testedClasses = testrun.getTestedClasses();

		for (TestedClass testedClass : testedClasses) {
			testedClass.setCurrentFailurePercentage(testrun);
		}
		return testedClasses;
	}

	private TestRun getTestRunFromID(String runID) {
		TestRun desiredRun = null;
		for (TestRun testRun : testData.getTestRunList()) {
			if (testRun.getRunID().equals(runID)) {
				desiredRun = testRun;
			}
		}
		return desiredRun;
	}

}
