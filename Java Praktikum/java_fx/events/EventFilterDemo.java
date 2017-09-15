// http://docs.oracle.com/javase/8/javafx/events-tutorial/convenience_methods.htm
package java_fx.events;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EventFilterDemo extends Application {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("EventHandlerDemo");
		VBox root = new VBox();
		Scene scene = new Scene(root, 300, 250);
		Label text1 = new Label("hallo");
		TextField text2 = new TextField();

		// Register an event filter for a single node and a specific event type
		text1.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("1: Filtering event " + event.getEventType());
			};
		});

		// Define an event filter
		EventHandler<InputEvent> filter = new EventHandler<InputEvent>() {
			public void handle(InputEvent event) {
				System.out.println("2: Filtering event " + event.getEventType());
			}
		};

		// Register the same filter for two different nodes
		text1.addEventFilter(MouseEvent.MOUSE_PRESSED, filter);
		text2.addEventFilter(MouseEvent.MOUSE_PRESSED, filter);

		// Register the filter for another event type
		text2.addEventFilter(KeyEvent.KEY_PRESSED, filter);

		root.getChildren().addAll(text1, text2);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
