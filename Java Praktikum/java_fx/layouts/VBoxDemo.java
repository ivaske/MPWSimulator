// aus Epple: JavaFX 8
package java_fx.layouts;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VBoxDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10, 20, 30, 40));
		vbox.setSpacing(50);
		Button claimExcessSpace = new Button("lass mich wachsen!");
		claimExcessSpace.setMaxHeight(300);
		VBox.setVgrow(claimExcessSpace, Priority.ALWAYS);
		Button staySmall = new Button("lass mich (zunaechst) in Ruhe!");
		staySmall.setPrefSize(200, 200);
		staySmall.setMaxHeight(1000);
		VBox.setVgrow(staySmall, Priority.SOMETIMES);
		vbox.getChildren().addAll(claimExcessSpace, staySmall);
		primaryStage.setScene(new Scene(vbox));
		primaryStage.show();
	}

}
