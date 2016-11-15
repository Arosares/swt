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
   
   /**
    *  Parses a given XML File in our Data-Structure.
    *  Mainly to {@code UnitTest.java} format
    */
	public void parse();

}
