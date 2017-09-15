// aus Epple: JavaFX 8
package java_fx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ContextMenuDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("Datei");
		MenuItem openProjectMenuItem = new MenuItem("Projekt öffnen...");
		fileMenu.getItems().add(openProjectMenuItem);
		Menu helpMenu = new Menu("Hilfe");
		menuBar.getMenus().addAll(fileMenu, helpMenu);
		root.setTop(menuBar);
		Label cLabel = new Label("Mit ContextMenu");
		root.setCenter(cLabel);
		final ContextMenu contextMenu = new ContextMenu();
		MenuItem halloMenuItem = new MenuItem("Sag 'Hallo'");
		halloMenuItem.setOnAction(e -> System.out.println("Duke: 'Hallo!'"));
		contextMenu.getItems().add(halloMenuItem);
		cLabel.setOnContextMenuRequested(e -> {
			contextMenu.show(cLabel, e.getScreenX(), e.getScreenY());
		});
		Scene scene = new Scene(root, 300, 250);
		primaryStage.setTitle("ContextMenu Demo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
