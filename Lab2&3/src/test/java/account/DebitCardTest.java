package account;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DebitCardTest {
    @Test
    void balanceOn_whenAccountIsNew() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        // when
        double valueFirst = accountFirst.balanceOn(null);
        // then
        assertEquals(0, valueFirst);
    }

    @Test
    void balanceOn_whenAccountIsNotNew() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        accountFirst.addCash(50);
        // when
        double valueFirst = accountFirst.balanceOn(null);
        // then
        assertEquals(50, valueFirst);
    }

    @Test
    void addCash_whenAmountIsNegative() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        // when
        boolean isSuccess = accountFirst.addCash(-5);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void addCash_whenAmountIsZero() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        // when
        boolean isSuccess = accountFirst.addCash(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void addCash_whenAmountIsValid() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        // when
        boolean isSuccess = accountFirst.addCash(50);
        // then
        assertTrue(isSuccess);
    }

    @Test
    void withdrawCash_whenAmountIsNegative() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        // when
        boolean isSuccess = accountFirst.withdrawCash(-5);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdrawCash_whenAmountIsZero() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        // when
        boolean isSuccess = accountFirst.withdrawCash(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdrawCash_whenAmountIsValid() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        accountFirst.addCash(50);
        // when
        boolean isSuccess = accountFirst.withdrawCash(20);
        // then
        assertTrue(isSuccess);
    }

    @Test
    void withdrawCash_whenBalanceIsNotEnough() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        accountFirst.addCash(50);
        // when
        boolean isSuccess = accountFirst.withdrawCash(51);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_whenAmountIsNegative() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        // when
        boolean isSuccess = accountFirst.withdraw(-5, null);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_whenAmountIsZero() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        // when
        boolean isSuccess = accountFirst.withdraw(0, null);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_whenAmountIsValid() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        accountFirst.addCash(50);
        // when
        boolean isSuccess = accountFirst.withdraw(20, null);
        // then
        assertTrue(isSuccess);
    }

    @Test
    void withdraw_whenBalanceIsNotEnough() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        accountFirst.addCash(50);
        // when
        boolean isSuccess = accountFirst.withdraw(51, null);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_checkBalanceOnBothAccounts() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        DebitCard accountSecond = new DebitCard(2, null, new TransactionManager());
        accountFirst.addCash(50);
        accountSecond.addCash(100);
        // when
        boolean isSuccess = accountFirst.withdraw(30, accountSecond);
        double valueFirst = accountFirst.balanceOn(null);
        double valueSecond = accountSecond.balanceOn(null);
        // then
        assertTrue(isSuccess);
        assertEquals(20, valueFirst);
        assertEquals(130, valueSecond);
    }

    @Test
    void history_whenFromAndToIsNotSet() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        DebitCard accountSecond = new DebitCard(2, null, new TransactionManager());
        // when
        int valueFirst = accountFirst.history(null, null).size();
        // then
        assertEquals(0, valueFirst);
        // given
        accountFirst.addCash(100);
        accountFirst.withdraw(30, accountSecond);
        accountFirst.addCash(20);
        accountFirst.withdraw(50, accountSecond);
        // when
        valueFirst = accountFirst.history(null, null).size();
        int valueSecond = accountSecond.history(null, null).size();
        // then
        assertEquals(4, valueFirst);
        assertEquals(2, valueSecond);
    }

    @Test
    void history_checkDateFrom() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        Transaction transaction = new TransactionManager().createTransaction(10, null, null);
        accountFirst.addEntry(new Entry(accountFirst, transaction, 4, LocalDateTime.now().minusDays(1)));
        accountFirst.addEntry(new Entry(accountFirst, transaction, 1, LocalDateTime.now()));
        accountFirst.addEntry(new Entry(accountFirst, transaction, 2, LocalDateTime.now()));
        accountFirst.addEntry(new Entry(accountFirst, transaction, 3, LocalDateTime.now().plusDays(1)));

        // when
        ArrayList<Entry> resultList = new ArrayList<>(accountFirst.history(LocalDate.now(), null));
        Entry firstEntry = resultList.get(0);
        Entry secondEntry = resultList.get(1);
        Entry threeEntry = resultList.get(2);

        // then
        assertEquals(3, resultList.size());
        assertEquals(1, firstEntry.getAmount());
        assertEquals(2, secondEntry.getAmount());
        assertEquals(3, threeEntry.getAmount());

        // when
        resultList = new ArrayList<>(accountFirst.history(LocalDate.now().plusDays(1), null));
        firstEntry = resultList.get(0);

        // then
        assertEquals(1, resultList.size());
        assertEquals(3, firstEntry.getAmount());
    }

    @Test
    void history_checkDateTo() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        Transaction transaction = new TransactionManager().createTransaction(10, null, null);
        accountFirst.addEntry(new Entry(accountFirst, transaction, 4, LocalDateTime.now().minusDays(1)));
        accountFirst.addEntry(new Entry(accountFirst, transaction, 1, LocalDateTime.now()));
        accountFirst.addEntry(new Entry(accountFirst, transaction, 2, LocalDateTime.now()));
        accountFirst.addEntry(new Entry(accountFirst, transaction, 3, LocalDateTime.now().plusDays(1)));

        // when
        ArrayList<Entry> resultList = new ArrayList<>(accountFirst.history(null, LocalDate.now()));
        Entry firstEntry = resultList.get(0);

        // then
        assertEquals(1, resultList.size());
        assertEquals(4, firstEntry.getAmount());

        // when
        resultList = new ArrayList<>(accountFirst.history(null, LocalDate.now().plusDays(1)));
        firstEntry = resultList.get(0);
        Entry secondEntry = resultList.get(1);
        Entry threeEntry = resultList.get(2);

        // then
        assertEquals(3, resultList.size());
        assertEquals(4, firstEntry.getAmount());
        assertEquals(1, secondEntry.getAmount());
        assertEquals(2, threeEntry.getAmount());
    }

    @Test
    void history_checkDateToAndDateFrom() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        Transaction transaction = new TransactionManager().createTransaction(10, null, null);
        accountFirst.addEntry(new Entry(accountFirst, transaction, 4, LocalDateTime.now().minusDays(1)));
        accountFirst.addEntry(new Entry(accountFirst, transaction, 1, LocalDateTime.now()));
        accountFirst.addEntry(new Entry(accountFirst, transaction, 2, LocalDateTime.now()));
        accountFirst.addEntry(new Entry(accountFirst, transaction, 3, LocalDateTime.now().plusDays(1)));

        // when
        ArrayList<Entry> resultList = new ArrayList<>(accountFirst.history(LocalDate.now(), LocalDate.now().plusDays(1)));
        Entry firstEntry = resultList.get(0);
        Entry secondEntry = resultList.get(1);

        // then
        assertEquals(2, resultList.size());
        assertEquals(1, firstEntry.getAmount());
        assertEquals(2, secondEntry.getAmount());
    }

    @Test
    void rollbackLastTransaction_whenTransactionsIsNotExists() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        // when
        boolean isSuccess = accountFirst.rollbackLastTransaction();
        // then
        assertFalse(isSuccess);
    }

    @Test
    void rollbackLastTransaction_whenTransactionsIsExists() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        accountFirst.addCash(10);
        accountFirst.addCash(20);
        accountFirst.addCash(30);
        // when
        boolean isSuccess = accountFirst.rollbackLastTransaction();
        double valueSecond = accountFirst.balanceOn(null);
        // then
        assertTrue(isSuccess);
        assertEquals(30, valueSecond);
        // when
        isSuccess = accountFirst.rollbackLastTransaction();
        valueSecond = accountFirst.balanceOn(null);
        // then
        assertFalse(isSuccess);
        assertEquals(30, valueSecond);
    }


    @Test
    void getBonuses_whenWithdrawAndBonusAccountIsNotExist() {
        // given
        DebitCard accountFirst = new DebitCard(1, null, new TransactionManager());
        accountFirst.addCash(100);
        // when
        accountFirst.withdraw(50, null);
        double bonuses = accountFirst.getBonuses(null);
        // then
        assertEquals(0, bonuses);
    }

    @Test
    void getBonuses_whenWithdrawAndBonusAccountIsExist() {
        double startCashOnAccount =  5000d;
        double amountWithdraw = 1000d;
        double bonusPercentage = 20d;
        double returnBonuses = 200d;
        // given
        BonusAccount bonusAccount = new BonusAccount(bonusPercentage, new TransactionManager());
        DebitCard accountFirst = new DebitCard(1, bonusAccount, new TransactionManager());
        accountFirst.addCash(startCashOnAccount);
        // when
        accountFirst.withdraw(amountWithdraw, new DebitCard(2, null, new TransactionManager()));
        double bonuses = accountFirst.getBonuses(null);
        // then
        assertEquals(returnBonuses, bonuses);
    }
}