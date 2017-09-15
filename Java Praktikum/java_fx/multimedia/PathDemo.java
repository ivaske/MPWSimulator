// aus Epple: JavaFX 8
package java_fx.multimedia;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class PathDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Group root = new Group();

		Path path = new Path();
		path.getElements().add(new MoveTo(100f, 50f));
		path.getElements().add(new CubicCurveTo(140f, 10f, 390f, 240f, 404, 10f));
		path.getElements().add(new ClosePath());
		root.getChildren().add(path);
		Scene scene = new Scene(root, 500, 300);

		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
