// https://gist.github.com/skrb/1c62b77ef7ddb3c7adf4

package java_fx.animation;


import java.net.URISyntaxException;
import java.util.stream.IntStream;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PageFlipper extends Application {
    private static final double WIDTH = 1024;
    private static final double HEIGHT = 680;
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();

        IntStream.range(1, 4)
                 .forEach(n -> {
                     try {
                         ImageView image = new ImageView(new Image(getClass().getResource("/java_fx/resources/IMG_" + n + ".jpg").toURI().toString()));
                         root.getChildren().add(image);
                     } catch (URISyntaxException ex) {
                         ex.printStackTrace();
                     }
                 });

        FlipTransition transition = new FlipTransition(WIDTH, HEIGHT);
        
        root.setOnMousePressed(e -> {
            transition.play(root);
        });
        
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}
