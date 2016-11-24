package tda.src.view;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tda.src.controller.Controller;
import tda.src.logic.TestedClass;
import tda.src.model.Model;

public class View extends Stage implements Observer {
	/**
	 * <pre>
	 *           1..1     1..1
	 * View ------------------------> Model
	 *           view        &gt;       model
	 * </pre>
	 */
	private Model model;
	private Controller controller;
	private BorderPane rootPane;
	private TableView<TestedClass> testedClassesTable;
	private String runID;

	public View(Model model, Controller controller) {
		super();
		this.model = model;
		this.controller = controller;
		this.model.addObserver(this);
		this.setScene(new Scene(createRootPane(), 1200, 900));
		this.setTitle("Test Data Analyser");
	}

	private Pane createRootPane() {
		this.rootPane = new BorderPane();
		this.rootPane.setTop(createMenueBar());
		this.rootPane.setCenter(createTestedClassesTable());
		return this.rootPane;
	}

	private Node createMenueBar() {
		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File");

		// Open the File Browser, capable of selecting multiple files
		MenuItem openFile = new MenuItem("Open File");
		openFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				controller.openFile();

			}
		});

		// Open the Folder Browser, capable of selecting multiple files
		MenuItem openFolder = new MenuItem("Open Folder");
		openFolder.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				controller.openFolder();

			}
		});
		MenuItem recentItem = new MenuItem("Recent");
		recentItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO
			}
		});
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.exitMain();
				event.consume();
			}
		});
		file.getItems().addAll(openFile, openFolder, recentItem, exitItem);
		menuBar.getMenus().add(file);

		return menuBar;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	public void errorAlert(String message) {
		Alert error = new Alert(AlertType.ERROR, message);
		error.showAndWait();
	}

	public void exitAlert() {
		Alert exitAlert = new Alert(AlertType.CONFIRMATION,
				"Do you really want to exit? All unsaved changes will be lost.");
		exitAlert.setTitle("Are you really, really sure? I mean Really!?");
		exitAlert.setHeaderText(null);
		Optional<ButtonType> result = exitAlert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			this.close();
		}
	}

	public List<File> pathAlert() {

		FileChooser fileBrowser = new FileChooser();
		fileBrowser.setTitle("Open Resource File");
		fileBrowser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));

		return fileBrowser.showOpenMultipleDialog(this);
	}

	public File directoryAlert() {
		DirectoryChooser directoryBrowser = new DirectoryChooser();
		directoryBrowser.setTitle("Select Root Directory");
		File selectedDirectory = directoryBrowser.showDialog(this);

		return selectedDirectory;
	}

	public void initCloseEventHandler(View view) {
		view.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				exitAlert();
				we.consume();
			}
		});
	}

	public void showTreeView(TreeView<File> treeView) {
		treeView.setPrefWidth(500);
		rootPane.setLeft(treeView);
	}

	// Initiate Table
	private Node createTestedClassesTable() {
		testedClassesTable = new TableView<TestedClass>();
		testedClassesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		testedClassesTable.setPrefWidth(800);
		testedClassesTable.setPrefHeight(300);

		ScrollPane scrollPane = new ScrollPane(testedClassesTable);

		return scrollPane;

	}

	private void fillTestedClassTable() {

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
