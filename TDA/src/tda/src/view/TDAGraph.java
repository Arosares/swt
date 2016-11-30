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
	private XYChart.Series series;
	
	public Node generateLineChart(){
		xAxis.setLabel("TestRun");
		yAxis.setLabel("Failure Percentage");
	
		lineChart.setTitle("Failure Percentage over all TestRuns");
		
		
		return lineChart;
	}
	
	public void setChartData(TestedClass testedClass){
		series = new XYChart.Series<>();
		
		int testRunCounter = 0;
		for (TestRun testRun : TestData.getInstance().getTestRunList()) {
			testRunCounter++;
			double yValue = testedClass.getFailurePercentageByTestrun(testRun);
			int xValue = testRunCounter;
			
			series.getData().add(new XYChart.Data<>(xValue, yValue));
			series.setName(testedClass.getClassName() + " over TestRuns");
			lineChart.getData().add(series);
			
		}
	}
	
}
