package uniandes.isis2304.superandes.negocio;

public class ProductoBodega implements VOProductoBodega{

	private long idProducto;
	private long idBodega;
	private int cantidad;
	
	public ProductoBodega() {
		this.idProducto = 0;
		this.idBodega = 0;
		this.cantidad = 0;
	}

	public ProductoBodega(long idProducto, long idBodega, int cantidad) {
		super();
		this.idProducto = idProducto;
		this.idBodega = idBodega;
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
	public long getIdBodega() {
		return idBodega;
	}

	public void setIdBodega(long idBodega) {
		this.idBodega = idBodega;
	}

	@Override
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
