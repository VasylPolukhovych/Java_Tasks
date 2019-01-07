package input;

import java.util.Map;

public interface InputData {

    boolean isInputEnd();

    Long inputLong();

    Map<String, Long> inputDetails();
}
