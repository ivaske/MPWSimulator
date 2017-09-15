package java_fx.layouts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PaneDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Pane canvas = new Pane();
		canvas.setPrefSize(200, 200);
		Circle circle = new Circle(50, Color.BLUE);
		circle.relocate(20, 20);
		Rectangle rectangle = new Rectangle(100, 100, Color.RED);
		rectangle.relocate(70, 70);
		Button button = new Button("Klick mich");
		button.relocate(90, 110);
		canvas.getChildren().addAll(circle, rectangle, button);

		Scene scene = new Scene(canvas, 300, 250);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
