package event;

import java.util.EventListener;

public interface Observer extends EventListener {
    void updatePerformed(UpdateEvent e);

}
