// aus Epple: JavaFX 8
package java_fx.events;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class KeyEventsDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		final TextField textBox = new TextField();
		textBox.setPromptText("Write here");

		textBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				System.out.println("Key Pressed: " + ke.getText());
			}
		});

		textBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				System.out.println("Key Released: " + ke.getText());
			}
		});
		Group root = new Group();
		root.getChildren().add(textBox);
		Scene scene = new Scene(root, 300, 250);

		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
