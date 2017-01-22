package tda.src.view;

import java.awt.TextField;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tda.src.controller.Controller;

public class AboutTDAView {
	
	private Controller controller;
	
	public AboutTDAView(Controller controller){
		this.controller = controller;
	}
	
	public void show(){
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.setTitle("About TDA");
		BorderPane aboutPane = new BorderPane();
		Text copyRight = new Text();
		String copyRight0 = "Copyright (c) 2017, SWT-Lab Group A\n";
		String copyRight1 = "\nPermission to use, copy, modify, and/or distribute this software for any\n"
				+ "purpose with or without fee is hereby granted, provided that the above\n"
				+ "copyright notice and this permission notice appear in all copies.\n";
		String copyRight2 = "\nTHE SOFTWARE IS PROVIDED \"AS IS\" AND THE AUTHOR DISCLAIMS ALL WARRANTIES\n"
				+ "WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF\n"
				+ "MERCHANTABILITY AND FITNESS.IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR\n"
				+ "ANYSPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES\n"
				+ "WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN\n"
				+ "AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF\n"
				+ "OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.";
		copyRight.setText(copyRight0 + copyRight1 + copyRight2);
		aboutPane.setCenter(copyRight);
		stage.setScene(new Scene(aboutPane, 600, 300));
		stage.setMinHeight(300);
		stage.setMinWidth(600);
		stage.showAndWait();
	}
	
}
