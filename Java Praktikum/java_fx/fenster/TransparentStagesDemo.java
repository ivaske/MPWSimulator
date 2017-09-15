package java_fx.fenster;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TransparentStagesDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Circle circle = new Circle(100, 100, 100);
		StackPane root = new StackPane();
		root.setOpacity(0.8);
		root.setShape(circle);
		Button button = new Button("Close");
		button.setOnAction(e -> Platform.exit());
		root.getChildren().add(button);
		Scene scene = new Scene(root, 200, 200);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
	}

}
