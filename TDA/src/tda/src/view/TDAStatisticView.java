package tda.src.view;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import tda.src.controller.Controller;
import tda.src.logic.TestData;
import tda.src.logic.TestedClass;
import tda.src.logic.statistics.StatisticAnalyzer;

public class TDAStatisticView {
	private Controller controller;

	private GridPane statisticsPane;
	private Slider distanceSlider;
	private Slider confidenceSlider;
	private TableView<TestedClass> classItemTable = new TableView<TestedClass>();
	private TableColumn<TestedClass, String> classColumn;
	private ObservableList<TestedClass> classItems;

	public TDAStatisticView(Controller controller) {
		this.controller = controller;

		statisticsPane = new GridPane();
		// margins around the whole center grid (top/right/bottom/left)
		statisticsPane.setPadding(new Insets(10, 10, 10, 10));
		statisticsPane.setVgap(10);
		statisticsPane.setAlignment(Pos.TOP_CENTER);

		Label header = new Label("Most Critical Classes");
		header.setFont(new Font(16));
		statisticsPane.add(header, 1, 1);
		statisticsPane.add(createClassItemTable(), 1, 2);

		Label distanceLabel = new Label("Class Distance");
		distanceLabel.setAlignment(Pos.TOP_RIGHT);
		Label confidenceLabel = new Label("Confidence");
		confidenceLabel.setAlignment(Pos.TOP_RIGHT);

		statisticsPane.add(distanceLabel, 1, 4);
		statisticsPane.add(createDistanceSlider(), 1, 5);
		statisticsPane.add(confidenceLabel, 1, 6);
		statisticsPane.add(createConfidenceSlider(), 1, 7);
	}

	private Node createConfidenceSlider() {
		confidenceSlider = new Slider(1, 100, 60);
		confidenceSlider.setShowTickLabels(true);
		confidenceSlider.setShowTickMarks(true);
		confidenceSlider.setMajorTickUnit(5);
		confidenceSlider.setMinorTickCount(10);
		confidenceSlider.setSnapToTicks(true);
		confidenceSlider.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				updateStatisticsView();
			}
		});
		return confidenceSlider;
	}

	private Node createDistanceSlider() {
		distanceSlider = new Slider(2, 10, 10);
		distanceSlider.setShowTickLabels(true);
		distanceSlider.setShowTickMarks(true);
		distanceSlider.setMajorTickUnit(1);
		distanceSlider.setMinorTickCount(0);
		distanceSlider.setSnapToTicks(true);
		distanceSlider.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				updateStatisticsView();
			}
		});
		return distanceSlider;
	}

	public Node createClassItemTable() {

		classColumn = new TableColumn<>("Tested Class");
		classColumn.setCellValueFactory(new PropertyValueFactory<TestedClass, String>("className"));
		classColumn.setSortable(false);

		classItemTable = new TableView<>();
		classItemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		classItemTable.setPrefWidth(800);
		classItemTable.setPrefHeight(300);

		classItemTable.getColumns().add(classColumn);

		classItemTable.setRowFactory(tv -> {
			TableRow<TestedClass> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					TestedClass testedClass = row.getItem();
					controller.handleClassItemTableClick(testedClass, confidenceSlider.getValue(), (int) distanceSlider.getValue());
				}
			});
			return row;
		});
		ScrollPane scrollPane = new ScrollPane(classItemTable);
		return scrollPane;
	}

	public void fillClassItemTable(double confidence, int distance) {
		List<TestedClass> strongRules = StatisticAnalyzer.getMostRelevantClasses(confidence, distance);
		classItems = FXCollections.observableArrayList(strongRules);
		classItemTable.setItems(classItems);
	}

	public void updateStatisticsView() {
		int maxDst = TestData.getInstance().getTreeHeight() * 2;
		distanceSlider.setMax(maxDst);

		int distance = (int) distanceSlider.getValue();
		if (distance >= maxDst)
			distanceSlider.setValue(maxDst);
		double confidence = confidenceSlider.getValue();

		fillClassItemTable(confidence, distance);
	}

	public GridPane getStatisticsPane() {
		return statisticsPane;
	}
}
