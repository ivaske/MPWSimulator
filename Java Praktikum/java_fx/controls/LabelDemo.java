// aus Epple: JavaFX 8
package java_fx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class LabelDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Label label = new Label("Hello World!");
		Image image = new Image(getClass().getResource("/java_fx/resources/world.png").toString());
		label.setGraphic(new ImageView(image));
		label.setFont(new Font("Arial", 20));
		label.setContentDisplay(ContentDisplay.TOP);
		label.setGraphicTextGap(30);
		label.setText("Ersetzen wir den Text gegen einen längeren Text, "
				+ " ist es sinnvoll, diesen Text bei Bedarf zu umbrechen.");
		label.setWrapText(true);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setRotate(45);

		StackPane root = new StackPane();
		root.getChildren().add(label);

		Scene scene = new Scene(root, 400, 300);

		primaryStage.setTitle("Label-Demo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
