// aus Epple: JavaFX 8
package java_fx.events;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class EventDeliveryProcessDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		EventHandler<MouseEvent> eh = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				System.out.println("Handler, Mouse pressed, Source: " + me.getSource().toString());
				System.out.println("Handler, Mouse pressed, Target: " + me.getTarget().toString());
				// me.consume();
			}
		};
		
		EventHandler<MouseEvent> ef = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				System.out.println("Filter, Mouse pressed, Source: " + me.getSource().toString());
				System.out.println("Filter, Mouse pressed, Target: " + me.getTarget().toString());
				// me.consume();
			}
		};

		VBox root = new VBox();

		Circle circle1 = new Circle();
		circle1.setRadius(75);
		circle1.setFill(Color.BLUE);

		Circle circle2 = new Circle();
		circle2.setRadius(40);
		circle2.setFill(Color.RED);

		root.getChildren().addAll(circle1, circle2);
		Scene scene = new Scene(root, 300, 400);

		primaryStage.setScene(scene);
		
		circle1.addEventFilter(MouseEvent.MOUSE_PRESSED, ef);
		circle2.addEventFilter(MouseEvent.MOUSE_PRESSED, ef);
		root.addEventFilter(MouseEvent.MOUSE_PRESSED, ef);
		scene.addEventFilter(MouseEvent.MOUSE_PRESSED, ef);
		primaryStage.addEventFilter(MouseEvent.MOUSE_PRESSED, ef);

		circle1.addEventHandler(MouseEvent.MOUSE_PRESSED, eh);
		circle2.addEventHandler(MouseEvent.MOUSE_PRESSED, eh);
		root.addEventHandler(MouseEvent.MOUSE_PRESSED, eh);
		scene.addEventHandler(MouseEvent.MOUSE_PRESSED, eh);
		primaryStage.addEventHandler(MouseEvent.MOUSE_PRESSED, eh);
		
		primaryStage.show();

	}
}
