// aus Epple: JavaFX 8
package java_fx.controls;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuItemsDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("Datei");

		MenuItem openProjectMenuItem = new MenuItem("Projekt öffnen...");
		openProjectMenuItem.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.showOpenDialog(primaryStage);
		});
		openProjectMenuItem.setAccelerator(KeyCombination.valueOf("SHORTCUT+O"));
		openProjectMenuItem.setGraphic(
				new ImageView(new Image(getClass().getResource("/java_fx/resources/world.png").toExternalForm())));
		fileMenu.getItems().add(openProjectMenuItem);

		menuBar.getMenus().addAll(fileMenu, new Menu("Hilfe"));
		root.setTop(menuBar);

		ImageView imageView = new ImageView(
				new Image(getClass().getResource("/java_fx/resources/world.png").toExternalForm()));
		root.setCenter(imageView);

		fileMenu.getItems().add(new SeparatorMenuItem());
		CheckMenuItem showWorldMenuItem = new CheckMenuItem("Welt anzeigen");
		showWorldMenuItem.setSelected(true);
		fileMenu.getItems().add(showWorldMenuItem);
		imageView.visibleProperty().bind(showWorldMenuItem.selectedProperty());
		fileMenu.getItems().add(new SeparatorMenuItem());

		ToggleGroup toggleGroup = new ToggleGroup();
		RadioMenuItem smallWorldMenuItem = new RadioMenuItem("Kleine Welt");
		smallWorldMenuItem.setToggleGroup(toggleGroup);
		fileMenu.getItems().add(smallWorldMenuItem);
		RadioMenuItem largeWorldMenuItem = new RadioMenuItem("Große Welt");
		largeWorldMenuItem.setToggleGroup(toggleGroup);
		fileMenu.getItems().add(largeWorldMenuItem);
		fileMenu.getItems().add(new SeparatorMenuItem());

		DoubleBinding scaleBinding = Bindings.createDoubleBinding(() -> smallWorldMenuItem.isSelected() ? 0.5 : 1.0,
				smallWorldMenuItem.selectedProperty());
		imageView.scaleXProperty().bind(scaleBinding);
		imageView.scaleYProperty().bind(scaleBinding);

		Slider fadeSlider = new Slider(0, 1, 1);
		imageView.opacityProperty().bind(fadeSlider.valueProperty());

		CustomMenuItem customMenuItem = new CustomMenuItem(fadeSlider, false);
		fileMenu.getItems().add(customMenuItem);

		Scene scene = new Scene(root, 600, 400);
		primaryStage.setTitle("MenuItems Demo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
