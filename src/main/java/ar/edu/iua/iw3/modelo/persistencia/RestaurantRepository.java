package ar.edu.iua.iw3.modelo.persistencia;
import ar.edu.iua.iw3.modelo.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository <Restaurant,Long> {

}
