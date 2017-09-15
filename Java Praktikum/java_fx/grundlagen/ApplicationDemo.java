package java_fx.grundlagen;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ApplicationDemo extends Application {

	public static void main(String[] args) {
		launch(args);
		System.out.println("main -> Thread: " + Thread.currentThread().getName());
	}

	@Override
	public void init() {
		Parameters p = getParameters();
		List<String> list = p.getRaw();
		System.out.println("Anzahl an Parametern = " + list.size());
		for (String string : list) {
			System.out.println("Parameter: " + string);
		}
		System.out.println("init -> Thread: " + Thread.currentThread().getName());
	}

	@Override
	public void start(Stage stage) {
		System.out.println("start -> Thread: " + Thread.currentThread().getName());
		Button button = new Button("quit");
		button.setOnAction(e -> Platform.exit());
		StackPane root = new StackPane();
		root.getChildren().add(button);

		Scene scene = new Scene(root, 300, 250);

		stage.setTitle("Application Demo");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void stop() {
		System.out.println("stop -> Thread: " + Thread.currentThread().getName());
		System.out.println("tschuess");
	}
}