package tda.src.view;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tda.src.controller.Controller;
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
	private TDATestRunTotals totals;

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
	 * @return
	 * Creates our base panes in our main View. 
	 * Includes the menuBar on top, and the TestRun Totals and Class Table below.
	 */
	private Pane createRootPane() {
		rootPane = new BorderPane();
		TDAMenuBar menuBar = new TDAMenuBar(controller, this);

		this.rootPane.setTop(menuBar.createMenuBar());
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		rootPane.setCenter(gridPane);

		tree = new TDATreeView(this);
		Label totalsLabel = new Label("Loaded TestRun Info:");
		gridPane.add(totalsLabel,1,1);
		
		totals = new TDATestRunTotals(controller);
		gridPane.add(totals.createTestRunTotalsBox(),1,2);
		
		table = new TDATableView(controller);
		rootPane.setLeft(tree.generateEmptyTreeView());
//		rootPane.setCenter(table.createTestedClassesTable());
		gridPane.add(table.createTestedClassesTable(),1,3);

		return rootPane;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

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

	public void showTreeView(TreeView treeView) {
		rootPane.setLeft(treeView);
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

	public Controller getController() {
		return controller;
	}

}
