package tda.src.logic;
import java.util.Set;

public interface Analyzer {
/**
 * <pre>
 *           1..1     0..*
 * Analyzer ------------------------- TestRun
 *           analyzer        &gt;       testRun
 * </pre>
 */
public Set<TestRun> getTestRun();

public void analyze();

}
