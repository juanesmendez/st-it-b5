package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Bodega;
import uniandes.isis2304.superandes.negocio.VOBodega;

class SQLBodega {
	
	
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
	public SQLBodega (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public long agregarBodega(PersistenceManager pm, long idBodega, long idSucursal, long idTipoProducto,
			double volumen, double peso) {
		// TODO Auto-generated method stub
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaBodegas() + " VALUES (?,?,?,?,?)");
		q.setParameters(idBodega,idSucursal,idTipoProducto,volumen,peso);
		return (long) q.executeUnique();
	}
	
	public List<Object> darBodegasPorTipoProductoYSucursal(PersistenceManager pm,long idSucursal, long idTipoProducto) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL,"SELECT id FROM "+ps.darTablaBodegas()+" WHERE idSucursal = ? AND idTipoProducto = ?");
		q.setParameters(idSucursal, idTipoProducto);
		q.setResultClass(Bodega.class);
		return (List<Object>)q.executeList();
	}

	public Object darCantidadTotalProductos(PersistenceManager pm, long  idSucursal, long idProducto) {
		// TODO Auto-generated method stub
		String sql = "SELECT "+ps.darTablaProductoBodega() + ".idProducto, SUM ("+ps.darTablaProductoBodega()+".cantidad) ";
		sql += "FROM " + ps.darTablaBodegas() + " ";
		sql += "INNER JOIN "+ps.darTablaProductoBodega()+" ON "+ps.darTablaBodegas()+".id = "+ps.darTablaProductoBodega()+".idBodega ";
		sql += "WHERE "+ps.darTablaBodegas()+".idSucursal = ? AND "+ps.darTablaProductoBodega()+".idProducto = ? ";
		sql += "GROUP BY "+ps.darTablaProductoBodega()+".idProducto";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idSucursal,idProducto);
		return (Object) q.executeUnique();
	}

	public Object darVolumenYPesoTotalCapacidad(PersistenceManager pm, long idSucursal, long idTipoProducto) {
		// TODO Auto-generated method stub
		String sql = "SELECT " + ps.darTablaBodegas() + ".idSucursal, " + ps.darTablaBodegas() + ".idTipoProducto, SUM (" + ps.darTablaBodegas() + ".volumen), SUM (" + ps.darTablaBodegas() + ".peso) ";
		sql +=	"FROM " + ps.darTablaBodegas() +" ";
		sql +=	"WHERE " + ps.darTablaBodegas() + ".idTipoProducto = ? AND " + ps.darTablaBodegas() + ".idSucursal = ? ";
		sql += "GROUP BY " + ps.darTablaBodegas() + ".idSucursal, " + ps.darTablaBodegas() + ".idTipoProducto";
		
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idTipoProducto,idSucursal);
		return (Object) q.executeUnique();
	}

	public Object darVolumenYPesoOcupado(PersistenceManager pm, long idSucursal, long idTipoProducto) {
		// TODO Auto-generated method stub
		String sql = "SELECT " + ps.darTablaBodegas() + ".idSucursal, " + ps.darTablaBodegas() + ".idTipoProducto, SUM (" + ps.darTablaProductoBodega() + ".cantidad * " + ps.darTablaProductos() + ".volEmpaque), SUM ( " + ps.darTablaProductoBodega() + ".cantidad * "+ ps.darTablaProductos() +".pesoEmpaque) ";
		sql += "FROM " + ps.darTablaProductoBodega()+ " ";
		sql += "INNER JOIN " + ps.darTablaProductos() + " ON " + ps.darTablaProductoBodega() + ".idProducto = " + ps.darTablaProductos() + ".id ";
		sql += "INNER JOIN " + ps.darTablaBodegas() + " ON " + ps.darTablaBodegas() + ".id = " + ps.darTablaProductoBodega() + ".idBodega ";
		sql += "WHERE " + ps.darTablaBodegas() + ".idSucursal = ? AND " + ps.darTablaBodegas() + ".idTipoProducto = ? ";
		sql += "GROUP BY " + ps.darTablaBodegas() + ".idSucursal, " +  ps.darTablaBodegas() + ".idTipoProducto";
		Query q	 = pm.newQuery(SQL, sql);
		q.setParameters(idSucursal,idTipoProducto);
		return (Object) q.executeUnique();
	}

	
}
