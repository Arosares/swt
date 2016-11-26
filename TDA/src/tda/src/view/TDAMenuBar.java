package tda.src.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import tda.src.controller.Controller;
import tda.src.logic.TestedClass;
import tda.src.model.Model;

public class TDAMenuBar {
	/**
	 * <pre>
	 *           1..1     1..1
	 * View ------------------------> Model
	 *           view        &gt;       model
	 * </pre>
	 */
	public Controller controller;

	public TDAMenuBar(Controller controller) {
		this.controller = controller;
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

				controller.openFolder();

			}
		});
/*		MenuItem recentItem = new MenuItem("Recent");
		recentItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO
			}
		});*/
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.exitMain();
				event.consume();
			}
		});
		file.getItems().addAll(openFile, openFolder, exitItem);
		menuBar.getMenus().add(file);
		
		return menuBar;
	}

}