// aus Epple: JavaFX 8
package java_fx.layouts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class FlowPaneDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Image[] images = new Image[10];
		for (int i = 0; i < 8; i++) {
			images[i] = new Image("java_fx/resources/world.png", 50, 72, true, true);

		}
		FlowPane iconView = new FlowPane();
		iconView.setVgap(10);
		iconView.setHgap(20);
		for (int i = 0; i < images.length; i++) {
			iconView.getChildren().add(new ImageView(images[i]));
		}
		primaryStage.setScene(new Scene(iconView, 300, 200));

		primaryStage.show();
	}

}
