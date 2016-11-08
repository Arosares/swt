package tda.src.logic;

import java.util.Set;
import java.util.HashSet;

public class Counters {
/**
 * <pre>
 *           0..*     0..*
 * Counters ------------------------- TestRun
 *           counters        &lt;       testRun
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
