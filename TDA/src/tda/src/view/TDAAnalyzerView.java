package tda.src.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import tda.src.logic.TestData;
import tda.src.logic.TestedClass;
import tda.src.logic.apriori.StrongRule;

public class TDAAnalyzerView {

	private TableView<Entry<String, String>> frequentItemsTable;
	private TableView<Entry<String, String>> strongRulesTable;
	
	private TableColumn<Entry<String, String>, String> confidenceCol;
	private TableColumn<Entry<String, String>, String> supportCountCol;

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
		TableColumn<Entry<String, String>, String> strongRuleCol = new TableColumn<>("Strong Rule");
		strongRuleCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Entry<String, String>, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Entry<String, String>, String> entry) {
						return new SimpleStringProperty(entry.getValue().getKey());
					}
				});

		confidenceCol = new TableColumn<>("Confidence");
		confidenceCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Entry<String, String>, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Entry<String, String>, String> entry) {
						// for second column we use value
						return new SimpleStringProperty(entry.getValue().getValue());
					}
				});
		
		strongRuleCol.setMinWidth(500);
		confidenceCol.setSortType(TableColumn.SortType.DESCENDING);
		strongRulesTable = new TableView<>();
		strongRulesTable.getColumns().setAll(strongRuleCol, confidenceCol);
		strongRulesTable.getSortOrder().add(confidenceCol);
		strongRulesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		strongRulesTable.setPrefWidth(800);
		strongRulesTable.setPrefHeight(300);
		

		ScrollPane scrollPane = new ScrollPane(strongRulesTable);

		return scrollPane;
	}

	public void fillFrequentItemTable(int distance) {
		HashMap<List<TestedClass>, Integer> itemSet = TestData.getInstance().getAnalyzer().getFrequentItemSets(distance);
		HashMap<String, String> stringItemSet = hashMapToString(itemSet);

		ObservableList<Entry<String, String>> items = FXCollections.observableArrayList(stringItemSet.entrySet());
		
		// Sort
		frequentItemsTable.setItems(items);
		supportCountCol.setSortType(TableColumn.SortType.DESCENDING);
		frequentItemsTable.getSortOrder().add(supportCountCol);
	}

	public void fillStrongRulesTable(double confidence, int distance) {
		List<StrongRule> strongRules = TestData.getInstance().getAnalyzer().getStrongRules(confidence, distance);
		HashMap<String, String> stringItemSet = strongRulesToStringHashMap(strongRules);
		
		ObservableList<Entry<String, String>> items = FXCollections.observableArrayList(stringItemSet.entrySet());
		
		// Sort
		strongRulesTable.setItems(items);
		confidenceCol.setSortType(TableColumn.SortType.DESCENDING);
		strongRulesTable.getSortOrder().add(confidenceCol);
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
}
