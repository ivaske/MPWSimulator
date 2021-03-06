// aus Epple: JavaFX 8
package java_fx.controls;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TextAreaDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		final TextArea textArea = new TextArea();
		textArea.setPrefRowCount(10);
		textArea.setPrefColumnCount(20);
		textArea.setWrapText(true);
		StackPane root = new StackPane(new Group(textArea));
		Scene scene = new Scene(root, 300, 250);
		primaryStage.setTitle("TextArea Demo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
