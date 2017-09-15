// aus Epple: JavaFX 8
package java_fx.multimedia;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImageDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox();
		Image image1 = new Image(getClass().getResource("/java_fx/resources/world.png").toString());
		ImageView view1 = new ImageView(image1);
		Image image2 = new Image(getClass().getResource("/java_fx/resources/world.png").toString(), 200, 400, false,
				true);
		ImageView view2 = new ImageView(image2);
		root.getChildren().addAll(view1, view2);
		Scene scene = new Scene(root, 500, 300);

		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
