// aus Epple: JavaFX 8
package java_fx.multimedia;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TransitionDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		Group root = new Group();
		Rectangle rect = new Rectangle(100, 100, 100, 100);
		RotateTransition transition = new RotateTransition();
		transition.setNode(rect);
		transition.setDuration(Duration.seconds(1));
		transition.setFromAngle(0);
		transition.setToAngle(360);
		
		Button b = new Button("Rotate");
		b.setOnAction(e -> transition.play());

		root.getChildren().addAll(rect, b);
		Scene scene = new Scene(root, 300, 300);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
