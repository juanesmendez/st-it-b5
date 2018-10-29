package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

class SQLEstante {
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
	public SQLEstante (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}
	
	public long agregarEstante(PersistenceManager pm, long idEstante, long idSucursal, long idTipoProducto,
			double volumen, double peso, int niveAbastecimiento) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "INSERT INTO "+ps.darTablaEstantes() + "VALUES (?,?,?,?,?,?)");
		q.setParameters(idEstante,idSucursal,idTipoProducto,volumen,peso,niveAbastecimiento);
		return (long) q.executeUnique();
	}

	public Object darCantidadTotalProductos(PersistenceManager pm, long idSucursal, long idProducto) {
		// TODO Auto-generated method stub
		String sql = "SELECT "+ps.darTablaProductoEstante() + ".idProducto, SUM ("+ps.darTablaProductoEstante()+".cantidad) ";
		sql += "FROM " + ps.darTablaEstantes() + " ";
		sql += "INNER JOIN "+ps.darTablaProductoEstante()+" ON "+ps.darTablaEstantes()+".id = "+ps.darTablaProductoEstante()+".idEstante ";
		sql += "WHERE "+ps.darTablaEstantes()+".idSucursal = ? AND "+ps.darTablaProductoEstante()+".idProducto = ? ";
		sql += "GROUP BY "+ps.darTablaProductoEstante()+".idProducto";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idSucursal,idProducto);
		return (Object)q.executeUnique();
	}

	public Object darVolumenYPesoTotalCapacidad(PersistenceManager pm, long idSucursal, long idTipoProducto) {
		// TODO Auto-generated method stub
		String sql = "SELECT " + ps.darTablaEstantes() + ".idSucursal, " + ps.darTablaEstantes() + ".idTipoProducto, SUM (" + ps.darTablaEstantes() + ".volumen), SUM (" + ps.darTablaEstantes() + ".peso) ";
		sql +=	"FROM " + ps.darTablaEstantes() +" ";
		sql +=	"WHERE " + ps.darTablaEstantes() + ".idTipoProducto = ? AND " + ps.darTablaEstantes() + ".idSucursal = ? ";
		sql += "GROUP BY " + ps.darTablaEstantes() + ".idSucursal, " + ps.darTablaEstantes()+ ".idTipoProducto";
		
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idTipoProducto,idSucursal);
		return (Object) q.executeUnique();
	}

	public Object darVolumenYPesoOcupado(PersistenceManager pm, long idSucursal, long idTipoProducto) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT " + ps.darTablaEstantes()+ ".idSucursal, " + ps.darTablaEstantes()+ ".idTipoProducto, SUM (" + ps.darTablaProductoEstante() + ".cantidad * " + ps.darTablaProductos() + ".volEmpaque), SUM ( " + ps.darTablaProductoEstante() + ".cantidad * "+ ps.darTablaProductos() +".pesoEmpaque) ";
		sql += "FROM " + ps.darTablaProductoEstante()+ " ";
		sql += "INNER JOIN " + ps.darTablaProductos() + " ON " + ps.darTablaProductoEstante() + ".idProducto = " + ps.darTablaProductos() + ".id ";
		sql += "INNER JOIN " + ps.darTablaEstantes() + " ON " + ps.darTablaEstantes() + ".id = " + ps.darTablaProductoEstante() + ".idEstante ";
		sql += "WHERE " + ps.darTablaEstantes() + ".idSucursal = ? AND " + ps.darTablaEstantes() + ".idTipoProducto = ? ";
		sql += "GROUP BY " + ps.darTablaEstantes() + ".idSucursal, " +  ps.darTablaEstantes() + ".idTipoProducto";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idSucursal,idTipoProducto);
		return (Object) q.executeUnique();
	}

	public Object darCantidadTotalDeUnProducto(PersistenceManager pm,long idSucursal, long idProducto) {
		// TODO Auto-generated method stub
		String sql = "SELECT " + ps.darTablaProductoEstante() + ".idProducto, SUM (" + ps.darTablaProductoEstante() + ".cantidad) ";
		sql +=	"FROM " + ps.darTablaEstantes() + " ";
		sql +=	"INNER JOIN " + ps.darTablaProductoEstante() + " ON " + ps.darTablaEstantes() + ".id = " + ps.darTablaProductoEstante() + ".idEstante ";
		sql +=	"WHERE " + ps.darTablaEstantes() + ".idSucursal = ? AND " + ps.darTablaProductoEstante() + ".idProducto = ? ";
		sql +=	"GROUP BY " + ps.darTablaProductoEstante() + ".idProducto";
		Query q = pm.newQuery(SQL,sql);
		q.setParameters(idSucursal,idProducto);
		return q.executeUnique();
	}

	public List<Object[]> darIndiceOcupacionPorSucursal(PersistenceManager pm,int idSucursal) {
		// TODO Auto-generated method stub
		String sql = "SELECT " + ps.darTablaEstantes() + ".id, J.totVol/"+ps.darTablaEstantes() + ".volumen *100, J." + "totPeso/" + ps.darTablaEstantes() + ".peso * 100 "
				+ "FROM "
				+ "(SELECT " + ps.darTablaEstantes() + ".id, NVL((SUM(" + ps.darTablaProductos() + ".volEmpaque * " + ps.darTablaProductoEstante() + ".cantidad)), 0) AS TOTVOL, NVL((SUM(" + ps.darTablaProductos() + ".pesoEmpaque * " + ps.darTablaProductoEstante() + ".cantidad)),0) AS TOTPESO "
				+ "FROM " + ps.darTablaEstantes() + " "
				+ "LEFT OUTER JOIN " + ps.darTablaProductoEstante() +" ON " + ps.darTablaEstantes() + ".id = " + ps.darTablaProductoEstante() + ".idEstante "
				+ "LEFT OUTER JOIN " + ps.darTablaProductos() + " ON " + ps.darTablaProductoEstante() + ".idProducto =" + ps.darTablaProductos() + ".id "
				+ "WHERE " + ps.darTablaEstantes() + ".idSucursal = ? "
				+ "GROUP BY " + ps.darTablaEstantes() + ".id) J "
				+ "INNER JOIN " + ps.darTablaEstantes() +" ON J.id = " + ps.darTablaEstantes() + ".id";
		Query q = pm.newQuery(SQL,sql);
		q.setParameters(idSucursal);
		return q.executeList();
	}

	
}
