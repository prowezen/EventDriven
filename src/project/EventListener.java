package project;

import java.util.ArrayList;
import java.util.List;

interface EventListener {
    void update(String eventType, Object data);
}

 class EventManager {
    private List<EventListener> listeners = new ArrayList<>();

    public void subscribe(EventListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(EventListener listener) {
        listeners.remove(listener);
    }

    public void notify(String eventType, Object data) {
        for (EventListener listener : listeners) {
            listener.update(eventType, data);
        }
    }
}