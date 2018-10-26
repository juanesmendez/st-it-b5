package uniandes.isis2304.superandes.negocio;

public class Bodega implements VOBodega{
	
	/**
	 * El identificador UNICO de una bodega
	 */
	private long id;
	/**
	 * El identificador de la sucursal que cuenta con la bodega
	 */
	private long idSucursal;
	/**
	 * El identificador del tipo de producto que se guarda en la bodega
	 */
	private long idTipoproducto;
	/**
	 * El volumen de la bodega
	 */
	private double volumen;
	/**
	 * El peso de la bodega
	 */
	private double peso;
	

	private Bodega() {
		this.id = 0;
		this.idSucursal = 0;
		this.idTipoproducto = 0;
		this.volumen = 0;
		this.peso = 0;
	}

	public Bodega(long id, long idSucursal, long idTipoProducto,double volumen, double peso) {
		this.id = id;
		this.idSucursal = idSucursal;
		this.idTipoproducto = idTipoProducto;
		this.volumen = volumen;
		this.peso = peso;
	}

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(long idSucursal) {
		this.idSucursal = idSucursal;
	}
	
	@Override
	public long getIdTipoproducto() {
		return idTipoproducto;
	}

	public void setIdTipoproducto(long idTipoproducto) {
		this.idTipoproducto = idTipoproducto;
	}

	@Override
	public double getVolumen() {
		return volumen;
	}

	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}

	@Override
	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	
	
	
	
}
