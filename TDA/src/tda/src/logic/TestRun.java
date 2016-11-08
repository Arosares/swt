package tda.src.logic;
import java.util.Set;
import java.util.HashSet;

public class TestRun {
private String/*No type specified!*/ tests;

public void setTests(String/*No type specified!*/ value) {
   this.tests = value;
}

public String/*No type specified!*/ getTests() {
   return this.tests;
}

private String/*No type specified!*/ failedPercentage;

public void setFailedPercentage(String/*No type specified!*/ value) {
   this.failedPercentage = value;
}

public String/*No type specified!*/ getFailedPercentage() {
   return this.failedPercentage;
}

/**
 * <pre>
 *           1..1     0..*
 * TestRun ------------------------- Test
 *           testRun        &gt;       test
 * </pre>
 */
private Set<Test> test;

public Set<Test> getTest() {
   if (this.test == null) {
this.test = new HashSet<Test>();
   }
   return this.test;
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
