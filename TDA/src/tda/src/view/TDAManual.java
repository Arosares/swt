package tda.src.view;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tda.src.controller.Controller;

public class TDAManual {
	private Controller controller;
	public TDAManual(Controller controller){
		this.controller = controller;
	}
	public void show(){
		Stage stage = new Stage(StageStyle.UTILITY);
		ScrollPane root = new ScrollPane();
		BorderPane manualPane = new BorderPane();
		manualPane.setPadding(new Insets(8, 8, 8, 8));
		Scene scene = new Scene(root, 1000, 800);
		
		stage.setTitle("Manual");
		Text manual = new Text();
		String manualString = controller.getManual();
		
		manual.setText(manualString);
		manualPane.setCenter(manual);
		root.setContent(manualPane);
		
		stage.setScene(scene);
		stage.setMinHeight(300);
		stage.setMinWidth(600);
		stage.showAndWait();
	}
}
