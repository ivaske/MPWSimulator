package java_fx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ToggleButtonDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		final ToggleGroup group = new ToggleGroup();

		ToggleButton tb1 = new ToggleButton("Minor");
		tb1.setToggleGroup(group);
		tb1.setSelected(true);

		ToggleButton tb2 = new ToggleButton("Major");
		tb2.setToggleGroup(group);

		ToggleButton tb3 = new ToggleButton("Critical");
		tb3.setToggleGroup(group);

		tb1.setUserData(Color.LIGHTGREEN);
		tb2.setUserData(Color.LIGHTBLUE);
		tb3.setUserData(Color.SALMON);

		Rectangle rect = new Rectangle();
		rect.setHeight(50);
		rect.setFill(Color.WHITE);
		rect.setStroke(Color.DARKGRAY);
		rect.setStrokeWidth(2);
		rect.setArcHeight(10);
		rect.setArcWidth(10);

		group.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> {
			if (newToggle == null)
				rect.setFill(Color.WHITE);
			else
				rect.setFill((Color) group.getSelectedToggle().getUserData());
		});
		rect.setWidth(100);

		FlowPane root = new FlowPane(tb1, tb2, tb3, rect);

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setTitle("Button-Demo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
