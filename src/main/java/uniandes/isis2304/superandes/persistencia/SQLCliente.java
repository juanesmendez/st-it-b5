package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.sun.org.apache.bcel.internal.classfile.PMGClass;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class SQLCliente {
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
	 * @param ps - El Manejador de persistencia de la aplicación
	 */
	public SQLCliente (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public long agregarCliente(PersistenceManager pm,long identificacion, String tipo, String nombre, String correo, String direccion) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "INSERT INTO "+ ps.darTablaClientes() + " VALUES (?,?,?,?,?,null) ");
		q.setParameters(identificacion,tipo,nombre,correo,direccion);
		return (long) q.executeUnique();
	}

	public Object darCliente(PersistenceManager pm, long idCliente) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL,"SELECT * FROM " + ps.darTablaClientes() + " WHERE id = ?");
		q.setParameters(idCliente);
		return q.executeUnique();
	}

	public List<Object[]> darClientesFrecuentes(PersistenceManager pm, long idSucursal)
	{
		Query q = pm.newQuery(SQL,"SELECT * FROM " +
				"( SELECT " + ps.darTablaClientes() + ".nombre, idcliente, EXTRACT(month FROM " + ps.darTablaFacturas() +".fecha) AS MES, COUNT( " +ps.darTablaFacturas() + ".id) AS COMPRAS " +
				" FROM " + ps.darTablaClientes() + " INNER JOIN " + ps.darTablaFacturas() + " ON " + ps.darTablaClientes() + ".id = " + ps.darTablaFacturas() + ".idcliente "+
				"WHERE " + ps.darTablaFacturas() + ".idSucursal = ? " +
				"GROUP BY " + ps.darTablaClientes() +".nombre, idcliente, extract(month from " + ps.darTablaFacturas() + ".fecha) " +
				") WHERE COMPRAS >=2 ");
		q.setParameters(idSucursal);
		return (List<Object[]>) q.executeList();
	}

	public List<Object[]> darConsumoComoAdministrador(PersistenceManager pm,long idProducto, Timestamp fechaInicio, Timestamp fechaFinal,
			String criterioOrdenacion, String criterioOrdenacionAscDesc, String criterioAgrupacion) {
		List<Object[]> lista;
		String sql = "";
		String select = "SELECT " +ps.darTablaClientes()+ ".ID, " +ps.darTablaClientes()+ ".NOMBRE, " + ps.darTablaFacturas() +".ID, " + ps.darTablaFacturas() +".IDSUCURSAL, " + ps.darTablaFacturas()+".FECHA, " + ps.darTablaFacturas()+".TOTAL, " + ps.darTablaFacturaProductos() +".IDPRODUCTO, " + ps.darTablaFacturaProductos()+".UNIVENDIDAS ";
		String from =
				" FROM " + ps.darTablaClientes() +
				" INNER JOIN " + ps.darTablaFacturas() + " ON " + ps.darTablaClientes() + ".ID = " + ps.darTablaFacturas() + ".ID" + ps.darTablaClientes() +
				" INNER JOIN " + ps.darTablaFacturaProductos() +  " ON " + ps.darTablaFacturas() + ".ID = " + ps.darTablaFacturaProductos() + ".IDFACTURA" +
				" WHERE " + ps.darTablaFacturaProductos()  +".IDPRODUCTO = ? AND " + ps.darTablaFacturas() + ".FECHA BETWEEN ? and ? ";

		if(criterioAgrupacion.equals("Cliente")){
			select =  "SELECT " + ps.darTablaClientes()+ ".ID, " + ps.darTablaClientes() + ".NOMBRE ,SUM(" + ps.darTablaFacturaProductos()+".UNIVENDIDAS) AS SUMA_TOT";
			sql = select + from;
			sql += " GROUP BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre";


		}
		else if(criterioAgrupacion.equals("Cliente y fecha")){
			select = "SELECT " + ps.darTablaClientes()+ ".ID, " + ps.darTablaClientes() + ".NOMBRE, TRUNC(" +ps.darTablaFacturas() + ".fecha) ,SUM(" + ps.darTablaFacturaProductos()+".UNIVENDIDAS) AS SUMA_TOT";
			sql = select + from;
			sql += " GROUP BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre, TRUNC(" + ps.darTablaFacturas() + ".fecha)";
		}
		else
		{
			sql = select + from;
		}

		if(criterioOrdenacion.equals("Cliente"))
			sql += " ORDER BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre";
		else if (criterioOrdenacion.equals("Numero de unidades compradas"))
			sql += " ORDER BY SUMA_TOT";

		if (!criterioOrdenacion.equals("")) {
			if (criterioOrdenacionAscDesc.equals("Ascendentemente"))
				sql += " ASC";
			else if (criterioOrdenacionAscDesc.equals("Descendentemente"))
				sql += " DESC";
		}
		Query q = pm.newQuery(SQL,sql);
		q.setParameters(idProducto,fechaInicio, fechaFinal);
		lista = (List<Object[]>) q.executeList();
		return lista;
	}

	public List<Object[]> darConsumoComoGerente(PersistenceManager pm,long idProducto, long idSucursal, Timestamp fechaInicio, Timestamp fechaFinal,
													  String criterioOrdenacion, String criterioOrdenacionAscDesc, String criterioAgrupacion) {
		List<Object[]> lista = new ArrayList<Object[]>();
		String sql = "";
		String select = "SELECT " + ps.darTablaClientes() + ".ID, " + ps.darTablaClientes() + ".NOMBRE, " + ps.darTablaFacturas() + ".ID, " + ps.darTablaFacturas() + ".IDSUCURSAL, " + ps.darTablaFacturas() + ".FECHA, " + ps.darTablaFacturas() + ".TOTAL, " + ps.darTablaFacturas() + ps.darTablaProductos() + ".IDPRODUCTO, " + ps.darTablaFacturas() + ps.darTablaProductos() + ".UNIVENDIDAS ";
		String from =
				" FROM " + ps.darTablaClientes() +
				" INNER JOIN " + ps.darTablaFacturas() + " ON " + ps.darTablaClientes() + ".ID = " + ps.darTablaFacturas() + ".ID" + ps.darTablaClientes() +
				" INNER JOIN " + ps.darTablaFacturaProductos() +  " ON " + ps.darTablaFacturas() + ".ID = " + ps.darTablaFacturaProductos() + ".IDFACTURA" +
				" WHERE " + ps.darTablaFacturaProductos()  +".IDPRODUCTO = ? AND " + ps.darTablaFacturas() + ".FECHA BETWEEN ? and ? and " + ps.darTablaFacturas() + ".IdSucursal = ?"  ;

		if(criterioAgrupacion.equals("Cliente")){
			select =  "SELECT " + ps.darTablaClientes()+ ".ID, " + ps.darTablaClientes() + ".NOMBRE ,SUM(" + ps.darTablaFacturaProductos()+".UNIVENDIDAS) AS SUMA_TOT";
			sql = select + from;
			sql += " GROUP BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre";


		}
		else if(criterioAgrupacion.equals("Cliente y fecha")){
			select = "SELECT " + ps.darTablaClientes()+ ".ID, " + ps.darTablaClientes() + ".NOMBRE, TRUNC(" +ps.darTablaFacturas() + ".fecha) ,SUM(" + ps.darTablaFacturaProductos()+".UNIVENDIDAS) AS SUMA_TOT";
			sql = select + from;
			sql += " GROUP BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre, TRUNC(" + ps.darTablaFacturas() + ".fecha)";
		}
		else
		{
			sql = select + from;
		}

		if(criterioOrdenacion.equals("Cliente"))
			sql += " ORDER BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre";
		else if (criterioOrdenacion.equals("Numero de unidades compradas"))
			sql += " ORDER BY SUMA_TOT";

		if (!criterioOrdenacion.equals("")) {
			if (criterioOrdenacionAscDesc.equals("Ascendentemente"))
				sql += " ASC";
			else if (criterioOrdenacionAscDesc.equals("Descendentemente"))
				sql += " DESC";
		}
		Query q = pm.newQuery(SQL,sql);
		q.setParameters(idProducto,fechaInicio, fechaFinal, idSucursal);
		lista = (List<Object[]>) q.executeList();
		return lista;
	}

	public List<Object[]> darConsumoComoCliente(PersistenceManager pm,long idProducto, long idCliente, Timestamp fechaInicio, Timestamp fechaFinal,
													  String criterioOrdenacion, String criterioOrdenacionAscDesc, String criterioAgrupacion) {
		List<Object[]> lista = new ArrayList<Object[]>();
		String sql = "";
		String select = "SELECT " + ps.darTablaClientes() + ".ID, " + ps.darTablaClientes() + ".NOMBRE, " + ps.darTablaFacturas() + ".ID, " + ps.darTablaFacturas() + ".IDSUCURSAL, " + ps.darTablaFacturas() + ".FECHA, " + ps.darTablaFacturas() + ".TOTAL, " + ps.darTablaFacturas() + ps.darTablaProductos() + ".IDPRODUCTO, " + ps.darTablaFacturas() + ps.darTablaProductos() + ".UNIVENDIDAS ";
		String from =
				" FROM " + ps.darTablaClientes() +
				" INNER JOIN " + ps.darTablaFacturas() + " ON " + ps.darTablaClientes() + ".ID = " + ps.darTablaFacturas() + ".ID" + ps.darTablaClientes() +
				" INNER JOIN " + ps.darTablaFacturaProductos() +  " ON " + ps.darTablaFacturas() + ".ID = " + ps.darTablaFacturaProductos() + ".IDFACTURA" +
				" WHERE " + ps.darTablaFacturaProductos()  +".IDPRODUCTO = ? AND " + ps.darTablaFacturas() + ".FECHA BETWEEN ? and ?  and " +ps.darTablaClientes() + ".id= ?";

		if(criterioAgrupacion.equals("Fecha")){
			select = "SELECT " + ps.darTablaClientes()+ ".ID, " + ps.darTablaClientes() + ".NOMBRE, TRUNC(" + ps.darTablaFacturas()  + ".fecha), SUM(" + ps.darTablaFacturaProductos()+".UNIVENDIDAS) AS SUMA_TOT";
			sql = select + from;
			sql += " GROUP BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre, TRUNC(" + ps.darTablaFacturas() + ".fecha)";
			if(criterioOrdenacion.equals("Unidades compradas")){
				sql += " ORDER BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre, SUMA_TOT";
			}else if(criterioOrdenacion.equals("Fecha")){
				sql += " ORDER BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre, TRUNC(" + ps.darTablaFacturas() + ".fecha)";
			}
		}
		else
		{
			sql = select + from;
		}
		if (!criterioOrdenacion.equals("")) {
			if (criterioOrdenacionAscDesc.equals("Ascendentemente"))
				sql += " ASC";
			else if (criterioOrdenacionAscDesc.equals("Descendentemente"))
				sql += " DESC";
		}

		Query q = pm.newQuery(SQL,sql);
		q.setParameters(idProducto,fechaInicio, fechaFinal, idCliente);
		lista = (List<Object[]>) q.executeList();
		return lista;
	}

	public List<Object[]> darNoConsumoComoAdministrador(PersistenceManager pm,long idProducto, Timestamp fechaInicio, Timestamp fechaFinal,
													  String criterioOrdenacion, String criterioOrdenacionAscDesc, String criterioAgrupacion) {
		List<Object[]> lista;
		String sql = "";
		String original = "SELECT * FROM (";
		String minus = " MINUS ( ";
		String select = " SELECT " +ps.darTablaClientes()+ ".ID, " +ps.darTablaClientes()+ ".NOMBRE, " + ps.darTablaFacturas() +".ID AS FACTURA_ID, " + ps.darTablaFacturas() +".IDSUCURSAL, " + ps.darTablaFacturas()+".FECHA, " + ps.darTablaFacturas()+".TOTAL, " + ps.darTablaFacturaProductos() +".IDPRODUCTO, " + ps.darTablaFacturaProductos()+".UNIVENDIDAS ";
		String from =
				" FROM " + ps.darTablaClientes() +
						" FULL OUTER JOIN " + ps.darTablaFacturas() + " ON " + ps.darTablaClientes() + ".ID = " + ps.darTablaFacturas() + ".ID" + ps.darTablaClientes() +
						" FULL OUTER JOIN " + ps.darTablaFacturaProductos() +  " ON " + ps.darTablaFacturas() + ".ID = " + ps.darTablaFacturaProductos() + ".IDFACTURA" ;
		String where = " WHERE " + ps.darTablaFacturaProductos()  +".IDPRODUCTO = ? AND " + ps.darTablaFacturas() + ".FECHA BETWEEN ? and ? ";

		if(criterioAgrupacion.equals("Cliente")){
			String groupBy = " GROUP BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre";
			select =  "SELECT " + ps.darTablaClientes()+ ".ID, " + ps.darTablaClientes() + ".NOMBRE ,SUM(" + ps.darTablaFacturaProductos()+".UNIVENDIDAS) AS SUMA_TOT";
			sql = original + select + from;
			sql += groupBy;
sql += minus + select + from + where  + groupBy + " ))";

		}
		else if(criterioAgrupacion.equals("Cliente y fecha")){
			String groupBy = " GROUP BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre, TRUNC(" + ps.darTablaFacturas() + ".fecha)";
			select = "SELECT " + ps.darTablaClientes()+ ".ID, " + ps.darTablaClientes() + ".NOMBRE, TRUNC(" +ps.darTablaFacturas() + ".fecha) ,SUM(" + ps.darTablaFacturaProductos()+".UNIVENDIDAS) AS SUMA_TOT";
			sql = original + select + from;
			sql += groupBy;
			sql += minus + select + from + where  + groupBy + " ))";
		}
		else
		{
			sql = original + select + from;
			sql += minus + select + from + where  + " ))";
		}

		if(criterioOrdenacion.equals("Cliente"))
			sql += " ORDER BY NOMBRE";
		else if (criterioOrdenacion.equals("Numero de unidades compradas"))
			sql += " ORDER BY SUMA_TOT";

		if (!criterioOrdenacion.equals("")) {
			if (criterioOrdenacionAscDesc.equals("Ascendentemente"))
				sql += " ASC";
			else if (criterioOrdenacionAscDesc.equals("Descendentemente"))
				sql += " DESC";
		}
		Query q = pm.newQuery(SQL,sql);
		System.out.println(sql);
		q.setParameters(idProducto,fechaInicio, fechaFinal);
		lista = (List<Object[]>) q.executeList();
		return lista;
	}

	public List<Object[]> darNoConsumoComoGerente(PersistenceManager pm,long idProducto, long idSucursal, Timestamp fechaInicio, Timestamp fechaFinal,
														String criterioOrdenacion, String criterioOrdenacionAscDesc, String criterioAgrupacion) {
		List<Object[]> lista;
		String sql = "";
		String original = "SELECT * FROM (";
		String minus = " MINUS ( ";
		String select = " SELECT " + ps.darTablaClientes() + ".ID, " + ps.darTablaClientes() + ".NOMBRE, " + ps.darTablaFacturas() + ".ID AS FACTURA_ID, " + ps.darTablaFacturas() + ".IDSUCURSAL, " + ps.darTablaFacturas() + ".FECHA, " + ps.darTablaFacturas() + ".TOTAL, " + ps.darTablaFacturaProductos() + ".IDPRODUCTO, " + ps.darTablaFacturaProductos() + ".UNIVENDIDAS ";
		String from =
				" FROM " + ps.darTablaClientes() +
						" FULL OUTER JOIN " + ps.darTablaFacturas() + " ON " + ps.darTablaClientes() + ".ID = " + ps.darTablaFacturas() + ".ID" + ps.darTablaClientes() +
						" FULL OUTER JOIN " + ps.darTablaFacturaProductos() + " ON " + ps.darTablaFacturas() + ".ID = " + ps.darTablaFacturaProductos() + ".IDFACTURA";
		String where = " WHERE " + ps.darTablaFacturaProductos() + ".IDPRODUCTO = ? AND " + ps.darTablaFacturas() + ".FECHA BETWEEN ? and ? and " +ps.darTablaFacturas() + ".IdSucursal = ?";

		if (criterioAgrupacion.equals("Cliente")) {
			String groupBy = " GROUP BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre";
			select = "SELECT " + ps.darTablaClientes() + ".ID, " + ps.darTablaClientes() + ".NOMBRE ,SUM(" + ps.darTablaFacturaProductos() + ".UNIVENDIDAS) AS SUMA_TOT";
			sql = original + select + from;
			sql += groupBy;
			sql += minus + select + from + where + groupBy + " ))";

		} else if (criterioAgrupacion.equals("Cliente y fecha")) {
			String groupBy = " GROUP BY " + ps.darTablaClientes() + ".id, " + ps.darTablaClientes() + ".nombre, TRUNC(" + ps.darTablaFacturas() + ".fecha)";
			select = "SELECT " + ps.darTablaClientes() + ".ID, " + ps.darTablaClientes() + ".NOMBRE, TRUNC(" + ps.darTablaFacturas() + ".fecha) ,SUM(" + ps.darTablaFacturaProductos() + ".UNIVENDIDAS) AS SUMA_TOT";
			sql = original + select + from;
			sql += groupBy;
			sql += minus + select + from + where + groupBy + " ))";
		} else {
			sql = original + select + from;
			sql += minus + select + from + where + " ))";
		}

		if (criterioOrdenacion.equals("Cliente"))
			sql += " ORDER BY NOMBRE";
		else if (criterioOrdenacion.equals("Numero de unidades compradas"))
			sql += " ORDER BY SUMA_TOT";

		if (!criterioOrdenacion.equals("")) {
			if (criterioOrdenacionAscDesc.equals("Ascendentemente"))
				sql += " ASC";
			else if (criterioOrdenacionAscDesc.equals("Descendentemente"))
				sql += " DESC";
		}
		Query q = pm.newQuery(SQL, sql);
		System.out.println(sql);
		q.setParameters(idProducto, fechaInicio, fechaFinal, idSucursal);
		lista = (List<Object[]>) q.executeList();
		return lista;
	}
}
