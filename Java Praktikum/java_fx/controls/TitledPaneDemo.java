package java_fx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TitledPaneDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		TitledPane circlePane = new TitledPane("Circle", new Circle(50));
		VBox vbox = new VBox();
		vbox.getChildren().add(circlePane);
		Scene scene = new Scene(vbox, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
