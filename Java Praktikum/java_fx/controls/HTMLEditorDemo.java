// aus Epple: JavaFX 8
package java_fx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class HTMLEditorDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		HTMLEditor htmlEditor = new HTMLEditor();
		Button getText = new Button("Get Text");
		getText.setOnAction(e -> System.out.println(htmlEditor.getHtmlText()));
		VBox root = new VBox();
		root.getChildren().addAll(htmlEditor, getText);
		Scene scene = new Scene(root, 300, 250);
		primaryStage.setTitle("HTMLEditor Demo");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
