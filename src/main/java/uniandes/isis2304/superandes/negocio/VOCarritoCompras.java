package uniandes.isis2304.superandes.negocio;

public interface VOCarritoCompras {
    long getId();
    String getEstado();
    long getIdCliente();
    long getIdSucursal();
	void convertirACarrito(Object[] object);

}
