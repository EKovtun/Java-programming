import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    boolean isSuccess;

    @Test
    @Order(1)
    void openAccount_whenAccountIsNotExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        isSuccess = customer.openAccount(1);
        // then
        assertTrue(isSuccess);
    }

    @Test
    void openAccount_whenAccountIsExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        customer.openAccount(1);
        // when
        isSuccess = customer.openAccount(2);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void closeAccount_whenAccountIsNotExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        isSuccess = customer.closeAccount();
        // then
        assertFalse(isSuccess);
    }

    @Test
    void closeAccount_whenAccountIsExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        customer.openAccount(1);
        // when
        isSuccess = customer.closeAccount();
        // then
        assertTrue(isSuccess);
    }

    @Test
    void addMoneyToCurrentAccount_whenAddedInvalidAmount() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        isSuccess = customer.addMoneyToCurrentAccount(-10);
        // then
        assertFalse(isSuccess);
        // when
        isSuccess = customer.addMoneyToCurrentAccount(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void addMoneyToCurrentAccount_whenAccountIsNotExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        isSuccess = customer.addMoneyToCurrentAccount(10);
        // then
        assertFalse(isSuccess);
    }

    @Test
    @Order(2)
    void addMoneyToCurrentAccount_whenAccountIsExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        customer.openAccount(1);
        // when
        isSuccess = customer.addMoneyToCurrentAccount(10);
        // then
        assertTrue(isSuccess);
    }

    @Test
    void withdrawFromCurrentAccount_whenWithdrawInvalidAmount() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        isSuccess = customer.withdrawFromCurrentAccount(-10);
        // then
        assertFalse(isSuccess);
        // when
        isSuccess = customer.withdrawFromCurrentAccount(0);
        // then
        assertFalse(isSuccess);
    }

    @Test
    void withdrawFromCurrentAccount_whenAccountIsNotExist() {
        // given
        Customer customer = new Customer("Ed", "Kv");
        // when
        isSuccess = customer.withdrawFromCurrentAccount(10);
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
        isSuccess = customer.withdrawFromCurrentAccount(10);
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
    void fullName_whenNameOrLastNameIsEmpty() {
        Customer customer;
        String fullName;

        // given
        customer = new Customer("", "");
        // when
        fullName = customer.fullName();
        // then
        assertEquals(fullName, "");

        // given
        customer = new Customer("Ed", "");
        // when
        fullName = customer.fullName();
        // then
        assertEquals(fullName, "Ed");

        // given
        customer = new Customer("", "Kv");
        // when
        fullName = customer.fullName();
        // then
        assertEquals(fullName, "Kv");
    }

    @Test
    void fullName_whenNameOrLastNameIsNull() {
        Customer customer;
        String fullName;

        // given
        customer = new Customer(null, null);
        // when
        fullName = customer.fullName();
        // then
        assertEquals(fullName, "");

        // given
        customer = new Customer("Ed", null);
        // when
        fullName = customer.fullName();
        // then
        assertEquals(fullName, "Ed");

        // given
        customer = new Customer(null, "Kv");
        // when
        fullName = customer.fullName();
        // then
        assertEquals(fullName, "Kv");
    }
}