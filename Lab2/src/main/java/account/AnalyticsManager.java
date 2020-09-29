package account;

import java.util.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) throws IllegalArgumentException {
        if (transactionManager == null) throw new IllegalArgumentException();
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) throws IllegalArgumentException {
        if (account == null) throw new IllegalArgumentException();
        // без стримов
        Map<Account, Integer> accountsCounters = new HashMap<>();
        int max = 0;
        Account maxAccount = null;

        for(var transaction : transactionManager.findAllTransactionsByAccount(account)) {
            Account beneficiary = transaction.getBeneficiary();
            if (!accountsCounters.containsKey(beneficiary)) {
                accountsCounters.put(beneficiary, 0);
            }
            Integer currentValue = accountsCounters.get(beneficiary);
            currentValue += 1;
            accountsCounters.put(beneficiary, currentValue);

            if (currentValue > max) {
                max = currentValue;
                maxAccount = beneficiary;
            }
        }

        return maxAccount;
    }

    public Collection<TransactionManager.Transaction> topTenExpensivePurchases(Account account) throws IllegalArgumentException {
        if (account == null) throw new IllegalArgumentException();
        // без стримов
        List<TransactionManager.Transaction> result = new ArrayList<>();
        LinkedList<TransactionManager.Transaction> transactions = new LinkedList<>(transactionManager.findAllTransactionsByAccount(account));
        transactions.sort(Comparator.comparing(TransactionManager.Transaction::getAmount));

        Iterator<TransactionManager.Transaction> iterator = transactions.descendingIterator();
        for(int i = 0; i < 10 && iterator.hasNext(); ++i) {
            var transaction = iterator.next();
            if (transaction.getOriginator() != account) continue;
            result.add(transaction);
        }

        return result;
    }
}
