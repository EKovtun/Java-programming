package account;

import java.time.LocalDate;

class BonusAccount implements Account {
    private final double bonusPercentage;
    private final TransactionManager transactionManager;
    private final Entries entries = new Entries();

    BonusAccount(double bonusPercentage, TransactionManager transactionManager) throws IllegalArgumentException {
        if (bonusPercentage <= 0 || transactionManager == null) throw new IllegalArgumentException();
        this.bonusPercentage = bonusPercentage;
        this.transactionManager = transactionManager;
    }

    double getBonusPercentage() {
        return bonusPercentage;
    }

    @Override
    public double balanceOn(LocalDate date) {
        double result = 0;
        for(var entry : entries.betweenDates(null, date)) {
            result += entry.getAmount();
        }
        return result;
    }

    @Override
    public void addEntry(Entry entry) {
        entries.addEntry(entry);
    }
}
