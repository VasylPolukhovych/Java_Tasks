package pizza.repository;

import org.springframework.data.repository.CrudRepository;
import pizza.dto.Order;

import java.util.Optional;

public interface OrderRep extends CrudRepository<Order, Long> {
    Optional<Order> findById(Long Id);

}
