package account;

import java.time.LocalDate;
import java.util.Collection;

public class DebitCard implements Account {
    private final long id;
    private final BonusAccount bonusAccount;
    private final TransactionManager transactionManager;
    private final Entries entries = new Entries();

    public DebitCard(long id, BonusAccount bonusAccount, TransactionManager transactionManager) throws IllegalArgumentException {
        if (transactionManager == null) throw new IllegalArgumentException();
        this.id = id;
        this.bonusAccount = bonusAccount;
        this.transactionManager = transactionManager;
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
    public boolean withdraw(double amount, DebitCard beneficiary) {
        if (amount <= 0 || balanceOn(null) - amount < 0) return false;
        var mainTransaction = transactionManager.createTransaction(
                amount, this, beneficiary);
        if (bonusAccount != null && beneficiary != null) {
            var bonusTransaction = transactionManager.createTransaction(
                    amount * bonusAccount.getBonusPercentage() / 100, null, bonusAccount);
            return transactionManager.executeTransactions(mainTransaction, bonusTransaction);
        } else {
            return transactionManager.executeTransaction(mainTransaction);
        }
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
     * @param to to date. May be <code>null</code>
     * @return entry collection in the specified date range
     */
    public Collection<Entry> history(LocalDate from, LocalDate to) {
        return entries.betweenDates(from, to);
    }

    /**
     * Finds the last transaction of the account and rollbacks it
     * @return true if rollback is successful else false
     */
    public boolean rollbackLastTransaction() {
        final Entry lastEntry = entries.last();
        if (lastEntry == null) return false;
        return transactionManager.rollbackTransaction(lastEntry.getTransaction());
    }

    /**
     * Return all bonuses on the date
     * @param date to date. May be <code>null</code>
     * @return all bonuses on the specified date
     */
    public double getBonuses(LocalDate date) {
        if (bonusAccount != null) {
            return bonusAccount.balanceOn(date);
        } else {
            return 0d;
        }
    }

    @Override
    public double balanceOn(LocalDate date) {
        double result = 0;
        for(var entry : entries.betweenDates(null, date)) {
            result += entry.getAmount();
        }
        return result;
    }

    @Override
    public void addEntry(Entry entry) {
        entries.addEntry(entry);
    }
}