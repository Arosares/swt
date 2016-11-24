package tda.src.view;

import java.util.ResourceBundle.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tda.src.controller.Controller;
import tda.src.logic.TestedClass;

public class TDATableView {
	
	private Controller controller;
	private TableView<TestedClass> testedClassesTable;
	
	

	public TDATableView(Controller controller) {
		super();
		this.controller = controller;
	}

	public Node createTestedClassesTable() {
		testedClassesTable = new TableView<TestedClass>();
		testedClassesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		testedClassesTable.setPrefWidth(800);
		testedClassesTable.setPrefHeight(300);

		ScrollPane scrollPane = new ScrollPane(testedClassesTable);

		return scrollPane;

	}
	
	public void fillTestedClassTable(String runID) {

		ObservableList<TestedClass> data = FXCollections
				.observableArrayList(controller.getTestedClassesFromTestRun(runID));

		TableColumn classNameCol = new TableColumn("Tested Class");
		TableColumn failurePercentageCol = new TableColumn("Failure Percentage");

		classNameCol.setCellValueFactory(new PropertyValueFactory<TestedClass, String>("className"));
		failurePercentageCol
				.setCellValueFactory(new PropertyValueFactory<TestedClass, Double>("currentFailurePercentage"));

		testedClassesTable.setItems(data);
		testedClassesTable.getColumns().addAll(classNameCol, failurePercentageCol);
	}
}
