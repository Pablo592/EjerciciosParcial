package ar.edu.iua.iw3.negocio;

import java.util.List;

import ar.edu.iua.iw3.modelo.Menu;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

public interface IMenuNegocio {

	public List<Menu> listado() throws NegocioException;

	public Menu cargar(long id) throws NegocioException, NoEncontradoException;

	public Menu agregar(Menu menu) throws NegocioException, EncontradoException;

	public Menu modificar(Menu menu) throws NegocioException, NoEncontradoException;

	public void eliminar(long id) throws NegocioException, NoEncontradoException;


}
