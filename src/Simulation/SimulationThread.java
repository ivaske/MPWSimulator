package Simulation;

import Model.IO;
import Model.Landschaft;
import Utils.Observer;
import javafx.application.Platform;

public class SimulationThread extends Thread implements Observer {

    private Landschaft _landschaft;
    private SimulationManager _manager;

    public SimulationThread(Landschaft landschaft, SimulationManager manager) {
        _landschaft = landschaft;
        _manager = manager;
    }


    @Override
    public void run() {
        try {
            _landschaft.addObserver(this);
            _landschaft.get_panzer().main(); //Main-Methode des Panzers aufrufen
        } catch (RuntimeException ex) {

        } finally {
            _landschaft.deleteObserver(this);
            _manager.stopSimulation();
        }
    }

    @Override
    public void update() {
        if (currentThread().getClass() == SimulationThread.class) {

            try {
                sleep(_manager.get_simSpeed() * 15 + 50);

                while (_manager.get_state() == SimulationState.PAUSED) {
                    sleep(100);
                }

            } catch (InterruptedException ex) {
                interrupt();

            }
            if (_manager.get_state() == SimulationState.STOPED) {
                throw new RuntimeException("Simulation has stopped.");
            }
        }
    }

}
