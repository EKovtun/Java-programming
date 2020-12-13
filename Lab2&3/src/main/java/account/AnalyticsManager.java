package account;

import storage.KeyExtractor;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) throws IllegalArgumentException {
        if (transactionManager == null) throw new IllegalArgumentException();
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) throws IllegalArgumentException {
        if (account == null) throw new IllegalArgumentException();

        return transactionManager.findAllTransactionsByAccount(account)
                .stream()
                .map(Transaction::getBeneficiary)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) throws IllegalArgumentException {
        if (account == null) throw new IllegalArgumentException();

        return account.getAllEntries().stream()
                .sorted(Comparator.comparing(Entry::getAmount))
                .filter(entry -> entry.getAmount() < 0)
                .limit(10)
                .map(Entry::getTransaction)
                .collect(Collectors.toList());
    }

    public double overallBalanceOfAccounts(List<Account> accounts) {
        if (accounts == null) return 0d;
        return accounts.stream()
                .filter(Objects::nonNull)
                .mapToDouble(account -> account.balanceOn(null))
                .sum();
    }

    public Optional<Entry> maxExpenseAmountEntryWithinInterval(List<? extends Account> accounts, LocalDate from, LocalDate to) {
        if (accounts == null) return Optional.empty();
        return accounts.stream()
                .filter(Objects::nonNull)
                .map(account -> account.getEntries(from, to))
                .flatMap(Collection::stream)
                .filter(entry -> entry.getAmount() < 0)
                .min(Comparator.comparing(Entry::getAmount));
    }

    public <R extends Comparable<? super R>, T extends Account> Set<R> uniqueKeysOf(List<T> accounts, KeyExtractor<R, Account> extractor) {
        if (accounts == null || extractor == null) return new TreeSet<>();
        return accounts.stream()
                .map(extractor::extract)
                .collect(Collectors.toSet());
    }

    public <T extends Account> List<T> accountsRangeFrom(List<T> accounts, T minAccount, Comparator<T> comparator) {
        if (accounts == null || comparator == null) return new ArrayList<>();
        return accounts.stream()
                .filter(Objects::nonNull)
                .filter(account -> comparator.compare(minAccount, account) >= 0)
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
