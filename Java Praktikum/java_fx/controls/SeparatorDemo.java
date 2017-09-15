// aus Epple: JavaFX 8
package java_fx.controls;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SeparatorDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		final String[] names = new String[] { "March", "April", "May", "June", "July", "August" };
		final CheckBox[] cbs = new CheckBox[names.length];
		final Separator separator = new Separator();
		separator.setMaxWidth(40);
		separator.setHalignment(HPos.LEFT);
		final VBox vbox = new VBox();

		for (int i = 0; i < names.length; i++) {
			cbs[i] = new CheckBox(names[i]);
		}


		vbox.getChildren().addAll(cbs);
		vbox.setSpacing(5);
		vbox.getChildren().add(3, separator);

		Scene scene = new Scene(vbox, 400, 300);

		primaryStage.setTitle("Label-Demo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
