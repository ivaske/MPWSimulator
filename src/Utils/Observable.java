package Utils;

import java.util.ArrayList;
import java.util.List;

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
