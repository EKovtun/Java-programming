package storage;

public interface KeyExtractor<K, V> {
    <T1 extends K, T2 extends V> T1 extract(T2 entity);
}
