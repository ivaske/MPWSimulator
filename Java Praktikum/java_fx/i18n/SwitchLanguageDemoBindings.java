package java_fx.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SwitchLanguageDemoBindings extends Application {

	public static void main(String[] args) throws Exception {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle(ResourcesController.getResourcesController().getValue("title"));
		Label label = new Label(ResourcesController.getResourcesController().getValue("hello"));
		TextField field = new TextField();
		field.setOnAction(e -> Utils.setLocale(new Locale(field.getText())));
		VBox root = new VBox();
		root.getChildren().addAll(label, field);
		Scene scene = new Scene(root, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.titleProperty()
				.bind(Bindings.createStringBinding(() -> Utils.i18n("title"), Utils.localeProperty()));
		label.textProperty().bind(Bindings.createStringBinding(() -> Utils.i18n("hello"), Utils.localeProperty()));
	}

}

class Utils {
	private static final ObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.getDefault());

	public static ObjectProperty<Locale> localeProperty() {
		return locale;
	}

	public static Locale getLocale() {
		return locale.get();
	}

	public static void setLocale(Locale locale) {
		localeProperty().set(locale);
	}

	public static String i18n(String key) {
		return ResourceBundle.getBundle("java_fx.resources.text", getLocale()).getString(key);
	}
}
