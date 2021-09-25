package ar.edu.iua.iw3.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.iua.iw3.modelo.Restaurant;
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
			if(null!=findRestaurantByAddress(restaurant.getAddress()))
				throw new EncontradoException("Ya existe una restaurante en la direccion  =" + restaurant.getAddress());
			cargar(restaurant.getId());  	// tira excepcion sino no lo encuentra
			throw new EncontradoException("Ya existe un restaurante con id=" + restaurant.getId());
		} catch (NoEncontradoException e) {
		}
		try {
			return restaurantDAO.save(restaurant);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}

	}

	private Restaurant findRestaurantByAddress(String address){
		Optional <Restaurant>  o  = restaurantDAO.findByAddress(address);
		if(o.isPresent())
			return o.get();
		else
			return null;
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
		//me viene un id existe con su monto y fecha
		//Paso 1: busco existencia del id del restaurante
		//Paso 2: busco existencia de "direccion"
		//Paso 3_a: si el "address" del restaurante esta asignado a un restaurante con diferente id del que se quiere modificar entonces tengo que generar un error
		//Paso 3_b: si la "address" del restaurante es el mismo id del restaurante que me viene, entonces no se debe de generar error
		//Paso 4:  Sino ninguna restaurante  tiene asignado la "address" se lo debe de modificar sin problemas

		cargar(restaurant.getId()); //Paso 1
		Restaurant restaurantExist = findRestaurantByAddress(restaurant.getAddress());

		if(null != restaurantExist ) { //Paso 2

			if (restaurant.getId() != restaurantExist.getId())
				throw new NegocioException("Ya existe un restuarante con el id" + restaurantExist.getId() + " con direccion  ="
						+ restaurant.getAddress() );	//Paso 3_a

			return	saveRestaurant(restaurant);	//Paso 3_b
		}

		return saveRestaurant(restaurant);	//Paso 4
	}

	private  Restaurant saveRestaurant(Restaurant restaurant) throws NegocioException {
		try {
			return restaurantDAO.save(restaurant); // sino existe el restaurante lo cargo
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

	@Override
	public Restaurant encontrarRestauranteConMasPuntaje() throws NegocioException, NoEncontradoException {

		Optional<Restaurant> o;
		try {
			o = restaurantDAO.findFirstByOrderByPointsDesc();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if (!o.isPresent()) {
			throw new NoEncontradoException("No hay restaurantes disponibles");
		}
		return o.get();
	}

	@Override
	public List<Restaurant> encontrarRestauranteAbiertos(Date fecha) throws NegocioException, NoEncontradoException {
		List <Restaurant> o = new ArrayList<>();
		try {
			o = restaurantDAO.findByTimeStartBeforeAndTimeEndAfter(fecha,fecha);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if(o.isEmpty())
			throw new NoEncontradoException("No hay restaurantes abiertos en la hora: " + fecha);
		return o;
	}

	@Override
	public Restaurant encontrarRestaurantePorNombre(String name) throws NegocioException, NoEncontradoException {
		Optional<Restaurant> o;
		try {
			o = restaurantDAO.findByName(name);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if (!o.isPresent()) {
			throw new NoEncontradoException("No hay restaurantes con el nombre:" + name);
		}
		return o.get();
	}

	@Override
	public List<Restaurant> encontrarRestaurantesPorComida(String comida) throws NegocioException, NoEncontradoException {
		List <Restaurant> o = new ArrayList<>();
		try {
			o = restaurantDAO.findByIdMenuName(comida);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if(o.isEmpty())
			throw new NoEncontradoException("No hay restaurantes que en su menu contengan la comida: " + comida);
		return o;
	}

	@Override
	public Restaurant encontrarRestaurantesPorDireccion(String direccion) throws NegocioException, NoEncontradoException {
		Optional<Restaurant> o;
		try {
			o = restaurantDAO.findByAddress(direccion);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if (!o.isPresent()) {
			throw new NoEncontradoException("No hay restaurantes en la direccion:" + direccion);
		}
		return o.get();
	}
}
