// aus Epple: JavaFX 8
package java_fx.controls;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SliderDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Slider slider = new Slider(0, 1, .5);
		Circle circle = new Circle(50, Color.RED);

		circle.setScaleX(slider.getValue());
		circle.setScaleY(slider.getValue());
		// beim Loslassen
//		slider.valueChangingProperty().addListener((observable, oldValue, newValue) -> {
//			circle.setScaleX(slider.getValue());
//			circle.setScaleY(slider.getValue());
//		});
		// kontinuierlich
		slider.valueProperty().addListener((observable, oldValue, newValue) -> {
			circle.setScaleX(newValue.doubleValue());
			circle.setScaleY(newValue.doubleValue());
		});
		slider.setOrientation(Orientation.VERTICAL);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(.1);
		slider.setMinorTickCount(4);
		slider.setSnapToTicks(true);
		slider.setLabelFormatter(new StringConverter<Double>() {
			@Override
			public String toString(Double value) {
				return String.format("%.2f", value);
			}

			@Override
			public Double fromString(String string) {
				throw new UnsupportedOperationException("Nicht implementiert.");
			}
		});
		StackPane root = new StackPane();
		root.getChildren().addAll(circle, slider);
		Scene scene = new Scene(root, 300, 250);
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
