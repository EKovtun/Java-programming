package storage;

public interface KeyExtractor<V> {
    <T extends V> Object extract(T entity);
}
