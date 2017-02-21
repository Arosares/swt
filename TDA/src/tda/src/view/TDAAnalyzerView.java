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
import tda.src.datastructure.TestData;
import tda.src.datastructure.TestedClass;
import tda.src.gui.controller.Controller;
import tda.src.logic.analyzer.StrongRule;

/**
 * Displays the outputs of the Apriori display
 *
 */
public class TDAAnalyzerView {
	private Controller controller;

	private GridPane aprioriPane;
	private Slider distanceSlider;
	private Slider confidenceSlider;
	private TableView<Entry<String, String>> frequentItemsTable = new TableView<>();
	private TableView<StrongRule> strongRulesTable = new TableView<StrongRule>();
	private ObservableList<StrongRule> strongRuleItems;
	ObservableList<Entry<String, String>> frequentItems;

	private TableColumn<Entry<String, String>, String> supportCountCol;
	TableColumn<StrongRule, String> strongRuleCol;
	private TableColumn<StrongRule, Double> confidenceCol;

	/**
	 * TDAAnalyzerView creates a new Tab in our main view, showing the Table
	 * with the visual output of the Apriori Analyzer. Built-In sliders allow
	 * filtering the provided data without recalculation.
	 * 
	 * @param controller
	 */
	@SuppressWarnings("unchecked")
	public TDAAnalyzerView(Controller controller) {
		super();
		this.controller = controller;
		aprioriPane = new GridPane();
		// margins around the whole center grid (top/right/bottom/left)
		aprioriPane.setPadding(new Insets(10, 10, 10, 10));
		aprioriPane.setVgap(10);
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

		confidenceSlider = new Slider(1, 100, 60);
		confidenceSlider.setShowTickLabels(true);
		confidenceSlider.setShowTickMarks(true);
		confidenceSlider.setMajorTickUnit(5);
		confidenceSlider.setMinorTickCount(10);
		confidenceSlider.setSnapToTicks(true);
		confidenceSlider.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				updateAprioriView();
			}
		});

		Label distanceLabel = new Label("Class Distance");
		distanceLabel.setAlignment(Pos.TOP_RIGHT);
		Label confidenceLabel = new Label("Confidence");
		confidenceLabel.setAlignment(Pos.TOP_RIGHT);
		aprioriPane.add(distanceLabel, 1, 4);
		aprioriPane.add(distanceSlider, 1, 5);
		aprioriPane.add(confidenceLabel, 1, 6);
		aprioriPane.add(confidenceSlider, 1, 7);
	}

	/**
	 * Creates the table with the frequent item sets discovered by Apriori. Alse
	 * creates a scrollpane for the table.
	 * 
	 * @return
	 */
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

	/**
	 * Creates a Table to show Strong Rules within the selected Data set, as
	 * discovered by our Apriori-Algorithm.
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public Node createStrongRulesTable() {

		strongRuleCol = new TableColumn<>("Strong Rule");
		strongRuleCol.setCellValueFactory(new PropertyValueFactory<StrongRule, String>("ruleString"));

		confidenceCol = new TableColumn<>("Confidence");
		confidenceCol.setCellValueFactory(new PropertyValueFactory<StrongRule, Double>("confidence"));

		strongRuleCol.setMinWidth(500);
		confidenceCol.setSortType(TableColumn.SortType.DESCENDING);
		strongRulesTable = new TableView<>();
		strongRulesTable.getSortOrder().add(confidenceCol);
		strongRulesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		strongRulesTable.setPrefWidth(800);
		strongRulesTable.setPrefHeight(300);

		strongRulesTable.getColumns().addAll(strongRuleCol, confidenceCol);

		strongRulesTable.setRowFactory(tv -> {
			TableRow<StrongRule> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					StrongRule strongRule = row.getItem();
					controller.handleStrongRuleTableClick(strongRule);
				}
			});
			return row;
		});
		ScrollPane scrollPane = new ScrollPane(strongRulesTable);
		return scrollPane;
	}

	/**
	 * Fills the Frequent Item Table with data. Distance is required for
	 * on-the-fly filtering. Minimum support can not be adjusted dynamically.
	 * 
	 * @param distance
	 */
	public void fillFrequentItemTable(int distance) {
		HashMap<List<TestedClass>, Integer> itemSet = TestData.getInstance().getAnalyzer()
				.getFrequentItemSets(distance);
		HashMap<String, String> stringItemSet = hashMapToString(itemSet);

		frequentItems = FXCollections.observableArrayList(stringItemSet.entrySet());

		// Sort
		frequentItemsTable.setItems(frequentItems);
		supportCountCol.setSortType(TableColumn.SortType.DESCENDING);
		frequentItemsTable.getSortOrder().add(supportCountCol);
	}

	/**
	 * Fills the Strong-Rules Table. Confidence and distance are provided for
	 * filtering.
	 * 
	 * @param confidence
	 * @param distance
	 */
	public void fillStrongRulesTable(double confidence, int distance) {
		List<StrongRule> strongRules = TestData.getInstance().getAnalyzer().getStrongRules(confidence, distance);

		strongRuleItems = FXCollections.observableArrayList(strongRules);

		// Sort
		strongRulesTable.setItems(strongRuleItems);
		confidenceCol.setSortType(TableColumn.SortType.DESCENDING);
		// strongRulesTable.getSortOrder().addAll(strongRuleCol, confidenceCol);
	}

	/**
	 * Sub-method called to turn the provided hash-table of ItemSets into
	 * display-able strings.
	 * 
	 * @param itemSet
	 * @return
	 */
	private HashMap<String, String> hashMapToString(HashMap<List<TestedClass>, Integer> itemSet) {
		HashMap<String, String> result = new HashMap<String, String>();

		for (Entry<List<TestedClass>, Integer> entry : itemSet.entrySet()) {
			String key = testedClassListToString(entry.getKey());
			String value = entry.getValue().toString();
			result.put(key, value);
		}
		return result;
	}

	/**
	 * Sub-method called to turn the provided hash-table of Strong Rules into
	 * display-able strings.
	 * 
	 * @param strongRules
	 * @return
	 */
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

	/**
	 * Turns class names to Strings, then returns them
	 * 
	 * @param testedClasses
	 * @return
	 */
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

	/**
	 * Updates the View then the sliders are moved by the user.
	 * 
	 */
	public void updateAprioriView() {
		int maxDst = TestData.getInstance().getTreeHeight() * 2;
		distanceSlider.setMax(maxDst);

		int distance = (int) distanceSlider.getValue();
		if (distance >= maxDst)
			distanceSlider.setValue(maxDst);
		double confidence = confidenceSlider.getValue();
		fillFrequentItemTable(distance);
		fillStrongRulesTable(confidence, distance);
	}

	public GridPane getAprioriPane() {
		return aprioriPane;
	}

	public ObservableList<StrongRule> getStrongRuleItems() {
		return strongRuleItems;
	}

	public ObservableList<Entry<String, String>> getFrequentItems() {
		return frequentItems;
	}
}
