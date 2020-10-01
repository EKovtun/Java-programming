import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void openAccount_whenAccountIsNotExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        boolean isSuccess = customer.openAccount(1);
        // then
        assertTrue(isSuccess);
    }

    @Test
    void openAccount_whenAccountIsExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        customer.openAccount(1);
        // when
        boolean isSuccess = customer.openAccount(2);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void closeAccount_whenAccountIsNotExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        boolean isSuccess = customer.closeAccount();
        // then
        assertFalse(isSuccess);
    }

    @Test
    void closeAccount_whenAccountIsExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        customer.openAccount(1);
        // when
        boolean isSuccess = customer.closeAccount();
        // then
        assertTrue(isSuccess);
    }

    @Test
    void addMoneyToCurrentAccount_whenAddedNegativeAmount() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        boolean isSuccess = customer.addMoneyToCurrentAccount(-10);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void addMoneyToCurrentAccount_whenAddedZeroAmount() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        boolean isSuccess = customer.addMoneyToCurrentAccount(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void addMoneyToCurrentAccount_whenAccountIsNotExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        boolean isSuccess = customer.addMoneyToCurrentAccount(10);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void addMoneyToCurrentAccount_whenAccountIsExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        customer.openAccount(1);
        // when
        boolean isSuccess = customer.addMoneyToCurrentAccount(10);
        // then
        assertTrue(isSuccess);
    }

    @Test
    void withdrawFromCurrentAccount_whenWithdrawNegativeAmount() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        boolean isSuccess = customer.withdrawFromCurrentAccount(-10);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdrawFromCurrentAccount_whenWithdrawZeroAmount() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        boolean isSuccess = customer.withdrawFromCurrentAccount(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdrawFromCurrentAccount_whenAccountIsNotExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        boolean isSuccess = customer.withdrawFromCurrentAccount(10);
        // then
        assertFalse(isSuccess);
    }

    @Test
    @Order(3)
    void withdrawFromCurrentAccount_whenAccountIsExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        customer.openAccount(1);
        customer.addMoneyToCurrentAccount(10);
        // when
        boolean isSuccess = customer.withdrawFromCurrentAccount(10);
        // then
        assertTrue(isSuccess);
    }

    @Test
    void fullName_checkFullName() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        String fullName = customer.fullName();
        // then
        assertEquals(fullName, "Ed Kv");
    }

    @Test
    void fullName_whenFirstNameIsEmpty() {
        // given
        Customer customer = new Customer("", "Second");
        // when
        String fullName = customer.fullName();
        // then
        assertEquals(fullName, "Second");
    }

    @Test
    void fullName_whenFirstNameIsNull() {
        // given
        Customer customer = new Customer(null, "Second");
        // when
        String fullName = customer.fullName();
        // then
        assertEquals(fullName, "Second");
    }

    @Test
    void fullName_whenSecondNameIsEmpty() {
        // given
        Customer customer = new Customer("First", "");
        // when
        String fullName = customer.fullName();
        // then
        assertEquals(fullName, "First");
    }

    @Test
    void fullName_whenSecondNameIsNull() {
        // given
        Customer customer = new Customer("First", null);
        // when
        String fullName = customer.fullName();
        // then
        assertEquals(fullName, "First");
    }
}