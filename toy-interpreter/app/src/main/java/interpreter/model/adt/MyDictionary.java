package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> map = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!map.containsKey(key))
            throw new ADTException("key '" + key + "' is not defined");
        return map.get(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    // TODO: update(key, value) - should throw if key doesn't exist
    // TODO: remove(key) - should throw if key doesn't exist

    @Override
    public Map<K, V> entries() {
        return Map.copyOf(map);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
