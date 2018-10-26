package uniandes.isis2304.superandes.negocio;

public class Estante implements VOEstante {
	/**
	 * El identificador unico del estante
	 */
	private long id;
	/**
	 * El identificador de la sucursal que posee el estante
	 */
	private long idSucursal;
	/**
	 * El identificador del tipo de producto que se almacena en el estante
	 */
	private long idTipoProducto;
	/**
	 * El volumen del estante
	 */
	private double volumen;
	/**
	 * El peso del estante
	 */
	private double peso;
	/**
	 * El nivel de abastecimiento del estante
	 */
	private int nivAbastecimiento;
	
	private Estante(){
		this.id = 0;
		this.idSucursal = 0;
		this.idTipoProducto = 0;
		this.volumen = 0;
		this.peso = 0;
		this.nivAbastecimiento = 0;

	}

	public Estante(long id, long idSucursal, long idTipoProducto, double volumen, double peso, int nivAbastecimiento) {
		this.id = id;
		this.idSucursal = idSucursal;
		this.idTipoProducto = idTipoProducto;
		this.volumen = volumen;
		this.peso = peso;
		this.nivAbastecimiento = nivAbastecimiento;
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
	public long getIdTipoProducto() {
		return idTipoProducto;
	}

	public void setIdTipoProducto(long idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
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

	@Override
	public int getNivAbastecimiento() {
		return nivAbastecimiento;
	}

	public void setNivAbastecimiento(int nivAbastecimiento) {
		this.nivAbastecimiento = nivAbastecimiento;
	}
	
	
}
