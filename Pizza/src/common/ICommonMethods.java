package common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface ICommonMethods {
    static float toCurrency(float f) {
        return new BigDecimal(f).setScale(2, RoundingMode.UP).floatValue();
    }

}
