package uniandes.isis2304.superandes.negocio;

public class Item implements VOItem {
	
	private long idProducto;
	
	private String nombre;
	
	private int cantidad;
	
	private double precio;
	
	private double subTotal;

	public Item(long idProducto, String nombre, int cantidad, double precio, double subTotal) {
		
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
		this.subTotal = subTotal;
	}

	/* (non-Javadoc)
	 * @see uniandes.isis2304.superandes.negocio.VOItem#getIdProducto()
	 */
	@Override
	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	/* (non-Javadoc)
	 * @see uniandes.isis2304.superandes.negocio.VOItem#getNombre()
	 */
	@Override
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/* (non-Javadoc)
	 * @see uniandes.isis2304.superandes.negocio.VOItem#getCantidad()
	 */
	@Override
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/* (non-Javadoc)
	 * @see uniandes.isis2304.superandes.negocio.VOItem#getPrecio()
	 */
	@Override
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/* (non-Javadoc)
	 * @see uniandes.isis2304.superandes.negocio.VOItem#getSubTotal()
	 */
	@Override
	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Item[idProducto="+idProducto+", nombre="+nombre+", cantidad="+cantidad+", precio="+precio+
				", subTotal="+subTotal+"]";
	}
	
	
	
}
