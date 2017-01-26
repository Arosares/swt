import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestedClassTests.class, TestRunTests.class, TestDataTests.class, TreeNodeTests.class, 
	CountersTests.class, UnitTestTests.class, UnitTestsToTestRunMapperTests.class }) //Add here in brackets multiple tests
public class AllTests {

}
