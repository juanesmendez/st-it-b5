package uniandes.isis2304.superandes.negocio;

public class Vende implements VOVende{
	
	private long idSucursal;
	private long idProducto;
	private double precio;
	private double precioUniMedida;
	private int nivReorden;
	private int cantRecompra;
	
	public Vende() {
		this.idSucursal = 0;
		this.idProducto = 0;
		this.precio = 0;
		this.precioUniMedida = 0;
		this.nivReorden = 0;
		this.cantRecompra = 0;
	}

	public Vende(long idSucursal, long idProducto, double precio, double precioUniMedida, int nivReorden,
			int cantRecompra) {
		super();
		this.idSucursal = idSucursal;
		this.idProducto = idProducto;
		this.precio = precio;
		this.precioUniMedida = precioUniMedida;
		this.nivReorden = nivReorden;
		this.cantRecompra = cantRecompra;
	}

	@Override
	public long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(long idSucursal) {
		this.idSucursal = idSucursal;
	}

	@Override
	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	@Override
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public double getPrecioUniMedida() {
		return precioUniMedida;
	}

	public void setPrecioUniMedida(double precioUniMedida) {
		this.precioUniMedida = precioUniMedida;
	}

	@Override
	public int getNivReorden() {
		return nivReorden;
	}

	public void setNivReorden(int nivReorden) {
		this.nivReorden = nivReorden;
	}

	@Override
	public int getCantRecompra() {
		return cantRecompra;
	}

	public void setCantRecompra(int cantRecompra) {
		this.cantRecompra = cantRecompra;
	}
	
	
}
