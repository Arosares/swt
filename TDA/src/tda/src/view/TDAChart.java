package tda.src.view;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import tda.src.controller.Controller;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;

public class TDAChart {

	private final CategoryAxis xAxis = new CategoryAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
	private XYChart.Series<String, Number> series;
	private Controller controller;

	public TDAChart(Controller controller) {
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

		List<XYChart.Data<TestRun, Number>> datas = controller.calculateChartData(testedClass);
		int cnt = 1;
		for (XYChart.Data<TestRun, Number> data : datas) {
			
			String xAxisLabel = Integer.toString(cnt);
			XYChart.Data<String, Number> dataPoint = new XYChart.Data<String, Number>(xAxisLabel, data.getYValue());
			//set the HoverNode for a dataPoint
			dataPoint.setNode(new HoveredThresholdNode(data.getXValue(), data.getYValue(), testedClass));
			
			series.getData().add(dataPoint);
			cnt++;
		} 
			
		series.setName(testedClass.getClassName());
		lineChart.getData().add(series);

	}

	public LineChart<String, Number> getLineChart() {
		return lineChart;
	}

	/** a node which displays a value on hover, but is otherwise empty 
	 *  inspired by https://gist.github.com/jewelsea/4681797	*/
	class HoveredThresholdNode extends StackPane {
		HoveredThresholdNode(TestRun testRun, Number value, TestedClass testedClass) {
			setPrefSize(10, 10);
			final Label label = createDataThresholdLabel(testRun, value, testedClass);
			setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					controller.handleChartNodeClick(testRun, testedClass);
				}
				
			});
			
			setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					getChildren().setAll(label);
					toFront();
				}
			});
			setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					getChildren().clear();
					setCursor(Cursor.CROSSHAIR);
				}
			});

		}

		private Label createDataThresholdLabel(TestRun testRun, Number value, TestedClass testedClass) {
			String labelString = testRun.getRunDate().toString();
			labelString += "\nPassed: " + testedClass.getUnitTestsByTestRun(testRun, true).size();
			labelString += "\nFailed: " + testedClass.getUnitTestsByTestRun(testRun, false).size();
			final Label label = new Label(labelString);
			label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
			label.setStyle("-fx-font-size: 10;");
			label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
			return label;
		}
	}
}
