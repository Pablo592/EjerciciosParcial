package ar.edu.iua.iw3.negocio;

import java.util.Date;
import java.util.List;

import ar.edu.iua.iw3.modelo.Restaurant;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

public interface IRestaurantNegocio {
	
	public List<Restaurant> listado() throws NegocioException;

	public Restaurant cargar(long id) throws NegocioException, NoEncontradoException;

	public Restaurant agregar(Restaurant restaurant) throws NegocioException, EncontradoException;

	public Restaurant modificar(Restaurant restaurant) throws NegocioException, NoEncontradoException;

	public void eliminar(long id) throws NegocioException, NoEncontradoException;

	public  Restaurant encontrarRestauranteConMasPuntaje() throws NegocioException, NoEncontradoException;

	public List<Restaurant> encontrarRestauranteAbiertos(Date fecha) throws NegocioException, NoEncontradoException;

	public Restaurant encontrarRestaurantePorNombre(String name) throws NegocioException, NoEncontradoException;

	public List<Restaurant> encontrarRestaurantesPorComida(String comida) throws NegocioException, NoEncontradoException;

	public Restaurant encontrarRestaurantesPorDireccion(String direccion) throws NegocioException, NoEncontradoException;
}
