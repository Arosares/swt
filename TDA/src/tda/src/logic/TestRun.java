package tda.src.logic;

import java.util.Set;
import java.util.HashSet;

public class TestRun {
	private String runID, runName, runUser;

	public String getRunID() {
		return runID;
	}

	public TestRun(String runID, String runName, String runUser) {
		super();
		this.runID = runID;
		this.runName = runName;
		this.runUser = runUser;
	}



private TestedClass testedClasses;

public void setTestedClasses(TestedClass value) {
   this.testedClasses = value;
}

public TestedClass getTestedClasses() {
   return this.testedClasses;
}

private Counters resultSummary;

public void setResultSummary(Counters value) {
   this.resultSummary = value;
}

public Counters getResultSummary() {
   return this.resultSummary;
}

	/**
	 * <pre>
	 *           0..*     1..1
	 * TestRun ------------------------- Analyzer
	 *           testRun        &lt;       analyzer
	 * </pre>
	 */
	private Analyzer analyzer;

	public void setAnalyzer(Analyzer value) {
		this.analyzer = value;
	}

	public Analyzer getAnalyzer() {
		return this.analyzer;
	}

	/**
	 * <pre>
	 *           0..*     1..1
	 * TestRun ------------------------- Parser
	 *           testRun        &lt;       parser
	 * </pre>
	 */
	private Parser parser;

	public void setParser(Parser value) {
		this.parser = value;
	}

	public Parser getParser() {
		return this.parser;
	}

}
