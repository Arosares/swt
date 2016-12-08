package tda.src.view;

import java.io.File;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import tda.src.controller.Controller;

public class TDAMenuBar {
	/**
	 * <pre>
	 *           1..1     1..1
	 * View ------------------------> Model
	 *           view        &gt;       model
	 * </pre>
	 */
	private Controller controller;
	private View view;

	public TDAMenuBar(Controller controller, View view) {
		this.controller = controller;
		this.view = view;
	}

	public Node createMenuBar() {
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

				File selectedDirectory = controller.openFolder();
				view.getTree().fillTreeView(selectedDirectory);
				view.fillClassTreeView();
			}
		});
		/*
		 * MenuItem recentItem = new MenuItem("Recent");
		 * recentItem.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { // TODO } });
		 */
		MenuItem clearData = new MenuItem("Clear All Data");
		clearData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.handleClearDataButton();
				event.consume();
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
		file.getItems().addAll(openFile, openFolder, clearData, exitItem);
		menuBar.getMenus().add(file);

		return menuBar;
	}

	public void clearDataAlert() {
		Alert clearDataAlert = new Alert(AlertType.CONFIRMATION,
				"Do you really want to clear all Data?\n All loaded TestRuns will be lost.");
		clearDataAlert.setTitle("Clear Data?");

		Optional<ButtonType> result = clearDataAlert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			controller.clearData();
		}
	}

}