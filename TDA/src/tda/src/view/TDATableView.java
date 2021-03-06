package tda.src.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tda.src.controller.Controller;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;

public class TDATableView {

	private Controller controller;
	private TableView<TestedClass> testedClassesTable;
	private ObservableList<TestedClass> data;
	private TableColumn<TestedClass, String> classNameCol;
	private TableColumn<TestedClass, Double> failurePercentageCol;

	public TDATableView(Controller controller) {
		super();
		this.controller = controller;
	}

	public Node createTestedClassesTable() {
		testedClassesTable = new TableView<TestedClass>();
		testedClassesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		testedClassesTable.setPrefWidth(800);
		testedClassesTable.setPrefHeight(300);

		classNameCol = new TableColumn<TestedClass, String>("Tested Class");
		failurePercentageCol = new TableColumn<TestedClass, Double>("Failure Percentage");

		classNameCol.setCellValueFactory(new PropertyValueFactory<TestedClass, String>("className"));
		failurePercentageCol
				.setCellValueFactory(new PropertyValueFactory<TestedClass, Double>("currentFailurePercentage"));

		failurePercentageCol.setSortType(TableColumn.SortType.DESCENDING);

		testedClassesTable.getColumns().addAll(classNameCol, failurePercentageCol);
		testedClassesTable.getSortOrder().add(failurePercentageCol);

		testedClassesTable.setRowFactory(tv -> {
			TableRow<TestedClass> row = new TableRow<TestedClass>() {

				@Override
				public void updateItem(TestedClass item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null) {
						setStyle("");
					} else if (item.getCurrentFailurePercentage() > 90.00) {
						setStyle("-fx-background-color: red;");
					} else if (item.getCurrentFailurePercentage() > 75.00) {
						setStyle("-fx-background-color: orange;");
					} else if (item.getCurrentFailurePercentage() > 50.00) {
						setStyle("-fx-background-color: yellow;");
					} else {
						setStyle("");
					}
				}

			};

			// Make rows able to be double clicked and display the selected row
			// in
			// the graph
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					TestedClass testedClass = row.getItem();

					controller.handleTableRowClick(testedClass);
				}
			});
			return row;
		});

		ScrollPane scrollPane = new ScrollPane(testedClassesTable);

		return scrollPane;

	}

	@SuppressWarnings("unchecked")
	public void fillTestedClassTable(TestRun testRun) {
		data = FXCollections.observableArrayList(controller.getTestedClassesFromTestRun(testRun));

		testedClassesTable.setItems(data);

		failurePercentageCol.setSortType(TableColumn.SortType.DESCENDING);
		testedClassesTable.getSortOrder().add(failurePercentageCol);

	}

	public ObservableList<TestedClass> getData() {
		return data;
	}

}
