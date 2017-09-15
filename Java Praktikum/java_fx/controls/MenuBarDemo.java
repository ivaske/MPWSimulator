// aus Epple: JavaFX 8
package java_fx.controls;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuBarDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("Datei");
		MenuItem openProjectMenuItem = new MenuItem("Projekt öffnen...");
		MenuItem quitMenuItem = new MenuItem("beenden");
		quitMenuItem.setOnAction(e -> Platform.exit());
		Menu subMenu = new Menu("Untermenu");
		subMenu.getItems().addAll(new MenuItem("Item 1"), new MenuItem("Item2"));
		fileMenu.getItems().addAll(openProjectMenuItem, subMenu, new SeparatorMenuItem(), quitMenuItem);
		Menu helpMenu = new Menu("Hilfe");
		menuBar.getMenus().addAll(fileMenu, helpMenu);
		root.setTop(menuBar);
		Scene scene = new Scene(root, 300, 250);
		primaryStage.setTitle("MenuBar Demo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
