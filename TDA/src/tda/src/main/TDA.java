package tda.src.main;

import javafx.application.Application;
import javafx.stage.Stage;
import tda.src.gui.controller.Controller;

/**
 * Main function; starts the project
 *
 */
public class TDA extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new Controller().start();
	}
}
