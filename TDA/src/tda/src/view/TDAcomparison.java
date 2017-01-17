package tda.src.view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
		rootPane = new BorderPane();
		rootPane.setPrefWidth(1200);

		Label comparisonLabel = new Label("Comparison:");
		rootPane.setTop(comparisonLabel);
	}

	public Pane generateEmptyComparisonPane() {
		comparisonSlot1 = generateEmptyComparisonSlot();
		comparisonSlot2 = generateEmptyComparisonSlot();

		rootPane.setLeft(comparisonSlot1);
		rootPane.setRight(comparisonSlot2);
		return rootPane;
	}

	private VBox generateEmptyComparisonSlot() {
		VBox comparisonVBox = new VBox();
		// comparisonVBox.setStyle("-fx-border: 1px solid black");
		comparisonVBox.setPrefWidth(300);
		Label timeStamp = new Label("Run - Date");

		comparisonVBox.getChildren().addAll(generateEmptyDetailsHBox(), timeStamp, generateEmptyTestsVBox());
		return comparisonVBox;
	}

	private Node generateEmptyDetailsHBox() {
		HBox testsBox = new HBox();
		Label passedLabel = new Label("Passed: XX");
		Label failedLabel = new Label("Failed: XX");
		Label failurePercentage = new Label("Failure-%: XX");
		testsBox.getChildren().addAll(passedLabel, failedLabel, failurePercentage);
		return testsBox;
	}

	public VBox generateEmptyTestsVBox() {
		VBox testsBox = new VBox();
		Label passedLabel = new Label("Passed:");
		Label failedLabel = new Label("Failed:");
		testsBox.getChildren().addAll(passedLabel, generateEmptyListView(), failedLabel, generateEmptyListView());
		return testsBox;
	}

	public Text generateEmptyListView() {
		Text testsView = new Text("No testrun loaded");
		return testsView;
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
	public VBox generateComparisonSlot(TestRun run, boolean isSlot1) {
//		VBox comparisonVBox = new VBox();
//		// comparisonVBox.setStyle("-fx-border: 1px solid black");
//		comparisonVBox.setPrefWidth(300);
		Label timeStamp = new Label(run.getRunDate().toString());
		if (isSlot1) {
			comparisonSlot1.getChildren().clear();
			comparisonSlot1.getChildren().addAll(generateDetailsHBox(run, isSlot1), timeStamp, generateTestsVBox(run, isSlot1));
			return comparisonSlot1;
		} else {
			comparisonSlot2.getChildren().clear();
			comparisonSlot2.getChildren().addAll(generateDetailsHBox(run, isSlot1), timeStamp, generateTestsVBox(run, isSlot1));
			return comparisonSlot2;
		}
		
	}

	public void updateComparisonSlot(TestedClass testedClass, TestRun run, boolean isSlot1) {
		// TODO: Check, compare and set current TestedClass
		loadComparisonSlot(testedClass, run, isSlot1);
		if (isSlot1) {
			comparisonSlot1 = generateComparisonSlot(run, isSlot1);
		} else {
			comparisonSlot2 = generateComparisonSlot(run, isSlot1);
		}

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
	public HBox generateDetailsHBox(TestRun run, boolean isSlot1) {
		HBox detailsBox = new HBox();
		int passed = 0;
		int failed = 0;
		if (isSlot1) {
			passed = passedList1.size();
			failed = failedList1.size();
		} else  {
			passed = passedList2.size();
			failed = failedList2.size();
		}

		Label passedLabel = new Label("Passed: " + passed);
		Label failedLabel = new Label("Failed: " + failed);
		Label failurePercentage = new Label("Failure-%: " + testedClass.getFailurePercentageByTestrun(run));
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
	public VBox generateTestsVBox(TestRun run, boolean isSlot1) {
		VBox testsBox = new VBox();
		Label passedLabel = new Label("Passed:");
		Label failedLabel = new Label("Failed:");
		testsBox.getChildren().addAll(passedLabel, generateListView(run, "passed", isSlot1), failedLabel,
				generateListView(run, "failed", isSlot1));
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
	public ListView<String> generateListView(TestRun run, String status, boolean isSlot1) {
		ListView<String> testsList;
		if (status.equals("passed")) {
			if (isSlot1) {
				testsList = new ListView<String>(FXCollections.observableArrayList(passedList1));
				testsList.setStyle("-fx-color: green");
				return testsList;
			} else {
				testsList = new ListView<String>(FXCollections.observableArrayList(passedList2));
				testsList.setStyle("-fx-color: green");
				return testsList;
			}

		} else if (status.equals("failed")) {
			if (isSlot1) {
				testsList = new ListView<String>(FXCollections.observableArrayList(failedList1));
				testsList.setStyle("-fx-color: red");
				return testsList;
			} else {
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
	 * @param run
	 */
	public void loadComparisonSlot(TestedClass testedClass, TestRun run, boolean isSlot1) {
		this.testedClass = testedClass;

		if (isSlot1) {
			passedList1 = testedClass.getUnitTestsNamesByTestRun(run, true);
			failedList1 = testedClass.getUnitTestsNamesByTestRun(run, false);
		} else {
			passedList2 = testedClass.getUnitTestsNamesByTestRun(run, true);
			failedList2 = testedClass.getUnitTestsNamesByTestRun(run, false);
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
