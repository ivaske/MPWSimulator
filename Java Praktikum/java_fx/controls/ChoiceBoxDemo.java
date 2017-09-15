// aus Epple: JavaFX 8
package java_fx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ChoiceBoxDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		ChoiceBox<Person> cb = new ChoiceBox<Person>();
		cb.getItems().add(new Person("Manuel", "Neuer", 28));
		cb.getItems().add(new Person("Philipp", "Lahm", 30));
		cb.getItems().add(new Person("Mats", "Hummels", 25));
		// ...
		cb.setConverter(new StringConverter<Person>() {
			@Override
			public String toString(Person p) {
				return p.getFirstName() + " " + p.getLastName() + " (" + p.getAge() + ")";
			}

			@Override
			public Person fromString(String string) {
				// muss fuer diesen Anwendungsfall nicht implementiert sein
				throw new UnsupportedOperationException("Not" + "supported yet.");
			}
		});

		cb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue.getLastName());
		});

		StackPane pane = new StackPane(cb);
		Scene scene = new Scene(pane, 300, 250);

		primaryStage.setScene(scene);

		primaryStage.show();
	}

}

class Person {
	String lastName;
	String firstName;
	int age;

	public Person(String firstName, String lastName, int age) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.age = age;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getAge() {
		return age;
	}
}
