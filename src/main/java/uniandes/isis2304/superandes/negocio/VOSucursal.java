package uniandes.isis2304.superandes.negocio;

public interface VOSucursal {

	String getNombre();

	String getDireccion();

	String getCiudad();

	long getId();
	
	@Override
	String toString();
}
