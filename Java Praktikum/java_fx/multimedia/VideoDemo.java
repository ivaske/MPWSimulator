package java_fx.multimedia;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class VideoDemo extends Application {

	private static final String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		// Create and set the Scene.
		Group root = new Group();
		Scene scene = new Scene(root, 540, 209);
		stage.setScene(scene);

		// Name and display the Stage.
		stage.setTitle("Hello Media");
		stage.show();

		// Create the media source.
		Media media = new Media(MEDIA_URL);

		// Create the player and set to play automatically.
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);

		// Create the view and add it to the Scene.
		MediaView mediaView = new MediaView(mediaPlayer);
		root.getChildren().add(mediaView);
	}

}
