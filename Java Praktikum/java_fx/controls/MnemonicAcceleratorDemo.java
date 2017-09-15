package java_fx.controls;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MnemonicAcceleratorDemo extends Application {
	@Override
	public void start(Stage stage) {
		// Mnemonics mit '_' versehen; werden mit ALT sichtbar
		Menu fileMenu = new Menu("_File");
		MenuItem newFileMenuItem = new MenuItem("_New...");
		// Demonstrates the use of accelerators and mnemonics on menus in a
		// JavaFX application. Note how in the example, it uses
		// KeyCombination.keyCombination("SHORTCUT+N") to specify the
		// accelerator shortcut. This is an OS independent way of specifying a
		// standard OS shortcut key in JavaFX. In Windows, SHORTCUT will map to
		// CTRL. In OS X, SHORTCUT will map to the OS X COMMAND key.
		newFileMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+N"));
		newFileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Something new, this way comes");
			}
		});
		fileMenu.getItems().add(newFileMenuItem);

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().setAll(fileMenu);

		VBox layout = new VBox(menuBar);
		layout.setPrefSize(200, 100);

		stage.setScene(new Scene(layout));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}