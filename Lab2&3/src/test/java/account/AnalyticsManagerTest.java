package account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.AccountKeyExtractor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsManagerTest {
    private DebitCard accountFirst, accountSecond, accountThird;
    private AnalyticsManager analyticsManager;

    @BeforeEach
    void setUp() {
        TransactionManager transactionManager = new TransactionManager();
        accountFirst = new DebitCard(1, null, transactionManager);
        accountSecond = new DebitCard(2, null, transactionManager);
        accountThird = new DebitCard(3, null, transactionManager);
        accountFirst.addCash(1000);
        accountFirst.withdraw(100, accountSecond);
        accountFirst.withdraw(200, accountSecond);
        accountFirst.withdraw(300, accountThird);
        analyticsManager = new AnalyticsManager(transactionManager);
    }

    @Test
    void mostFrequentBeneficiaryOfAccount_whenAccountIsNull() {
        assertThrows(IllegalArgumentException.class, () -> analyticsManager.mostFrequentBeneficiaryOfAccount(null));
    }

    @Test
    void mostFrequentBeneficiaryOfAccount_whenAccountIsValid() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        accountFirst = new DebitCard(1, null, transactionManager);
        accountSecond = new DebitCard(2, null, transactionManager);
        accountFirst.addCash(1000);
        accountFirst.withdraw(100, accountSecond);
        analyticsManager = new AnalyticsManager(transactionManager);
        // when
        Account account = analyticsManager.mostFrequentBeneficiaryOfAccount(accountFirst);
        boolean linksIsEqual = account.equals(accountFirst);
        // then
        assertTrue(linksIsEqual);
    }

    @Test
    void topTenExpensivePurchases_whenAccountIsNull() {
        assertThrows(IllegalArgumentException.class, () -> analyticsManager.topTenExpensivePurchases(null));
    }

    @Test
    void topTenExpensivePurchases_whenAccountDontHavePurchases() {
        // when
        ArrayList<Transaction> resultList = new ArrayList<>(analyticsManager.topTenExpensivePurchases(accountSecond));
        int sizeOfResultList = resultList.size();
        // then
        assertEquals(0, sizeOfResultList);
    }

    @Test
    void topTenExpensivePurchases_whenAccountIsValid() {
        // when
        ArrayList<Transaction> resultList = new ArrayList<>(analyticsManager.topTenExpensivePurchases(accountFirst));
        int sizeOfResultList = resultList.size();
        double transaction1Amount = resultList.get(0).getAmount();
        double transaction2Amount = resultList.get(1).getAmount();
        double transaction3Amount = resultList.get(2).getAmount();
        // then
        assertEquals(3, sizeOfResultList);
        assertEquals(300, transaction1Amount);
        assertEquals(200, transaction2Amount);
        assertEquals(100, transaction3Amount);
    }

    @Test
    void overallBalanceOfAccounts_whenAccountsIsNull() {
        // when
        double result = analyticsManager.overallBalanceOfAccounts(null);
        // then
        assertEquals(0d, result);
    }

    @Test
    void overallBalanceOfAccounts_whenAccountsIsEmpty() {
        // when
        double result = analyticsManager.overallBalanceOfAccounts(new ArrayList<>());
        // then
        assertEquals(0d, result);
    }

    @Test
    void overallBalanceOfAccounts_whenVariousAccountsIsNull() {
        // given
        accountFirst = new DebitCard(0, null, new TransactionManager());
        accountSecond = new DebitCard(0, null, new TransactionManager());
        accountThird = new DebitCard(0, null, new TransactionManager());
        accountFirst.addCash(100);
        accountSecond.addCash(100);
        accountThird.addCash(100);
        // when
        List<Account> accounts = new ArrayList<>();
        accounts.add(accountFirst);
        accounts.add(null);
        accounts.add(accountSecond);
        accounts.add(null);
        accounts.add(accountThird);
        double result = analyticsManager.overallBalanceOfAccounts(accounts);
        // then
        assertEquals(300d, result);
    }

    @Test
    void uniqueKeysOf_whenAccountsIsNull() {
        // when
        Collection<Integer> keys = analyticsManager.uniqueKeysOf(null, new AccountKeyExtractor());
        // then
        assertEquals(0, keys.size());
    }

    @Test
    void uniqueKeysOf_whenExtractorIsNull() {
        // given
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new DebitCard(0, null, new TransactionManager()));
        // when
        Collection<Integer> keys = analyticsManager.uniqueKeysOf(accounts, null);
        // then
        assertEquals(0, keys.size());
    }

    @Test
    void uniqueKeysOf_whenAccountsIsEmpty() {
        // when
        Collection<Integer> keys = analyticsManager.uniqueKeysOf(new ArrayList<>(), new AccountKeyExtractor());
        // then
        assertEquals(0, keys.size());
    }
}