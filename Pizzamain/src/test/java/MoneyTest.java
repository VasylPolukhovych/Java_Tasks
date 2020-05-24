
import pizza.dto.common.Money;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoneyTest {
        private static Money money;

        @BeforeClass
        public static void init() {
                money = new Money(3.21234567);
       }

        @AfterClass
        public static void tearDown() {
                money = null;
        }

        @Test
        public void addTest() {
                double expectedMoney = (double) 8.54;
                Money actualMoney;
                actualMoney = money.add(new Money((double) 5.33145));
                assertEquals(expectedMoney, actualMoney.getCount(), (double) 0.00000000001);
                Money zeroMoney = new Money(0);
                actualMoney = zeroMoney.add(money);
                assertEquals((double) 3.21, actualMoney.getCount(), (double) 0.00000000001);
        }

        @Test
        public void subtractTest() {
                double expectedMoney = (double) 1.00;
                Money actualMoney;

                actualMoney = money.subtract(new Money((double) 2.20555555));
                assertEquals(expectedMoney, actualMoney.getCount(), (double) 0.00000000001);
        }

        @Test
        public void multyplyTest() {
                double expectedMoney = (double) 6.42;
                Money actualMoney;
                actualMoney = money.multiply(2);
                assertEquals(expectedMoney, actualMoney.getCount(), (double) 0.00000000001);
        }

        @Test
        public void divideTest() {
                double expectedMoney = (double) 1.00;
                Money actualMoney;
                actualMoney = money.divide(3.21);
                assertEquals(expectedMoney, actualMoney.getCount(), (double) 0.00000000001);
        }

        @Test
        public void getCountTest() {
                double expectedMoney = (double) 3.21;
                double actualMoney;
                actualMoney = money.getCount();
                assertEquals(expectedMoney, actualMoney, (double) 0.00000000001);
        }

}
