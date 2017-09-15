package java_fx.controls;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ComboBoxWithNodesDemo extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {
		primaryStage.setTitle("");
		Group root = new Group();
		Scene scene = new Scene(root, 400, 300, Color.WHITE);

		GridPane gridpane = new GridPane();

		ComboBox<ImageView> cmb = new ComboBox<ImageView>();
		ImageView image1 = new ImageView(
				new Image(this.getClass().getResource("/java_fx/resources/chart_1.png").toString()));
		ImageView image2 = new ImageView(
				new Image(this.getClass().getResource("/java_fx/resources/chart_2.png").toString()));
		ImageView image3 = new ImageView(
				new Image(this.getClass().getResource("/java_fx/resources/chart_3.png").toString()));

		cmb.getItems().addAll(image1, image2, image3);

		Callback<ListView<ImageView>, ListCell<ImageView>> mcf = new Callback<ListView<ImageView>, ListCell<ImageView>>() {
			@Override
			public ListCell<ImageView> call(ListView<ImageView> p) {
				return new ListCell<ImageView>() {
					private final ImageView imgView;
					{
						this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						this.imgView = new ImageView();
					}

					@Override
					protected void updateItem(ImageView item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							this.setGraphic(null);
						} else {
							this.imgView.setImage(item.getImage());
							this.setGraphic(this.imgView);
						}
					}
				};
			}
		};
		cmb.setCellFactory(mcf);
		cmb.setButtonCell(mcf.call(null));

		gridpane.add(cmb, 2, 0);

		root.getChildren().add(gridpane);
		primaryStage.setScene(scene);

		primaryStage.show();
	}

}
