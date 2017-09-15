// aus Epple: JavaFX 8
package java_fx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TabPaneDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		TabPane tabPane = new TabPane();
		for (int i = 0; i < 20; i++) {
			Tab tab = new Tab("JavaFXDemo" + i + ".java");
			tab.setGraphic(new ImageView(new Image(getClass().getResource("/java_fx/resources/world.png").toString())));
			Text text = new Text("Dies ist der Inhalt von Dokument " + i);
			tab.setContent(new StackPane(text));
			tabPane.getTabs().add(tab);
		}
		// tabPane.setSide(Side.LEFT);
		// tabPane.setRotateGraphic(true);
		Tab tab = tabPane.getTabs().get(0);
		tabPane.getTabs().remove(0);
		tabPane.getTabs().add(4, tab);
		StackPane pane = new StackPane(tabPane);
		Scene scene = new Scene(pane, 300, 250);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
