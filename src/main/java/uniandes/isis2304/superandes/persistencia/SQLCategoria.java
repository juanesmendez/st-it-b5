package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Categoria;

class SQLCategoria {
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
	public SQLCategoria (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}

	public List<Categoria> darCategorias(PersistenceManager pm) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL,"SELECT * FROM "+ ps.darTablaCategorias());
		q.setResultClass(Categoria.class);
		return (List<Categoria>)q.executeList();
	}

	public long agregarCategoria(PersistenceManager pm, long idCategoria, String nombre) {
		// TODO Auto-generated method stub
		Query q = pm.newQuery(SQL, "INSERT INTO "+ ps.darTablaCategorias() + "VALUES (?,?)");
		q.setParameters(idCategoria,nombre);
		return (long) q.executeUnique();
	}
}
