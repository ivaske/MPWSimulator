package View;

import Model.PlaceButtons;
import Model.PlacingItems;
import Utils.Observer;
import javafx.scene.control.ToggleButton;

/**
 * @author Yannick Vaske
 * @version 15.09.2017
 *
 * Diese Klasse erweitert die ToggleButtons, sodass diese als Observer dienen können. Sobald nämlich einer umgeschaltet wird, informiert der Observeble alle anderen Buttons, sodass diese sich umschalten.
 */
public class ObservedToggleButton extends ToggleButton implements Observer {
    private PlacingItems placingItems;
    private PlaceButtons buttons;

    public ObservedToggleButton(PlacingItems placingItems, PlaceButtons typ) {
        this.placingItems = placingItems;
        this.buttons = typ;
        placingItems.addObserver(this);

    }

    @Override
    public void update() {
        super.setSelected(buttons == placingItems.get_isSelected());
    }

    public PlaceButtons getButtons() {
        return buttons;
    }
}
