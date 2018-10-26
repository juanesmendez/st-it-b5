package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

public class Factura implements VOFactura{
	
	/**
	 * El identificador unico de la factura
	 */
	private long id;
	/**
	 * El identificador del cliente que realizo compra que generlo la factura 
	 */
	private long idCliente;
	/**
	 * El identificador de la sucursal en la que se realizo la factura
	 */
	private long idSucursal;
	/**
	 * La fecha de la factura
	 */
	private Timestamp fecha;
	/**
	 * El valor total de la factura
	 */
	private double total;
	
	public Factura() {
		this.id = 0;
		this.idCliente = 0;
		this.idSucursal = 0;
		this.fecha = new Timestamp(0);
		this.total = 0;
	}

	public Factura(long id, long idCliente, long idSucursal, Timestamp fecha, double total) {
		this.id = id;
		this.idCliente = idCliente;
		this.idSucursal = idSucursal;
		this.fecha = fecha;
		this.total = total;
	}

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(long idSucursal) {
		this.idSucursal = idSucursal;
	}

	@Override
	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	@Override
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Factura[id="+id + ", idCliente="+idCliente + ", idSucursal=" + idSucursal + ", fecha=" + fecha.toString() + ", total="+ total+ "]" ;
	}
	
}
