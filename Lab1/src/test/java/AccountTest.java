import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account account;
    boolean isSuccess;

    @BeforeEach
    void setUp() {
        account = new Account(1);
    }

    @AfterEach
    void tearDown() {
        account = null;
    }

    @Test
    @Order(1)
    void add_whenAddedInvalidAmount() {
        // when
        isSuccess = account.add(-10);
        // then
        assertFalse(isSuccess);
        // when
        isSuccess = account.add(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    @Order(2)
    void add_checkCorrectAdditionAmount() {
        // when
        isSuccess = account.add(10.05);
        // then
        assertTrue(isSuccess);
        assertEquals(account.getBalance(), 10.05);
    }

    @Test
    void withdraw_whenWithdrawInvalidAmount() {
        // when
        isSuccess = account.withdraw(-10);
        // then
        assertFalse(isSuccess);
        // when
        isSuccess = account.withdraw(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_whenBalanceIsNotEnoughToWithdraw() {
        // when
        isSuccess = account.withdraw(10);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdraw_checkCorrectWithdrawAmount() {
        // when
        account.add(100.40);
        account.withdraw(60.20);
        // then
        assertEquals(account.getBalance(), 40.20);
    }
}