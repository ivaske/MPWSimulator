// aus Epple: JavaFX 8
package java_fx.layouts;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BorderPaneDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane borderPane = new BorderPane();
		final Label toolbar = new Label("toolbar");
		borderPane.setTop(toolbar);
		final Label taskbar = new Label("taskbar");
		borderPane.setBottom(taskbar);
		final Label document = new Label("document");
		borderPane.setCenter(document);
		final Label navigator = new Label("navigator");
		borderPane.setLeft(navigator);
		final Label properties = new Label("properties");
		borderPane.setRight(properties);
		properties.setPrefHeight(500); // wird ignoriert
		BorderPane.setAlignment(toolbar, Pos.CENTER);
		BorderPane.setAlignment(taskbar, Pos.CENTER);
		BorderPane.setAlignment(document, Pos.CENTER);
		BorderPane.setAlignment(navigator, Pos.CENTER);
		BorderPane.setAlignment(properties, Pos.CENTER);

		primaryStage.setScene(new Scene(borderPane, 300, 200));

		primaryStage.show();
	}

}
