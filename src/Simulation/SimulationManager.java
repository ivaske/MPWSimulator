package Simulation;

import Model.IO;
import Model.Landschaft;
import Utils.Observable;

public class SimulationManager extends Observable {

    private Landschaft _landschaft;
    private int _simSpeed;
    private SimulationThread _simulation;
    private SimulationButton _button;
    private volatile SimulationState _state;


    public SimulationManager(Landschaft landschaft) {
        _landschaft = landschaft;
        _state = SimulationState.STOPED;

    }

    public void startSimulation() {
        _button = SimulationButton.START;
        notifyObserver();

        if (_state == SimulationState.PAUSED) {
            resumeSimulation();
        } else if (_state == SimulationState.STOPED) {
            _simulation = new SimulationThread(_landschaft, this);
            _simulation.setDaemon(true);
            _state = SimulationState.RUNNING;
            _simulation.start();
        }
    }

    public void resumeSimulation() {
        _state = SimulationState.RUNNING;

    }

    public void stopSimulation() {
        _button = SimulationButton.STOP;
        notifyObserver();

        _state = SimulationState.STOPED;
        _simulation.interrupt();
    }

    public void pauseSimulation() {
        _button = SimulationButton.PAUSE;
        notifyObserver();

        _state = SimulationState.PAUSED;
    }

    public int get_simSpeed() {
        return _simSpeed;
    }

    public void set_simSpeed(int _simSpeed) {
        this._simSpeed = _simSpeed;
        IO.println(_simSpeed);
    }

    public SimulationState get_state() {
        return _state;
    }

    public SimulationButton get_button() {
        return _button;
    }
}
