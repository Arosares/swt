package tda.src.logic;

public class Test {
private String/*No type specified!*/ failurePercentage;

public void setFailurePercentage(String/*No type specified!*/ value) {
   this.failurePercentage = value;
}

public String/*No type specified!*/ getFailurePercentage() {
   return this.failurePercentage;
}

/**
 * <pre>
 *           0..*     1..1
 * Test ------------------------- TestRun
 *           test        &lt;       testRun
 * </pre>
 */
private TestRun testRun;

public void setTestRun(TestRun value) {
   this.testRun = value;
}

public TestRun getTestRun() {
   return this.testRun;
}

}
