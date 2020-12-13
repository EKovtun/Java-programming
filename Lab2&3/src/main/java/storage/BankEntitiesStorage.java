package storage;

import java.util.Collection;

public interface BankEntitiesStorage<V> {
    <T extends V> void save(T entity);
    void saveAll(Collection<? extends V> entities);

    V findByKey(Object key);
    Collection<V> findAll();

    void deleteByKey(Object key);
    void deleteAll(Collection<? extends V> entities);
}
