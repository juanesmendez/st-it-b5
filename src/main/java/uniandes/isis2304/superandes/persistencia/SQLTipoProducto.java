package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import oracle.net.aso.t;
import uniandes.isis2304.superandes.negocio.TipoProducto;

class SQLTipoProducto {
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
	public SQLTipoProducto (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public List<TipoProducto> darTipoProductos(PersistenceManager pm) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL,"SELECT * FROM "+ps.darTablaTipoProducto());
		q.setResultClass(TipoProducto.class);
		return (List<TipoProducto>) q.executeList();
	}

	public Object verificarTipoProductoOfrecidoPorSucursal(PersistenceManager pm, long idTipoProducto,
			long idSucursal) {
		// TODO Auto-generated method stub
		String sql = "SELECT * ";
		sql += "FROM "+ ps.darTablaTipoProducto() + " ";
		sql += "INNER JOIN " + ps.darTablaCategoriaSucursal() + " ON " + ps.darTablaTipoProducto() + ".idCategoria = "+ ps.darTablaCategoriaSucursal() + ".idCategoria ";
		sql += "WHERE " + ps.darTablaTipoProducto() + ".id = ?";
		
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idTipoProducto);
		
		return (Object)q.executeUnique();
	}
}
