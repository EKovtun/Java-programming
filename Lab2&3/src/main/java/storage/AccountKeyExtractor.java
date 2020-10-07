package storage;

import account.Account;

public class AccountKeyExtractor implements KeyExtractor<Integer, Account> {
    @Override
    public Integer extract(Account entity) {
        return entity.hashCode();
    }
}
