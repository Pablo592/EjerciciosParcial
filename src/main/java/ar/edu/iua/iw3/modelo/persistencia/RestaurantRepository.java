package ar.edu.iua.iw3.modelo.persistencia;
import ar.edu.iua.iw3.modelo.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository <Restaurant,Long> {
    Optional<Restaurant> findByAddress(String address);

    Optional<Restaurant> findFirstByOrderByPointsDesc();

    List<Restaurant> findByTimeStartBeforeAndTimeEndAfter(Date fecha1,Date fecha2);

    Optional<Restaurant> findByName(String name);

    List<Restaurant> findByIdMenuName(String comida);


}
