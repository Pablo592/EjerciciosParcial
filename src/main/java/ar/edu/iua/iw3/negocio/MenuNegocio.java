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
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

@Service
public class MenuNegocio implements IMenuNegocio {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MenuRepository menuDAO;
	
	@Override
	public List<Menu> listado() throws NegocioException {
		try {
			return menuDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public Menu cargar(long id) throws NegocioException, NoEncontradoException {
		Optional<Menu> o;
		try {
			o = menuDAO.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if (!o.isPresent()) {
			throw new NoEncontradoException("No se encuentra el menu con id=" + id);
		}
		return o.get();
	}

	@Override
	public Menu agregar(Menu menu) throws NegocioException, EncontradoException {
		try {
			cargar(menu.getId());
			throw new EncontradoException("Ya existe un restaurante con id=" + menu.getId());
		} catch (NoEncontradoException e) {
		}
		try {
			return menuDAO.save(menu);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public Menu modificar(Menu menu) throws NegocioException, NoEncontradoException {
		cargar(menu.getId());

		try {
			return menuDAO.save(menu);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public void eliminar(long id) throws NegocioException, NoEncontradoException {
		cargar(id);

		try {
			menuDAO.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}

	}

}
