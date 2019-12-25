package pizza.input;

import java.util.Map;

public interface InputData {

    boolean isInputEnd();

    Integer inputInt(String message);

    Map<String, Long> inputDetails();
}
