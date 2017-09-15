package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yannick Vaske
 * @version 15.09.2017
 * @see Observer
 *
 * Eine eigene kleine Implementation der Observable Klasse, damit nicht ständig setChanged extra aufgerufen wird.
 */
public class Observable {

    private List<Observer> _listObservables;

    public Observable() {
        this._listObservables = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        if (!_listObservables.contains(observer)) {
            _listObservables.add(observer);
        }
    }

    public void deleteObserver(Observer observer) {
        if (_listObservables.contains(observer)) {
            _listObservables.remove(observer);
        }
    }

    public void notifyObserver() {
        for (Observer observer : _listObservables) {
            observer.update();
        }
    }

}
