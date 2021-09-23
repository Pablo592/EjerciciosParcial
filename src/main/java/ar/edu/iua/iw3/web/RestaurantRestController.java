package ar.edu.iua.iw3.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.iw3.modelo.Menu;
import ar.edu.iua.iw3.modelo.Restaurant;
import ar.edu.iua.iw3.negocio.IRestaurantNegocio;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

@RestController
public class RestaurantRestController {
	
	@Autowired
	private IRestaurantNegocio restaurantNegocio;
	

	@GetMapping(value="/restaurant")
	public ResponseEntity<List<Restaurant>> listado() throws NegocioException {
			try {
				return new ResponseEntity<List<Restaurant>>(restaurantNegocio.listado(), HttpStatus.OK);
			} catch (NegocioException e) {
				return new ResponseEntity<List<Restaurant>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	}

	@GetMapping(value="/restaurant/{id}")
	public ResponseEntity<Restaurant> cargar(@PathVariable("id") long id) throws NegocioException, NoEncontradoException {
		try {
			return new ResponseEntity<Restaurant>(restaurantNegocio.cargar(id), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Restaurant>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value="/restaurant")
	public ResponseEntity<String> agregar(@RequestBody Restaurant restaurant) throws NegocioException, EncontradoException {
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

	@PutMapping(value="/restaurant")
	public ResponseEntity<String> modificar(Restaurant restaurant) throws NegocioException, NoEncontradoException {
		try {
			restaurantNegocio.modificar(restaurant);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value="/restaurant/{id}")
	public ResponseEntity<String> eliminar(long id) throws NegocioException, NoEncontradoException {
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
