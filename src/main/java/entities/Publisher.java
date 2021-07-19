package entities;

import event.Observer;

/**
 * Class that is used when implementing the Observer design pattern.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */

public interface Publisher {
    /**
     * Function that adds observer in publisher's observer list.
     * @param observer observer to be added
     * */
    void addObserver(Observer observer);
    /**
     * Function that removes observer from publisher's list of observers.
     * @param observer observer to be removed
     * */
    void removeObserver(Observer observer);

    /**
     * Function that notifies observers that an event has occurred.
     * */
    void notifyObservers();
}
