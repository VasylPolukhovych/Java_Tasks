package order;
import java.time.LocalDate;
import java.util.*;


import menu.*;
public class ActionsClient implements IActionsClient {

    Scanner scanner = new Scanner(System.in);

    public ActionsClient() {
    }
    private int isOrderEnd (){
        System.out.println("--------------------------------------------------------------");
        System.out.println("The order has already been formed?");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            return 1;
        }
        return 0;
    }
    private DishForOrder[] addElement(DishForOrder[] orders, DishForOrder element) {
        orders = Arrays.copyOf(orders, orders.length + 1);
        orders[orders.length - 1] = element;
        return orders;
    }

    public Order findOrderById(int orderId, Orders orders) {

        for (int i = 0; i < orders.getOrders().length; i++) {
            if (orders.getOrders()[i].getIdOrder() == orderId) {
                return orders.getOrders()[i];
            }
        }
        return null;
    }

    public CookedDish findCookedDish(String nameDish, Menu menu) {
        LocalDate lD = LocalDate.now();
        for (int i = 0; i < menu.getMenu().length; i++) {
            if (menu.getMenu()[i].getDish().getNameDish().equals(nameDish)) {
                long plusDay = menu.getMenu()[i].getDish().getExpirationDate();
                int compDt = menu.getMenu()[i].getDateOfMaking().plusDays(plusDay).compareTo(lD);
                if ( compDt > 0) {
                    return menu.getMenu()[i];
                }
            }
        }
        return null;
    }

    public float calcOrderSum(DishForOrder[] orderDish) {

        float sum = 0;

        for (int i = 0; i < orderDish.length; i++) {
            sum = sum + orderDish[i].getSum();
}
        return sum;
    }

    public Order inputOrder(Menu menu) {
        CookedDish cookedDishW;
        DishForOrder[] listDishes = new DishForOrder[1];
        Order newOrder = new Order();
        int endOfOrder = 0;
        int cnt = 0;
        while (endOfOrder == 0) {
            System.out.println("What are you want?");
            String nameD = scanner.nextLine();
            cookedDishW = findCookedDish(nameD, menu);
            if (cookedDishW == null) {
                System.out.println("Sorry, unfortunately but we don't have such dish at the moment");
                endOfOrder =isOrderEnd();
            } else {
                DishForOrder dishDetails = new DishForOrder();
                System.out.println("How many servings do you want to get?");
                dishDetails.setCount(Integer.parseInt(scanner.nextLine()));
                dishDetails.setDishName(cookedDishW.getDish().getNameDish());
                dishDetails.setCookedDishId(cookedDishW.getIdCookedDish());
                dishDetails.setSum(IActionsClient.toCurrency(dishDetails.getCount()*cookedDishW.getDish().getPrice()));
                if (cnt > 0) {
                    listDishes = addElement(listDishes, dishDetails);
                } else {
                    listDishes[0] = dishDetails;
                }
                cnt++;
                endOfOrder = isOrderEnd();

            }
        }
        if (cnt >0) {
            newOrder.setArrayDishs(listDishes);
            System.out.println("Would you like to leave a tip?");
            newOrder.setTip(Integer.parseInt(scanner.nextLine()));
            newOrder.setOrderDate(LocalDate.now());
            //newOrder.setIdOrder(orders.getOrders().length + 1);
            newOrder.setPrice(calcOrderSum(listDishes));
        }
        if (newOrder.getPrice() > 0) {
            return newOrder;
        } else {
            return null;
        }
    }

    public void printOrder(int orderId, Orders orders, Menu menu) {
        Order orderForPrint = new Order();
        orderForPrint = findOrderById(orderId, orders);
        float summa = orderForPrint.getPrice();
        int tip = orderForPrint.tip;
        if (orderForPrint == null) {
            System.out.println("Bill #" + orderId + " does not exist on the system");
        } else {
            System.out.println("Bill #" + orderId);
            for (int i = 0; i < orderForPrint.getArrayDishs().length; i++) {
                System.out.printf("Dish - %s Count %d  Price %8.2f", orderForPrint.getArrayDishs()[i].getDishName(),
                                  orderForPrint.getArrayDishs()[i].getCount(),
                                  findCookedDish(orderForPrint.getArrayDishs()[i].getDishName(), menu).getDish().getPrice());
                System.out.println("");
            }
            System.out.println("________________________________________________");
            System.out.printf("Summa = %8.2f Tip = %d Total = %8.2f", summa, tip, (summa + summa * tip / 100));
            System.out.println("");
        }
    }

    public void printMenu(Menu menu) {
        System.out.println("*****************************************");
        System.out.println("Menu");
        for (int i = 0; i < menu.getMenu().length; i++) {
            System.out.println("Dish - " + menu.getMenu()[i].getDish().getNameDish() + ". Count - " + menu.getMenu()[i].getCount() + ". Price - " + menu.getMenu()[i].getDish().getPrice());
        }
        System.out.println("*****************************************");
    }

}
