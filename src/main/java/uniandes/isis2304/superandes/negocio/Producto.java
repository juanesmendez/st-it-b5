package uniandes.isis2304.superandes.negocio;

public class Producto implements VOProducto{
	
	/**
	 * El identificador unico del producto
	 */
	private long id;
	/**
	 * El nombre del producto
	 */
	private String nombre;
	/**
	 * La marca el producto
	 */
	private String marca;
	/**
	 * El identificador el tipo de producto al que pertenece el producto
	 */
	private long idTipoProducto;
	/**
	 * La presentacion en la que viene el producto
	 */
	private String presentacion;
	/**
	 * La cantidad total de la presentacion del producto
	 */
	private double cantPresentacion;
	/*
	 * La unidad de medida del producto en gr o ml
	 */
	private String uniMedida;
	/**
	 * El volumen del empaque del producto
	 */
	private double volEmpaque;
	/**
	 * El peso del empaque del producto
	 */
	private double pesoEmpaque;
	/**
	 * El codigo de barras del producto
	 */
	private String codBarras;

	public Producto() {
		this.id = 0;
		this.nombre = "";
		this.marca = "";
		this.idTipoProducto = 0;
		this.presentacion = "";
		this.cantPresentacion = 0;
		this.uniMedida = "";
		this.volEmpaque = 0;
		this.pesoEmpaque = 0;
		this.codBarras = "";
	}

	public Producto(long id, String nombre, String marca, long idTipoProducto, String presentacion,
			double cantPresentacion, String uniMedida, double volEmpaque, double pesoEmpaque, String codBarras) {
		this.id = id;
		this.nombre = nombre;
		this.marca = marca;
		this.idTipoProducto = idTipoProducto;
		this.presentacion = presentacion;
		this.cantPresentacion = cantPresentacion;
		this.uniMedida = uniMedida;
		this.volEmpaque = volEmpaque;
		this.pesoEmpaque = pesoEmpaque;
		this.codBarras = codBarras;
	}

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Override
	public long getIdTipoProducto() {
		return idTipoProducto;
	}

	public void setIdTipoProducto(long idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}

	@Override
	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	@Override
	public double getCantPresentacion() {
		return cantPresentacion;
	}

	public void setCantPresentacion(double cantPresentacion) {
		this.cantPresentacion = cantPresentacion;
	}

	@Override
	public String getUniMedida() {
		return uniMedida;
	}

	public void setUniMedida(String uniMedida) {
		this.uniMedida = uniMedida;
	}

	@Override
	public double getVolEmpaque() {
		return volEmpaque;
	}

	public void setVolEmpaque(double volEmpaque) {
		this.volEmpaque = volEmpaque;
	}

	@Override
	public double getPesoEmpaque() {
		return pesoEmpaque;
	}

	public void setPesoEmpaque(double pesoEmpaque) {
		this.pesoEmpaque = pesoEmpaque;
	}

	@Override
	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Producto[id="+id+", nombre="+nombre+", marca="+marca+", idTipoProducto="+idTipoProducto+
				", presentacion="+presentacion+", cantidad presentacion="+cantPresentacion+", unidad medida="+uniMedida+
				", volumen empaque="+volEmpaque+", peso empaque="+pesoEmpaque+"codigo de barras="+codBarras+"]";
	}

}
