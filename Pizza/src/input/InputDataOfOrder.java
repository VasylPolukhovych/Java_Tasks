package input;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputDataOfOrder implements InputData {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public boolean isInputEnd() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("The order has already been formed?");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }

    @Override
    public Long inputLong() {
        System.out.println("Would you like to leave a tip?");
        return Long.parseLong(scanner.nextLine());
    }

    @Override
    public Map<String, Long> inputDetails() {
        boolean endOfOrder = false;
        Map<String, Long> map = new HashMap<String, Long>();

        while (!endOfOrder) {
            System.out.println("What are you want?");
            String nameD = scanner.nextLine();
            System.out.println("How many servings do you want to get?");
            Long count = Long.parseLong(scanner.nextLine());
            map.put(nameD, count);
            if (isInputEnd()) {
                endOfOrder = true;
            }
        }
        return map;

    }


}
