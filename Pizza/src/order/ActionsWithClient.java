package order;
import java.time.LocalDate;
import java.util.*;
import common.ICommonMethods;
import menu.*;
public class ActionsWithClient implements IActionsWithClient {
    ActionsWithMenu aM = new ActionsWithMenu();
    Scanner scanner = new Scanner(System.in);

    public ActionsWithClient() {
    }

    private int isOrderEnd() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("The order has already been formed?");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            return 1;
        }
        return 0;
    }

    public float calcOrderSum(HashMap<String, SelectedDishes> selectedDishes) {
        float sum = 0;
        for (Map.Entry<String, SelectedDishes> item : selectedDishes.entrySet()) {
            sum = sum + item.getValue().getPrice();
        }
        return sum;
    }

    private SelectedDishes fillSelectedDishes(CookedDish cookedDish) {
        SelectedDishes dishDetails = new SelectedDishes();
        dishDetails.setDishName(cookedDish.getDish().getNameDish());
        dishDetails.setDishId(cookedDish.getDish().getIdDish());
        dishDetails.setPrice(ICommonMethods.toCurrency(cookedDish.getDish().getPrice()));
        dishDetails.setCount(cookedDish.getCount());
        return dishDetails;
    }

    public Order inputOrder(LinkedList<CookedDish> menu) {
        LinkedList<CookedDish> tmpMenu = new LinkedList<>();
        tmpMenu.addAll(menu);
        CookedDish selectedDishFromMenu;
        HashMap<String, SelectedDishes> listDishes = new HashMap<>();
        Order newOrder = new Order();
        int endOfOrder = 0;
        int cnt = 0;
        while (endOfOrder == 0) {
            System.out.println("What are you want?");
            String nameD = scanner.nextLine();
            System.out.println("How many servings do you want to get?");
            int inputedCountDish = Integer.parseInt(scanner.nextLine());
            int inCntDish = inputedCountDish;
            while (inCntDish > 0) {
                selectedDishFromMenu = aM.findMenuItem(nameD, tmpMenu);
                if (selectedDishFromMenu == null) {
                    if ((listDishes.entrySet().isEmpty()) || (!(listDishes.containsKey(nameD)))) {
                        System.out.println("Sorry, unfortunately but we don't have " +
                                nameD + " at the moment");
                    } else {
                        System.out.println("Sorry, unfortunately but we have only " +
                                listDishes.get(nameD).getCount()
                                + " " + nameD + " at the moment");
                    }
                    inCntDish = 0;
                } else {
                    int cntDishInMenu = selectedDishFromMenu.getCount();
                    SelectedDishes dishDetails = fillSelectedDishes(selectedDishFromMenu);
                    if (selectedDishFromMenu.getCount() >= inCntDish) {
                        dishDetails.setCount(inCntDish);
                        inCntDish = 0;
                    } else {
                        dishDetails.setCount(cntDishInMenu);
                        inCntDish = inCntDish - cntDishInMenu;
                    }
                    if (listDishes.keySet().contains(dishDetails.getDishName())) {
                        listDishes.get(dishDetails.getDishName()).setCount(
                                listDishes.get(dishDetails.getDishName()).getCount() +
                                        dishDetails.getCount());
                    } else {
                        listDishes.put(dishDetails.getDishName(), dishDetails);
                    }
                    cnt++;
                    tmpMenu.remove(selectedDishFromMenu);
                }
            }
            endOfOrder = isOrderEnd();
        }
        if (cnt > 0) {
            newOrder.setSelectedDishes(listDishes);
            System.out.println("Would you like to leave a tip?");
            newOrder.setTip(Integer.parseInt(scanner.nextLine()));
            newOrder.setDate(LocalDate.now());
            newOrder.setSumma(calcOrderSum(listDishes));
            return newOrder;
        }
        return null;
    }

    public void printOrder(Integer orderId, HashMap<Integer, Order> orders) {
        Order orderForPrint = orders.get(orderId);
        float summa = orderForPrint.getSumma();
        int tip = orderForPrint.getTip();
        if (orderForPrint == null) {
            System.out.println("Bill #" + orderId + " does not exist on the system");
        } else {
            System.out.println("Bill #" + orderId);
            for (Map.Entry<String, SelectedDishes> i : orderForPrint.getSelectedDishes().entrySet()) {
                System.out.printf("Dish - %s Count %d  Price %8.2f", i.getValue().getDishName(),
                        i.getValue().getCount(),
                        i.getValue().getPrice());
                System.out.println("");
            }
            System.out.println("________________________________________________");
            System.out.printf("Summa = %8.2f Tip = %d Total = %8.2f", summa, tip, (summa + summa * tip / 100));
            System.out.println("");
        }
    }

}
