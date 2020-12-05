package storage;

import account.DebitCard;
import account.TransactionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountKeyExtractorTest {

    @Test
    void extract() {
        // given
        DebitCard accout = new DebitCard(0, null, new TransactionManager());
        // when
        Integer key = new AccountKeyExtractor().extract(accout);
        // then
        assertEquals(accout.hashCode(), key);
    }

    @Test
    void extract_whenAccountIsNull() {
        // when
        Integer key = new AccountKeyExtractor().extract(null);
        // then
        assertNull(key);
    }
}