package ar.edu.iua.iw3.modelo.persistencia;
import ar.edu.iua.iw3.modelo.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository  extends JpaRepository <Menu,Long> {



}
