package uniandes.isis2304.superandes.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import org.datanucleus.query.inmemory.BigDecimalAggregateExpression;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.superandes.negocio.Bodega;
import uniandes.isis2304.superandes.negocio.CarritoCompras;
import uniandes.isis2304.superandes.negocio.Categoria;
import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Estante;
import uniandes.isis2304.superandes.negocio.Factura;
import uniandes.isis2304.superandes.negocio.Item;
import uniandes.isis2304.superandes.negocio.Orden;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.negocio.Promocion;
import uniandes.isis2304.superandes.negocio.Provee;
import uniandes.isis2304.superandes.negocio.Proveedor;
import uniandes.isis2304.superandes.negocio.Sucursal;
import uniandes.isis2304.superandes.negocio.TipoProducto;
import uniandes.isis2304.superandes.negocio.VOBodega;
import uniandes.isis2304.superandes.negocio.VOCarritoCompras;
import uniandes.isis2304.superandes.negocio.VOCliente;
import uniandes.isis2304.superandes.negocio.VOFactura;
import uniandes.isis2304.superandes.negocio.VOOrden;
import uniandes.isis2304.superandes.negocio.VOProducto;
import uniandes.isis2304.superandes.negocio.VOProvee;
import uniandes.isis2304.superandes.negocio.VOSucursal;
import uniandes.isis2304.superandes.negocio.VOVende;
import uniandes.isis2304.superandes.negocio.Vende;


public class PersistenciaSuperandes {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaSuperandes.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaSuperandes instance;

	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * 
	 */
	private List <String> tablas;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaSuperandes
	 */
	private SQLUtil sqlUtil;

	/**
	 * Atributo para el acceso a tabla BODEGA en la base de datos
	 */
	private SQLBodega sqlBodega;
	/**
	 * Atributo para el acceso a tabla CATEGORIA en la base de datos
	 */
	private SQLCategoria sqlCategoria;
	/**
	 * Atributo para el acceso a tabla CLIENTE en la base de datos
	 */
	private SQLCliente sqlCliente;
	/**
	 * Atributo para el acceso a tabla ESTANTE en la base de datos
	 */
	private SQLEstante sqlEstante;
	/**
	 * Atributo para el acceso a tabla FACTURA en la base de datos
	 */
	private SQLFactura sqlFactura;
	/**
	 * Atributo para el acceso a tabla FACTURAPRODUCTO en la base de datos
	 */
	private SQLFacturaProducto sqlFacturaProducto;
	/**
	 * Atributo para el acceso a tabla ORDEN en la base de datos
	 */
	private SQLOrden sqlOrden;
	/**
	 * Atributo para el acceso a tabla PEDIDO en la base de datos
	 */
	private SQLPedido sqlPedido;
	/**
	 * Atributo para el acceso a tabla PRODUCTO en la base de datos
	 */
	private SQLProducto sqlProducto;
	/**
	 * Atributo para el acceso a tabla PRODUCTOBODEGA en la base de datos
	 */
	private SQLProductoBodega sqlProductoBodega;
	/**
	 * Atributo para el acceso a tabla PRODUCTOESTANTE en la base de datos
	 */
	private SQLProductoEstante sqlProductoEstante;
	/**
	 * Atributo para el acceso a tabla PROVEE en la base de datos
	 */
	private SQLProvee sqlProvee;
	/**
	 * Atributo para el acceso a tabla PROVEEDOR en la base de datos
	 */
	private SQLProveedor sqlProveedor;
	/**
	 * Atributo para el acceso a tabla SUCURSAL en la base de datos
	 */
	private SQLSucursal sqlSucursal;
	/**
	 * Atributo para el acceso a tabla TIPOPRODUCTO en la base de datos
	 */
	private SQLTipoProducto sqlTipoProducto;
	/**
	 * Atributo para el acceso a tabla VENDE en la base de datos
	 */
	private SQLVende sqlVende;

	/**
	 * Atributo para el acceso a tabla PROMOCION en la base de datos
	 */
	private SQLPromocion sqlPromocion;

	/**
	 * Atributo para el acceso a tabla CARRITOCOMPRAS en la base de datos
	 */
	private SQLCarritoCompras sqlCarritoCompras;

	private SQLVendeCarrito sqlVendeCarrito;

