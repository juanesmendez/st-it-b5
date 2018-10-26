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
}
