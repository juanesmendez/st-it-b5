package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Proveedor;

class SQLProveedor {
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
	public SQLProveedor (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}
	
	
	public List<Proveedor> darProveedores(PersistenceManager pm) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL,"SELECT * FROM "+ ps.darTablaProveedores());
		q.setResultClass(Proveedor.class);
		return (List<Proveedor>) q.executeList();
	}


	public long agregarProveedor(PersistenceManager pm,long idProveedor, long nit, String nombreProveedor) {
		// TODO Auto-generated method stub
		
		Query q = pm.newQuery(SQL,"INSERT INTO "+ ps.darTablaProveedores() + " values (?,?,?)");
		q.setParameters(idProveedor,nit,nombreProveedor);
		q.setResultClass(Proveedor.class);
		return (long) q.executeUnique();
	}


	public List<Object[]> darProveedorMasSolicitadoPorSemana(PersistenceManager pm) {
		String sql ="SELECT PEDIDOSPROVEEDORXSEMANA.SEMANA, PEDIDOSPROVEEDORXSEMANA.IDPROVEEDOR, "+ ps.darTablaProveedores()+".nombre," +  "PEDIDOSPROVEEDORXSEMANA.NUM_PEDIDOS " + 
				"FROM " + 
				"(SELECT SEMANA, MAX(NUM_PEDIDOS) AS NUM_PEDIDOS " + 
				"FROM PEDIDOSPROVEEDORXSEMANA " + 
				"GROUP BY SEMANA, IDPROVEEDOR) SUBQUERY " + 
				"INNER JOIN PEDIDOSPROVEEDORXSEMANA ON PEDIDOSPROVEEDORXSEMANA.SEMANA = SUBQUERY.SEMANA AND PEDIDOSPROVEEDORXSEMANA.NUM_PEDIDOS = SUBQUERY.NUM_PEDIDOS " + 
				"INNER JOIN " + ps.darTablaProveedores() + " ON " + ps.darTablaProveedores() + ".id = PEDIDOSPROVEEDORXSEMANA.IDPROVEEDOR"; 
		
		Query q = pm.newQuery(SQL,sql);
				
		return (List<Object[]>) q.executeList();
	}


	public List<Object[]> darProveedorMenosSolicitadoPorSemana(PersistenceManager pm) {
		String sql ="SELECT PEDIDOSPROVEEDORXSEMANA.SEMANA, PEDIDOSPROVEEDORXSEMANA.IDPROVEEDOR, "+ ps.darTablaProveedores()+".nombre," +  "PEDIDOSPROVEEDORXSEMANA.NUM_PEDIDOS " + 
				"FROM " + 
				"(SELECT SEMANA, MIN(NUM_PEDIDOS) AS NUM_PEDIDOS " + 
				"FROM PEDIDOSPROVEEDORXSEMANA " + 
				"GROUP BY SEMANA, IDPROVEEDOR) SUBQUERY " + 
				"INNER JOIN PEDIDOSPROVEEDORXSEMANA ON PEDIDOSPROVEEDORXSEMANA.SEMANA = SUBQUERY.SEMANA AND PEDIDOSPROVEEDORXSEMANA.NUM_PEDIDOS = SUBQUERY.NUM_PEDIDOS " + 
				"INNER JOIN " + ps.darTablaProveedores() + " ON " + ps.darTablaProveedores() + ".id = PEDIDOSPROVEEDORXSEMANA.IDPROVEEDOR"; 
		
		Query q = pm.newQuery(SQL,sql);
				
		return (List<Object[]>) q.executeList();
	}
}
