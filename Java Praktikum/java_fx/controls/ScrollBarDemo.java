// http://docs.oracle.com/javase/8/javafx/user-interface-tutorial/scrollbar.htm#BGBEGJDE
package java_fx.controls;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScrollBarDemo extends Application {

	final Image[] images = new Image[5];
	final ImageView[] pics = new ImageView[5];

	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, 180, 180);
		stage.setScene(scene);
		stage.setTitle("Scrollbar Demo");

		VBox vb = new VBox();
		vb.setLayoutX(5);
		vb.setSpacing(10);
		for (int i = 0; i < 5; i++) {
			images[i] = new Image(getClass().getResourceAsStream("/java_fx/resources/world.png"));
			pics[i] = new ImageView(images[i]);
			vb.getChildren().add(pics[i]);
		}

		ScrollBar scrollbar = new ScrollBar();
		scrollbar.setLayoutX(scene.getWidth() - scrollbar.getWidth()); // rechts in der VBox
		scrollbar.setMin(0);
		scrollbar.setMax(100);
		scrollbar.setOrientation(Orientation.VERTICAL);
		scrollbar.setPrefHeight(180);
		scrollbar.setMax(360);

		scrollbar.valueProperty()
				.addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
					vb.setLayoutY(-new_val.doubleValue());
				});

		root.getChildren().addAll(vb, scrollbar);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}