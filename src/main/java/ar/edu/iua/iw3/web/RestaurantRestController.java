package ar.edu.iua.iw3.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.iua.iw3.modelo.Restaurant;
import ar.edu.iua.iw3.negocio.IRestaurantNegocio;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

@RestController
public class RestaurantRestController {
	
	@Autowired
	private IRestaurantNegocio restaurantNegocio;
	

	@GetMapping(value="/restaurants")
	public ResponseEntity<List<Restaurant>> listado() throws NegocioException {
			try {
				return new ResponseEntity<List<Restaurant>>(restaurantNegocio.listado(), HttpStatus.OK);
			} catch (NegocioException e) {
				return new ResponseEntity<List<Restaurant>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	}

	@GetMapping(value="/restaurants/puntaje-mayor")
	public ResponseEntity<Restaurant> restauranteConPuntajeMayor(){
		try {
			return new ResponseEntity<Restaurant>(restaurantNegocio.encontrarRestauranteConMasPuntaje(), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Restaurant>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value="/restaurants/opens")		//para obtener los parametros que vienen despues del ?
	public ResponseEntity<List<Restaurant>> restauranteOpens(@RequestParam ("hora") int hora)  {
		try {
			Date o = new Date();
			o.setHours(hora);
			return new ResponseEntity<List<Restaurant>>(restaurantNegocio.encontrarRestauranteAbiertos(o), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<List<Restaurant>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<List<Restaurant>>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value="/restaurants/buscar")
	public ResponseEntity<Restaurant> buscarRestaurantePorNombre(@RequestParam ("name") String name)  {
		try {
			return new ResponseEntity<Restaurant>(restaurantNegocio.encontrarRestaurantePorNombre(name), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Restaurant>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
		}
	}


	@GetMapping(value="/restaurants/buscarPorComida")
	public ResponseEntity<List<Restaurant>> buscarRestaurantePorComida(@RequestParam ("comida") String comida)  {
		try {
			return new ResponseEntity<List<Restaurant>>(restaurantNegocio.encontrarRestaurantesPorComida(comida), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<List<Restaurant>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<List<Restaurant>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value="/restaurants/buscarPorDireccion")
	public ResponseEntity<Restaurant> buscarRestaurantePorDireccion(@RequestParam ("direccion") String direccion)  {
		try {
			return new ResponseEntity<Restaurant>(restaurantNegocio.encontrarRestaurantesPorDireccion(direccion), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Restaurant>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value="/restaurants")
	public ResponseEntity<String> agregar(@RequestBody Restaurant restaurant) {
		try {
			Restaurant respuesta=restaurantNegocio.agregar(restaurant);
			HttpHeaders responseHeaders=new HttpHeaders();
			responseHeaders.set("location", "/restaurant/"+respuesta.getId());
			return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (EncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.FOUND);
		}
	}

	@PutMapping(value="/restaurants")
	public ResponseEntity<String> modificar(@RequestBody Restaurant restaurant)  {
		try {
			restaurantNegocio.modificar(restaurant);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value="/restaurants/{id}")
	public ResponseEntity<String> eliminar(@PathVariable("id") long id)  {
		try {
			restaurantNegocio.eliminar(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	
}
