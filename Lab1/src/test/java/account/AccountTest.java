package account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account accountFirst, accountSecond;
    boolean isSuccess;
    double valueFirst, valueSecond;

    @BeforeEach
    void beforeTest() {
        accountFirst = new Account(1);
        accountSecond = new Account(2);
    }

    @Test
    @Order(1)
    void balanceOn_whenAccountIsNew() {
        // when
        valueFirst = accountFirst.balanceOn(null);
        // then
        assertEquals(0, valueFirst);
    }

    @Test
    @Order(2)
    void addCash_whenAmountIsInvalid() {
        // when
        isSuccess = accountFirst.addCash(-5);
        // then
        assertFalse(isSuccess);
        // when
        isSuccess = accountFirst.addCash(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    @Order(2)
    void addCashAndBalanceOn_whenAmountIsValid() {
        // when
        isSuccess = accountFirst.addCash(50);
        valueFirst = accountFirst.balanceOn(null);
        // then
        assertTrue(isSuccess);
        assertEquals(50, valueFirst);
        // when
        isSuccess = accountFirst.addCash(20);
        valueFirst = accountFirst.balanceOn(null);
        // then
        assertTrue(isSuccess);
        assertEquals(70, valueFirst);
    }

    @Test
    void withdrawCash_whenAmountIsInvalid() {
        // when
        isSuccess = accountFirst.withdrawCash(-5);
        // then
        assertFalse(isSuccess);
        // when
        isSuccess = accountFirst.withdrawCash(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdrawCash_whenAmountIsValid() {
        // given
        accountFirst.addCash(50);
        // when
        isSuccess = accountFirst.withdrawCash(20);
        valueFirst = accountFirst.balanceOn(null);
        // then
        assertTrue(isSuccess);
        assertEquals(30, accountFirst.balanceOn(null));
        // when
        isSuccess = accountFirst.withdrawCash(30);
        valueFirst = accountFirst.balanceOn(null);
        // then
        assertTrue(isSuccess);
        assertEquals(0, accountFirst.balanceOn(null));
    }

    @Test
    void withdrawCash_whenBalanceIsNotEnough() {
        // given
        accountFirst.addCash(50);
        // when
        isSuccess = accountFirst.withdrawCash(51);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_whenAmountIsInvalid() {
        // when
        isSuccess = accountFirst.withdraw(-5, null);
        // then
        assertFalse(isSuccess);
        // when
        isSuccess = accountFirst.withdraw(0, null);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_whenAmountIsValid() {
        // given
        accountFirst.addCash(50);
        // when
        isSuccess = accountFirst.withdraw(20, null);
        valueFirst = accountFirst.balanceOn(null);
        // then
        assertTrue(isSuccess);
        assertEquals(30, accountFirst.balanceOn(null));
        // when
        isSuccess = accountFirst.withdraw(30, null);
        valueFirst = accountFirst.balanceOn(null);
        // then
        assertTrue(isSuccess);
        assertEquals(0, accountFirst.balanceOn(null));
    }

    @Test
    void withdraw_whenBalanceIsNotEnough() {
        // given
        accountFirst.addCash(50);
        // when
        isSuccess = accountFirst.withdraw(51, null);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_checkBalanceOnBothAccounts() {
        // given
        accountFirst.addCash(50);
        accountSecond.addCash(100);
        // when
        isSuccess = accountFirst.withdraw(30, accountSecond);
        valueFirst = accountFirst.balanceOn(null);
        valueSecond = accountSecond.balanceOn(null);
        // then
        assertTrue(isSuccess);
        assertEquals(20, valueFirst);
        assertEquals(130, valueSecond);
    }

    @Test
    void history_whenFromAndToIsNotSet() {
        // when
        valueFirst = accountFirst.history(null, null).size();
        // then
        assertEquals(0, valueFirst);
        // given
        accountFirst.addCash(100);
        accountFirst.withdraw(30, accountSecond);
        accountFirst.addCash(20);
        accountFirst.withdraw(50, accountSecond);
        // when
        valueFirst = accountFirst.history(null, null).size();
        valueSecond = accountSecond.history(null, null).size();
        // then
        assertEquals(4, valueFirst);
        assertEquals(2, valueSecond);
    }
}