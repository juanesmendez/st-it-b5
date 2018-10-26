package uniandes.isis2304.superandes.negocio;

public class TipoProducto implements VOTipoProducto{

	private long id;
	
	private String nombre;
	
	private long idCategoria;
	
	public TipoProducto() {
		this.id = 0;
		this.nombre = "";
		this.idCategoria = 0;
	}

	public TipoProducto(long id, String nombre, long idCategoria) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.idCategoria = idCategoria;
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
	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TipoProducto[id="+id+", nombre="+nombre+", idCategoria="+idCategoria+"]";
	}
}
