package java_fx.bindings;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BindingGUIDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox();
		TextField field = new TextField("");
		Label label = new Label("");
		Slider slider = new Slider(10, 200, 100);
		Rectangle rect = new Rectangle(100, 50);
		root.getChildren().addAll(field, label, slider, rect);
		
		label.textProperty().bind(field.textProperty());
		rect.widthProperty().bind(slider.valueProperty());

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setTitle("BindingGUIDemo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
