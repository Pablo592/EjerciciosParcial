package ar.edu.iua.iw3.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "restaurante")
public class Restaurant implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 30, nullable = false)
	private String name;

	@Column(length = 30, unique = true)
	private String address;

	@Column(name = "hora_inicio")
	@Temporal(TemporalType.TIME)
	private Date timeStart;

	@Column(name = "hora_fin")
	@Temporal(TemporalType.TIME)
	private Date timeEnd;

	@Column(nullable = false)
	private int points;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idMenu")
	private Menu idMenu;
	
	
	public Menu getIdMenu() {
		return idMenu;
	}

	
	public void setIdMenu(Menu idMenu) {
		this.idMenu = idMenu;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	
}

/*
 * Objetivo: Implementar una API backend que soporte la necesidad de negocio,
 * con el diseño del modelo de datos adecuado a dicha necesidad.
 * 
 * Problema a resolver: Se debe desarrollar el backend de una aplicación web que
 * permita dar soporte a una aplicación de delivery de comidas, al estilo
 * “Rappi”. La API debe manejar restaurantes (donde estarán englobados todos los
 * locales de comida donde se podrá pedir a través de la aplicación, por ej:
 * Johnny B Good, Casserato, etc). Los restaurantes tienen un nombre, una
 * dirección, un horario de apertura, un horario de cierre y una puntuación. A
 * su vez, cada restaurante ofrecerá un unico menú de comidas. Este menú tendrá
 * un nombre, un precio, una cantidad y una unidad (ej: para un lomito la
 * cantidad es 1 y la unidad es porción. Para un helado la cantidad es 250 y la
 * unidad es gramos). La API deberá permitir crear restaurantes, crear el menú,
 * asignar el menú a un restaurante, modificar datos tanto de restaurantes como
 * menú, eliminar un restaurante y/o eliminar una comida. Además, a través de
 * servicios rest se podrá: ● Consultar el restaurante con mayor puntaje. ●
 * Consultar el listado de restaurantes abiertos en determinado horario. ●
 * Consultar el menú de comidas para el restaurante con X nombre. ● Consultar el
 * restaurante que tiene disponible X comida. ● Consultar la dirección de X
 * restaurante. Para este desarrollo se deben utilizar las herramientas
 * trabajadas en clase: spring boot, hibernate y maven y respetar los patrones
 * MVC y DAO. Se solicitará que se presenten tests usando Postman para cada
 * servicio. Aclaraciones: ❏ El proyecto deberá ser creado desde cero y
 * utilizarse una nueva base de datos. ❏ El trabajo debe presentarse en grupos
 * de a 2 alumnos.
 */
