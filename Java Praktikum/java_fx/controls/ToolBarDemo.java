package java_fx.controls;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ToolBarDemo extends Application {

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
		
		BorderPane contentPane = new BorderPane();
		ToolBar toolBar = new ToolBar(new Button("New"), new Button("Open"), new Button("Save"), new Separator(),
				new Button("Clean"), new Button("Compile"), new Button("Run"), new Separator(), new Button("Debug"),
				new Slider());
		contentPane.setTop(toolBar);
		Label document = new Label("document");
		contentPane.setCenter(document);
		Label message = new Label("Herzlich willkommen");
		contentPane.setBottom(message);
		BorderPane.setAlignment(toolBar, Pos.CENTER_LEFT);
		BorderPane.setAlignment(document, Pos.CENTER);
		BorderPane.setAlignment(message, Pos.CENTER_LEFT);
		root.setCenter(contentPane);
		BorderPane.setAlignment(root, Pos.CENTER);

		primaryStage.setScene(new Scene(root, 400, 600));

		primaryStage.show();
	}

}
