package View;

import Model.PlaceButtons;
import Model.PlacingItems;
import Utils.Observer;
import javafx.scene.control.RadioMenuItem;

/**
 * @author Yannick Vaske
 * @version 15.09.2017
 *
 * Diese Klasse erweitert die RadioMenuItem, sodass diese als Observer dienen können. Sobald nämlich einer umgeschaltet wird, informiert der Observeble alle anderen Buttons, sodass diese sich umschalten.
 */
public class ObservedRadioMenuItem extends RadioMenuItem implements Observer {
    private PlacingItems placingItems;
    private PlaceButtons buttons;

    public ObservedRadioMenuItem(PlacingItems placingItems, PlaceButtons typ, String text) {
        super(text);
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
