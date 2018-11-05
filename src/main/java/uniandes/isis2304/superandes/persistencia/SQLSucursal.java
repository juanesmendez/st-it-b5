package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Sucursal;

public class SQLSucursal {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperandes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperandes ps;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLSucursal (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de las SUCURSALES de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos SUCURSAL
	 */
	public List<Sucursal> darSucursales(PersistenceManager pm) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL,"SELECT * FROM "+ ps.darTablaSucursal());
		q.setResultClass(Sucursal.class);
		return (List<Sucursal>) q.executeList();
	}


	public long agregarSucursal(PersistenceManager pm, long idSucursal, String ciudad, String direccion,
			String nombre) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "INSERT INTO "+ps.darTablaSucursal() + " VALUES (?,?,?,?)");
		q.setParameters(idSucursal,ciudad,direccion,nombre);
		return (long) q.executeUnique();
	}


	public Sucursal darSucursal(PersistenceManager pm,long idSucursal) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL,"SELECT * FROM " + ps.darTablaSucursal() + " WHERE id = ?");
		q.setParameters(idSucursal);
		q.setResultClass(Sucursal.class);
		return (Sucursal) q.executeUnique();
	}
}
