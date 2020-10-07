package storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SimpleEntitiesStorage<V> implements BankEntitiesStorage<V> {
    private final Map<Object, V> storage = new HashMap<>();
    private final KeyExtractor<?, ? super V> keyExtractor;

    public SimpleEntitiesStorage(KeyExtractor<?, ? super V> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    @Override
    public <T extends V> void save(T entity) {
        storage.put(keyExtractor.extract(entity), entity);
    }

    @Override
    public void saveAll(Collection<? extends V> entities) {
        for (V entity : entities) {
            save(entity);
        }
    }

    @Override
    public V findByKey(Object key) {
        return storage.get(key);
    }

    @Override
    public Collection<V> findAll() {
        return storage.values();
    }

    @Override
    public void deleteByKey(Object key) {
        storage.remove(key);
    }

    @Override
    public void deleteAll(Collection<? extends V> entities) {
        storage.values().removeAll(entities);
    }
}

