package account;

import java.time.LocalDate;
import java.util.*;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
class Entries {
    private SortedMap<LocalDate, Collection<Entry>> entriesMap;

    Entries() {
        entriesMap = new TreeMap<>();
    }

    void addEntry(Entry entry) {
        if (entry == null) return;
        LocalDate entryDate = entry.getTime().toLocalDate();
        if (!entriesMap.containsKey(entryDate)) {
            entriesMap.put(entryDate, new ArrayList<>());
        }
        entriesMap.get(entryDate).add(entry);
    }

    Collection<Entry> from(LocalDate date) {
        if (date == null || !entriesMap.containsKey(date)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(entriesMap.get(date));
    }

    Collection<Entry> fromDate(LocalDate from) {
        if (from == null) return getAll();
        SortedMap<LocalDate, Collection<Entry>> subMap = entriesMap.tailMap(from);
        List<Entry> result = new ArrayList<>();
        for(var entry: subMap.entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }

    Collection<Entry> toDate(LocalDate to) {
        if (to == null) return getAll();
        SortedMap<LocalDate, Collection<Entry>> subMap = entriesMap.headMap(to);
        List<Entry> result = new ArrayList<>();
        for(var entry: subMap.entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }

    Collection<Entry> getAll() {
        List<Entry> result = new ArrayList<>();
        for(var entry: entriesMap.entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        if (from == null) return toDate(to);
        if (to == null) return fromDate(from);
        List<Entry> result = new ArrayList<>();
        SortedMap<LocalDate, Collection<Entry>> subMap = entriesMap.subMap(from, to);
        for(var entry: subMap.entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }

    Entry last() {
        if (entriesMap.isEmpty()) return null;
        ArrayList<Entry> entriesList = new ArrayList<>(entriesMap.get(entriesMap.lastKey()));
        return entriesList.get(entriesList.size() - 1);
    }
}