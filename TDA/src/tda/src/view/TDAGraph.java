package tda.src.view;

import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;

public class TDAGraph {

	private final NumberAxis xAxis = new NumberAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
	private XYChart.Series<Number, Number> series;

	public Node generateLineChart() {
		xAxis.setLabel("TestRun");
		yAxis.setLabel("Failure Percentage");

		lineChart.setTitle("Failure Percentage over all TestRuns");

		return lineChart;
	}

	public void setChartData(TestedClass testedClass) {
		// a series equals a line in the graph; so here there will be line for
		// every class you click
		series = new XYChart.Series<>();

		int testRunCounter = 0;
		for (TestRun testRun : TestData.getInstance().getTestRunList()) {
			testRunCounter++;
			double yValue = testedClass.getFailurePercentageByTestrun(testRun);
			int xValue = testRunCounter;

			series.getData().add(new XYChart.Data<Number, Number>(xValue, yValue));

		}
		series.setName(
				testedClass.getClassName() + " over " + TestData.getInstance().getTestRunList().size() + " runs");
		lineChart.getData().add(series);

	}

}
