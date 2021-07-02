package pizza.repository;

import org.springframework.data.repository.CrudRepository;
import pizza.dto.DishInOrder;

public interface DishInOrederRep extends CrudRepository<DishInOrder, Long> {
}
