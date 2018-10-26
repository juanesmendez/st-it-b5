package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

class SQLFacturaProducto {
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
	public SQLFacturaProducto (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public long agregarFacturaProducto(PersistenceManager pm,long idFactura, long idProducto, long numUnidades) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaFacturaProductos() + " VALUES (?,?,?)");
		q.setParameters(idFactura,idProducto,numUnidades);
		return (long) q.executeUnique();
	}
}
