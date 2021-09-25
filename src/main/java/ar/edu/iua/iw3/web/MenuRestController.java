package ar.edu.iua.iw3.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ar.edu.iua.iw3.negocio.IMenuNegocio;
import ar.edu.iua.iw3.negocio.MenuNegocio;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

@RestController
public class MenuRestController {
	
	private Logger log = LoggerFactory.getLogger(MenuNegocio.class);
	
	@Autowired
	private IMenuNegocio menuNegocio;
	
	

	@GetMapping(value="/menu")
	public ResponseEntity<List<Menu>> listado() throws NegocioException {
			try {
				return new ResponseEntity<List<Menu>>(menuNegocio.listado(), HttpStatus.OK);
			} catch (NegocioException e) {
				return new ResponseEntity<List<Menu>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	}
	
	@PostMapping(value="/menu")
	public ResponseEntity<String> agregar(@RequestBody Menu menu) {
		try {
			Menu respuesta=menuNegocio.agregar(menu);
			HttpHeaders responseHeaders=new HttpHeaders();
			responseHeaders.set("location", "/menu/"+respuesta.getId());
			return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (EncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.FOUND);
		}
	}
	
	@GetMapping(value="/menu/{id}")
	public ResponseEntity<Menu> cargar(@PathVariable("id") long id) throws NegocioException, NoEncontradoException {
		try {
			return new ResponseEntity<Menu>(menuNegocio.cargar(id), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Menu>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<Menu>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value="/menu")
	public ResponseEntity<String> modificar(@RequestBody  Menu menu) throws NegocioException, NoEncontradoException {
		try {
			menuNegocio.modificar(menu);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value="/menu/{id}")
	public ResponseEntity<String> eliminar(@PathVariable("id") long id) throws NegocioException, NoEncontradoException {
		try {
			menuNegocio.eliminar(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	
}
