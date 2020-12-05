package storage;

import java.util.Collection;

public interface BankEntitiesStorage<K, V> {
    <T extends V> void save(T entity);
    void saveAll(Collection<? extends V> entities);

    V findByKey(K key);
    Collection<V> findAll();

    void deleteByKey(K key);
    void deleteAll(Collection<? extends V> entities);
}
