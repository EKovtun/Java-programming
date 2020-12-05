package storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SimpleEntitiesStorage<K, V> implements BankEntitiesStorage<K, V> {
    private final Map<K, V> storage = new HashMap<>();
    private final KeyExtractor<? super K, ? super V> keyExtractor;

    public SimpleEntitiesStorage(KeyExtractor<? super K, ? super V> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    @Override
    public <T extends V> void save(T entity) {
        if (entity == null) return;
        storage.put(keyExtractor.extract(entity), entity);
    }

    @Override
    public void saveAll(Collection<? extends V> entities) {
        if (entities == null) return;
        for (V entity : entities) {
            save(entity);
        }
    }

    @Override
    public V findByKey(K key) {
        return storage.get(key);
    }

    @Override
    public Collection<V> findAll() {
        return storage.values();
    }

    @Override
    public void deleteByKey(K key) {
        storage.remove(key);
    }

    @Override
    public void deleteAll(Collection<? extends V> entities) {
        storage.values().removeAll(entities);
    }
}

