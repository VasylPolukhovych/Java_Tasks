package pizza.input;

import java.util.Map;

public interface InputData {

    boolean isInputEnd(String question);

    Integer inputInt(String message);

    String inputString(String message);

    Map<String, Long> inputDetails();
}
