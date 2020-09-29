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

    Entry(Account account, TransactionManager.Transaction transaction, double amount, LocalDateTime time) throws IllegalArgumentException {
        if (account == null || transaction == null || time == null) {
            throw new IllegalArgumentException();
        }

        this.account = account;
        this.transaction = transaction;
        this.amount = amount;
        this.time = time;
    }

    LocalDateTime getTime() {
        return time;
    }

    Account getAccount() {
        return account;
    }

    double getAmount() {
        return amount;
    }

    TransactionManager.Transaction getTransaction() {
        return transaction;
    }
}
