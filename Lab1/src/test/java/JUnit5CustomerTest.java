import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnit5CustomerTest {
    @Test
    @Order(1)
    void openAccount() {
        Customer customer = new Customer("Ed", "Kv");

        assertTrue(customer.openAccount(1));
        assertFalse(customer.openAccount(1));
        assertFalse(customer.openAccount(2));
    }

    @Test
    @Order(2)
    void addMoneyToCurrentAccount() {
        Customer customer = new Customer("Ed", "Kv");

        assertFalse(customer.addMoneyToCurrentAccount(10));

        customer.openAccount(1);

        assertFalse(customer.addMoneyToCurrentAccount(-10));
        assertFalse(customer.addMoneyToCurrentAccount(0));

        assertTrue(customer.addMoneyToCurrentAccount(10.5));
    }

    @Test
    void closeAccount() {
        Customer customer = new Customer("Ed", "Kv");

        assertFalse(customer.closeAccount());

        customer.openAccount(1);

        assertTrue(customer.closeAccount());
        assertFalse(customer.closeAccount());
    }

    @Test
    void fullName() {
        Customer customer;

        customer = new Customer("Ed", "Kv");
        assertEquals(customer.fullName(), "Ed Kv");

        customer = new Customer("", "Kv");
        assertEquals(customer.fullName(), "Kv");

        customer = new Customer(null, "Kv");
        assertEquals(customer.fullName(), "Kv");

        customer = new Customer("Ed", "");
        assertEquals(customer.fullName(), "Ed");

        customer = new Customer("Ed", null);
        assertEquals(customer.fullName(), "Ed");

        customer = new Customer("", "");
        assertEquals(customer.fullName(), "");

        customer = new Customer(null, null);
        assertEquals(customer.fullName(), "");
    }

    @Test
    void withdrawFromCurrentAccount() {
        Customer customer = new Customer("Ed", "Kv");

        assertFalse(customer.withdrawFromCurrentAccount(10));

        customer.openAccount(1);

        assertFalse(customer.withdrawFromCurrentAccount(-10));
        assertFalse(customer.withdrawFromCurrentAccount(0));
        assertFalse(customer.withdrawFromCurrentAccount(10));

        customer.addMoneyToCurrentAccount(10.5);

        assertFalse(customer.withdrawFromCurrentAccount(-10));
        assertFalse(customer.withdrawFromCurrentAccount(0));

        assertTrue(customer.withdrawFromCurrentAccount(10));
        assertTrue(customer.withdrawFromCurrentAccount(0.5));
        assertFalse(customer.withdrawFromCurrentAccount(1));
    }
}