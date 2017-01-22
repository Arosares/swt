package tda.src.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tda.src.controller.Controller;

public class AboutTDAView {

	private Controller controller;

	public AboutTDAView(Controller controller) {
		this.controller = controller;
	}

	public void show() {
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.setTitle("About TDA");
		BorderPane aboutPane = new BorderPane();
		Text copyRight = new Text(controller.getLicence());

		aboutPane.setCenter(copyRight);
		stage.setScene(new Scene(aboutPane, 600, 300));
		stage.setMinHeight(300);
		stage.setMinWidth(600);
		stage.showAndWait();
	}

}
