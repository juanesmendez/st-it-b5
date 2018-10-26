package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

public interface VOFactura {

	double getTotal();

	Timestamp getFecha();

	long getIdCliente();
	
	long getIdSucursal();

	long getId();

	

}
