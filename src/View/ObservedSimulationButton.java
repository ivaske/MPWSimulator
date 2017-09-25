package View;

import Model.PlaceButtons;
import Model.PlacingItems;
import Simulation.SimulationButton;
import Simulation.SimulationManager;
import Simulation.SimulationState;
import Utils.Observer;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

/**
 * @author Yannick Vaske
 * @version 15.09.2017
 * <p>
 * Diese Klasse erweitert die ToggleButtons, sodass diese als Observer dienen können. Sobald nämlich einer umgeschaltet wird, informiert der Observeble alle anderen Buttons, sodass diese sich umschalten.
 */
public class ObservedSimulationButton extends Button implements Observer {
    private SimulationButton _button;
    private SimulationManager _manager;

    public ObservedSimulationButton(SimulationButton button, SimulationManager manager) {
        this._button = button;
        this._manager = manager;
        _manager.addObserver(this);

    }

    @Override
    public void update() {
        SimulationButton button = _manager.get_button();

        if (button == this._button) {
            super.setDisable(true);
        }

        switch (this._button) {
            case START:
                if (button == SimulationButton.STOP || button == SimulationButton.PAUSE) {
                    super.setDisable(false);
                }
                break;
            case STOP:
                if (button == SimulationButton.START || button == SimulationButton.PAUSE) {
                    super.setDisable(false);
                }

            case PAUSE:
                if (button == SimulationButton.START) {
                    super.setDisable(false);
                } else if (button == SimulationButton.STOP) {
                    super.setDisable(true);
                }
        }

    }

    public SimulationButton get_button() {
        return _button;
    }
}
