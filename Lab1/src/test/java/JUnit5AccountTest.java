import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnit5AccountTest {
    Account account;

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
    void add() {
        assertFalse(account.add(-10));
        assertFalse(account.add(0));

        assertTrue(account.add(10.05));
        assertEquals(account.getBalance(), 10.05);
    }

    @Test
    @Order(2)
    void withdraw() {
        account.add(100);

        assertFalse(account.withdraw(-10));
        assertFalse(account.withdraw(0));
        assertFalse(account.withdraw(101));

        assertTrue(account.withdraw(10));
        assertTrue(account.withdraw(90));

        assertFalse(account.withdraw(1));
    }


}