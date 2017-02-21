package tda.src.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import tda.src.datastructure.Counters;
import tda.src.datastructure.TestRun;
import tda.src.gui.controller.Controller;

/**
 * Displays the Totals of one selected testRun
 *
 */
public class TDATestRunTotals {

	private Controller controller;
	private ListView<String> testRunTotals;
	private Counters totals;
	private ObservableList<String> testResults;
	private Label idLabel;

	public TDATestRunTotals(Controller controller, Label idLabel) {
		super();
		this.controller = controller;
		this.idLabel = idLabel;
	}

	/**
	 * Creates a flat Horizontal Box containing our Counters, to be displayed at
	 * the top of the main View.
	 * 
	 * @return
	 */
	public Node createTestRunTotalsBox() {

		testRunTotals = new ListView<String>();
		testRunTotals.setPrefSize(800, 32);

		testRunTotals.setOrientation(Orientation.HORIZONTAL);

		ScrollPane scrollPane = new ScrollPane(testRunTotals);
		scrollPane.setMinHeight(45);

		return scrollPane;

	}

	/**
	 * Asks the linked Methods for an Observable list by passing on values from
	 * the controller, then creates that list.
	 * 
	 * @param testRun
	 */
	public void showTestRunTotals(TestRun testRun) {
		testResults = listedCounters(testRun);
		idLabel.setText("Loaded TestRun: ".concat(testRun.getRunName()));
		testRunTotals.setItems(testResults);

	}

	/**
	 * Grabs a summary of test run results from the chosen testrun, then filters
	 * the input to remove results of 0 with a series of if-functions. 
	 * 
	 * @param totals
	 * @return Creates an Observable List with the all Counters, and returns it.
	 */
	public ObservableList<String> listedCounters(TestRun testRun) {

		totals = testRun.getResultSummary();
		// Important Counters:
		String stringTotal = "Total: ".concat(totals.getSumTotal());
		String stringExecuted = "Executed: ".concat(totals.getSumExecuted());
		String stringPassed = "Passed: ".concat(totals.getSumPassed());
		String stringFailed = "Failed: ".concat(totals.getSumFailed());

		ObservableList<String> testResults = FXCollections.observableArrayList(stringTotal, stringPassed, stringFailed,
				stringExecuted);
		if (Integer.parseInt(totals.getSumAborted()) != 0) {
			testResults.add("Aborted: ".concat(totals.getSumAborted()));
		}
		if (Integer.parseInt(totals.getSumCompleted()) != 0) {
			testResults.add("Completed: ".concat(totals.getSumCompleted()));
		}
		if (Integer.parseInt(totals.getSumDisconnected()) != 0) {
			testResults.add("Disconnected: ".concat(totals.getSumDisconnected()));
		}
		if (Integer.parseInt(totals.getSumError()) != 0) {
			testResults.add("Error: ".concat(totals.getSumError()));
		}
		if (Integer.parseInt(totals.getSumInProgress()) != 0) {
			testResults.add("In Progress: ".concat(totals.getSumInProgress()));
		}
		if (Integer.parseInt(totals.getSumInconclusive()) != 0) {
			testResults.add("Inconclusive: ".concat(totals.getSumInconclusive()));
		}
		if (Integer.parseInt(totals.getSumNotExecuted()) != 0) {
			testResults.add("Not Executed: ".concat(totals.getSumNotExecuted()));
		}
		if (Integer.parseInt(totals.getSumNotRunnable()) != 0) {
			testResults.add("Not Runnable: ".concat(totals.getSumNotRunnable()));
		}
		if (Integer.parseInt(totals.getSumPassedButRunAborted()) != 0) {
			testResults.add("Passed But Run Aborted: ".concat(totals.getSumPassedButRunAborted()));
		}
		if (Integer.parseInt(totals.getSumPending()) != 0) {
			testResults.add("Pending: ".concat(totals.getSumPending()));
		}
		if (Integer.parseInt(totals.getSumTimeOut()) != 0) {
			testResults.add("Time Out: ".concat(totals.getSumTimeOut()));
		}
		if (Integer.parseInt(totals.getSumWarning()) != 0) {
			testResults.add("Warning: ".concat(totals.getSumWarning()));
		}
		return testResults;
	}

	public ObservableList<String> getTestResults() {
		return testResults;
	}

	public Label getIdLabel() {
		return idLabel;
	}

}
