package java_fx.bindings;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChangeListenerGUIDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		MyValue myBean = new MyValue();

		VBox root = new VBox();
		Button button = new Button("Zufallswert");
		button.setOnAction(e -> myBean.setValue(Math.random()));
		Label label = new Label("");
		root.getChildren().addAll(button, label);

		// Lambda-Notation
		//myBean.valueProperty().addListener((obs, o, n) -> label.setText(n.toString()));
//		myBean.valueProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				label.setText(newValue.toString());
//			}
//		});
		
		// oder via Binding:
		label.textProperty().bind(myBean.valueProperty().asString());

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setTitle("ChangeListenerGUIDemo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}

class MyValue {

	private final DoubleProperty value = new SimpleDoubleProperty(this, "value", 0);

	public Double getValue() {
		return value.get();
	}

	public void setValue(Double val) {
		value.set(val);
	}

	public DoubleProperty valueProperty() {
		return value;
	}
}
