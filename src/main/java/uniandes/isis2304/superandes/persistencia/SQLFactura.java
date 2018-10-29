package uniandes.isis2304.superandes.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Factura;

class SQLFactura {
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
	public SQLFactura (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public long agregarFactura(PersistenceManager pm, long idFactura, long idCliente, long idSucursal, Timestamp fecha,
			double total) {
		// TODO Auto-generated method stub
		
		Query q = pm.newQuery(SQL,"INSERT INTO " + ps.darTablaFacturas() + " VALUES (?,?,?,?,?)");
		q.setParameters(idFactura,idCliente,idSucursal,fecha,total);
		
		return (long) q.executeUnique();
	}

	public List<Object[]> darDineroRecolectadoSucursales(PersistenceManager pm,Timestamp fechaInicio, Timestamp fechaFinal) {
		// TODO Auto-generated method stub
		
		Query q = pm.newQuery(SQL,"SELECT idSucursal, SUM(total) "
				+ "FROM "+ps.darTablaFacturas()+" "
				+ "WHERE fecha BETWEEN ? AND ? "
				+ "GROUP BY idSucursal "
				+ "ORDER BY idSucursal");
		q.setParameters(fechaInicio,fechaFinal);
		//System.out.println(fechaInicio);
		return (List<Object[]>) q.executeList();
		
	}

	public List<Factura> darVentasUsuarioEnRango(PersistenceManager pm, String idUsuario,Timestamp fechaInicio, Timestamp fechaFinal) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL,"SELECT * FROM "+ps.darTablaFacturas() + " "
								+ "WHERE idCliente = ? AND fecha BETWEEN ? AND ?");
		q.setParameters(idUsuario,fechaInicio,fechaFinal);
		q.setResultClass(Factura.class);
		return (List<Factura> )q.executeList();
	}
}
