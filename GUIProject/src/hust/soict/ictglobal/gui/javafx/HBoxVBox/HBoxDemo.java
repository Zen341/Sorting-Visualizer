package hust.soict.ictglobal.gui.javafx.HBoxVBox;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HBoxDemo extends Application {

	@Override
	public void start(Stage primaryStage) {
		HBox root = new HBox();

		root.setSpacing(10);
		root.setPadding(new Insets(15, 20, 10, 10));

		Button button1 = new Button("Butoon1");
		root.getChildren().add(button1);

		Button button2 = new Button("Button2");
		button2.setPrefSize(100, 100);
		root.getChildren().add(button2);

		TextField textField = new TextField("Text Field");
		textField.setPrefWidth(110);
		root.getChildren().add(textField);

		CheckBox checkBox = new CheckBox("Check Box");
		root.getChildren().add(checkBox);

		RadioButton radioButton = new RadioButton("Radio Button");
		root.getChildren().add(radioButton);

		Scene scene = new Scene(root, 550, 250);

		primaryStage.setTitle("HBox Layout Demo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
