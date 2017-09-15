package java_fx.fenster;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StagesDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Label label = new Label("Hello 1!");
		StackPane root = new StackPane();
		root.getChildren().add(label);
		Scene scene = new Scene(root, 300, 250);
		primaryStage.setTitle("Fenster 1");
		primaryStage.setScene(scene);
		primaryStage.setX(20);
		primaryStage.setY(100);
		primaryStage.show();
		
		Stage secondStage = new Stage();
		secondStage.setTitle("Fenster 2");
		secondStage.setScene(new Scene(new StackPane(new Label("Hello 2")), 200, 400));
		secondStage.setX(40);
		secondStage.setY(200);
		secondStage.sizeToScene();
		secondStage.show();
	}

}

