package uniandes.isis2304.superandes.persistencia;

import java.math.BigDecimal;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Provee;
import uniandes.isis2304.superandes.negocio.VOProvee;

class SQLProvee {
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
	public SQLProvee (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public Provee darProvee(PersistenceManager pm,long idProveedor, long idProducto) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "SELECT * FROM "+ps.darTablaProvee() +" WHERE idProveedor = ? AND idProducto = ?");
		q.setParameters(idProveedor,idProducto);
		q.setResultClass(Provee.class);
		return (Provee)q.executeUnique();
	}

	public long darListaProveePorIdProducto(PersistenceManager pm,long idProducto) {
		Query q = pm.newQuery(SQL,"SELECT idProveedor FROM " + ps.darTablaProvee() + " WHERE idProducto = ?");
		q.setParameters(idProducto);
		if (q.executeList().size() != 0)
		{
			Object a = q;
			return ((BigDecimal) q.executeList().get(0)).longValue();
		}
		return -1;
		 
				
	}
}
