package account;

import java.time.LocalDateTime;

/**
 * The record of allocating the amount to the account
 * Amount can be either positive or negative depending on originator or beneficiary
 */
class Entry {
    private final Account account;
    private final TransactionManager.Transaction transaction;
    private final double amount;
    private final LocalDateTime time;

    public Entry(Account account, TransactionManager.Transaction transaction, double amount, LocalDateTime time) throws IllegalArgumentException {
        if (account == null || transaction == null || time == null) {
            throw new IllegalArgumentException();
        }

        this.account = account;
        this.transaction = transaction;
        this.amount = amount;
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Account getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    TransactionManager.Transaction getTransaction() {
        return transaction;
    }
}
