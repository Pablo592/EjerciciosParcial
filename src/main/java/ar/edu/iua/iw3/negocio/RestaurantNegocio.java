package ar.edu.iua.iw3.negocio;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.iw3.modelo.Menu;
import ar.edu.iua.iw3.modelo.Restaurant;
import ar.edu.iua.iw3.modelo.persistencia.MenuRepository;
import ar.edu.iua.iw3.modelo.persistencia.RestaurantRepository;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

@Service
public class RestaurantNegocio implements IRestaurantNegocio {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestaurantRepository restaurantDAO;

	
	@Override
	public List<Restaurant> listado() throws NegocioException {
		try {
			return restaurantDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public Restaurant agregar(Restaurant restaurant) throws NegocioException, EncontradoException {
		try {
			cargar(restaurant.getId());
			throw new EncontradoException("Ya existe un restaurante con id=" + restaurant.getIdMenu());
		} catch (NoEncontradoException e) {
		}
		try {
			return restaurantDAO.save(restaurant);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}

	}

	@Override
	public Restaurant cargar(long id) throws NegocioException, NoEncontradoException {
		Optional<Restaurant> o;
		try {
			o = restaurantDAO.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if (!o.isPresent()) {
			throw new NoEncontradoException("No se encuentra el restaurante con id=" + id);
		}
		return o.get();

	}

	@Override
	public Restaurant modificar(Restaurant restaurant) throws NegocioException, NoEncontradoException {
		cargar(restaurant.getId());

		try {
			return restaurantDAO.save(restaurant);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public void eliminar(long id) throws NegocioException, NoEncontradoException {
		cargar(id);

		try {
			restaurantDAO.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

}
