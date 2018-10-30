package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import oracle.net.aso.t;
import uniandes.isis2304.superandes.negocio.Vende;

class SQLVende {
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
	public SQLVende (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public Vende darPorIdSucursalYIdProducto(PersistenceManager pm, long idSucursal, long idProducto) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL,"SELECT * FROM "+ps.darTablaVende()+" WHERE idSucursal = ? AND idProducto = ?");
		q.setParameters(idSucursal,idProducto);
		q.setResultClass(Vende.class);
		return (Vende) q.executeUnique();
	}

	public List<Object[]> darProductosDisponiblesSucursal(PersistenceManager pm, int idSucursal) {
		// TODO Auto-generated method stub
		String sql = "SELECT " + ps.darTablaVende() + ".idProducto, " + ps.darTablaProductos() + ".nombre, " + ps.darTablaVende() + ".precio, " + ps.darTablaProductoEstante() + ".cantidad "
				+ "FROM " + ps.darTablaEstantes() + " "
				+ "INNER JOIN " + ps.darTablaProductoEstante() + " ON " + ps.darTablaEstantes() + ".id = " + ps.darTablaProductoEstante() + ".idEstante "
				+ "INNER JOIN " + ps.darTablaVende() + " ON " + ps.darTablaProductoEstante() + ".idProducto = " + ps.darTablaVende() + ".idProducto "
				+ "INNER JOIN " + ps.darTablaProductos() + " ON " + ps.darTablaVende() + ".idProducto = " + ps.darTablaProductos() + ".id "
				+ "WHERE " + ps.darTablaVende() + ".idSucursal = ?";    
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idSucursal);
		return q.executeList();
	}
}
