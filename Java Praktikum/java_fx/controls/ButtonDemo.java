package java_fx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ButtonDemo extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("Bitte klicken Sie mich!");
        button.setOnAction(e -> button.setText("Danke!"));
        Button defaultButton = new Button("Default");
        defaultButton.setOnAction(e -> System.out.println("Default clicked"));
        defaultButton.setDefaultButton(true); // VK_ENTER-Taste
        Button button3 = new Button("Hello World!");
		Image image = new Image(getClass().getResource("/java_fx/resources/world.png").toString());
		button3.setGraphic(new ImageView(image));
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> System.out.println("Canceled clicked"));
        cancelButton.setCancelButton(true); // VK_ESC-Taste
        
        FlowPane root = new FlowPane(button, defaultButton, button3, cancelButton);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Button-Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
