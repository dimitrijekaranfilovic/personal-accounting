package event;

import entities.Publisher;

import java.util.EventObject;

/**
 * Class that represents an event which {@link entities.Publisher} sends
 * its {@link event.Observer} when {@link Publisher#notifyObservers()} is
 * called.
 * */

public class UpdateEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public UpdateEvent(Object source) {
        super(source);
    }
}