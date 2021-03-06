package tda.src.view;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.event.ActionEvent;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tda.src.controller.Controller;
import tda.src.logic.TestRun;
import tda.src.logic.statistics.StatisticAnalyzer;
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
	private TDAAnalyzerView analyzer;
	private TDAStatisticView statistics;
	private TDAChart graph;
	private TDATestRunTotals totals;
	private TDAMenuBar menuBar;
	private TabPane sideTabPane;
	private TabPane mainWindowTabPane;
	private Tab tableTab;
	private Tab chartTab;
	private Tab analyzerTab;
	private Tab statisticsTab;
	private TDAClassView classTree;
	private TDAcomparison comparison;
	private AboutTDAView aboutTDAView;
	private TDAManual manualView;

	private HBox nothingLoadedPane;
	private boolean isInitiated = false;
	private GridPane tablePane;
	private GridPane chartPane;
	private GridPane aprioriPane;
	private GridPane statisticsPane;

	private Label idLabel;

	public View(Model model, Controller controller) {
		super();
		this.model = model;
		this.controller = controller;
		this.model.addObserver(this);
		this.setScene(new Scene(createNothingLoadedPane()));
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
		aboutTDAView = new AboutTDAView(controller);
		manualView = new TDAManual(controller);

		// sticks the menubar to top
		this.rootPane.setTop(menuBar.createMenuBar());

		/*---- CONTENT GRIDPANE with the main content (TableView, Graph,...) ----*/
		tablePane = new GridPane();

		// margins around the whole center grid (top/right/bottom/left)
		tablePane.setPadding(new Insets(10, 10, 10, 10));
		tablePane.setAlignment(Pos.TOP_CENTER);

		idLabel = new Label();
		tablePane.add(idLabel, 1, 1);
		Label totalsLabel = new Label("TestRun Result Summary: ");
		tablePane.add(totalsLabel, 1, 2);

		totals = new TDATestRunTotals(controller, idLabel);
		tablePane.add(totals.createTestRunTotalsBox(), 1, 3);

		tree = new TDATreeView(this);
		table = new TDATableView(controller);
		classTree = new TDAClassView(this);

		tablePane.add(table.createTestedClassesTable(), 1, 4);

		graph = new TDAChart(controller);

		comparison = new TDAcomparison(this);

		chartPane = new GridPane();
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(100);
		chartPane.getColumnConstraints().add(column1);
		chartPane.setPadding(new Insets(10, 10, 10, 10));
		chartPane.add(graph.generateLineChart(), 0, 0);

		Button resetLineChart = new Button("Reset Data");
		resetLineChart.setOnMouseClicked(click -> {
			controller.handleResetGraph();
			controller.handleResetComparison();
		});

		resetLineChart.setMinSize(80, 60);
		chartPane.add(resetLineChart, 0, 3);

		chartPane.add(new Separator(), 0, 1);

		chartPane.add(comparison.generateEmptyComparisonPane(), 0, 2);
		ScrollPane comparisonScrollPane = new ScrollPane(chartPane);
		comparisonScrollPane.setFitToHeight(true);
		comparisonScrollPane.setFitToWidth(true);

		// Analyzer (Apriori) Pane
		analyzer = new TDAAnalyzerView(controller);
		aprioriPane = analyzer.getAprioriPane();
		
		// Statistics (Most Relevant Classes) Pane
		statistics = new TDAStatisticView(controller);
		statisticsPane = statistics.getStatisticsPane();


		GridPane.setHalignment(resetLineChart, HPos.CENTER);
		GridPane.setValignment(resetLineChart, VPos.BOTTOM);
		tablePane.setVgap(20);

		/*----- SIDEBAR TABPANE for switching between testruns and classes */
		sideTabPane = new TabPane();
		sideTabPane.setPrefWidth(300);
		Tab tab1 = new Tab();
		tab1.setText("Testruns");
		tab1.setClosable(false);
		tab1.setContent(tree.generateEmptyTreeView());
		sideTabPane.getTabs().add(tab1);

		Tab tab2 = new Tab();
		tab2.setText("Classes");
		tab2.setClosable(false);
		tab2.setContent(classTree.generateEmptyClassView());
		sideTabPane.getTabs().add(tab2);
//		sideTabPane.setMinWidth(300);
//		sideTabPane.setPrefWidth(300);
		sideTabPane.setPrefWidth(250);

		rootPane.setLeft(sideTabPane);
		mainWindowTabPane = new TabPane();

		tableTab = new Tab("Table");
		tableTab.setClosable(false);
		tableTab.setContent(tablePane);

		mainWindowTabPane.getTabs().add(tableTab);
		chartTab = new Tab("Chart");
		chartTab.setClosable(false);
		chartTab.setContent(comparisonScrollPane);
		mainWindowTabPane.getTabs().add(chartTab);

		analyzerTab = new Tab("Analyzer");
		analyzerTab.setClosable(false);
		analyzerTab.setContent(aprioriPane);
		mainWindowTabPane.getTabs().add(analyzerTab);
		
		statisticsTab = new Tab("Statistics");
		statisticsTab.setClosable(false);
		statisticsTab.setContent(statisticsPane);
		mainWindowTabPane.getTabs().add(statisticsTab);
		
		mainWindowTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if (newTab == statisticsTab) {
				statistics.updateStatisticsView();
			} else if (newTab == analyzerTab) {
				System.out.println("Apriori Update");
				analyzer.updateAprioriView();
			}
		});

		rootPane.setCenter(mainWindowTabPane);

		return rootPane;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	public void setTestRunLabel(TestRun testRun) {

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

	/**
	 * The exit Alert window called by exiting through the "Data" dropdown menu.
	 */
	public void exitAlert() {
		Alert exitAlert = new Alert(AlertType.CONFIRMATION,
				"Do you really want to exit? All unsaved changes will be lost.");
		exitAlert.setTitle("Are you sure?");
		exitAlert.setHeaderText(null);
		Optional<ButtonType> result = exitAlert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			this.close();
		}
	}

	public void updateTreeView(TreeView treeView) {
		sideTabPane.getTabs().get(0).setContent(treeView);
	}

	public void updateClassView(TreeView treeView) {
		sideTabPane.getTabs().get(1).setContent(treeView);
	}

	public void clearTotals() {
		totals = new TDATestRunTotals(controller, idLabel);
		tablePane.add(totals.createTestRunTotalsBox(), 1, 3);
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

	public TDAChart getGraph() {
		return graph;
	}

	public TDAMenuBar getMenuBar() {
		return menuBar;
	}

	public AboutTDAView getAboutTDAView() {
		return aboutTDAView;
	}

	public TDAManual getManualView() {
		return manualView;
	}

	public Controller getController() {
		return controller;
	}

	public TDAClassView getClassTree() {
		return classTree;
	}

	public boolean isInitiated() {
		return isInitiated;
	}

	public TDAcomparison getComparison() {
		return comparison;
	}

	public TabPane getMainWindowTabPane() {
		return mainWindowTabPane;
	}

	public Tab getTableTab() {
		return tableTab;
	}

	public Tab getChartTab() {
		return chartTab;
	}

	public TDAAnalyzerView getAnalyzer() {
		return analyzer;
	}

	public Tab getAnalyzerTab() {
		return analyzerTab;
	}

	public void fillClassTreeView() {

		// TODO: update list when folders are added more than once

		// TODO updateClassTreeView();
	}

	public void updateRootPane() {
		this.getScene().setRoot(createRootPane());
		isInitiated = true;
	}

	public Pane createNothingLoadedPane() {
		nothingLoadedPane = new HBox();

		nothingLoadedPane.setAlignment(Pos.CENTER);
		nothingLoadedPane.setPadding(new Insets(15, 12, 15, 12));
		nothingLoadedPane.setSpacing(10);
		nothingLoadedPane.setStyle("-fx-background-color: #336699;");

		Button loadFileButton = new Button();
		loadFileButton.setPrefSize(164, 164);
		loadFileButton.setStyle("-fx-background-image: url('/tda/src/view/openfile.png')");
		loadFileButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				controller.openFile();
			}
		});
		/*
		 * currently not working, bc openFile is using deprecated calls
		 * loadFileButon.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) {
		 * 
		 * controller.openFile(); if (isInitiated == false) { updateRootPane();
		 * }
		 * 
		 * } });
		 */

		Button loadFolderButton = new Button();
		loadFolderButton.setPrefSize(164, 164);
		loadFolderButton.setStyle("-fx-background-image: url('/tda/src/view/openfolder.png')");
		loadFolderButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				controller.openFolder();
			}
		});
		nothingLoadedPane.getChildren().addAll(loadFileButton, loadFolderButton);

		return nothingLoadedPane;
	}
}
