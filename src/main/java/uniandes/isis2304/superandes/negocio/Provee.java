package uniandes.isis2304.superandes.negocio;

public class Provee implements VOProvee{
	public long idProveedor;
	public long idProducto;
	
	public Provee() {
		this.idProveedor = 0;
		this.idProducto = 0;
	}

	public Provee(long idProveedor, long idProducto) {
		super();
		this.idProveedor = idProveedor;
		this.idProducto = idProducto;
	}

	@Override
	public long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(long idProveedor) {
		this.idProveedor = idProveedor;
	}

	@Override
	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}
	
	
	
}
