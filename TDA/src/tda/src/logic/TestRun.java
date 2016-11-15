package tda.src.logic;

import java.util.Set;
import java.util.HashSet;

public class TestRun implements Comparable<TestRun> {
	/**
	 * <pre>
	 *           0..*     0..*
	 * TestRun ------------------------- TestedClass
	 *           testRun        &gt;       testedClass
	 * </pre>
	 */
	private Set<TestedClass> testedClass;
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

	public Set<TestedClass> getTestedClass() {
		if (this.testedClass == null) {
			this.testedClass = new HashSet<TestedClass>();
		}
		return this.testedClass;
	}

	/**
	 * <pre>
	 *           0..*     0..*
	 * TestRun ------------------------- Counters
	 *           testRun        &gt;       counters
	 * </pre>
	 */
	private Set<Counters> counters;

	public Set<Counters> getCounters() {
		if (this.counters == null) {
			this.counters = new HashSet<Counters>();
		}
		return this.counters;
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

	@Override
	public int compareTo(TestRun o) {
		// TODO Auto-generated method stub
		if (runID.equals(o.getRunID())) {
			return 0;
		}
		return -1;
	}

}
