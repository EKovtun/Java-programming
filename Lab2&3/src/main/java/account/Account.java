package account;

import java.time.LocalDate;

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
}
