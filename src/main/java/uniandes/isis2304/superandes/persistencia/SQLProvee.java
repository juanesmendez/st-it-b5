package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Provee;

class SQLProvee {
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
	public SQLProvee (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public Provee darProvee(PersistenceManager pm,long idProveedor, long idProducto) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "SELECT * FROM "+ps.darTablaProvee() +" WHERE idProveedor = ? AND idProducto = ?");
		q.setParameters(idProveedor,idProducto);
		q.setResultClass(Provee.class);
		return (Provee)q.executeUnique();
	}
}
