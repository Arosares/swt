package tda.src.view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tda.src.datastructure.TestRun;
import tda.src.datastructure.TestedClass;

/**
 * Implements the comparison between two testruns of one testedclass
 *
 */
public class TDAcomparison {

	private final View view;
	private BorderPane rootPane;
	private VBox comparisonSlot1;
	private VBox comparisonSlot2;
	private VBox comparedSlot;
	private TestedClass testedClass;
	private List<String> passedList1;
	private List<String> failedList1;
	private List<String> passedList2;
	private List<String> failedList2;
	private double failurePercentage1;
	private double failurePercentage2;
	private TestRun run2;
	private Button compareButton;
	private boolean currentlyComparing = false;
	private boolean isEmptySlot1 = true;
	private boolean isEmptySlot2 = true;

	public TDAcomparison(View view) {
		this.view = view;
		this.view.getScene().getStylesheets()
				.add(getClass().getResource("ComparisonSlotStylesheet.css").toExternalForm());
		rootPane = new BorderPane();
		rootPane.setPrefWidth(1200);
		rootPane.setMinHeight(400);

		Label comparisonLabel = new Label("Comparison of one class over two testruns");
		comparisonLabel.setStyle("-fx-font: 18 Verdana;");
		rootPane.setAlignment(comparisonLabel, Pos.CENTER);
		rootPane.setMargin(comparisonLabel, new Insets(12, 12, 12, 12));
		rootPane.setTop(comparisonLabel);
	}

