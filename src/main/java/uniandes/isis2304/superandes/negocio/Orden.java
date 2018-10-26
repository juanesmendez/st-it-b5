package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

public class Orden implements VOOrden{
	/**
	 * El identificador unico de la orden
	 */
	private long id;
	/**
	 * El identificador unico del proveedor al que se le realizo la orden
	 */
	private long idProveedor;
	/**
	 * Identificador de la sucursal que realizo la orden
	 */
	private long idSucursal;
	
	/**
	 * Identificador del producto que se solicitara al proveedor
	 */
	private long idProducto;
	/**
	 * La cantidad e productos de la orden
	 */
	private int cantidad;
	/**
	 * El precio al que se vendera la unidad del producto
	 */
	private double precio;
	
	/**
	 * El estado en el que se encuentra la orden (entregado, no entregado)
	 */
	private String estado;
	/**
	 * La fecha esperada de entrega de la orden
	 */
	private Timestamp fechaEsperadaEntrega;
	/**
	 * La fecha de entrega de la orden
	 */
	private Timestamp fechaEntrega;
	/**
	 * La calificacion otorgada a la orden que depende de su calidad
	 */
	private String calificacion;
	
	public Orden() {
		this.id = 0;
		this.idProveedor = 0;
		this.idSucursal = 0;
		this.idProducto = 0;
		this.cantidad = 0;
		this.precio = 0;
		this.estado = "";
		this.fechaEsperadaEntrega = new Timestamp(0);
		this.fechaEntrega = new Timestamp(0);
		this.calificacion = "";
	}

	public Orden(long id, long idProveedor, long idSucursal, long idProducto, int cantidad, double precio,String estado,Timestamp fechaEsperadaEntrega, Timestamp fechaEntrega, String calificacion) {
		this.id = id;
		this.idProveedor = idProveedor;
		this.idSucursal = idSucursal;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.precio = precio;
		this.estado = estado;
		this.fechaEsperadaEntrega = fechaEsperadaEntrega;
		this.fechaEntrega = fechaEntrega;
		this.calificacion = calificacion;
	}

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(long idProveedor) {
		this.idProveedor = idProveedor;
	}
	
	@Override
	public long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(long idSucursal) {
		this.idSucursal = idSucursal;
	}
	
	@Override
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	
	@Override
	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	@Override
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String getCalificacion() {
		return calificacion;
	}

	@Override
	public Timestamp getFechaEsperadaEntrega() {
		return fechaEsperadaEntrega;
	}

	public void setFechaEsperadaEntrega(Timestamp fechaEsperadaEntrega) {
		this.fechaEsperadaEntrega = fechaEsperadaEntrega;
	}

	@Override
	public Timestamp getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Timestamp fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Orden[id="+id+", idProveedor="+idProveedor+", idSucursal="+idSucursal+", idProducto="+idProducto+
				", cantidad="+cantidad+", precio="+precio+", estado="+estado+", fechaEsperadaEntrega="+fechaEsperadaEntrega+", fechaEntrega="+fechaEntrega+
				", calificacion="+calificacion+"]";
	}
}
