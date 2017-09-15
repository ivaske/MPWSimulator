package java_fx.i18n;

/* Anmerkungen:
 * Nicht nachträglich ändern kann man bspw.
 * - FileChooser
 * - DialogButtons (bspw. ButtonType.CANCEL)
 */

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SwitchLanguageDemo extends Application {

	public static void main(String[] args) throws Exception {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Scene scene = this.initStage(primaryStage);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Scene initStage(Stage stage) {
		stage.setTitle(ResourcesController.getResourcesController().getValue("hello"));
		Label label = new Label(ResourcesController.getResourcesController().getValue("hello"));
		Button button = new Button(ResourcesController.getResourcesController().getValue("click"));
		button.setOnAction(e -> this.switchLanguage(stage));
		VBox root = new VBox();
		root.getChildren().addAll(label, button);
		Scene scene = null;
		if (stage.getScene() != null) {
			scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
		} else {
			scene = new Scene(root, 300, 250);
		}
		return scene;
	}

	private void switchLanguage(Stage stage) {
		if (ResourcesController.getResourcesController().getLocale().getLanguage().equals("de")) {
			ResourcesController.getResourcesController().setLocale(Locale.ENGLISH);
		} else {
			ResourcesController.getResourcesController().setLocale(Locale.GERMAN);
		}
		Scene scene = this.initStage(stage);
		stage.setScene(scene);
	}

}


class ResourcesController {

	private static ResourcesController resourcesController = null;

	public static ResourcesController getResourcesController() {
		if (ResourcesController.resourcesController == null) {
			ResourcesController.resourcesController = new ResourcesController();
		}
		return ResourcesController.resourcesController;
	}

	private Locale locale;
	private ResourceBundle bundle;

	private ResourcesController() {
		setLocale(Locale.getDefault());
	}

	public String getValue(String key) {
		return this.bundle.getString(key);
	}

	public Locale getLocale() {
		return this.locale;
	}

	public void setLocale(Locale loc) {
		this.locale = loc;
		Locale.setDefault(this.locale);
		this.bundle = ResourceBundle.getBundle("java_fx.resources.text", this.locale);
	}

}

