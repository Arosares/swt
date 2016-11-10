package tda.src.logic;

import java.util.Set;
import java.util.HashSet;

public class UnitTest {
/**
 * <pre>
 *           0..*     0..*
 * Test ------------------------- TestedClass
 *           test        &lt;       testedClass
 * </pre>
 */
private Set<TestedClass> testedClass;

public Set<TestedClass> getTestedClass() {
   if (this.testedClass == null) {
this.testedClass = new HashSet<TestedClass>();
   }
   return this.testedClass;
}

/**
 * <pre>
 *           0..*     0..*
 * Test ------------------------- Test
 *           test1        &lt;       test
 * </pre>
 */
private Set<UnitTest> test;

public Set<UnitTest> getTest() {
   if (this.test == null) {
this.test = new HashSet<UnitTest>();
   }
   return this.test;
}

/**
 * <pre>
 *           0..*     0..*
 * Test ------------------------- Test
 *           test        &gt;       test1
 * </pre>
 */
private Set<UnitTest> test1;

public Set<UnitTest> getTest1() {
   if (this.test1 == null) {
this.test1 = new HashSet<UnitTest>();
   }
   return this.test1;
}

private String testName;

public void setTestName(String value) {
   this.testName = value;
}

public String getTestName() {
   return this.testName;
}

private int executionID;

public void setExecutionID(int value) {
   this.executionID = value;
}

public int getExecutionID() {
   return this.executionID;
}

private boolean outcome;

public void setOutcome(boolean value) {
   this.outcome = value;
}

public boolean isOutcome() {
   return this.outcome;
}

}
