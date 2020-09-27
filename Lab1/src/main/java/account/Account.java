package account;

import java.time.LocalDate;
import java.util.Collection;

public class Account {
    private final long id;
    private final TransactionManager transactionManager;
    private final Entries entries;

    public Account(long id) {
        this.id = id;
        this.transactionManager = new TransactionManager();
        this.entries = new Entries();
    }

    /**
     * Adds cash money to account. <b>Should use account.TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    public boolean addCash(double amount) {
        if (amount <= 0) return false;
        var transaction = transactionManager.createTransaction(amount, null, this);
        return transactionManager.executeTransaction(transaction);
    }

    /**
     * Withdraws money from account and added money to other account. <b>Should use account.TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @param beneficiary account-beneficiary
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdraw(double amount, Account beneficiary) {
        if (amount <= 0 || balanceOn(null) - amount < 0) return false;
        var transaction = transactionManager.createTransaction(amount, this, beneficiary);
        return transactionManager.executeTransaction(transaction);
    }

    /**
     * Withdraws cash money from account. <b>Should use account.TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdrawCash(double amount) {
        return withdraw(amount, null);
    }


    /**
     * Get entries history
     *
     * @param from from date, can be null
     * @param to to date, can be null
     * @return entry collection in the specified date range
     */
    public Collection<Entry> history(LocalDate from, LocalDate to) {
        return entries.betweenDates(from, to);
    }

    /**
     * Calculates balance on the accounting entries basis
     * @param date can be null
     * @return balance
     */
    public double balanceOn(LocalDate date) {
        double result = 0;
        for(var entry : entries.betweenDates(null, date)) {
            result += entry.getAmount();
        }
        return result;
    }

    /**
     * Finds the last transaction of the account and rollbacks it
     * @return true if rollback is successful else false
     */
    public boolean rollbackLastTransaction() {
        return transactionManager.rollbackTransaction(entries.last().getTransaction());
    }

    /**
     * Add entry to entries
     * @param entry
     */
    void addEntry(Entry entry) {
        entries.addEntry(entry);
    }
}