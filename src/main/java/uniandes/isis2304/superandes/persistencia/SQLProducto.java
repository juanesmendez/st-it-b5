package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Producto;

class SQLProducto {
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
	public SQLProducto (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}
	/**
	 * Consulta todos los poductos de superandes de la tabla PRODUCTO y los retorna en una lista de objetos Producto
	 * @param pm
	 * @return - La lista de objetos Producto 
	 */
	public List<Producto> darProductos(PersistenceManager pm) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL,"SELECT * FROM "+ps.darTablaProductos());
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
	public long agregarProducto(PersistenceManager pm,long idProducto, String nombre, String marca, long idTipoproducto, String presentacion,
			double cantPres, String uniMed, double volEmpaque, double pesoEmpaque, String codBarras) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaProductos() + " values (?,?,?,?,?,?,?,?,?,?)");
		q.setParameters(idProducto,nombre,marca,idTipoproducto,presentacion,cantPres,uniMed,volEmpaque,pesoEmpaque,codBarras);
		return (long) q.executeUnique();
	}
	public Producto darProducto(PersistenceManager pm, long idProducto) {
		Query q = pm.newQuery(SQL, "SELECT * FROM "+ps.darTablaProductos()+" WHERE id = ?");
		q.setParameters(idProducto);
		q.setResultClass(Producto.class);
		return (Producto) q.executeUnique();
	}
	public Object darProductoOfrecidoPorSucursal(PersistenceManager pm,long idProducto, long idSucursal) {
		String sql = "SELECT "+ ps.darTablaProductos()+".id, "+ps.darTablaCategoriaSucursal()+".idSucursal ";
		sql += "FROM "+ps.darTablaProductos()+" ";
		sql += "INNER JOIN "+ps.darTablaTipoProducto()+" ON "+ps.darTablaProductos()+".idTipoProducto = "+ps.darTablaTipoProducto()+".id ";
		sql += "INNER JOIN "+ps.darTablaCategoriaSucursal()+" ON "+ps.darTablaTipoProducto()+".idCategoria = "+ps.darTablaCategoriaSucursal()+".idCategoria ";
		sql += "WHERE "+ps.darTablaProductos()+".id = ? AND "+ ps.darTablaCategoriaSucursal()+ ".idSucursal = ?";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idProducto,idSucursal);
		return q.executeUnique();
	}
	
}	
