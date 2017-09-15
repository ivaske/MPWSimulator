// http://docs.oracle.com/javase/8/javafx/events-tutorial/convenience_methods.htm
package java_fx.events;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EventHandlerDemo extends Application {

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
		Group root = new Group();
		Scene scene = new Scene(root, 300, 250);
		Button btn = new Button();
		btn.setLayoutX(100);
		btn.setLayoutY(80);
		btn.setText("Klick mich");

		// herkoemmliche Methode
		
		// Langversion
		btn.setOnAction(new ButtonClickHandler());
		
		// mit anonymer Klasse
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("Hello World 1");
			}
		});
		
		// mit Lambda-Expression
		btn.setOnAction(e -> System.out.println("Hello World 2"));
		
		// via addEventHandler
		
		// Langversion
		btn.addEventHandler(ActionEvent.ACTION, new ButtonClickHandler());

		// mit anonymer Klasse
		btn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Hello World 3");
			};
		});

		// mit Lambda-Expression
		btn.addEventHandler(ActionEvent.ACTION, e -> System.out.println("Hello World 4"));
		
		root.getChildren().add(btn);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}

class ButtonClickHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		System.out.println("Hello World 5");
	}

}
