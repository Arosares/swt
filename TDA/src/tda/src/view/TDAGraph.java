package tda.src.view;

import javax.naming.StringRefAddr;

import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;

public class TDAGraph {

	private final CategoryAxis xAxis = new CategoryAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
	private XYChart.Series<String, Number> series;

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
			String[] xmlName = testRun.getPath().split("/|\\\\");
			System.out.println(xmlName[xmlName.length -1]);
			String xValue = xmlName[xmlName.length - 1];
			series.getData().add(new XYChart.Data<String, Number>(xValue, yValue));
		}
		series.setName(testedClass.getClassName() + " over " + testRunCounter + " runs");
		lineChart.getData().add(series);

	}

	public LineChart<String, Number> getLineChart() {
		return lineChart;
	}

}
