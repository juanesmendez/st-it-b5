package uniandes.isis2304.superandes.negocio;

public interface VOProducto {

	String getCodBarras();

	double getPesoEmpaque();

	double getVolEmpaque();

	String getUniMedida();

	double getCantPresentacion();

	String getPresentacion();

	long getIdTipoProducto();

	String getMarca();

	String getNombre();

	long getId();

	@Override
	String toString();
	
}
