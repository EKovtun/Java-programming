package account;

import java.time.LocalDate;
import java.util.*;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
class Entries {
    SortedMap<LocalDate, LinkedList<Entry>> entriesMap;

    Entries() {
        entriesMap = new TreeMap<>();
    }

    public void addEntry(Entry entry) {
        if (entry == null) return;
        LocalDate entryDate = entry.getTime().toLocalDate();
        if (!entriesMap.containsKey(entryDate)) {
            entriesMap.put(entryDate, new LinkedList<>());
        }
        entriesMap.get(entryDate).add(entry);
    }

    public Collection<Entry> from(LocalDate date) {
        if (date == null || !entriesMap.containsKey(date)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(entriesMap.get(date));
    }

    public Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        List<Entry> result = new ArrayList<>();
        SortedMap<LocalDate, LinkedList<Entry>> subMap;

        if (from == null) {
            if (to == null) {
                subMap = entriesMap;
            } else {
                subMap = entriesMap.headMap(to);
            }
        } else if (to == null) {
            subMap = entriesMap.tailMap(from);
        } else {
            subMap = entriesMap.subMap(from, to);
        }

        for(var entry: subMap.entrySet()) {
            result.addAll(entry.getValue());
        }

        return result;
    }

    public Entry last() {
        if (entriesMap.isEmpty()) return null;
        LinkedList<Entry> entries = entriesMap.get(entriesMap.lastKey());
        entries.sort(Comparator.comparing(Entry::getTime));
        return entries.getLast();
    }
}