package uniandes.isis2304.superandes.negocio;

public class ProductoEstante implements VOProductoEstante{

	private long idProducto;
	private long idEstante;
	private int cantidad;
	
	public ProductoEstante() {
		this.idProducto =  0;
		this.idEstante = 0;
		this.cantidad = 0;
	}

	public ProductoEstante(long idProducto, long idEstante, int cantidad) {
		this.idProducto = idProducto;
		this.idEstante = idEstante;
		this.cantidad = cantidad;
	}

	@Override
	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	@Override
	public long getIdEstante() {
		return idEstante;
	}

	public void setIdEstante(long idEstante) {
		this.idEstante = idEstante;
	}

	@Override
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
}
