// aus Epple: JavaFX 8
package java_fx.fxml_css;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorDemo extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Calculator.fxml"));
		Scene scene = new Scene(root);
		// scene.getStylesheets().add(getClass().getResource("calculator.css").toExternalForm());
		// in FXML-Datei angegeben
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
