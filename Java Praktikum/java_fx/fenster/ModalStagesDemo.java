package java_fx.fenster;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalStagesDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Button button = new Button("Open");
		button.setOnAction(e -> openModalStage(primaryStage));
		StackPane root = new StackPane();
		root.getChildren().add(button);
		Scene scene = new Scene(root, 300, 250);
		primaryStage.setTitle("Fenster 1");
		primaryStage.setScene(scene);
		primaryStage.setX(200);
		primaryStage.setY(100);
		primaryStage.show();


	}
	
	private void openModalStage(Stage owner) {
		Stage secondStage = new Stage();
		secondStage.setTitle("Fenster 2");
		secondStage.setScene(new Scene(new StackPane(new Label("Hello 2")), 200, 400));
		secondStage.sizeToScene();
		secondStage.initOwner(owner);
		secondStage.initModality(Modality.WINDOW_MODAL);
		secondStage.show();
	}

}
