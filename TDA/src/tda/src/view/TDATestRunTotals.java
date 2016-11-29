package tda.src.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import tda.src.controller.Controller;
import tda.src.logic.Counters;
import tda.src.logic.TestRun;

public class TDATestRunTotals {

	private Controller controller;
	private ListView<String> testRunTotals;
	private Counters totals;

	public TDATestRunTotals(Controller controller) {
		super();
		this.controller = controller;
	}

	/**
	 * @return Creates a flat Horizontal Box containing our Counters, to be
	 *         displayed at the top of the main View.
	 */
	public Node createTestRunTotalsBox() {

		testRunTotals = new ListView<String>();
		testRunTotals.setPrefSize(800, 32);

		testRunTotals.setOrientation(Orientation.HORIZONTAL);

		ScrollPane scrollPane = new ScrollPane(testRunTotals);

		return scrollPane;

	}

	/**
	 * @param testRun
	 * @param details
	 *            Asks the linked Methods for an Observable list by passing on
	 *            values from the controller, then creates that list.
	 */
	public void showTestRunTotals(TestRun testRun, Boolean details) {

		if (details == false) {
			ObservableList<String> testResults = fillTestRunCounterLists(testRun, false);
			testRunTotals.setItems(testResults);
		} else {
			ObservableList<String> allCounters = fillTestRunCounterLists(testRun, true);
			testRunTotals.setItems(allCounters);
		}
	}

	/**
	 * @param testRun
	 * @param details
	 * @return Returns either the most Important Counters (Total, Executed,
	 *         Passed, Failed), or all of them, depending on the incoming
	 *         boolean.
	 */
	public ObservableList<String> fillTestRunCounterLists(TestRun testRun, Boolean details) {

		totals = testRun.getResultSummary();

		ObservableList<String> generatedList = importantCounters();
		// Show all Counters:
		ObservableList<String> allCounters = importantCounters();
		allCounters = additionalCounters(allCounters);
		if(details){
			testRunTotals.setItems(allCounters);
		}else{
			testRunTotals.setItems(generatedList);
		}
		return generatedList;

	}

	/**
	 * @param totals
	 * @return Creates an Observable List with the important Counters, and
	 *         returns it.
	 */
	public ObservableList<String> importantCounters() {
		// Important Counters:
		String stringTotal = "Total: ".concat(totals.getSumTotal());
		String stringExecuted = "Executed: ".concat(totals.getSumExecuted());
		String stringPassed = "Passed: ".concat(totals.getSumPassed());
		String stringFailed = "Failed: ".concat(totals.getSumFailed());

		ObservableList<String> testResults = FXCollections.observableArrayList(stringTotal, stringPassed, stringFailed,
				stringExecuted);
		return testResults;
	}

	/**
	 * @param allCounters
	 * @return Adds all remaining Counters to a List that already contains the
	 *         important ones.
	 */
	public ObservableList<String> additionalCounters(ObservableList<String> allCounters) {

		allCounters.add("Aborted: ".concat(totals.getSumAborted()));
		allCounters.add("Completed: ".concat(totals.getSumCompleted()));
		allCounters.add("Disconnected: ".concat(totals.getSumDisconnected()));
		allCounters.add("Error: ".concat(totals.getSumError()));
		allCounters.add("In Progress: ".concat(totals.getSumInProgress()));
		allCounters.add("Inconclusive: ".concat(totals.getSumInconclusive()));
		allCounters.add("Not Executed: ".concat(totals.getSumNotExecuted()));
		allCounters.add("Not runable: ".concat(totals.getSumNotRunnable()));
		allCounters.add("Passed but Run Aborted: ".concat(totals.getSumPassedButRunAborted()));
		allCounters.add("Pending: ".concat(totals.getSumPending()));
		allCounters.add("Time Out: ".concat(totals.getSumTimeOut()));
		allCounters.add("Warning: ".concat(totals.getSumWarning()));

		return allCounters;
	}
}
