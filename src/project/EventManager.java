package project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class EventManager {
    // A map to hold sets of listeners for different event types
    private HashMap<String, Set<Consumer<Object>>> listeners = new HashMap<>();

    // Subscribe to an event
    public void subscribe(String eventType, Consumer<Object> listener) {
        // Add the listener to the set of listeners for the given event type
        listeners.computeIfAbsent(eventType, k -> new HashSet<>()).add(listener);
    }

    // Notify all subscribers of an event
    public void notify(String eventType, Object data) {
        // Get the set of listeners for the given event type
        Set<Consumer<Object>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            // Notify each listener by passing the event data to its accept method
            for (Consumer<Object> listener : eventListeners) {
                listener.accept(data);
            }
        }
    }
}