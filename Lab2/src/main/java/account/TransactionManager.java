package account;

import java.util.*;

/**
 * Manages all transactions within the application
 */
class TransactionManager {

    private final Map<Account, Collection<Transaction>> transactionsMap;
    private long nextTransactionId;

    TransactionManager() {
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
        return new Transaction(nextTransactionId++, amount, originator, beneficiary, false, false);
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        if (account == null || !transactionsMap.containsKey(account))
            return Collections.emptyList();
        return new ArrayList<>(transactionsMap.get(account));
    }

    public boolean rollbackTransaction(Transaction transaction) {
        if (transaction == null) return false;
        Transaction rollbackedTransaction;
        try {
            rollbackedTransaction = transaction.rollback();
        } catch (IllegalStateException e) {
            System.out.printf("Error rollback transaction '%d'%n", transaction.getId());
            return false;
        }
        if (transaction.getBeneficiary() != null) transactionsMap.get(transaction.getBeneficiary()).add(rollbackedTransaction);
        if (transaction.getOriginator() != null) transactionsMap.get(transaction.getOriginator()).add(rollbackedTransaction);
        return true;
    }

    public boolean executeTransaction(Transaction transaction) {
        if (transaction == null) return false;
        Transaction executedTransaction;
        try {
            executedTransaction = transaction.execute();
        } catch (IllegalStateException e) {
            System.out.printf("Error execute transaction '%d'%n", transaction.getId());
            return false;
        }
        if (transaction.getBeneficiary() != null) transactionsMap.get(transaction.getBeneficiary()).add(executedTransaction);
        if (transaction.getOriginator() != null) transactionsMap.get(transaction.getOriginator()).add(executedTransaction);
        return true;
    }
}
