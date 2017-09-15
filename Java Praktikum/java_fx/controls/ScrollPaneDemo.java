// http://docs.oracle.com/javase/8/javafx/user-interface-tutorial/scrollpane.htm#CBBFFBCH
package java_fx.controls;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScrollPaneDemo extends Application {

	final Image[] images = new Image[5];
	final ImageView[] pics = new ImageView[5];
	final String[] imageNames = new String[] { "fw1.jpg", "fw2.jpg", "fw3.jpg", "fw4.jpg", "fw5.jpg" };

	@Override
	public void start(Stage stage) {

		Label label = new Label();
		label.setLayoutX(30);
		label.setLayoutY(160);

		VBox vbox = new VBox();
		for (int i = 0; i < 5; i++) {
			images[i] = new Image(getClass().getResourceAsStream("/java_fx/resources/world.png"));
			pics[i] = new ImageView(images[i]);
			pics[i].setFitWidth(100);
			pics[i].setPreserveRatio(true);
			vbox.getChildren().add(pics[i]);
		}

		ScrollPane scrollpane = new ScrollPane();
		scrollpane.setVmax(440);
		scrollpane.setPrefSize(115, 150);
		scrollpane.setContent(vbox);
		scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollpane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		VBox.setVgrow(scrollpane, Priority.ALWAYS);
		scrollpane.vvalueProperty()
				.addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
					label.setText(imageNames[(new_val.intValue() - 1) / 100]);
				});

		VBox box = new VBox();
		Scene scene = new Scene(box, 180, 180);
		stage.setScene(scene);
		stage.setTitle("Scroll Pane");
		box.getChildren().addAll(scrollpane, label);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}