package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLVendeCarrito {

    /* ****************************************************************
     * 			Constantes
     *****************************************************************/
    /**
     * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
     * Se renombra ac� para facilitar la escritura de las sentencias
     */
    private final static String SQL = PersistenciaSuperandes.SQL;

    /* ****************************************************************
     * 			Atributos
     *****************************************************************/
    /**
     * El manejador de persistencia general de la aplicaci�n
     */
    private PersistenciaSuperandes ps;

    /* ****************************************************************
     * 			M�todos
     *****************************************************************/
    /**
     * Constructor
     * @param ps - El Manejador de persistencia de la aplicaci�n
     */
    public SQLVendeCarrito (PersistenciaSuperandes ps)
    {
        this.ps = ps;
    }

    public long agregarVendeCarrito(PersistenceManager pm, long idCarrito, long idProducto, int cantidadProductos)
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaVendeCarrito () + "(idCarrito, idProducto, cantidadProductos) values (?, ?, ?)");
        q.setParameters(idCarrito, idProducto, cantidadProductos);
        return (long) q.executeUnique();
    }

    
}
