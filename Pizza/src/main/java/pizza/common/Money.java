package pizza.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final int DEFAULT_FRACTION_DIGITS = 2;
    private double count;

    public Money(double count) {
        this.count = new BigDecimal(count).setScale(DEFAULT_FRACTION_DIGITS, RoundingMode.HALF_UP).doubleValue();
    }

    public double getCount() {
        return count;
    }

    public Money add(Money moneyToAdd) {
        return new Money(count + moneyToAdd.count);
    }

    public Money subtract(Money moneyToSubstract) {
        return new Money(count - moneyToSubstract.count);
    }

    public Money multiply(double multiplyBy) {
        return new Money(count * multiplyBy);
    }

    public Money divide(double divideBy) {
        return new Money(count / divideBy);
    }

    public int compareTo(Money moneyToCompare) {
        if (count < moneyToCompare.count) {
            return -1;
        }
        if (count == moneyToCompare.count) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return count == money.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }
}
