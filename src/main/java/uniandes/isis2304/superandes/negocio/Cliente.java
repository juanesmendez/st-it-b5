package uniandes.isis2304.superandes.negocio;

public class Cliente implements VOCliente{
	
	/**
	 * El identificador unico de un cliente
	 */
	private long identificacion;
	/**
	 * El tipo de cliente
	 */
	private String tipo;
	/**
	 * El nombre del cliente
	 */
	private String nombre;
	/**
	 * El correo del cliente
	 */
	private String correo;
	/**
	 * La direccion del cliente. Obligatoria para empresas y opcional para personas naturales
	 */
	private String direccion;
	/**
	 * Los puntos que acumula el cliente
	 */
	private int puntos;
	
	public Cliente() {
		this.identificacion = 0;
		this.tipo = "";
		this.nombre = "";
		this.correo = "";
		this.direccion = "";
		this.puntos = 0;
	}

	public Cliente(long identificacion, String tipo, String nombre, String correo, String direccion, int puntos) {
		this.identificacion = identificacion;
		this.tipo = tipo;
		this.nombre = nombre;
		this.correo = correo;
		this.direccion = direccion;
		this.puntos = puntos;
	}


	@Override
	public long getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(long identificacion) {
		this.identificacion = identificacion;
	}

	@Override
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Override
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	
}
