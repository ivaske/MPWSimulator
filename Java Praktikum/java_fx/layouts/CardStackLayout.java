// aus Epple: JavaFX 8
package java_fx.layouts;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class CardStackLayout extends Pane {

	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		ObservableList<Node> children = getChildren();
		int i = 0;
		for (Node child : children) {
			if (child.isManaged()) {
				child.relocate(i, 0);
				i += 10;
			} 
		}
	}
}
