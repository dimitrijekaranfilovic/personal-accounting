package event;

import java.util.EventListener;

/**
 * Class that represents an observer.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */

public interface Observer extends EventListener {


    /**
     * Function that is called in every observer after publisher calls the notifyObservers function.
     * @param e UpdateEvent which specifies the event source(class that implements the Publisher interface and has called the notifyObservers function)/
     * */
    void updatePerformed(UpdateEvent e);

}
