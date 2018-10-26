package uniandes.isis2304.superandes.negocio;

public class FacturaProducto implements VOFacturaProducto {
	/**
	 * El identificador unico de la factura
	 */
	private long idFactura;
	/**
	 * El identificador unico del producto
	 */
	private long idProducto;
	/**
	 * Las unidades vendidas del producto asociado
	 */
	private int uniVendidas;
	
	public FacturaProducto() {
		this.idFactura = 0;
		this.idProducto = 0;
		this.uniVendidas = 0;
	}

	public FacturaProducto(long idFactura, long idProducto, int uniVendidas) {
		this.idFactura = idFactura;
		this.idProducto = idProducto;
		this.uniVendidas = uniVendidas;
	}

	@Override
	public long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(long idFactura) {
		this.idFactura = idFactura;
	}

	@Override
	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	@Override
	public int getUniVendidas() {
		return uniVendidas;
	}

	public void setUniVendidas(int uniVendidas) {
		this.uniVendidas = uniVendidas;
	}
	
	
}
