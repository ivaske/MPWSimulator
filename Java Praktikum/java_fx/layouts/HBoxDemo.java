// aus Epple: JavaFX 8
package java_fx.layouts;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class HBoxDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10, 20, 30, 40));
		hbox.setSpacing(50);
		Button claimExcessSpace = new Button("lass mich wachsen!");
		claimExcessSpace.setMaxWidth(300);
		HBox.setHgrow(claimExcessSpace, Priority.ALWAYS);
		Button staySmall = new Button("lass mich in Ruhe!");
		staySmall.setPrefSize(200, 200);
		staySmall.setMaxWidth(1000);
		HBox.setHgrow(staySmall, Priority.NEVER);
		hbox.getChildren().addAll(claimExcessSpace, staySmall);
		primaryStage.setScene(new Scene(hbox));
		primaryStage.show();
	}

}
