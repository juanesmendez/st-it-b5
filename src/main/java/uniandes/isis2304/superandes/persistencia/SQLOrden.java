package uniandes.isis2304.superandes.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Orden;

class SQLOrden {
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
	public SQLOrden (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public long adicionarOrden(PersistenceManager pm, long idOrden, long idProveedor, long idSucursal, long idProducto,
			long cantidad, double precio, Timestamp fecha) {
		// TODO Auto-generated method stub
		
		Query q = pm.newQuery(SQL, "INSERT INTO "+ ps.darTablaOrden() + "(id,idProveedor,idSucursal,idProducto,cantidad,precio,estado,fechaEsperadaEntrega) VALUES(?,?,?,?,?,?,?,?)");
		q.setParameters(idOrden,idProveedor,idSucursal,idProducto,cantidad,precio, "NO ENTREGADO", fecha);
		return (long) q.executeUnique();
	}

	public Orden darOrden(PersistenceManager pm,int idOrden) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "SELECT * FROM "+ps.darTablaOrden()+" WHERE id = ?");
		q.setParameters(idOrden);
		q.setResultClass(Orden.class);
		return (Orden) q.executeUnique();
	}

	public long actualizarOrdenLlegada(PersistenceManager pm, int idOrden, Timestamp fecha, String calificacion) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaOrden() + " SET fechaEntrega = ? AND calificacion = ?");
		q.setParameters(fecha,calificacion);
		return (long) q.executeUnique();
	}

	public List<Orden> darOrdenes(PersistenceManager pm) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaOrden());
		q.setResultClass(Orden.class);
		return (List<Orden>) q.executeList();
	}
}
