package account;

import java.time.LocalDate;
import java.util.Collection;

public interface Account {
    /**
     * Calculates balance on the accounting entries basis
     * @param date May be <code>null</code>
     * @return balance
     */
    double balanceOn(LocalDate date);

    /**
     * Add entry to entries
     * @param entry entry
     */
    void addEntry(Entry entry);

    /**
     * Return entries between date from and date to
     * @param from date from
     * @param to date to
     * @return entries
     */
    Collection<Entry> getEntries(LocalDate from, LocalDate to);

    Collection<Entry> getAllEntries();
}
