package tda.src.model;
import java.util.Set;

import tda.src.logic.Parser;
import tda.src.logic.TestRun;

import java.util.HashSet;

public class Model {
/**
 * <pre>
 *           1..1     1..1
 * Model ------------------------> Parser
 *           model        &gt;       parser
 * </pre>
 */
private Parser parser;

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
