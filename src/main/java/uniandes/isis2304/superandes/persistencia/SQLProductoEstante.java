package uniandes.isis2304.superandes.persistencia;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLProductoEstante {
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
	public SQLProductoEstante (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public long actualizarCantidad(PersistenceManager pm, long idSucursal, long idProducto, long numUnidades) {
		// TODO Auto-generated method stub
		String sql = "UPDATE (SELECT " + ps.darTablaEstantes() + ".id, " + ps.darTablaEstantes() + ".idSucursal, " + ps.darTablaProductoEstante() + ".idProducto, " + ps.darTablaProductoEstante() + ".cantidad ";
		sql +=  "FROM " + ps.darTablaEstantes() + " ";
		sql += "INNER JOIN " + ps.darTablaProductoEstante() + " ON " + ps.darTablaEstantes() + ".id = " + ps.darTablaProductoEstante() + ".idEstante ";
		sql += "WHERE " + ps.darTablaEstantes() + ".idSucursal = ? AND  " + ps.darTablaProductoEstante() + ".idProducto = ? ) T ";
		sql += "SET T.CANTIDAD = T.CANTIDAD - ?";
		Query q = pm.newQuery(SQL, sql);
		/*
		Map<String,Long> params = new HashMap();
		params.put("idsucursal", idSucursal);
		params.put("idproducto", idProducto);
		params.put("cantidad", numUnidades);
		q.setNamedParameters(params);
		*/
		q.setParameters(idSucursal,idProducto,numUnidades);
		
		return (long)q.executeUnique();
	}
}
