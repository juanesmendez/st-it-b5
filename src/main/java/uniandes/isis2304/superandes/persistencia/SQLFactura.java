package uniandes.isis2304.superandes.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

class SQLFactura {
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
	public SQLFactura (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public long agregarFactura(PersistenceManager pm, long idFactura, long idCliente, long idSucursal, Timestamp fecha,
			double total) {
		// TODO Auto-generated method stub
		
		Query q = pm.newQuery(SQL,"INSERT INTO " + ps.darTablaFacturas() + " VALUES (?,?,?,?,?)");
		q.setParameters(idFactura,idCliente,idSucursal,fecha,total);
		
		return (long) q.executeUnique();
	}
}
