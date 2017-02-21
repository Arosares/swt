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
import tda.src.datastructure.TestData;
import tda.src.gui.controller.Controller;

/**
 * Implements the Menubar of the software
 *
 */
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

	/**
	 * Carries over the controller and view
	 * 
	 * @param controller
	 * @param view
	 */
	public TDAMenuBar(Controller controller, View view) {
		this.controller = controller;
		this.view = view;
	}

	// TODO: Kann hier nochmal wer drueber gucken?
	/**
	 * Creates a Menu Bar with two dropdown menus, "File" and "Help". The Menu
	 * Items are "Open File", "Open Folder", "Clear Data" and "Exit" in the
	 * first and "Manual" and "About TDA" in the second Menu, respectively.
	 * 
	 * @return
	 */
	public Node createMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File");
		Menu help = new Menu("Help");

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

		MenuItem manual = new MenuItem("Manual");
		manual.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.handleTDAManualClick();
				event.consume();
			}
		});
		MenuItem aboutTDA = new MenuItem("About TDA");
		aboutTDA.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.handleAboutTDAClick();
				event.consume();
			}

		});

		help.getItems().addAll(manual, aboutTDA);
		menuBar.getMenus().add(help);
		return menuBar;
	}

	/**
	 * Shows a Popup Window asking the user if they really want to clear all
	 * Data.
	 */
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