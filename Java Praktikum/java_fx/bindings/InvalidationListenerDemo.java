package java_fx.bindings;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;

public class InvalidationListenerDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		DoubleProperty number = new SimpleDoubleProperty(123);
		number.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				System.out.println("Die Property ist ungültig, neuer Wert: " + number.getValue());
				// System.out.println("Die Property ist ungültig");
			}
		});
		// Lambda-Kurzform
		number.addListener(observable -> {
			System.out.println("Die Property ist ungültig, neuer Wert: " + number.getValue());
			// System.out.println("Die Property ist ungültig");
		});
		number.setValue(90);
		// durch das "number.getValue()" im Listener wird das Binding wieder
		// scharf geschaltet; ohne getValue würde ein InvalidationListener die
		// nächste Änderung nicht mehr mit bekommen
		number.setValue(10);
		Platform.exit();
	}

}
