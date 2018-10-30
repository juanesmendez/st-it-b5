package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

public interface VOPromocion {
    long getId();
    long getIdProveedor();
    long getIdProducto();
    int getCantidadProductos();
    Timestamp getFechaInicio();
    Timestamp getFechaFin();


}
