package java_fx.bindings;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MultiBindings extends Application {

	public static void main(String[] args) throws Exception {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Multi");
		TextField field1 = new TextField();
		TextField field2 = new TextField();
		TextField field3 = new TextField();
		VBox root = new VBox();
		root.getChildren().addAll(field1, field2, field3);
		Scene scene = new Scene(root, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();

		field1.textProperty().bindBidirectional(field2.textProperty());
		field2.textProperty().bindBidirectional(field3.textProperty());
	}

}
