package uniandes.isis2304.superandes.negocio;

public class Proveedor implements VOProveedor {
	
	
	private long id;
	private long nit;
	private String nombre;
	
	public Proveedor() {
		this.id = 0;
		this.nit = 0;
		this.nombre = "";
	}

	public Proveedor(long id, long nit, String nombre) {
		super();
		this.id = id;
		this.nit = nit;
		this.nombre = nombre;
	}

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getNit() {
		return nit;
	}

	public void setNit(long nit) {
		this.nit = nit;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Proveedor [id=" + id + ", nit=" + nit + ", nombre=" + nombre +"]";
	}
	
}
