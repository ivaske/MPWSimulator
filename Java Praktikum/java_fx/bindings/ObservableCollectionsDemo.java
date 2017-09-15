package java_fx.bindings;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class ObservableCollectionsDemo extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		ObservableList<String> ol = FXCollections.observableArrayList();
		ol.addAll("Eins", "Zwei");
		ol.add("Drei");
		ol.addListener((ListChangeListener.Change<? extends String> c) -> {
			while (c.next()) {
				if (c.wasAdded()) {
					List<? extends String> asl = c.getAddedSubList();
					asl.stream().forEach((s) -> {
						System.out.println("Neuer Inhalt " + s);
					});
				}
				if (c.wasRemoved()) {
					List<? extends String> removed = c.getRemoved();
					removed.stream().forEach((s) -> {
						System.out.println("Entfernter Inhalt " + s);
					});
				}
			}
		});

		ArrayList<String> l = new ArrayList<String>();
		Bindings.bindContent(l, ol);

		ol.remove("Zwei");
		ol.addAll("Fünf", "Sechs");
		ol.set(0, "Zehn");
		ol.remove("Fünf");
		ol.set(0, "Fünf");

		for (String s : l) {
			System.out.println(s);
		}

		Platform.exit();
	}

}
