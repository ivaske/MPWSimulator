// aus Epple: JavaFX 8
package java_fx.multimedia;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimelineDemo extends Application {

	@Override
	public void start(Stage primaryStage) {
		final Rectangle rect = new Rectangle(100, 100, 10, 10);
		final KeyFrame keyFrame0 = new KeyFrame(Duration.millis(0), new KeyValue(rect.widthProperty(), 10),
				new KeyValue(rect.heightProperty(), 10));
		final KeyFrame keyFrame1 = new KeyFrame(Duration.millis(800), new KeyValue(rect.widthProperty(), 80),
				new KeyValue(rect.heightProperty(), 80));
		final KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1000), new KeyValue(rect.widthProperty(), 400),
				new KeyValue(rect.heightProperty(), 400));
		Timeline t = new Timeline(keyFrame0, keyFrame1, keyFrame2);
		Button b = new Button("Start");
		b.setOnAction(e -> t.play());
		Group root = new Group(rect, b);
		Scene scene = new Scene(root, 600, 600);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
