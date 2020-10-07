package storage;

public interface KeyExtractor<K, V> {
    <T extends V> K extract(T entity);
}
