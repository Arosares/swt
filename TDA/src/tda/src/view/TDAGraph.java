package tda.src.view;

import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import tda.src.controller.Controller;
import tda.src.logic.TestedClass;

public class TDAGraph {

	private final CategoryAxis xAxis = new CategoryAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
	private XYChart.Series<String, Number> series;
	private Controller controller;
	
	
	public TDAGraph(Controller controller) {
		this.controller = controller;
	}

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
		
		double[] yValues = controller.calculateChartData(testedClass);
		for (int i = 0; i < yValues.length; i++) {
			series.getData().add(new XYChart.Data<String, Number>(Integer.toString(i + 1), yValues[i]));
		}
		series.setName(testedClass.getClassName());
		lineChart.getData().add(series);

	}

	public LineChart<String, Number> getLineChart() {
		return lineChart;
	}

}
