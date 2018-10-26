package uniandes.isis2304.superandes.negocio;

public class Categoria implements VOCategoria{
	
	/**
	 * El identificador unico de cada categoria
	 */
	private long id;
	/**
	 * El nombre de la categoria
	 */
	private String nombre;
	
	public Categoria() {
		this.id = 0;
		this.nombre = "";
	}


	public Categoria(long id, String nombre) {
		this.id = id;
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
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Categoria[id="+id+", nombre="+nombre+"]";
	}
	
}
