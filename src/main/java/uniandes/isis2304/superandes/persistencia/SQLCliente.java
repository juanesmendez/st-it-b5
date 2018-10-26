package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Cliente;

class SQLCliente {
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
	public SQLCliente (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public long agregarCliente(PersistenceManager pm,long identificacion, String tipo, String nombre, String correo, String direccion) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "INSERT INTO "+ ps.darTablaClientes() + " VALUES (?,?,?,?,?,null) ");
		q.setParameters(identificacion,tipo,nombre,correo,direccion);
		return (long) q.executeUnique();
	}

	public Object darCliente(PersistenceManager pm, long idCliente) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL,"SELECT * FROM " + ps.darTablaClientes() + " WHERE id = ?");
		q.setParameters(idCliente);
		return q.executeUnique();
	}
}
