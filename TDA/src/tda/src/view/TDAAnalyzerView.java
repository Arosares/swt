package tda.src.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.util.Callback;
import tda.src.controller.Controller;
import tda.src.logic.TestData;
import tda.src.logic.TestedClass;
import tda.src.logic.apriori.StrongRule;

public class TDAAnalyzerView {
	private Controller controller;
	
	private GridPane aprioriPane;
	private Slider distanceSlider;
	private Slider confidenceSlider;
	private TableView<Entry<String, String>> frequentItemsTable;
	private TableView<StrongRule> strongRulesTable;
	
	private TableColumn<Entry<String, String>, String> supportCountCol;
	TableColumn<StrongRule, String> strongRuleCol;
	private TableColumn<StrongRule, Double> confidenceCol;

	public TDAAnalyzerView(Controller controller) {
		super();
		this.controller = controller;
		aprioriPane = new GridPane();
		// margins around the whole center grid (top/right/bottom/left)
		aprioriPane.setPadding(new Insets(10, 10, 10, 10));
		aprioriPane.setAlignment(Pos.TOP_CENTER);

		Label aprioriHeader = new Label("Apriori Analyzer");
		aprioriPane.add(aprioriHeader, 1, 1);
		aprioriPane.add(createFrequentItemTable(), 1, 2);
		aprioriPane.add(createStrongRulesTable(), 1, 3);
		
		
		
		distanceSlider = new Slider(2, 10, 10);
		distanceSlider.setShowTickLabels(true);
		distanceSlider.setShowTickMarks(true);
		distanceSlider.setMajorTickUnit(1);
		distanceSlider.setMinorTickCount(0);
		distanceSlider.setSnapToTicks(true);
		distanceSlider.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				updateAprioriView();
			}
		});
		
		
		confidenceSlider = new Slider(.5, 1, 0.8);
		confidenceSlider.setShowTickLabels(true);
		confidenceSlider.setShowTickMarks(true);
		confidenceSlider.setMajorTickUnit(.1);
		confidenceSlider.setMinorTickCount(10);
		confidenceSlider.setSnapToTicks(true);
		confidenceSlider.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				updateAprioriView();
			}
		});
		

		aprioriPane.add(distanceSlider, 1, 4);
		aprioriPane.add(confidenceSlider, 1, 5);
	}

	@SuppressWarnings({ "unchecked" })
	public Node createFrequentItemTable() {
		TableColumn<Entry<String, String>, String> itemSetCol = new TableColumn<>("Frequent Items");
		itemSetCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Entry<String, String>, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Entry<String, String>, String> entry) {
						return new SimpleStringProperty(entry.getValue().getKey());
					}
				});

		supportCountCol = new TableColumn<>("Support Count");
		supportCountCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Entry<String, String>, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Entry<String, String>, String> entry) {
						// for second column we use value
						return new SimpleStringProperty(entry.getValue().getValue());
					}
				});

		itemSetCol.setMinWidth(500);
		supportCountCol.setSortType(TableColumn.SortType.DESCENDING);
		frequentItemsTable = new TableView<>();
		frequentItemsTable.getColumns().setAll(itemSetCol, supportCountCol);
		frequentItemsTable.getSortOrder().add(supportCountCol);
		frequentItemsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		frequentItemsTable.setPrefWidth(800);
		frequentItemsTable.setPrefHeight(300);

		ScrollPane scrollPane = new ScrollPane(frequentItemsTable);

		return scrollPane;
	}

	@SuppressWarnings({ "unchecked" })
	public Node createStrongRulesTable() {
		
		
		
		strongRuleCol = new TableColumn<>("Strong Rule");
		strongRuleCol.setCellValueFactory(new PropertyValueFactory<StrongRule, String>("ruleString"));

		confidenceCol = new TableColumn<>("Confidence");
		confidenceCol.setCellValueFactory(
				new PropertyValueFactory<StrongRule, Double>("confidence"));

		strongRuleCol.setMinWidth(500);
		confidenceCol.setSortType(TableColumn.SortType.DESCENDING);
		strongRulesTable = new TableView<>();
		strongRulesTable.getSortOrder().add(confidenceCol);
		strongRulesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		strongRulesTable.setPrefWidth(800);
		strongRulesTable.setPrefHeight(300);
		
		strongRulesTable.getColumns().addAll(strongRuleCol, confidenceCol);
		
		strongRulesTable.setRowFactory( tv -> {
		    TableRow<StrongRule> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            StrongRule strongRule = row.getItem();
		            controller.handleStrongRuleTableClick(strongRule);
		        }
		    });
		    return row ;
		});
		
		ScrollPane scrollPane = new ScrollPane(strongRulesTable);

		return scrollPane;
	}

	public void fillFrequentItemTable(int distance) {
		HashMap<List<TestedClass>, Integer> itemSet = TestData.getInstance().getAnalyzer()
				.getFrequentItemSets(distance);
		HashMap<String, String> stringItemSet = hashMapToString(itemSet);

		ObservableList<Entry<String, String>> items = FXCollections.observableArrayList(stringItemSet.entrySet());

		// Sort
		frequentItemsTable.setItems(items);
		supportCountCol.setSortType(TableColumn.SortType.DESCENDING);
		frequentItemsTable.getSortOrder().add(supportCountCol);
	}

	public void fillStrongRulesTable(double confidence, int distance) {
		List<StrongRule> strongRules = TestData.getInstance().getAnalyzer().getStrongRules(confidence, distance);

		ObservableList<StrongRule> items = FXCollections.observableArrayList(strongRules);

		// Sort
		strongRulesTable.setItems(items);
		confidenceCol.setSortType(TableColumn.SortType.DESCENDING);
//		strongRulesTable.getSortOrder().addAll(strongRuleCol, confidenceCol);
	}

	private HashMap<String, String> hashMapToString(HashMap<List<TestedClass>, Integer> itemSet) {
		HashMap<String, String> result = new HashMap<String, String>();

		for (Entry<List<TestedClass>, Integer> entry : itemSet.entrySet()) {
			String key = testedClassListToString(entry.getKey());
			String value = entry.getValue().toString();
			result.put(key, value);
		}
		return result;
	}

	private HashMap<String, String> strongRulesToStringHashMap(List<StrongRule> strongRules) {
		HashMap<String, String> result = new HashMap<String, String>();

		for (StrongRule strongRule : strongRules) {
			String key = testedClassListToString(strongRule.getLeftSide()) + " -> "
					+ testedClassListToString(strongRule.getRightSide());
			int conf = (int) (strongRule.getConfidence() * 100);
			String value = conf + "%";
			result.put(key, value);
		}
		return result;
	}

	private String testedClassListToString(List<TestedClass> testedClasses) {
		String result = "";
		for (int i = 0; i < testedClasses.size(); i++) {
			result += testedClasses.get(i).getClassName();
			if (i < testedClasses.size() - 1) {
				result += ", ";
			}
		}
		return result;
	}

	public void updateAprioriView() {
		int maxDst = TestData.getInstance().getTreeHeight() * 2;
		distanceSlider.setMax(maxDst);
		
		int distance = (int) distanceSlider.getValue();
		if (distance >= maxDst) distanceSlider.setValue(maxDst);
		double confidence = confidenceSlider.getValue();
		fillFrequentItemTable(distance);
		fillStrongRulesTable(confidence, distance);
	}

	public GridPane getAprioriPane() {
		return aprioriPane;
	}
}
