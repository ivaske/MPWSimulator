// aus Epple: JavaFX 8
package java_fx.multimedia;

import com.sun.javafx.application.PlatformImpl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ThreadingDemo2 extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		Circle circle = new Circle();
		circle.setCenterX(100);
		circle.setCenterY(125);
		circle.setRadius(75);
		circle.setFill(Color.BLUE);

		Group root = new Group();

		root.getChildren().add(circle);

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setScene(scene);
		primaryStage.show();

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					System.out.println("hallo: " + Platform.isFxApplicationThread());
					// JavaFX creates an application thread for running the
					// application start method, processing input events, and
					// running animation timelines. Creation of JavaFX Scene and
					// Stage objects as well as modification of scene graph
					// operations to live objects (those objects already
					// attached to a scene) must be done on the JavaFX
					// application thread.
					PlatformImpl.runAndWait(() -> circle.setCenterX(circle.getCenterX() + 1));
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}

			}
		});
		th.setDaemon(true);
		th.start();

	}
}
