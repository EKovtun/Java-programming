package account;

import java.time.LocalDate;
import java.util.Objects;

public class BonusAccount implements Account {
    private final double bonusPercentage;
    private final TransactionManager transactionManager;
    private final Entries entries = new Entries();

    public BonusAccount(double bonusPercentage, TransactionManager transactionManager) throws IllegalArgumentException {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BonusAccount that = (BonusAccount) o;
        return Double.compare(that.bonusPercentage, bonusPercentage) == 0 &&
                Objects.equals(transactionManager, that.transactionManager) &&
                Objects.equals(entries, that.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bonusPercentage, transactionManager, entries);
    }
}
