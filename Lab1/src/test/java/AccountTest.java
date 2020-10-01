import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void add_whenAddedNegativeAmount() {
        // given
        Account account = new Account(1);
        // when
        boolean isSuccess = account.add(-10);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void add_whenAddedZeroAmount() {
        // given
        Account account = new Account(1);
        // when
        boolean isSuccess = account.add(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void add_checkCorrectAdditionAmount() {
        // given
        Account account = new Account(1);
        // when
        boolean isSuccess = account.add(10.05);
        // then
        assertTrue(isSuccess);
        assertEquals(account.getBalance(), 10.05);
    }

    @Test
    void withdraw_whenWithdrawNegativeAmount() {
        // given
        Account account = new Account(1);
        // when
        boolean isSuccess = account.withdraw(-10);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_whenWithdrawZeroAmount() {
        // given
        Account account = new Account(1);
        // when
        boolean isSuccess = account.withdraw(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_whenBalanceIsNotEnoughToWithdraw() {
        // given
        Account account = new Account(1);
        // when
        boolean isSuccess = account.withdraw(10);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_checkCorrectWithdrawAmount() {
        // given
        Account account = new Account(1);
        // when
        account.add(100.40);
        account.withdraw(60.20);
        // then
        assertEquals(account.getBalance(), 40.20);
    }
}