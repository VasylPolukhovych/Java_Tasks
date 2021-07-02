package pizza.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pizza.dto.CookedDish;

import java.util.List;

public interface CookedDishRep
        extends CrudRepository<CookedDish, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE cooked_dish" +
            " SET  current_count = ?1" +
            " WHERE id = ?2", nativeQuery = true)
    public void setCookedDishCount(Integer curCount, Long id);

    public List<CookedDish> findAll();
}
