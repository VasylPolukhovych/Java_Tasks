package pizza.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pizza.dto.CookedDishTable;

public interface CookedDishRep
        extends CrudRepository<CookedDishTable, Long> {
    @Modifying
    @Query("UPDATE public.cooked_dish" +
            " SET  current_count = :curcount" +
            " WHERE id = :id")
    public void setCount(@Param("curcount") Integer curCount, @Param("id") Long id);
}
