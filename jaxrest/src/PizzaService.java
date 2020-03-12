import java.util.HashMap;
import java.util.Map;

public class PizzaService {
    public static void addPizza(Pizza pizza) {
        pizzas.put(pizza.getName(), pizza);
    }

    public static void removePizza(String name) {
        pizzas.remove(name);
    }

    public static Pizza getPizza(String name) {
        return pizzas.get(name);
    }

    private static Map<String, Pizza> pizzas = new HashMap<String, Pizza>();

    static {
        generatePizzas();
    }

    private static void generatePizzas() {
        addPizza(new Pizza("Pizza1", 43.12, 3));
        addPizza(new Pizza("Pizza2", 3.12, 13));
        addPizza(new Pizza("Pizza3", 4.27, 32));

    }
}
