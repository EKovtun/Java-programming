package account;

import java.time.LocalDate;
import java.util.*;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
class Entries {
    private SortedMap<LocalDate, ArrayList<Entry>> entriesMap;

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

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        List<Entry> result = new ArrayList<>();
        SortedMap<LocalDate, ArrayList<Entry>> subMap;

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

    Entry last() {
        if (entriesMap.isEmpty()) return null;
        ArrayList<Entry> entriesList = entriesMap.get(entriesMap.lastKey());
        //entriesList.sort(Comparator.comparing(Entry::getTime));
        return entriesList.get(entriesList.size() - 1);
    }
}