	public PersistenciaSuperandes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Superandes");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		/*
		tablas = new LinkedList<String> ();
		tablas.add ("Parranderos_sequence");
		tablas.add ("TIPOBEBIDA");
		tablas.add ("BEBIDA");
		tablas.add ("BAR");
		tablas.add ("BEBEDOR");
		tablas.add ("GUSTAN");
		tablas.add ("SIRVEN");
		tablas.add ("VISITAN");
		 */
	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaSuperandes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}


	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaSuperandes getInstance() {
		// TODO Auto-generated method stub
		if (instance == null)
		{
			instance = new PersistenciaSuperandes ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaSuperandes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperandes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	public PersistenceManagerFactory getPersistenceManager() {
		return this.pmf;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlBodega = new SQLBodega(this);
		sqlCategoria = new SQLCategoria(this);
		sqlCliente = new SQLCliente(this);
		sqlEstante = new SQLEstante(this);
		sqlFactura = new SQLFactura(this);
		sqlFacturaProducto = new SQLFacturaProducto(this);
		sqlOrden = new SQLOrden(this);
		sqlPedido = new SQLPedido(this);
		sqlProducto = new SQLProducto(this);
		sqlProductoBodega  = new SQLProductoBodega(this);
		sqlProductoEstante = new SQLProductoEstante(this);
		sqlProvee = new SQLProvee(this);
		sqlProveedor = new SQLProveedor(this);
		sqlSucursal = new SQLSucursal(this);
		sqlTipoProducto = new SQLTipoProducto(this);
		sqlVende = new SQLVende(this);
		sqlPromocion = new SQLPromocion(this);
		sqlCarritoCompras = new SQLCarritoCompras(this);
		sqlVendeCarrito = new SQLVendeCarrito(this);
		sqlUtil = new SQLUtil(this);

	}

	
	

	public List<String> getTablas() {
		return tablas;
	}

	public void setTablas(List<String> tablas) {
		this.tablas = tablas;
	}

	public SQLUtil getSqlUtil() {
		return sqlUtil;
	}

	public SQLBodega getSqlBodega() {
		return sqlBodega;
	}

	public SQLCategoria getSqlCategoria() {
		return sqlCategoria;
	}

	public SQLCliente getSqlCliente() {
		return sqlCliente;
	}

	public SQLEstante getSqlEstante() {
		return sqlEstante;
	}

	public SQLFactura getSqlFactura() {
		return sqlFactura;
	}

	public SQLFacturaProducto getSqlFacturaProducto() {
		return sqlFacturaProducto;
	}

	public SQLOrden getSqlOrden() {
		return sqlOrden;
	}

	public SQLPedido getSqlPedido() {
		return sqlPedido;
	}

	public SQLProducto getSqlProducto() {
		return sqlProducto;
	}

	public SQLProductoBodega getSqlProductoBodega() {
		return sqlProductoBodega;
	}

	public SQLProductoEstante getSqlProductoEstante() {
		return sqlProductoEstante;
	}

	public SQLProvee getSqlProvee() {
		return sqlProvee;
	}

	public SQLProveedor getSqlProveedor() {
		return sqlProveedor;
	}

	public SQLSucursal getSqlSucursal() {
		return sqlSucursal;
	}

	public SQLTipoProducto getSqlTipoProducto() {
		return sqlTipoProducto;
	}

	public SQLVende getSqlVende() {
		return sqlVende;
	}

	public SQLPromocion getSqlPromocion() {
		return sqlPromocion;
	}

	public SQLCarritoCompras getSqlCarritoCompras() {
		return sqlCarritoCompras;
	}

	public SQLVendeCarrito getSqlVendeCarrito() {
		return sqlVendeCarrito;
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de superandes
	 */
	public String darSeqSuperandes ()
	{
		return tablas.get (0);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla BODEGA de superandes
	 */
	public String darTablaBodegas() {
		// TODO Auto-generated method stub
		return tablas.get(1);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla CATEGORIA de superandes
	 */
	public String darTablaCategorias() {
		// TODO Auto-generated method stub
		return tablas.get(2);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla CATEGORIASUCURSAL de superandes
	 */
	public String darTablaCategoriaSucursal() {
		// TODO Auto-generated method stub
		return tablas.get(3);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla CLIENTE de superandes
	 */
	public String darTablaClientes() {
		// TODO Auto-generated method stub
		return tablas.get(4);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla ESTANTE de superandes
	 */
	public String darTablaEstantes() {
		// TODO Auto-generated method stub
		return tablas.get(5);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla FACTURA de superandes
	 */
	public String darTablaFacturas() {
		// TODO Auto-generated method stub
		return tablas.get(6);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla FACTURAPRODUCTO de superandes
	 */
	public String darTablaFacturaProductos() {
		// TODO Auto-generated method stub
		return tablas.get(7);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla ORDEN de superandes
	 */
	public String darTablaOrden() {
		// TODO Auto-generated method stub
		return tablas.get(8);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla PRODUCTO de superandes
	 */
	public String darTablaProductos() {
		// TODO Auto-generated method stub
		return tablas.get(9);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla PRODUCTOBODEGA de superandes
	 */
	public String darTablaProductoBodega() {
		// TODO Auto-generated method stub
		return tablas.get(10);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla PRODUCTOESTANTE de superandes
	 */
	public String darTablaProductoEstante() {
		// TODO Auto-generated method stub
		return tablas.get(11);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla PROVEE de superandes
	 */
	public String darTablaProvee() {
		// TODO Auto-generated method stub
		return tablas.get(12);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla PROVEEDOR de superandes
	 */
	public String darTablaProveedores() {
		// TODO Auto-generated method stub
		return tablas.get(13);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla SUCURSAL de superandes
	 */
	public String darTablaSucursal()
	{
		return tablas.get(14);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla TIPOPRODUCTO de superandes
	 */
	public String darTablaTipoProducto() {
		// TODO Auto-generated method stub
		return tablas.get(15);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla VENDE de superandes
	 */
	public String darTablaVende() {
		// TODO Auto-generated method stub
		return tablas.get(16);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla PROMOCIÓN de superandes
	 */
	public String darTablaPromocion() {
		// TODO Auto-generated method stub
		return tablas.get(17);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla PROMOCIÓNSUCURSAL de superandes
	 */
	public String darTablaPromocionSucursal() {
		// TODO Auto-generated method stub
		return tablas.get(18);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla PROMOCIÓNSUCURSAL de superandes
	 */
	public String darTablaCarritoCompras() {
		// TODO Auto-generated method stub
		return tablas.get(19);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla ProductoCarrito de superandes
	 */
	public String darTablaVendeCarrito() {
		// TODO Auto-generated method stub
		return tablas.get(20);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Sucursal
	 * @return La lista de objetos Sucursal, construidos con base en las tuplas de la tabla SUCURSAL
	 */
	public List<Sucursal> darSucursales() {
		// TODO Auto-generated method stub
		return (List<Sucursal>) sqlSucursal.darSucursales(pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla Proveedor
	 * @return La lista de objetos Proveedor, construidos con base en las tuplas de la tabla PROVEEDOR
	 */
	public List<Proveedor> darProveedores() {
		// TODO Auto-generated method stub
		return (List<Proveedor>) sqlProveedor.darProveedores(pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla Categoria
	 * @return La lista de objetos Categoria, construidos con base en las tuplas de la tabla CATEGORIA
	 */
	public List<Categoria> darCategorias() {
		// TODO Auto-generated method stub
		return (List<Categoria>) sqlCategoria.darCategorias(pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla TipoProducto
	 * @return La lista de objetos TipoProducto, construidos con base en las tuplas de la tabla TIPOPRODUCTO
	 */
	public List<TipoProducto> darTipoProductos() {
		// TODO Auto-generated method stub
		return (List<TipoProducto>) sqlTipoProducto.darTipoProductos(pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla Producto
	 * @return La lista de objetos Producto, construidos con base en las tuplas de la tabla PRODUCTO
	 */
	public List<Producto> darProductos() {
		// TODO Auto-generated method stub
		return (List<Producto>) sqlProducto.darProductos(pmf.getPersistenceManager());
	}

	public List<Orden> darOrdenes() {
		// TODO Auto-generated method stub
		return (List<Orden>) sqlOrden.darOrdenes(pmf.getPersistenceManager());
	}
	/**
	 * Registra un Proveedor en la base de datos de Superandes
	 * @param nit El NIT del proveedor a registrar
	 * @param nombreProveedor El nombre del proveedor a registrar
	 * @return El proveedor registrado en la base de datos de superandes
	 */
	public Proveedor registrarProveedor(long nit, String nombreProveedor) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			long idProveedor = nextval();
			long tuplasInsertadas = sqlProveedor.agregarProveedor(pm,idProveedor,nit,nombreProveedor);

			log.trace ("Inserción proveedor: " + idProveedor + ": " + tuplasInsertadas + " tuplas insertadas");
			tx.commit();
			return new Proveedor(idProveedor, nit, nombreProveedor);
		}catch(Exception e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));

		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return null;
	}
	/**
	 * Registra una categoria en la base de datos de superandes
	 * @param nombre - El nombre de la categoria 
	 * @return
	 */
	public Categoria registrarCategoria(String nombre) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			long idCategoria = nextval();
			long tuplasInsertadas = sqlCategoria.agregarCategoria(pm,idCategoria,nombre);

			log.trace ("Inserción categoria: " + idCategoria + ": " + tuplasInsertadas + " tuplas insertadas");
			tx.commit();
			return new Categoria(idCategoria, nombre);
		}catch(Exception e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));

		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return null;
	}
	/**
	 * Registra un producto en la base de datos de superandes
	 * @param nombre - El nombre del producto
	 * @param marca - La marca del producto
	 * @param idTipoproducto - El identificador del tipo de producto que es el producto
	 * @param presentacion - La presentacion en la que viene el producto
	 * @param cantPres - La cantidad que viene en la presentacion del producto
	 * @param uniMed -  La unidad de medida del producto
	 * @param volEmpaque - El volumen del producto comn empaque
	 * @param pesoEmpaque - El peso del producto con empaque
	 * @param codBarras . El codigo de barras del producto
	 * @return
	 */
	public Producto registrarProducto(String nombre, String marca, long idTipoproducto, String presentacion,
			double cantPres, String uniMed, double volEmpaque, double pesoEmpaque, String codBarras) {
		// TODO Auto-generated method stub

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			long idProducto = nextval();
			long tuplasInsertadas = sqlProducto.agregarProducto(pm,idProducto,nombre,marca,idTipoproducto,presentacion,cantPres,uniMed,volEmpaque,pesoEmpaque,codBarras);
			log.trace ("Inserción producto: " + idProducto + ": " + tuplasInsertadas + " tuplas insertadas");
			tx.commit();
			return new Producto(idProducto, nombre, marca, idTipoproducto, presentacion, cantPres, uniMed, volEmpaque, pesoEmpaque, codBarras);
		}catch(Exception e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));

		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return null;
	}
	/**
	 * registra una tupla en la tabla Vende de la base de datos de Superandes
	 * @param idProducto - El identificador del producto
	 * @param idSucursal- El identificador de la sucursal
	 * @param precioProducto - El precio del producto
	 * @param precioUniMedida - El precio por unidad de medida del producto
	 * @param nivReorden - El nivel de reorden del producto
	 * @param cantRecompra - La cantidad de recompra del producto
	 * @return - El objeto Vende con toda la información de la tupla creada
	 * @throws Exception 
	 */
	public Vende registrarProductoEnSucursal(long idProducto, long idSucursal, double precioProducto,
			double precioUniMedida, int nivReorden, int cantRecompra) throws Exception {
		// TODO Auto-generated method stub

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			Sucursal sucursal = sqlSucursal.darSucursal(pm,idSucursal);
			if(sucursal == null) {
				throw new Exception("La sucursal no existe");
			}
			Producto producto = sqlProducto.darProducto(pm, idProducto);
			if(producto == null) {
				throw new Exception("El producto no existe");
			}
			long tuplasInsertadas = sqlVende.agregarVende(pm,idSucursal,idProducto,precioProducto,precioUniMedida,nivReorden,cantRecompra);

			log.trace ("Inserción en la tabla vende: " + idProducto + ": " + tuplasInsertadas + " tuplas insertadas");
			tx.commit();
			return new Vende(idSucursal, idProducto, precioProducto, precioUniMedida, nivReorden, cantRecompra);

		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));

		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return null;
	}
	/**
	 * Registra un cliente en la base de datos de superandes
	 * @param identificacion - La identificacion del cliente
	 * @param tipo - El tipo de cliente (Natural, empresa)
	 * @param nombre - El nombre del cliente
	 * @param correo - El correo del cliente
	 * @param direccion - La direccion del cliente (Obligatorio para empresas)
	 * @return El cliente registrado
	 */
	public Cliente registrarCliente(long identificacion, String tipo, String nombre, String correo, String direccion) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			long tuplasInsertadas = sqlCliente.agregarCliente(pm,identificacion,tipo,nombre,correo,direccion);
			log.trace ("Inserción cliente: " + identificacion + ": " + tuplasInsertadas + " tuplas insertadas");
			tx.commit();
			return new Cliente(identificacion, tipo, nombre, correo, direccion, 0);
		}catch(Exception e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));

		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return null;
	}
	/**
	 * Registra una sucursal en la base de datos de superandes
	 * @param ciudad - La ciudad de la sucursal
	 * @param direccion - La direccion de la sucursal
	 * @param nombre - El nombre de la sucursal
	 * @return - La sucursal
	 */
	public Sucursal registrarSucursal(String ciudad, String direccion, String nombre) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			long idSucursal = nextval();
			long tuplasInsertadas = sqlSucursal.agregarSucursal(pm,idSucursal,ciudad,direccion,nombre);
			log.trace ("Inserción sucursal: " + nombre+ ": " + tuplasInsertadas + " tuplas insertadas");
			tx.commit();
			return new Sucursal(idSucursal, ciudad, direccion, nombre);
		}catch(Exception e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));

		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return null;
	}
	/**
	 * 
	 * @param idSucursal -  El identifiador de la sucursal a la que se le agregara la bodega
	 * @param idTipoProducto - El identificador del tipo de producto que almacenara la bodega
	 * @param volumen - El volumen de capacidad que soportara la bodega 
	 * @param peso - El peso que soportara la bodega
	 * @return - La bodega
	 * @throws Exception 
	 */
	public Bodega registrarBodega(long idSucursal, long idTipoProducto, double volumen, double peso) throws Exception {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();

			Sucursal sucursal = sqlSucursal.darSucursal(pm,idSucursal);
			if(sucursal == null) {
				throw new Exception("La sucursal no existe");
			}
			Object ans = (Object) sqlTipoProducto.verificarTipoProductoOfrecidoPorSucursal(pm,idTipoProducto,idSucursal);
			if(ans == null) {
				throw new Exception ("La sucursal no vende ese tipo de producto");
			}
			long idBodega = nextval();
			long tuplasInsertadas = sqlBodega.agregarBodega(pm,idBodega,idSucursal,idTipoProducto,volumen,peso);
			log.trace ("Inserción bodega: " + idBodega+ ": " + tuplasInsertadas + " tuplas insertadas");
			tx.commit();
			return new Bodega(idBodega, idSucursal, idTipoProducto, volumen, peso);
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));

		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return null;
	}

	public Estante registrarEstante(long idSucursal, long idTipoProducto, double volumen, double peso,
			int niveAbastecimiento) throws Exception {
		// TODO Auto-generated method stub

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();

			Sucursal sucursal = sqlSucursal.darSucursal(pm,idSucursal);
			if(sucursal == null) {
				throw new Exception("La sucursal no existe");
			}
			Object ans = (Object) sqlTipoProducto.verificarTipoProductoOfrecidoPorSucursal(pm,idTipoProducto,idSucursal);
			if(ans == null) {
				throw new Exception ("La sucursal no vende ese tipo de producto");
			}
			long idEstante = nextval();
			long tuplasInsertadas = sqlEstante.agregarEstante(pm,idEstante,idSucursal,idTipoProducto,volumen,peso,niveAbastecimiento);
			if(tuplasInsertadas == 0) {
				throw new Exception("No se pudo agregar el estante");
			}
			log.trace ("Inserción estante: " + idEstante+ ": " + tuplasInsertadas + " tuplas insertadas");
			tx.commit();
			return new Estante(idEstante, idSucursal, idTipoProducto, volumen, peso,niveAbastecimiento);
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));

		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return null;
	}

	/**
	 * Método que registra un pedido de una Sucursal a un Proveedor, teniendo en cuenta el nivel de reorden del prducto y que haya capacidad de almacenamiento tanto en
	 * bodega como estantes para los productos a solicitar
	 * @param idProveedor El identificador del proveedor al que se le hara el pedido
	 * @param idSucursal El identificador de la Sucursal que esta solicitando el pedido del producto
	 * @param idProducto El identificador del producto que se esta solicitando la Sucursal
	 * @param precio El precio al que se comprara la unidad del producto al proveedor
	 * @param fecha La fecha esperada de entrega del pedido
	 * @return Retorna la orden/pedido hecha por la sucursal al proveedor
	 * @throws Exception
	 */
	public Orden registrarPedido(long idProveedor, long idSucursal, long idProducto, double precio, Timestamp fecha) throws Exception{
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		int cantidadEnBodega;
		int cantidadEnEstante;
		int cantidadTotalProducto;
		long idTipoProducto;
		Object answerBodega ;
		Object answerEstante ;
		Object[] tuplaBodega;
		Object[] tuplaEstante;
		try
		{
			tx.begin();
			//Reviso que la sucursal si ofrezca la categoria del producto
			Object answer = sqlProducto.darProductoOfrecidoPorSucursal(pm,idProducto,idSucursal);
			if(answer == null) {
				throw new Exception ("La sucursal no vende ese tipo de productos");

			}
			//Revisar que el Proveedor si provea el producto
			VOProvee provee = sqlProvee.darProvee(pm,idProveedor, idProducto);
			if(provee == null) {
				throw new Exception ("El proveedor no provee ese producto");
			}
			//Ahora reviso que la sucursal si venda ese producto, es decir que ya tenga un precio etc. (Que exista una tupla en la tabla VENDE donde el idSucursal y el idProducto exista
			VOVende vende = sqlVende.darPorIdSucursalYIdProducto(pm, idSucursal, idProducto);
			if(vende == null) {
				throw new Exception("La sucursal no ha registrado la informacion del producto para ponerlo a la venta.");
			}
			//Ahora reviso el NIVEL DE REORDEN este en deficit y que en realidad se necesita realizar un pedido.
			// Sumo la cantidad de ese producto que hay en Estantes y en bodega haber si es menor que el NIVEL DE REORDEN
			answerBodega = sqlBodega.darCantidadTotalProductos(pm,idSucursal,idProducto);
			tuplaBodega = (Object[]) answerBodega;
			cantidadEnBodega = ((BigDecimal) tuplaBodega[1]).intValue();
			// Reviso bodegas y estantes para saber donde se van a almacenar los productos que compre la sucursal
			answerEstante = sqlEstante.darCantidadTotalProductos (pm,idSucursal, idProducto);
			tuplaEstante = (Object[]) answerEstante;
			cantidadEnEstante = ((BigDecimal) tuplaEstante[1]).intValue();
			//Sumo la cantidad del producto que hay en Bodega y en Estantes para saber si es menor o igual que el NIVEL DE REORDEN
			cantidadTotalProducto = cantidadEnBodega + cantidadEnEstante;
			if(cantidadTotalProducto > vende.getNivReorden()) {
				throw new Exception("La cantidad total del producto que se tienen en la sucursal es mayor a la cantidad del nivel de reorden del producto.");
			}
			//Reviso que las bodegas y estantes tengan la capacidad de almacenamiento: CAPACIDAD DISPONIBLE)
			VOProducto producto = sqlProducto.darProducto(pm, idProducto);
			idTipoProducto = producto.getIdTipoProducto();
			/*
			Object[] aux = (Object[]) answer;
			idTipoProducto = ((BigDecimal)aux[0]).intValue();
			 */
			//---PRUEBA
			System.out.println("ID TIPO PRODUCTO: "+ idTipoProducto);
			Object infoCapacidadTotalBodega = sqlBodega.darVolumenYPesoTotalCapacidad(pm,idSucursal,idTipoProducto);
			Object[] tuplaInfoCapTotaBod = (Object[]) infoCapacidadTotalBodega;
			//-----PRUEBA
			System.out.println(((BigDecimal)tuplaInfoCapTotaBod[2]).doubleValue());
			double capVolumenBod = ((BigDecimal)tuplaInfoCapTotaBod[2]).doubleValue();
			double capPesoBod= ((BigDecimal) tuplaInfoCapTotaBod[3]).doubleValue();

			Object infoCapacidadTotalEstante = sqlEstante.darVolumenYPesoTotalCapacidad(pm,idSucursal,idTipoProducto);
			Object[] tuplaInfoCapTotEst = (Object[]) infoCapacidadTotalEstante;
			double capVolumenEst = ((BigDecimal) tuplaInfoCapTotEst[2]).doubleValue();
			double capPesoEst = ((BigDecimal) tuplaInfoCapTotEst[3]).doubleValue();

			Object infoCapacidadOcupadaBodega = sqlBodega.darVolumenYPesoOcupado(pm, idSucursal, idTipoProducto);
			Object[] tuplaInfoCapOcuBod = (Object[]) infoCapacidadOcupadaBodega;
			double capVolumenOcuBod = ((BigDecimal) tuplaInfoCapOcuBod[2]).doubleValue();
			double capPesoOcuBod = ((BigDecimal) tuplaInfoCapOcuBod[3]).doubleValue();

			Object infoCapacidadOcupadaEstante = sqlEstante.darVolumenYPesoOcupado(pm, idSucursal, idTipoProducto);
			Object[] tuplaInfoCapOcuEst = (Object[]) infoCapacidadOcupadaEstante;
			double capVolumenOcuEst = ((BigDecimal) tuplaInfoCapOcuEst[2]).doubleValue();
			double capPesoOcuEst = ((BigDecimal) tuplaInfoCapOcuEst[3]).doubleValue();


			double capDisponibleVolBod = capVolumenBod - capVolumenOcuBod;
			double capDisponiblePesBod = capPesoBod - capPesoOcuBod;

			//Hayar diferencia para Estante
			double capDisponibleVolEst = capVolumenEst - capVolumenOcuEst;
			double capDisponiblePesEst = capPesoEst - capPesoOcuEst;

			//Averiguar la capacidad que se necesitara guardar

			double volumenRequerido = vende.getCantRecompra() * producto.getVolEmpaque();
			double pesoRequerido = vende.getCantRecompra() * producto.getPesoEmpaque();

			if(((capDisponibleVolBod + capDisponibleVolEst) < volumenRequerido) || ((capDisponiblePesBod + capDisponiblePesEst) < pesoRequerido)) {
				throw new Exception ("No hay capacidad en bodega y estantes para almacenar los productos del pedido");

			}
			//------------
			if((cantidadTotalProducto > vende.getNivReorden())) {
				throw new Exception ("Aun no se ha alcanzado el nivel de reorden del producto");
			}
			long idOrden = nextval ();
			long tuplasInsertadas = sqlOrden.adicionarOrden(pm, idOrden, idProveedor, idSucursal, idProducto, vende.getCantRecompra(),precio,fecha);
			log.trace ("Inserción orden: " + idOrden + ": " + tuplasInsertadas + " tuplas insertadas");
			tx.commit();
			return new Orden(idOrden, idProveedor, idSucursal, idProducto, vende.getCantRecompra(), precio, "No entregado", fecha, null, null);
		}
		catch (javax.jdo.JDOException e)
		{
			//javax.jdo.JDOException
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public void registrarLlegadaPedido(int idOrden, int cantidad, String calificacion) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			//Busco la orden en la base de datos
			VOOrden orden = sqlOrden.darOrden(pm,idOrden);
			if(orden != null) {
				Calendar calendar = Calendar.getInstance();
				Date now = calendar.getTime();
				Timestamp fecha = new Timestamp(now.getTime());
				long tuplasActualizadas = sqlOrden.actualizarOrdenLlegada(pm, idOrden, fecha, calificacion);

				if(tuplasActualizadas == 1) //Valido que si se haya realizado la actualizacion
				{
					VOProducto producto = sqlProducto.darProducto(pm, orden.getIdProducto()); //Devuelve el producto que se pidio en la Orden
					//Ahora procedo a buscar una Bodega y un Estante que guarde el tipo de producto del producto y que sea de la Sucursal que realizo el pedido
					List<Object> tuplas = sqlBodega.darBodegasPorTipoProductoYSucursal(pm,orden.getIdSucursal(),producto.getIdTipoProducto());
					List<Long> idsBodegas = new LinkedList<>();
					for(Object tupla: tuplas) {
						Object[] data = (Object[]) tupla;
						idsBodegas.add((Long)data[0]);
					}
					//tuplas = sqlEstante.darEstantesPorTipoProductoYSucursal(pm)

				}

			}else {
				//No se lleva a cabo ningun proceso por que el pedido/orden no existe 
			}


			tx.commit();
		}catch(Exception e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));

		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	/**
	 * Registra la venta de un producto en una sucursal en la base de datos de superandes, generando la correspondiente factura
	 * @param idSucursal - El identificador de la sucursal donde se realizara la compra
	 * @param idProducto - El identificador del producto vendido - El numero de uniades vendidas
	 * @return La factura generada
	 * @throws Exception 
	 */
	public Factura registrarVenta(long idSucursal, long idProducto, long idCliente, long numUnidades) throws Exception {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			Object cliente = sqlCliente.darCliente(pm,idCliente);
			if(cliente == null) {
				throw new Exception("El cliente no existe");
			}
			Sucursal sucursal = sqlSucursal.darSucursal(pm,idSucursal);
			if(sucursal == null) {
				throw new Exception("La sucursal no existe");
			}
			Vende vende = sqlVende.darPorIdSucursalYIdProducto(pm, idSucursal, idProducto);
			if(vende == null) {
				throw new Exception ("La sucursal no vende ese producto");
			}
			//Agregar soporte para poder comprar promociones
			Object answer = sqlEstante.darCantidadTotalDeUnProducto(pm,idSucursal,idProducto);

			if(answer == null) {
				throw new Exception("El producto no se ha puesto en estantes por ende no se puede vender");
			}

			Object[] tupla = (Object[]) answer;
			long cantidadTotalEnEstantes = ((BigDecimal)tupla[1]).longValue(); 
			if(numUnidades > cantidadTotalEnEstantes) {
				throw new Exception("La cantidad a comprar supera la cantidad en estantes");
			}

			long tuplasActualizadas = sqlProductoEstante.actualizarCantidad(pm,idSucursal,idProducto,numUnidades);
			if(tuplasActualizadas == 0) {
				throw new Exception("Error intentando actualizar el inventario");
			}
			//Chequear nivel de reabastecimiento para pedir a proveedor

			double total = vende.getPrecio() * numUnidades;
			long idFactura = nextval();
			Timestamp fecha = Timestamp.valueOf(LocalDateTime.now());
			long tuplasInsertadas = sqlFactura.agregarFactura(pm,idFactura,idCliente,idSucursal,fecha,total);
			long tuplasInsertadas2 = sqlFacturaProducto.agregarFacturaProducto(pm,idFactura,idProducto,numUnidades);
			tx.commit();
			return new Factura(idFactura, idCliente, idSucursal, fecha, total);
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public Promocion registrarPromocion(long idSucursal, long idProducto, long idProveedor,long cantProd, Timestamp fechaInicio,
			Timestamp fechaFin) throws Exception {
	
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			Sucursal sucursal = sqlSucursal.darSucursal(pm,idSucursal);
			if(sucursal == null) {
				throw new Exception("La sucursal no existe");
			}
			Vende vende = sqlVende.darPorIdSucursalYIdProducto(pm, idSucursal, idProducto);
			if(vende == null) {
				throw new Exception ("La sucursal no vende ese producto");
			}
			Provee provee = sqlProvee.darProvee(pm, idProveedor, idProducto);
			
			if(provee == null) {
				throw new Exception ("El proveedor no provee ese producto");
			}
			if(fechaInicio.before(Timestamp.valueOf(LocalDateTime.now()))) {
				throw new Exception("La fecha de inicio no puede ser anterior a la fecha de hoy.");
			}
			if(fechaFin.before(Timestamp.valueOf(LocalDateTime.now()))) {
				throw new Exception("La fecha de finalización no puede ser anterior a la fecha de hoy.");
			}
			if(cantProd < 1) {
				throw new Exception("La cantidad de productos tiene que ser minimo 1");
			}
			long idPromocion = nextval();
			sqlPromocion.agregarPromocion(pm, idPromocion, idProveedor, idProducto, (int)cantProd, true, fechaInicio, fechaFin, 0);
			
			tx.commit();
			return new Promocion(idPromocion, idProveedor, idProducto, (int)cantProd, true, fechaInicio, fechaFin);
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public CarritoCompras registrarCarritoSucursal(int idSucursal) throws Exception {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();

			Sucursal sucursal = sqlSucursal.darSucursal(pm,idSucursal);
			if(sucursal == null) {
				throw new Exception("La sucursal no existe");
			}



			//long tuplasInsertadas = sqlCarritoCompras.agregarCarritoCompras(pm, id, estado, idCliente)
			long idCarrito = nextval();
			sqlCarritoCompras.agregarCarritoCompras(pm, idCarrito, "DISPONIBLE",  (long)idSucursal);
			tx.commit();
			return new CarritoCompras(idCarrito, "DISPONIBLE", null,idSucursal);

		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<Object[]> consultarDineroRecolectadoSucursales(Timestamp fechaInicio, Timestamp fechaFinal) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			//Busco la orden en la base de datos

			List<Object[]> lista = sqlFactura.darDineroRecolectadoSucursales(pm,fechaInicio,fechaFinal);
			tx.commit();
			return lista;
		}catch(Exception e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

	}

	public List<Object[]> consultarIndiceOcupacionEstantesPorSucursal(int idSucursal) throws Exception {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			//Busco la orden en la base de datos
			VOSucursal s = (VOSucursal)sqlSucursal.darSucursal(pm, idSucursal);
			if(s==null) {
				throw new Exception("La sucursal no existe");
			}
			List<Object[]> lista = sqlEstante.darIndiceOcupacionPorSucursal(pm,idSucursal);
			tx.commit();
			return lista;
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<Object[]> consultarIndiceOcupacionBodegasPorSucursal(int idSucursal) throws Exception {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			//Busco la orden en la base de datos
			VOSucursal s = (VOSucursal)sqlSucursal.darSucursal(pm, idSucursal);
			if(s==null) {
				throw new Exception("La sucursal no existe");
			}
			List<Object[]> lista = sqlBodega.darIndiceOcupacionPorSucursal(pm,idSucursal);
			tx.commit();
			return lista;
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<Factura> consultarVentasUsuarioEnRango(String idUsuario,Timestamp fechaInicio, Timestamp fechaFinal) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			//Busco la orden en la base de datos

			List<Factura> lista = sqlFactura.darVentasUsuarioEnRango(pm,idUsuario,fechaInicio,fechaFinal);
			tx.commit();
			return lista;
		}catch(Exception e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	public List<Object[]> consultarOperacion(long idTipoProducto, Timestamp fechaInicio, Timestamp fechaFinal) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			//Busco la orden en la base de datos

			List<Object[]> lista = sqlTipoProducto.darOperacionSuperandesPorTipoProducto(pm, idTipoProducto, fechaInicio, fechaFinal);
			tx.commit();
			return lista;
		}catch(Exception e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	
	public List<Object[]> consultarClientesFrecuentes(long idSucursal) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();

			List<Object[]> lista = sqlCliente.darClientesFrecuentes(pm, idSucursal);
			tx.commit();
			return lista;
		}catch(Exception e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public void agarrarCarrito(int idSucursal, int idCliente, int idCarrito) throws Exception {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			//Busco la orden en la base de datos

			VOSucursal sucursal = sqlSucursal.darSucursal(pm, idSucursal);
			if(sucursal == null) {
				throw new Exception ("La sucursal no existe.");
			}

			Object cliente = sqlCliente.darCliente(pm, idCliente);
			if(cliente == null) {
				throw new Exception ("El cliente no se encuentra en la base de datos de Superandes.");
			}
			//Añadir validación de que el carrito existe y modificar tablas
			Object[] object = sqlCarritoCompras.darObjetoCarritoComprasPorId(pm, idCarrito);
			VOCarritoCompras carrito = new CarritoCompras();
			if(object == null) {
				throw new Exception ("El carrito de compras no existe en la sucursal "+idSucursal);
			}
			carrito.convertirACarrito(object);
			System.out.println(carrito);
			
			if(carrito.getEstado().equals("DISPONIBLE")) {
				//Actualizar la tupla en la tabla CarritoCompras con el ID de cliente y cambiar el estado a NO DISPONIBLE:
				long tuplasActualizadas = sqlCarritoCompras.actualizarCarritoComprasEstadoYIdCliente(pm, idCarrito, "NO DISPONIBLE", idCliente);
				if(tuplasActualizadas == 0) {
					throw new Exception ("No se logró agarrar el carrito con exito");
				}
			}else {
				throw new Exception("El carrito no se encuentra disponible. Otro cliente lo tiene agarrado");
			}





			tx.commit();
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<Object[]>  adicionarProductoACarrito(long idCliente, long idCarrito, long idSucursal,
			int cantidadCarrito, int idEstante, int idProducto) throws Exception {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			int cantidadTotal = sqlProductoEstante.darCantidadProducto(pm, idEstante, idProducto);
			int cantidadNueva = cantidadTotal-cantidadCarrito;
			if(cantidadNueva < 0 ) {
				throw new Exception("La cantidad solicitada supera la cantidad disponible en estante");
			}

			long tuplasActualizadas = sqlProductoEstante.actualizarCantidadEstantePorCarrito(pm, (long)idEstante, (long)idProducto, cantidadNueva);
			if(tuplasActualizadas == 0) {
				throw new Exception("No se pudo actualizar la cantidad en estantes");
			}

			tuplasActualizadas = sqlVendeCarrito.agregarVendeCarrito(pm, idCarrito, (long) idProducto, cantidadCarrito);
			if(tuplasActualizadas == 0) {
				throw new Exception("No se pudo insertar el producto al carrito");
			}

			List<Object[]> listaItems = sqlVendeCarrito.darListaItems(pm,idCarrito);

			tx.commit();

			return listaItems;
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<Object[]> devolverProductoCarrito(int idProducto, int idCarrito, int cantidadCarrito, int idEstante) throws Exception {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			int cantidadEnEstante = sqlProductoEstante.darCantidadProducto(pm, idEstante, idProducto);
			int cantidadNueva = cantidadEnEstante + cantidadCarrito;
			long tuplasActualizadas = sqlProductoEstante.actualizarCantidadEstantePorCarrito(pm, (long)idEstante, (long)idProducto, cantidadNueva);
			if(tuplasActualizadas == 0) {
				throw new Exception("No se pudo actualizar la cantidad en estantes");
			}
			tuplasActualizadas = sqlVendeCarrito.eliminarVendeCarrito(pm, idCarrito, (long) idProducto);
			if(tuplasActualizadas == 0) {
				throw new Exception("No se pudo eliminar el producto del carrito");
			}
			List<Object[]> listaItems = sqlVendeCarrito.darListaItems(pm,idCarrito);
			tx.commit();
			return listaItems;
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public void abandonarCarrito(int idCarrito) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();
			List<Object[]> listaItemsABorrar = sqlVendeCarrito.darListaItemsParaBorrar(pm, idCarrito);
			for(Object[] obj:listaItemsABorrar){
				//Campos de la consulta
				long idProducto = ((BigDecimal) obj[0]).longValue();
				long idEstante = ((BigDecimal) obj[1]).longValue();
				String nombre = (String) obj[2];
				int cantidadCarrito = ((BigDecimal) obj[3]).intValue();
				double precio = ((BigDecimal) obj[4]).doubleValue();
				double subtotal = ((BigDecimal) obj[5]).doubleValue();


				int cantidadEnEstante = sqlProductoEstante.darCantidadProducto(pm, idEstante, idProducto);
				int cantidadNueva = cantidadEnEstante + cantidadCarrito;
				long tuplasActualizadas = sqlProductoEstante.actualizarCantidadEstantePorCarrito(pm, (long)idEstante, (long)idProducto, cantidadNueva);
				long tuplasBorradas = sqlVendeCarrito.eliminarVendeCarrito(pm, idCarrito, idProducto);
			}
			sqlCarritoCompras.actualizarCarritoComprasAbandonado(pm, idCarrito, "DISPONIBLE");

			tx.commit();
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

	}

	public Factura pagarProductosCarrito(int idCarrito, int idCliente, int idSucursal) throws Exception {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		int cantidadEnBodega;
		int cantidadEnEstante;
		int cantidadTotalProducto;
		long idTipoProducto;
		Object answerBodega ;
		Object answerEstante ;
		Object[] tuplaBodega;
		Object[] tuplaEstante;
		
		tx.setOptimistic(false); //CHEQUEAR SI ESTA LINEA ESTA CORRECTO
		try {
			tx.begin();
			List<Object[]> listaItemsAPagar = sqlVendeCarrito.darListaItemsParaBorrar(pm, idCarrito);
			List<Item> items = new ArrayList<>();
			double totalCompra=0;
			for(Object[] obj:listaItemsAPagar){
				//Campos de la consulta
				long idProducto = ((BigDecimal) obj[0]).longValue();
				long idEstante = ((BigDecimal) obj[1]).longValue();
				String nombre = (String) obj[2];
				int cantidadCarrito = ((BigDecimal) obj[3]).intValue();
				double precio = ((BigDecimal) obj[4]).doubleValue();
				double subTotal = ((BigDecimal) obj[5]).doubleValue();
				
				totalCompra+=subTotal;
				Item item = new Item(idProducto, nombre, cantidadCarrito, precio, subTotal);
				items.add(item);
			}
			Timestamp fecha = Timestamp.valueOf(LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(), LocalDateTime.now().getHour(), LocalDateTime.now().getMinute()));
			long idFactura = nextval();
			long tuplasInsertadas = sqlFactura.agregarFactura(pm, idFactura, idCliente, idSucursal, fecha, totalCompra);
			if(tuplasInsertadas == 0) {
				throw new Exception("Error generando la factura de la compra");
			}
			for(Item i:items) {
				sqlFacturaProducto.agregarFacturaProducto(pm, idFactura, i.getIdProducto(), i.getCantidad());
				sqlVendeCarrito.eliminarVendeCarrito(pm, idCarrito, i.getIdProducto());
				
				//---------------------------------------------------------------------------------------------------------------
				
				answerBodega = sqlBodega.darCantidadTotalProductos(pm,idSucursal,i.getIdProducto());
				tuplaBodega = (Object[]) answerBodega;
				cantidadEnBodega = ((BigDecimal) tuplaBodega[1]).intValue();
				// Reviso bodegas y estantes para saber donde se van a almacenar los productos que compre la sucursal
				answerEstante = sqlEstante.darCantidadTotalProductos (pm,idSucursal, i.getIdProducto());
				tuplaEstante = (Object[]) answerEstante;
				cantidadEnEstante = ((BigDecimal) tuplaEstante[1]).intValue();
				//Sumo la cantidad del producto que hay en Bodega y en Estantes para saber si es menor o igual que el NIVEL DE REORDEN
				cantidadTotalProducto = cantidadEnBodega + cantidadEnEstante;
				
				Object[] obj= sqlPromocion.darPromocionDeProducto(pm, i.getIdProducto());
				if(obj != null) {
					long idPromocion = ((BigDecimal) obj[0]).longValue();
					long tuplasActualizadas= sqlPromocion.actualizarCantidadesPromocion(pm, idPromocion, i.getCantidad());
				}
				
				VOVende vende = sqlVende.darPorIdSucursalYIdProducto(pm, idSucursal, i.getIdProducto());
				if(cantidadTotalProducto < vende.getNivReorden()) {
					long idProveedor = sqlProvee.darListaProveePorIdProducto(pm,i.getIdProducto());
					if(idProveedor != -1) {
						this.registrarPedido(idProveedor, idSucursal, i.getIdProducto(), i.getPrecio(), Timestamp.valueOf(LocalDateTime.now().plusDays(3)));
					}
					
				}
			}
			
			sqlCarritoCompras.actualizarCarritoComprasAbandonado(pm, idCarrito, "DISPONIBLE");
			
			
			
			tx.commit();
			return new Factura(idFactura, idCliente, idSucursal, fecha, totalCompra);
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<Object[]> darProductosDisponiblesSucursal(int idSucursal) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();

			List<Object[]> productos = sqlVende.darProductosDisponiblesSucursal(pm,idSucursal);


			tx.commit();
			return productos;
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	public List<Object[]> generarInfoFactura(long idFactura) throws Exception {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {
			tx.begin();

			List<Object[]> infoFactura = sqlFactura.darInfoFactura(pm,idFactura);
			if(infoFactura == null) {
				throw new Exception("Error generando información de la factura");
			}

			tx.commit();
			return infoFactura;
		}catch(javax.jdo.JDOException e) {
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Transacción para el generador de secuencia de Superandes
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Superandes
	 */
	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	
	


	















































}
