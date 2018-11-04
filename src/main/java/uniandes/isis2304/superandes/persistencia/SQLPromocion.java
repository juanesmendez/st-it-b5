package uniandes.isis2304.superandes.persistencia;

import uniandes.isis2304.superandes.negocio.Promocion;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLPromocion {

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
    public SQLPromocion (PersistenciaSuperandes ps)
    {
        this.ps = ps;
    }

    public long agregarPromocion(PersistenceManager pm, long id, long idProveedor, long idProducto, int cantidadProductos, boolean disponible, Timestamp fechaInicio, Timestamp fechaFin, int cantidadProductosVendidos)
    {
        int esDisponible = disponible? 1 : 0;
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaPromocion () + "(id, idProveedor, idProducto,cantidadProductos,disponible, fechaInicio, fechaFin, cantidadProductosVendidos) values (?, ?, ?, ?, ?,?,?, ? )");
        q.setParameters(id, idProveedor, idProducto, cantidadProductos, esDisponible, fechaInicio, fechaFin, cantidadProductosVendidos);
        return (long) q.executeUnique();
    }

    public long actualizarPromocion(PersistenceManager pm, long id, long idProveedor, long idProducto, int cantidadProductos, boolean disponible, Timestamp fechaInicio, Timestamp fechaFin)
    {
        int esDisponible = disponible? 1 : 0;
        Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaPromocion () + " SET  idProveedor = ?, idProducto = ?, cantidadProductos = ?, disponible = ?, fechaInicio = ?, fechaFin = ?) where id = ?");
        q.setParameters(idProveedor, idProducto, cantidadProductos, esDisponible, fechaInicio, fechaFin, id);
        return (long) q.executeUnique();
    }

    public Long eliminarPromocion(PersistenceManager pm,long id)
    {
        Query q = pm.newQuery(SQL,"DELETE FROM" + ps.darTablaPromocion() + "WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
    }


    public Promocion darPromocionPorId (PersistenceManager pm, long idPromocion)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPromocion() + " WHERE id = ?");
        q.setResultClass(Promocion.class);
        q.setParameters(idPromocion);
        return (Promocion) q.executeUnique();
    }

    public List<Promocion> darPromocionesPorRangoFecha (PersistenceManager pm, Timestamp fechaInicio, Timestamp fechaFin)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPromocion() + " WHERE fechaInicio >= '?' AND fechaFin <= '?'");
        q.setResultClass(Promocion.class);
        q.setParameters(fechaInicio,fechaFin);
        return (List<Promocion>) q.executeList();
    }

    public List<Promocion> darPromociones (PersistenceManager pm)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPromocion());
        q.setResultClass(Promocion.class);
        return (List<Promocion>) q.executeList();
    }
    
    public Object[] darPromocionDeProducto(PersistenceManager pm, long idProducto) {
    	
    	Query q = pm.newQuery(SQL,"SELECT * FROM " + ps.darTablaPromocion() + " WHERE idProducto = ?");
    	q.setParameters(idProducto);
    	return (Object[]) q.executeUnique();
    }
    
    public long actualizarCantidadesPromocion(PersistenceManager pm, long idPromocion,int cantidadCompra) {
    	
    	Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaPromocion() + " SET cantidadProductos = cantidadProductos - ?, cantidadProductosVendidos = cantidadProductosVendidos + ? WHERE id = ?");
    	q.setParameters(cantidadCompra,cantidadCompra,idPromocion);
    	return (long) q.executeUnique();
    }


}
