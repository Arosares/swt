package tda.src.view;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tda.src.controller.Controller;
import tda.src.logic.TestRun;
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
	private TDATableView table;
	private TDATreeView tree;
	private TDAGraph graph;
	private TDATestRunTotals totals;
	private TDAMenuBar menuBar;
	private TabPane tabPane;
	private TreeView classTreeView;
	private Label idLabel;
	public View(Model model, Controller controller) {
		super();
		this.model = model;
		this.controller = controller;
		this.model.addObserver(this);
		this.setScene(new Scene(createRootPane(), 1200, 900));
		this.setTitle("Test Data Analyser");
		this.setMaximized(true);
	}

	/**
	 * @return Creates our base panes in our main View. Includes the menuBar on
	 *         top, the TreeView on the Left and the TestRun Totals and Class
	 *         Table + Graph below.
	 */
	private Pane createRootPane() {
		rootPane = new BorderPane();
		menuBar = new TDAMenuBar(controller, this);

		this.rootPane.setTop(menuBar.createMenuBar());
		GridPane gridPane = new GridPane();

		// margins around the whole grid (top/right/bottom/left)
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setAlignment(Pos.TOP_CENTER);
		rootPane.setCenter(gridPane);

		tree = new TDATreeView(this);

		idLabel = new Label();
		gridPane.add(idLabel, 1, 1);
		Label totalsLabel = new Label("TestRun Result Summary: ");
		gridPane.add(totalsLabel, 1, 2);

		totals = new TDATestRunTotals(controller, idLabel);
		gridPane.add(totals.createTestRunTotalsBox(), 1, 3);

		table = new TDATableView(controller);

		/*----- sidebar TabPane for switching between testruns and classes */
		tabPane = new TabPane();

		Tab tab1 = new Tab();
		tab1.setText("Testruns");
		tab1.setClosable(false);
		tab1.setContent(tree.generateEmptyTreeView());
		tabPane.getTabs().add(tab1);

		Tab tab2 = new Tab();
		tab2.setText("Classes");
		tab2.setClosable(false);
		tab2.setContent(generateEmptyClassTreeView());
		tabPane.getTabs().add(tab2);

		rootPane.setLeft(tabPane);

		gridPane.add(table.createTestedClassesTable(), 1, 4);
		graph = new TDAGraph(controller);
		gridPane.add(graph.generateLineChart(), 1, 5);

		Button resetGraphs = new Button("Reset Graph");
		resetGraphs.setOnMouseClicked(click -> {
			controller.handleResetGraph();
		});

		gridPane.add(resetGraphs, 1, 6);
		GridPane.setHalignment(resetGraphs, HPos.CENTER);
		GridPane.setValignment(resetGraphs, VPos.BOTTOM);
		gridPane.setVgap(20);

		return rootPane;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
	public void setTestRunLabel(TestRun testRun){
		
	}

	public void errorAlert(String message) {
		Alert error = new Alert(AlertType.ERROR, message);
		error.showAndWait();
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

	public void updateTreeView(TreeView treeView) {
		tabPane.getTabs().get(0).setContent(treeView);
	}

	public void updateClassTreeView() {
		tabPane.getTabs().get(1).setContent(this.classTreeView);
	}

	public TDATableView getTable() {
		return table;
	}

	public TDATestRunTotals getTotals() {
		return totals;
	}

	public TDATreeView getTree() {
		return tree;
	}

	public TDAGraph getGraph() {
		return graph;
	}

	public TDAMenuBar getMenuBar() {
		return menuBar;
	}

	public Controller getController() {
		return controller;
	}

	public TreeView generateEmptyClassTreeView() {
		this.classTreeView = new TreeView();
		return this.classTreeView;
	}

	public void fillClassTreeView() {
		/*
		 * DEPRECATED! ObservableList<TestedClass> items =
		 * FXCollections.observableArrayList(controller.getTestedClassList());
		 * this.classListView = new ListView<>(items);
		 * 
		 * classListView.setCellFactory(new Callback<ListView<TestedClass>,
		 * ListCell<TestedClass>>() {
		 * 
		 * @Override public ListCell<TestedClass> call(ListView<TestedClass> lv)
		 * { return new ListCell<TestedClass>() {
		 * 
		 * @Override public void updateItem(TestedClass item, boolean empty) {
		 * super.updateItem(item, empty); if (item == null) { setText(null); }
		 * else { // assume MyDataType.getSomeProperty() returns a // string
		 * setText(item.getClassName()); } } }; } });
		 */

		// controller.handleTableRowClick(testedClass);

		// TODO: not strings but objects, so they are clickable for the graph
		// TODO: update list when folders are added more than once

		updateClassTreeView();
	}

}
