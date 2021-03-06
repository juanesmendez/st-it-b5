package uniandes.isis2304.superandes.persistencia;

import uniandes.isis2304.superandes.negocio.CarritoCompras;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;

public class SQLCarritoCompras {

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
    public SQLCarritoCompras (PersistenciaSuperandes ps)
    {
        this.ps = ps;
    }

    public long agregarCarritoCompras(PersistenceManager pm, long id, String estado, long idCliente, long idSucursal)
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCarritoCompras () + "(id, estado, idCliente, idSucursal) values (?, ?, ?, ?)");
        q.setParameters(id, estado, idCliente, idSucursal);
        return (long) q.executeUnique();
    }
    
    public long agregarCarritoCompras(PersistenceManager pm, long id, String estado, long idSucursal)
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCarritoCompras () + "(id, estado, idSucursal) values (?, ?, ?)");
        q.setParameters(id, estado, idSucursal);
        return (long) q.executeUnique();
    }

    public long actualizarCarritoCompras(PersistenceManager pm, long id, String estado, long idCliente, long idSucursal)
    {
        Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaCarritoCompras () + " SET  estado = ?, idCliente = ?, idSucursal = ? WHERE id = ?");
        q.setParameters(estado, idCliente, idSucursal, id);
        return (long) q.executeUnique();
    }
    
    public long actualizarCarritoComprasEstadoYIdCliente(PersistenceManager pm, long id, String estado, long idCliente)
    {
        Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaCarritoCompras () + " SET  estado = ?, idCliente = ? WHERE id = ?");
        q.setParameters(estado, idCliente, id);
        return (long) q.executeUnique();
    }
    
    public long actualizarCarritoComprasAbandonado(PersistenceManager pm, long id, String estado)
    {
        Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaCarritoCompras () + " SET  estado = ?, idCliente = null WHERE id = ?");
        q.setParameters(estado, id);
        return (long) q.executeUnique();
    }

    public long eliminarCarritoCompras(PersistenceManager pm,long id)
    {
        Query q = pm.newQuery(SQL,"DELETE FROM" + ps.darTablaCarritoCompras() + "WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
    }


    public CarritoCompras darCarritoComprasPorId (PersistenceManager pm, long idCarrito)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCarritoCompras() + " WHERE id = ?");
        q.setResultClass(CarritoCompras.class);
        q.setParameters(idCarrito);
        return (CarritoCompras) q.executeUnique();
    }
    
    public Object[] darObjetoCarritoComprasPorId (PersistenceManager pm, long idCarrito)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCarritoCompras() + " WHERE id = ?");
        q.setParameters(idCarrito);
        return (Object[])q.executeUnique();
    }

    public List<CarritoCompras> darCarritosCompras (PersistenceManager pm)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCarritoCompras());
        q.setResultClass(CarritoCompras.class);
        return (List<CarritoCompras>) q.executeList();
    }


}
