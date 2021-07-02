package pizza.repository;

import org.springframework.data.repository.CrudRepository;
import pizza.dto.Order;

public interface OrderRep extends CrudRepository<Order, Long> {
}
