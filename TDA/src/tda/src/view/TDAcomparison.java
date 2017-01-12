package tda.src.view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.logic.UnitTest;

public class TDAcomparison {

	private final View view;
	private BorderPane rootPane;
	private VBox comparisonSlot1;
	private VBox comparisonSlot2;
	private TestedClass testedClass;
	private TestRun run1;
	private TestRun run2;
	private List<String> passedList1;
	private List<String> failedList1;
	private List<String> passedList2;
	private List<String> failedList2;

	public TDAcomparison(View view) {
		this.view = view;

	}

	/**
	 * generates the upper level Pane
	 * <ul>
	 * <li>comparisonLabel: Simple Label displaying "Comparison:"</li>
	 * <li>comparisonSlot1
	 * <li>
	 * <li>comparisonSlot2</li>
	 * </ul>
	 * 
	 * @param run
	 * @return rootPane: BorderPane
	 */
	public Pane generateComparisonPane(TestRun run) {
		rootPane = new BorderPane();
		rootPane.setPrefWidth(1200);
		Label comparisonLabel = new Label("Comparison:");
		rootPane.setTop(comparisonLabel);

		// TODO: generateComparisonSlot and fill with content
		rootPane.setLeft(comparisonSlot1);
		rootPane.setRight(comparisonSlot2);
		return rootPane;
	}

	/**
	 * generates one comparison slot <br/>
	 * Elements:
	 * <ul>
	 * <li>DetailsHBox on top</li>
	 * <li>timeStamp of TestRun</li>
	 * <li>TestsBox with two lists of UnitTests (passed and failed)</li>
	 * </ul>
	 * 
	 * @param run
	 * @return VBox
	 */
	public VBox generateComparisonSlot(TestRun run) {
		VBox comparisonVBox = new VBox();
		comparisonVBox.setStyle("-fx-border: 1px solid black");
		comparisonVBox.setPrefWidth(300);
		Label timeStamp = new Label(run.getRunDate().toString());

		comparisonVBox.getChildren().addAll(generateDetailsHBox(run), timeStamp, generateTestsVBox(run));
		return comparisonVBox;
	}

	/**
	 * generates a detailsHBox for the header of a comparisonSlot <br/>
	 * Elements:
	 * <ul>
	 * <li>passedLabel: number of passed tests</li>
	 * <li>failedLabel: number of failed tests</li>
	 * <li>failurePercentage: failure percentage of tested class in this run
	 * </li>
	 * </ul>
	 * 
	 * @param run
	 * @return HBox
	 */
	public HBox generateDetailsHBox(TestRun run) {
		HBox detailsBox = new HBox();
		int passed = 0;
		int failed = 0;
		if (run == run1) {
			passed = passedList1.size();
			failed = failedList1.size();
		} else if (run == run2) {
			passed = passedList2.size();
			failed = failedList2.size();
		}

		Label passedLabel = new Label("Passed: " + passed);
		Label failedLabel = new Label("Failed: " + failed);
		Label failurePercentage = new Label("Failure-%: " + testedClass.getFailurePercentageByTestrun(run1));
		detailsBox.getChildren().addAll(passedLabel, failedLabel, failurePercentage);
		return detailsBox;
	}

	/**
	 * generates TestVBox <br/>
	 * Elements:
	 * <ul>
	 * <li>passedLabel: Simple Label displaying "Passed:"</li>
	 * <li>passedListView: String-List containing only passed tests</li>
	 * <li>failedLabel: Simple Label displaying "Failed:"</li>
	 * <li>failedListView: String-List containing only failed tests</li>
	 * </ul>
	 * 
	 * @param run
	 * @return VBox
	 */
	public VBox generateTestsVBox(TestRun run) {
		VBox testsBox = new VBox();
		Label passedLabel = new Label("Passed:");
		Label failedLabel = new Label("Failed:");
		testsBox.getChildren().addAll(passedLabel, generateListView(run, "passed"), failedLabel,
				generateListView(run, "failed"));
		return testsBox;
	}

	/**
	 * generates a ListView<String> element depending on whether the
	 * status-parameter is "passed" or "failed"
	 * 
	 * @param run
	 * @param status
	 *            : can either be "passed" or "failed"
	 * @return ListView<String>
	 */
	public ListView<String> generateListView(TestRun run, String status) {
		ListView<String> testsList;
		if (status.equals("passed")) {
			if (run == run1) {
				testsList = new ListView<String>(FXCollections.observableArrayList(passedList1));
				testsList.setStyle("-fx-color: green");
				return testsList;
			} else if (run == run2) {
				testsList = new ListView<String>(FXCollections.observableArrayList(passedList2));
				testsList.setStyle("-fx-color: green");
				return testsList;
			}

		} else if (status.equals("failed")) {
			if (run == run1) {
				testsList = new ListView<String>(FXCollections.observableArrayList(failedList1));
				testsList.setStyle("-fx-color: red");
				return testsList;
			} else if (run == run2) {
				testsList = new ListView<String>(FXCollections.observableArrayList(failedList2));
				testsList.setStyle("-fx-color: red");
				return testsList;
			}
		} else {
			System.err.println("Only failed and passed possible as testresult");
		}

		return null;
	}

	/**
	 * sets the global testedClass attribute, sets the global run1 attribute,
	 * fills the global passedList1 and failedList1 lists
	 * 
	 * @param testedClass
	 * @param run1
	 */
	public void loadComparisonSlot1(TestedClass testedClass, TestRun run1) {
		this.testedClass = testedClass;
		this.run1 = run1;

		passedList1 = new ArrayList<String>();
		failedList1 = new ArrayList<String>();

		for (UnitTest oneTest : testedClass.getUnitTestsByTestRun(run1)) {
			if (oneTest.hasPassed()) {
				passedList1.add(oneTest.getUnitTestName());
			} else {
				failedList1.add(oneTest.getUnitTestName());
			}
		}
	}

	/**
	 * sets the global testedClass attribute, sets the global run2 attribute,
	 * fills the global passedList2 and failedList2 lists
	 * 
	 * @param testedClass
	 * @param run2
	 */
	public void loadComparisonSlot2(TestedClass testedClass, TestRun run2) {
		this.testedClass = testedClass;
		this.run2 = run2;

		passedList2 = new ArrayList<String>();
		failedList2 = new ArrayList<String>();

		for (UnitTest oneTest : testedClass.getUnitTestsByTestRun(run2)) {
			if (oneTest.hasPassed()) {
				passedList2.add(oneTest.getUnitTestName());
			} else {
				failedList2.add(oneTest.getUnitTestName());
			}
		}
	}

	public List<String> getPassedList1() {
		return passedList1;
	}

	public void setPassedList1(List<String> passedList1) {
		this.passedList1 = passedList1;
	}

	public List<String> getFailedList1() {
		return failedList1;
	}

	public void setFailedList1(List<String> failedList1) {
		this.failedList1 = failedList1;
	}

	public List<String> getPassedList2() {
		return passedList2;
	}

	public void setPassedList2(List<String> passedList2) {
		this.passedList2 = passedList2;
	}

	public List<String> getFailedList2() {
		return failedList2;
	}

	public void setFailedList2(List<String> failedList2) {
		this.failedList2 = failedList2;
	}
}
