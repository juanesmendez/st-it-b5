package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

public interface VOOrden {


	Timestamp getFechaEntrega();

	Timestamp getFechaEsperadaEntrega();

	long getIdProveedor();

	long getId();

	long getIdSucursal();

	String getCalificacion();

	String getEstado();

	double getPrecio();

	int getCantidad();

	long getIdProducto();

	@Override
	String toString();
}
