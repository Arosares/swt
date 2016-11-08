package tda.src.logic;
import java.util.Set;

public interface Parser {
/**
    * <pre>
    *           1..1     0..*
    * Parser ------------------------- TestRun
    *           parser        &gt;       testRun
    * </pre>
    */
   public Set<TestRun> getTestRun();
   
   public void parse();

}
