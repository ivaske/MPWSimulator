// aus Epple: JavaFX 8
package java_fx.events;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MouseEventsDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		Circle circle = new Circle();
		circle.setCenterX(100);
		circle.setCenterY(125);
		circle.setRadius(75);
		circle.setFill(Color.BLUE);

		circle.setOnMouseEntered(e -> System.out.println("Mouse entered"));
		circle.setOnMouseExited(e -> System.out.println("Mouse exited"));

		circle.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				System.out.println("Mouse pressed");
				if (me.isShiftDown()) {
					System.out.println("shift down");
				}
				int count = 0;
				if ((count = me.getClickCount()) > 1) {
					System.out.println("KlickCount: " + count);
				}
			}
		});
		
		circle.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Type: " + e.getEventType().toString());
			};
		});

		Group root = new Group();
		root.getChildren().add(circle);
		Scene scene = new Scene(root, 300, 250);

		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
