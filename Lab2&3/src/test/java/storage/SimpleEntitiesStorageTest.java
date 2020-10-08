package storage;

import account.Account;
import account.BonusAccount;
import account.DebitCard;
import account.TransactionManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SimpleEntitiesStorageTest {

    @Test
    void save() {
        // given
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(new AccountKeyExtractor());
        BonusAccount bonusAccount = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard = new DebitCard(0, bonusAccount, new TransactionManager());
        // when
        storage.save(bonusAccount);
        storage.save(debitCard);
        Collection<Account> allEntity = storage.findAll();
        int sizeResult = allEntity.size();
        boolean bonusContains = allEntity.contains(bonusAccount);
        boolean debitContains = allEntity.contains(debitCard);
        // then
        assertEquals(2, sizeResult);
        assertTrue(bonusContains);
        assertTrue(debitContains);
    }

    @Test
    void save_whenEntityIsNull() {
        // given
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(new AccountKeyExtractor());
        // when
        storage.save(null);
        Collection<Account> allEntity = storage.findAll();
        int sizeResult = allEntity.size();
        // then
        assertEquals(0, sizeResult);
    }

    @Test
    void saveAll() {
        // given
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(new AccountKeyExtractor());
        BonusAccount bonusAccount = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard = new DebitCard(0, bonusAccount, new TransactionManager());
        // when
        LinkedList<Account> accountsList = new LinkedList<>();
        accountsList.push(bonusAccount);
        accountsList.push(debitCard);
        storage.saveAll(accountsList);
        Collection<Account> allEntity = storage.findAll();
        int sizeResult = allEntity.size();
        boolean bonusContains = allEntity.contains(bonusAccount);
        boolean debitContains = allEntity.contains(debitCard);
        // then
        assertEquals(2, sizeResult);
        assertTrue(bonusContains);
        assertTrue(debitContains);
    }

    @Test
    void saveAll_whenCollectionIsNull() {
        // given
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(new AccountKeyExtractor());
        // when
        storage.saveAll(null);
        Collection<Account> allEntity = storage.findAll();
        int sizeResult = allEntity.size();
        // then
        assertEquals(0, sizeResult);
    }

    @Test
    void saveAll_whenNullInList() {
        // given
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(new AccountKeyExtractor());
        BonusAccount bonusAccount = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard = new DebitCard(0, bonusAccount, new TransactionManager());
        // when
        LinkedList<Account> accountsList = new LinkedList<>();
        accountsList.push(null);
        accountsList.push(bonusAccount);
        accountsList.push(null);
        accountsList.push(debitCard);
        accountsList.push(null);
        storage.saveAll(accountsList);
        Collection<Account> allEntity = storage.findAll();
        int sizeResult = allEntity.size();
        boolean bonusContains = allEntity.contains(bonusAccount);
        boolean debitContains = allEntity.contains(debitCard);
        // then
        assertEquals(2, sizeResult);
        assertTrue(bonusContains);
        assertTrue(debitContains);
    }

    @Test
    void findByKey() {
        // given
        AccountKeyExtractor keyExtractor = new AccountKeyExtractor();
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(keyExtractor);
        BonusAccount bonusAccount = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard = new DebitCard(0, bonusAccount, new TransactionManager());
        storage.save(bonusAccount);
        storage.save(debitCard);
        // when
        Account resultFirst = storage.findByKey(keyExtractor.extract(bonusAccount));
        Account resultSecond = storage.findByKey(keyExtractor.extract(debitCard));
        // then
        assertEquals(bonusAccount, resultFirst);
        assertEquals(debitCard, resultSecond);
    }

    @Test
    void findByKey_whenKeyIsNull() {
        // given
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(new AccountKeyExtractor());
        BonusAccount bonusAccount = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard = new DebitCard(0, bonusAccount, new TransactionManager());
        storage.save(bonusAccount);
        storage.save(debitCard);
        // when
        Account resultFirst = storage.findByKey(null);
        // then
        assertNull(resultFirst);
    }

    @Test
    void findByKey_whenKeyIsNotExists() {
        // given
        AccountKeyExtractor keyExtractor = new AccountKeyExtractor();
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(new AccountKeyExtractor());
        BonusAccount bonusAccount = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard = new DebitCard(0, bonusAccount, new TransactionManager());
        storage.save(bonusAccount);
        storage.save(debitCard);
        // when
        Account resultFirst = storage.findByKey(keyExtractor.extract(
                new BonusAccount(1, new TransactionManager()))
        );
        // then
        assertNull(resultFirst);
    }

    @Test
    void findAll() {
        // given
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(new AccountKeyExtractor());
        BonusAccount bonusAccount = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard = new DebitCard(0, bonusAccount, new TransactionManager());
        storage.save(bonusAccount);
        storage.save(debitCard);
        // when
        ArrayList<Account> resultList = new ArrayList<>(storage.findAll());
        int sizeResult = resultList.size();
        boolean bonusContains = resultList.contains(bonusAccount);
        boolean debitContains = resultList.contains(debitCard);
        // then
        assertEquals(2, sizeResult);
        assertTrue(bonusContains);
        assertTrue(debitContains);
    }

    @Test
    void findAll_whenStorageIsEmpty() {
        // given
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(new AccountKeyExtractor());
        // when
        ArrayList<Account> resultList = new ArrayList<>(storage.findAll());
        int sizeResult = resultList.size();
        // then
        assertEquals(0, sizeResult);
    }

    @Test
    void deleteByKey() {
        // given
        AccountKeyExtractor keyExtractor = new AccountKeyExtractor();
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(keyExtractor);
        BonusAccount bonusAccount = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard = new DebitCard(0, bonusAccount, new TransactionManager());
        storage.save(bonusAccount);
        storage.save(debitCard);
        // when
        storage.deleteByKey(keyExtractor.extract(bonusAccount));
        ArrayList<Account> resultList = new ArrayList<>(storage.findAll());
        int sizeResult = resultList.size();
        boolean bonusContains = resultList.contains(bonusAccount);
        boolean debitContains = resultList.contains(debitCard);
        // then
        assertEquals(1, sizeResult);
        assertFalse(bonusContains);
        assertTrue(debitContains);
    }

    @Test
    void deleteByKey_whenKeyIsNull() {
        // given
        AccountKeyExtractor keyExtractor = new AccountKeyExtractor();
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(keyExtractor);
        BonusAccount bonusAccount = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard = new DebitCard(0, bonusAccount, new TransactionManager());
        storage.save(bonusAccount);
        storage.save(debitCard);
        // when
        storage.deleteByKey(null);
        ArrayList<Account> resultList = new ArrayList<>(storage.findAll());
        int sizeResult = resultList.size();
        boolean bonusContains = resultList.contains(bonusAccount);
        boolean debitContains = resultList.contains(debitCard);
        // then
        assertEquals(2, sizeResult);
        assertTrue(bonusContains);
        assertTrue(debitContains);
    }

    @Test
    void deleteByKey_whenKeyIsNotExists() {
        // given
        AccountKeyExtractor keyExtractor = new AccountKeyExtractor();
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(keyExtractor);
        BonusAccount bonusAccount = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard = new DebitCard(0, bonusAccount, new TransactionManager());
        storage.save(bonusAccount);
        storage.save(debitCard);
        // when
        storage.deleteByKey(keyExtractor.extract(new BonusAccount(1, new TransactionManager())));
        ArrayList<Account> resultList = new ArrayList<>(storage.findAll());
        int sizeResult = resultList.size();
        boolean bonusContains = resultList.contains(bonusAccount);
        boolean debitContains = resultList.contains(debitCard);
        // then
        assertEquals(2, sizeResult);
        assertTrue(bonusContains);
        assertTrue(debitContains);
    }

    @Test
    void deleteAll() {
        // given
        AccountKeyExtractor keyExtractor = new AccountKeyExtractor();
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(keyExtractor);
        BonusAccount bonusAccount = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard = new DebitCard(0, bonusAccount, new TransactionManager());
        BonusAccount bonusAccount2 = new BonusAccount(10, new TransactionManager());
        DebitCard debitCard2 = new DebitCard(0, bonusAccount2, new TransactionManager());
        storage.save(bonusAccount);
        storage.save(debitCard);
        storage.save(bonusAccount2);
        storage.save(debitCard2);
        ArrayList<Account> deleteList = new ArrayList<>();
        deleteList.add(debitCard);
        deleteList.add(bonusAccount2);
        // when
        storage.deleteAll(deleteList);
        ArrayList<Account> resultList = new ArrayList<>(storage.findAll());
        int sizeResult = resultList.size();
        boolean bonusContains = resultList.contains(bonusAccount);
        boolean debitContains = resultList.contains(debitCard);
        boolean bonusContains2 = resultList.contains(bonusAccount2);
        boolean debitContains2 = resultList.contains(debitCard2);
        // then
        assertEquals(2, sizeResult);
        assertTrue(bonusContains);
        assertFalse(debitContains);
        assertFalse(bonusContains2);
        assertTrue(debitContains2);
    }
}