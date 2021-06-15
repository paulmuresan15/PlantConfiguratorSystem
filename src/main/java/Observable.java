import java.util.ArrayList;
import java.util.List;

public class Observable {

    private List<Observer> observers = new ArrayList<Observer>();

    public void changeState(Object event, String id) {
        notifyAllObservers(event,id);
    }

    public void register(Observer observer) {
        observers.add(observer);
    }

    private void notifyAllObservers(Object event, String id) {
        for (Observer observer : observers) {
            observer.update(event,id);
        }
    }
}