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
            accountsCounters.putIfAbsent(beneficiary, 0);
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

    public Collection<Transaction> topTenExpensivePurchases(Account account) throws IllegalArgumentException {
        if (account == null) throw new IllegalArgumentException();
        // без стримов
        List<Transaction> result = new ArrayList<>();
        SortedSet<Transaction> transactions =
                new TreeSet<>(Comparator.comparing(Transaction::getAmount)).descendingSet();
        transactions.addAll(transactionManager.findAllTransactionsByAccount(account));

        Iterator<Transaction> iterator = transactions.iterator();
        for(int i = 0; i < 10 && iterator.hasNext(); ++i) {
            var transaction = iterator.next();
            if (transaction.getOriginator() != account) continue;
            result.add(transaction);
        }

        return result;
    }
}
