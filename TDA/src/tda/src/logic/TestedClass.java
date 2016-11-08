package tda.src.logic;
import java.util.HashSet;
import java.util.Set;

public class TestedClass {
/**
 * <pre>
 *           0..*     0..*
 * TestedClass ------------------------- TestRun
 *           testedClass        &lt;       testRun
 * </pre>
 */
private Set<TestRun> testRun;

public Set<TestRun> getTestRun() {
   if (this.testRun == null) {
this.testRun = new HashSet<TestRun>();
   }
   return this.testRun;
}

/**
 * <pre>
 *           0..*     0..*
 * TestedClass ------------------------- Test
 *           testedClass        &gt;       test
 * </pre>
 */
private Set<Test> test;

public Set<Test> getTest() {
   if (this.test == null) {
this.test = new HashSet<Test>();
   }
   return this.test;
}

private String className;

public void setClassName(String value) {
   this.className = value;
}

public String getClassName() {
   return this.className;
}

private String/*No type specified!*/ failurePercentage;

public void setFailurePercentage(String/*No type specified!*/ value) {
   this.failurePercentage = value;
}

public String/*No type specified!*/ getFailurePercentage() {
   return this.failurePercentage;
}

}
