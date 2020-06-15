package event;

import java.util.EventObject;

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