	public Pane generateEmptyComparisonPane() {
		comparisonSlot1 = generateEmptyComparisonSlot();
		comparisonSlot2 = generateEmptyComparisonSlot();

		compareButton = new Button("Compare");
		compareButton.setMinWidth(150);
		compareButton.setMinHeight(70);
		compareButton.setPrefHeight(70);
		compareButton.setPadding(new Insets(10, 10, 10, 10));

		compareButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!(currentlyComparing)) {
					compare();
					compareButton.setText("End comparison");
				} else {
					endComparison();
					compareButton.setText("Compare");
				}

			}
		});

		rootPane.setLeft(comparisonSlot1);
		rootPane.setRight(comparisonSlot2);
		rootPane.setCenter(compareButton);
		rootPane.setAlignment(compareButton, Pos.CENTER);
		rootPane.setMargin(compareButton, new Insets(12, 12, 12, 12));
		return rootPane;
	}

	public void reset() {
		rootPane = (BorderPane) generateEmptyComparisonPane();

	}

	private VBox generateEmptyComparisonSlot() {
		VBox comparisonVBox = new VBox();
		comparisonVBox.setStyle("-fx-border-color: black");
		comparisonVBox.setPadding(new Insets(15, 15, 15, 15));
		comparisonVBox.setPrefWidth(530);
		Label timeStamp = new Label("Run - Date");
		comparisonVBox.getChildren().addAll(generateEmptyDetailsHBox(), timeStamp, generateEmptyTestsVBox());
		return comparisonVBox;
	}

	private Node generateEmptyDetailsHBox() {
		HBox testsBox = new HBox();
		testsBox.setSpacing(10);
		Label passedLabel = new Label("Passed: XX");
		Label failedLabel = new Label("Failed: XX");
		Label failurePercentage = new Label("Failurepercentage: XX.X %");
		testsBox.getChildren().addAll(passedLabel, failedLabel, failurePercentage);
		return testsBox;
	}

	public VBox generateEmptyTestsVBox() {
		VBox testsBox = new VBox();
		testsBox.setSpacing(15);
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
		// VBox comparisonVBox = new VBox();
		// // comparisonVBox.setStyle("-fx-border: 1px solid black");
		// comparisonVBox.setPrefWidth(300);
		Label timeStamp = new Label(run.getRunDate().toString());
		if (isSlot1) {
			comparisonSlot1.getChildren().clear();
			comparisonSlot1.getChildren().addAll(generateDetailsHBox(run, isSlot1), timeStamp,
					generateTestsVBox(run, isSlot1));
			comparisonSlot1.setSpacing(15);
			isEmptySlot1 = false;
			return comparisonSlot1;
		} else {
			comparisonSlot2.getChildren().clear();
			comparisonSlot2.getChildren().addAll(generateDetailsHBox(run, isSlot1), timeStamp,
					generateTestsVBox(run, isSlot1));
			comparisonSlot2.setSpacing(15);
			run2 = run;
			isEmptySlot2 = false;
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

	public void switchComparisonSlots() {
		// TODO

	}

	public void compare() {
		if (isEmptySlot1 || isEmptySlot2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Comparison slots not loaded yet");
			alert.setHeaderText(null);
			alert.setContentText("Add testruns to both comparison slots first!");
			alert.showAndWait();
		} else {
			currentlyComparing = true;
			comparedSlot = generateComparedSlot();
			rootPane.setRight(comparedSlot);
		}
	}

	public void endComparison() {
		currentlyComparing = false;
		rootPane.setRight(comparisonSlot2);
	}

	public VBox generateComparedSlot() {
		Label timeStamp = new Label(run2.getRunDate().toString());
		comparedSlot = new VBox();
		comparedSlot.setStyle("-fx-border-color: black");
		comparedSlot.setPadding(new Insets(15, 15, 15, 15));
		comparedSlot.setPrefWidth(530);

		HBox detailsBox = new HBox();
		// detailsBox.setPadding(new Insets(10, 10, 10, 10));
		detailsBox.setSpacing(30);

		/** Detailsbox **/
		Label passedLabel = new Label();
		Label failedLabel = new Label();
		Label failurePercentage = new Label();

		int passedDiff = passedList2.size() - passedList1.size();
		if (passedDiff > 0) {
			passedLabel.getStyleClass().add("goodComparisonResults");
			passedLabel.setText("Passed: " + passedList2.size() + " (+" + passedDiff + ")");

		} else if (passedDiff == 0) {
			passedLabel.setText("Passed: " + passedList2.size() + " (" + passedDiff + ")");
		} else if (passedDiff < 0) {
			passedLabel.getStyleClass().add("badComparisonResults");
			passedLabel.setText("Passed: " + passedList2.size() + " (" + passedDiff + ")");
		}

		int failedDiff = failedList2.size() - failedList1.size();
		if (failedDiff > 0) {
			failedLabel.getStyleClass().add("badComparisonResults");
			failedLabel.setText("Failed: " + failedList2.size() + " (+" + failedDiff + ")");

		} else if (failedDiff == 0) {
			failedLabel.setText("Failed: " + failedList2.size() + " (" + failedDiff + ")");
		} else if (failedDiff < 0) {
			failedLabel.getStyleClass().add("goodComparisonResults");
			failedLabel.setText("Failed: " + failedList2.size() + " (" + failedDiff + ")");
		}

		double failurePercentageDiff = failurePercentage2 - failurePercentage1;
		if (failurePercentageDiff > 0) {
			failurePercentage.getStyleClass().add("badComparisonResults");
			failurePercentage
					.setText("Failurepercentage: " + failurePercentage2 + " % (+" + failurePercentageDiff + "%)");

		} else if (failurePercentageDiff == 0) {
			failurePercentage
					.setText("Failurepercentage: " + failurePercentage2 + " % (" + failurePercentageDiff + "%)");
		} else if (failurePercentageDiff < 0) {
			failurePercentage.getStyleClass().add("goodComparisonResults");
			failurePercentage
					.setText("Failurepercentage: " + failurePercentage2 + " % (" + failurePercentageDiff + "%)");
		}

		detailsBox.getChildren().addAll(passedLabel, failedLabel, failurePercentage);

		/** Listbox **/
		VBox testsBox = new VBox();
		testsBox.setSpacing(5);
		Label nowPassedLabel = new Label("Now Passed:");
		Label nowFailedLabel = new Label("Now Failed:");
		testsBox.getChildren().addAll(nowPassedLabel, generateComparedListView("passed"), nowFailedLabel,
				generateComparedListView("failed"));

		comparedSlot.getChildren().addAll(detailsBox, timeStamp, testsBox);
		comparedSlot.setSpacing(15);
		return comparedSlot;
	}

	public ListView<String> generateComparedListView(String status) {
		ListView<String> testsList;
		if (status.equals("passed")) {
			List<String> nowPassedList = new ArrayList<String>();
			boolean isNew;
			for (String passedTest : passedList2) {
				isNew = true;
				for (int i = 0; i < passedList1.size(); i++) {
					if (passedTest.equals(passedList1.get(i))) {
						isNew = false;
					}
				}
				if (isNew) {
					nowPassedList.add(passedTest);
				}
			}
			testsList = new ListView<String>(FXCollections.observableArrayList(nowPassedList));
			testsList.getStyleClass().add("greentext");
			return testsList;

		} else {
			List<String> nowFailedList = new ArrayList<String>();
			boolean isNew;
			for (String failedTest : failedList2) {
				isNew = true;
				for (int i = 0; i < failedList1.size(); i++) {
					if (failedTest.equals(failedList1.get(i))) {
						isNew = false;
					}
				}
				if (isNew) {
					nowFailedList.add(failedTest);
				}
			}
			testsList = new ListView<String>(FXCollections.observableArrayList(nowFailedList));
			testsList.getStyleClass().add("redtext");
			return testsList;
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
		// detailsBox.setPadding(new Insets(10, 10, 10, 10));
		detailsBox.setSpacing(50);

		int passed = 0;
		int failed = 0;
		if (isSlot1) {
			passed = passedList1.size();
			failed = failedList1.size();
			this.failurePercentage1 = testedClass.getFailurePercentageByTestrun(run);
		} else {
			passed = passedList2.size();
			failed = failedList2.size();
			this.failurePercentage2 = testedClass.getFailurePercentageByTestrun(run);
		}

		Label passedLabel = new Label("Passed: " + passed);
		Label failedLabel = new Label("Failed: " + failed);
		Label failurePercentage = new Label(
				"Failurepercentage: " + testedClass.getFailurePercentageByTestrun(run) + " %");
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
		testsBox.setSpacing(5);
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
				testsList.getStyleClass().add("greentext");
				return testsList;
			} else {
				testsList = new ListView<String>(FXCollections.observableArrayList(passedList2));
				testsList.getStyleClass().add("greentext");
				return testsList;
			}

		} else if (status.equals("failed")) {
			if (isSlot1) {
				testsList = new ListView<String>(FXCollections.observableArrayList(failedList1));
				testsList.getStyleClass().add("redtext");
				return testsList;
			} else {
				testsList = new ListView<String>(FXCollections.observableArrayList(failedList2));
				testsList.getStyleClass().add("redtext");
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
