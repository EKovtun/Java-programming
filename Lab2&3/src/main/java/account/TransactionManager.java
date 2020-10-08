package account;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Manages all transactions within the application
 */
public class TransactionManager {
    public static class Transaction {
        private final long id;
        private final double amount;
        private final Account originator;
        private final Account beneficiary;
        private boolean executed;
        private boolean rolledBack;

        private Transaction(long id, double amount, Account originator, Account beneficiary) {
            this.id = id;
            this.amount = amount;
            this.originator = originator;
            this.beneficiary = beneficiary;
            this.executed = false;
            this.rolledBack = false;
        }

        /**
         * Adding entries to both accounts
         *
         * @throws IllegalStateException when was already executed
         */
        private Transaction execute() throws IllegalStateException {
            if (executed) throw new IllegalStateException();
            if (originator != null) {
                originator.addEntry(new Entry(originator,this, -amount, LocalDateTime.now()));
            }
            if (beneficiary != null) {
                beneficiary.addEntry(new Entry(beneficiary,this, amount, LocalDateTime.now()));
            }
            executed = true;
            return this;
        }

        /**
         * Removes all entries of current transaction from originator and beneficiary
         *
         * @throws IllegalStateException when was already rolled back
         */
        private Transaction rollback() throws IllegalStateException {
            if (!executed || rolledBack) throw new IllegalStateException();
            if (originator != null) {
                originator.addEntry(new Entry(originator,this, amount, LocalDateTime.now()));
            }
            if (beneficiary != null) {
                beneficiary.addEntry(new Entry(beneficiary,this, -amount, LocalDateTime.now()));
            }
            rolledBack = true;
            return this;
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

    private HashMap<Account, ArrayList<Transaction>> transactionsMap;
    private long nextTransactionId;

    public TransactionManager() {
        transactionsMap = new HashMap<>();
        nextTransactionId = 0;
    }

    public Transaction createTransaction(double amount, Account originator, Account beneficiary) {
        if (originator != null && !transactionsMap.containsKey(originator)) {
            transactionsMap.put(originator, new ArrayList<>());
        }
        if (beneficiary != null && !transactionsMap.containsKey(beneficiary)) {
            transactionsMap.put(beneficiary, new ArrayList<>());
        }
        return new Transaction(nextTransactionId++, amount, originator, beneficiary);
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        if (account == null || !transactionsMap.containsKey(account)) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(transactionsMap.get(account));
        }
    }

    public boolean rollbackTransaction(Transaction transaction) {
        if (transaction == null) return false;
        try {
            transaction.rollback();
        } catch (IllegalStateException e) {
            System.out.printf("Error rollback transaction '%d'%n", transaction.id);
            return false;
        }
        if (transaction.beneficiary != null) transactionsMap.get(transaction.beneficiary).remove(transaction);
        if (transaction.originator != null) transactionsMap.get(transaction.originator).remove(transaction);
        return true;
    }

    public boolean executeTransaction(Transaction transaction) {
        if (transaction == null) return false;
        try {
            transaction.execute();
        } catch (IllegalStateException e) {
            System.out.printf("Error execute transaction '%d'%n", transaction.id);
            return false;
        }
        if (transaction.beneficiary != null) transactionsMap.get(transaction.beneficiary).add(transaction);
        if (transaction.originator != null) transactionsMap.get(transaction.originator).add(transaction);
        return true;
    }

    public boolean executeTransactions(Transaction... transactions) {
        LinkedList<Transaction> executedTransactionsList = new LinkedList<>();
        for (Transaction transaction: transactions) {
            if (transaction == null) continue;
            if (!executeTransaction(transaction)) {
                for (Transaction executedTransaction : executedTransactionsList) {
                    rollbackTransaction(executedTransaction);
                }
            } else {
                executedTransactionsList.push(transaction);
            }
        }
        return true;
    }
}
