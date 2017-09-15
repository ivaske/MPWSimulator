package java_fx.multimedia;

import javafx.application.Application;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PrintDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Button button = new Button("Druck mich!");
		button.setOnAction(e -> print(button, primaryStage));

		StackPane root = new StackPane();
		root.getChildren().add(button);

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void print(final Node node, Stage stage) {
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		if (printerJob != null)
			if (printerJob.showPrintDialog(stage.getOwner()))
				if (printerJob.printPage(node))
					printerJob.endJob();

	}

}
