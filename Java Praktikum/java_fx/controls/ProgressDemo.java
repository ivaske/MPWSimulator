package java_fx.controls;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ProgressDemo extends Application {

	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Progress Controls");

		final Slider slider = new Slider();
		slider.setMin(0);
		slider.setMax(50);

		final ProgressBar progressbar = new ProgressBar(0);
		final ProgressIndicator progressindicator = new ProgressIndicator(0);

		slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
			progressbar.setProgress(new_val.doubleValue() / 50);
			progressindicator.setProgress(new_val.doubleValue() / 50);
		});

		final HBox hb = new HBox();
		hb.setSpacing(5);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(slider, progressbar, progressindicator);
		scene.setRoot(hb);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}