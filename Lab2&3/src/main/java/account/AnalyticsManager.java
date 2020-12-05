package account;

import storage.KeyExtractor;

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

    public double overallBalanceOfAccounts(List<? extends Account> accounts) {
        if (accounts == null) return 0d;
        double result = 0d;
        for (Account account : accounts) {
            if (account == null) continue;
            result += account.balanceOn(null);
        }
        return result;
    }

    public <R, T extends Account> Collection<R> uniqueKeysOf(List<T> accounts, KeyExtractor<R, Account> extractor) {
        Collection<R> resultList = new ArrayList<>();
        if (accounts == null || extractor == null) return resultList;
        for(Account account : accounts) {
            resultList.add(extractor.extract(account));
        }
        return resultList;
    }

    public <T extends Account> List<T> accountsRangeFrom(List<? extends T> accounts, T minAccount, Comparator<? super T> comparator) {
        if (accounts == null || comparator == null) return new ArrayList<>();
        var resultList = new ArrayList<T>(accounts);
        resultList.sort(comparator);
        int minAccountIndex = minAccount == null ? 0 : resultList.indexOf(minAccount);
        resultList.subList(minAccountIndex, resultList.size() - 1);
        return resultList;
    }

}
