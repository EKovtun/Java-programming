package account;

import java.time.LocalDateTime;

public class Transaction {
    private final long id;
    private final double amount;
    private final Account originator;
    private final Account beneficiary;
    private final boolean executed;
    private final boolean rolledBack;

    Transaction(long id, double amount, Account originator, Account beneficiary, boolean executed, boolean rolledBack) {
        this.id = id;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = executed;
        this.rolledBack = rolledBack;
    }

    /**
     * Adding entries to both accounts
     *
     * @throws IllegalStateException when was already executed
     */
    public Transaction execute() throws IllegalStateException {
        if (executed) throw new IllegalStateException();
        Transaction executedTransaction =
                new Transaction(this.getId() + 1, amount, originator, beneficiary, true, rolledBack);
        if (originator != null) {
            originator.addEntry(new Entry(originator, executedTransaction, -amount, LocalDateTime.now()));
        }
        if (beneficiary != null) {
            beneficiary.addEntry(new Entry(beneficiary, executedTransaction, amount, LocalDateTime.now()));
        }
        return executedTransaction;
    }

    /**
     * Removes all entries of current transaction from originator and beneficiary
     *
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() throws IllegalStateException {
        if (!executed || rolledBack) throw new IllegalStateException();
        Transaction rollbackedTransaction
                = new Transaction(this.getId() + 1, amount, originator, beneficiary, true, true);
        if (originator != null) {
            originator.addEntry(new Entry(originator, rollbackedTransaction, amount, LocalDateTime.now()));
        }
        if (beneficiary != null) {
            beneficiary.addEntry(new Entry(beneficiary, rollbackedTransaction, -amount, LocalDateTime.now()));
        }
        return rollbackedTransaction;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Account getOriginator() {
        return originator;
    }

    public Account getBeneficiary() {
        return beneficiary;
    }
}
