package pizza.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pizza.dto.Dish;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRep extends CrudRepository<Dish, Long> {

    Optional<Dish> findByName(String name);

    List<Dish> findAll();

}
