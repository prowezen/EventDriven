package project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class EventManager {
    private HashMap<String, Set<Consumer<Object>>> listeners = new HashMap<>();

    // Subscribe to an event
    public void subscribe(String eventType, Consumer<Object> listener) {
        listeners.computeIfAbsent(eventType, k -> new HashSet<>()).add(listener);
    }

    // Notify all subscribers of an event
    public void notify(String eventType, Object data) {
        Set<Consumer<Object>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            for (Consumer<Object> listener : eventListeners) {
                listener.accept(data);
            }
        }
    }
}
