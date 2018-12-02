package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import oracle.net.aso.p;
import uniandes.isis2304.superandes.persistencia.PersistenciaSuperandes;

public class Superandes {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Superandes.class.getName());
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaSuperandes ps;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Superandes ()
	{
		ps = PersistenciaSuperandes.getInstance ();
	}

	/**
	 * El constructor que recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Superandes (JsonObject tableConfig)
	{
		ps = PersistenciaSuperandes.getInstance (tableConfig);
	}

	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		ps.cerrarUnidadPersistencia ();
	}
	
	public PersistenciaSuperandes getPersistenciaSuperandes() {
		return this.ps;
	}


	/**
	 * Encuentra todos las sucursales en superandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Sucursal con todos las sucursales que conoce la aplicación, llenos con su información básica
	 */
	public List<Sucursal> darSucursales() {
		// TODO Auto-generated method stub
		log.info ("Consultando Sucursales");
		List<Sucursal> sucursales = ps.darSucursales ();	
		log.info ("Consultando Sucursales: " + sucursales.size() + " existentes");
		return sucursales;
	}

	/**
	 * Encuentra todos las sucursales en Superandes y los devuelve como una lista de VOSucursal
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOSucursal con todos las sucursales que conoce la aplicación, llenos con su información básica
	 */
	public List<VOSucursal> darVOSucursales ()
	{
		log.info ("Generando los VO de Sucursales");        
		List<VOSucursal> voSucursales = new LinkedList<VOSucursal> ();
		for (Sucursal s : ps.darSucursales())
		{
			voSucursales.add (s);
		}
		log.info ("Generando los VO de Sucursales: " + voSucursales.size() + " existentes");
		return voSucursales;
	}
	/**
	 * Encuentra todos los proveedores en superandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Proveedor con todos los proveedores que conoce la aplicación, llenos con su información básica
	 */
	public List<Proveedor> darProveedores() {
		// TODO Auto-generated method stub
		log.info ("Consultando Proveedores");
		List<Proveedor> proveedores = ps.darProveedores();	
		log.info ("Consultando Proveedores: " + proveedores.size() + " existentes");
		return proveedores;
	}
	/**
	 * Encuentra todos lss categorias en superandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Categoria con todos las categorias que conoce la aplicación, llenos con su información básica
	 */
	public List<Categoria> darCategorias() {
		// TODO Auto-generated method stub
		log.info ("Consultando Categorias");
		List<Categoria> categorias = ps.darCategorias();	
		log.info ("Consultando Categorias: " + categorias.size() + " existentes");
		return categorias;
	}
	/**
	 * Encuentra todos los tipo de producto en superandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos TipoProducto con todos los tipos de producto que conoce la aplicación, llenos con su información básica
	 */
	public List<TipoProducto> darTipoProductos() {
		// TODO Auto-generated method stub
		log.info ("Consultando TipoProductos");
		List<TipoProducto> tipoProductos= ps.darTipoProductos();	
		log.info ("Consultando TipoProductos: " + tipoProductos.size() + " existentes");
		return tipoProductos;
	}
	/**
	 * Encuentra todos los productos en superandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Producto con todos los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<Producto> darProductos() {
		// TODO Auto-generated method stub
		log.info ("Consultando Productos");
		List<Producto> productos= ps.darProductos();	
		log.info ("Consultando Productos: " + productos.size() + " existentes");
		return productos;
	}
	/**
	 * Encuentra los productos disponibles para la venta de la sucursal dada. Es decir que se encuentren en estantes y que la
	 * sucursal efectivamente venda esos productos
	 * @param idSucursal- el identificador de la sucursal
	 */
	public List<Object[]> darProductosDisponiblesSucursal(int idSucursal) {
		// TODO Auto-generated method stub
		log.info("Consultando los productos disponibles de la sucursal " + idSucursal);
		List<Object[]> productos = ps.darProductosDisponiblesSucursal(idSucursal);
		log.info ("Consultando productos disponibles de la sucursal: "+ idSucursal+ " total productos:" +  productos.size() + " existentes");
		return productos;
	}
	
	/**
	 * Encuentra todos los proveedores en Superandes y los devuelve como una lista de VOProveedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOProveedor con todos los proveedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOProveedor> darVOProveedores() {
		// TODO Auto-generated method stub
		log.info ("Generando los VO de Proveedores");        
		List<VOProveedor> voProveedores= new LinkedList<VOProveedor> ();
		for (Proveedor p : ps.darProveedores())
		{
			voProveedores.add (p);
		}
		log.info ("Generando los VO de Proveedores: " + voProveedores.size() + " existentes");
		return voProveedores;
	}
	/**
	 * Encuentra todos las categorias en Superandes y los devuelve como una lista de VOCategoria
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOCategoria con todos las categorias que conoce la aplicación, llenos con su información básica
	 */
	public List<VOCategoria> darVOCategorias() {
		// TODO Auto-generated method stub
		log.info ("Generando los VO de Categorias");        
		List<VOCategoria> voCategorias= new LinkedList<VOCategoria> ();
		for (Categoria c : ps.darCategorias())
		{
			voCategorias.add (c);
		}
		log.info ("Generando los VO de Categorias: " + voCategorias.size() + " existentes");
		return voCategorias;
	}
	/**
	 * Encuentra todos los tipos de productos en Superandes y los devuelve como una lista de VOTipoProducto
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOTipoProducto con todos las tipos de producto que conoce la aplicación, llenos con su información básica
	 */
	public List<VOTipoProducto> darVOTipoProductos() {
		// TODO Auto-generated method stub
		log.info ("Generando los VO de Tipo Producto");        
		List<VOTipoProducto> voTipoProductos= new LinkedList<VOTipoProducto> ();
		for (TipoProducto tp : ps.darTipoProductos())
		{
			voTipoProductos.add (tp);
		}
		log.info ("Generando los VO de TipoProducto: " + voTipoProductos.size() + " existentes");
		return voTipoProductos;
	}
	/**
	 * Encuentra todos los productos en Superandes y los devuelve como una lista de VOProducto
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOProducto con todos los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<VOProducto> darVOProductos() {
		// TODO Auto-generated method stub
		log.info ("Generando los VO de Producto");        
		List<VOProducto> voProductos= new LinkedList<VOProducto> ();
		for (Producto p : ps.darProductos())
		{
			voProductos.add (p);
		}
		log.info ("Generando los VO de Producto: " + voProductos.size() + " existentes");
		return voProductos;
	}

	public List<VOOrden> darVOOrdenes() {
		// TODO Auto-generated method stub
		log.info("Generando los VO de Ordenes");
		List<VOOrden> voOrdenes = new LinkedList<VOOrden>();
		for(Orden o: ps.darOrdenes()) {
			voOrdenes.add(o);
		}
		log.info("Generando los VO de Orden: "+voOrdenes.size() + " existentes");
		return voOrdenes;
	}

	public Proveedor registrarProveedor(long nit, String nombreProveedor) {
		// TODO Auto-generated method stub
		log.info("Registrando proveedor "+nit+ " con nombre "+ nombreProveedor);
		Proveedor proveedor = ps.registrarProveedor(nit,nombreProveedor);
		log.info("Registrando proveedor "+proveedor);
		return proveedor;

	}

	public Categoria registrarCategoria(String nombre) {
		// TODO Auto-generated method stub
		log.info("Registrando categoria con nombre "+nombre);
		Categoria categoria= ps.registrarCategoria(nombre);
		log.info("Registrando categoria "+categoria);
		return categoria;
	}

	public Producto registrarProducto(String nombre, String marca, long idTipoproducto, String presentacion,
			double cantPres, String uniMed, double volEmpaque, double pesoEmpaque, String codBarras) {
		// TODO Auto-generated method stub
		log.info("Registrando producto "+nombre+ " de marca "+ marca);
		Producto producto = ps.registrarProducto(nombre,marca,idTipoproducto,presentacion,cantPres,uniMed,volEmpaque,pesoEmpaque,codBarras);
		log.info("Registrando producto "+producto);
		return producto;
	}
	
	public Vende registrarProductoEnSucursal(long idProducto, long idSucursal, double precioProducto,
			double precioUniMedida, int nivReorden, int cantRecompra) throws Exception {
		// TODO Auto-generated method stub
		log.info("Registrando producto "+idProducto +" en sucursal " + idSucursal);
		Vende vende = ps.registrarProductoEnSucursal(idProducto,idSucursal,precioProducto,precioUniMedida,nivReorden,cantRecompra);
		log.info("Registrando producto "+idProducto +" en sucursal " + idSucursal);
		return vende;
	}

	public Cliente registrarCliente(long identificacion, String tipo, String nombre, String correo, String direccion) {
		// TODO Auto-generated method stub
		log.info("Registrando cliente: "+ nombre+ "con identificacion "+ identificacion);
		Cliente cliente = ps.registrarCliente(identificacion,tipo,nombre,correo,direccion);
		log.info("Registrando cliente " + cliente);
		return cliente;
	}

	public Sucursal registrarSucursal(String ciudad, String direccion, String nombre) {
		// TODO Auto-generated method stub
		log.info("Registrando sucursal "+ nombre + "en "+ ciudad);
		Sucursal sucursal = ps.registrarSucursal(ciudad,direccion,nombre);
		log.info("Registrando sucursal " + sucursal);
		return sucursal;
	}

	public Bodega registrarBodega(long idSucursal, long idTipoProducto, double volumen, double peso) throws Exception {
		// TODO Auto-generated method stub
		log.info("Registrando bodega");
		Bodega bodega = ps.registrarBodega(idSucursal,idTipoProducto,volumen,peso); 
		log.info("Registrando bodega " + bodega );
		return bodega;
	}

	public Estante registrarEstante(long idSucursal, long idTipoProducto, double volumen, double peso,
			int niveAbastecimiento) throws Exception {
		log.info("Registrando estante");
		// TODO Auto-generated method stub
		Estante estante = ps.registrarEstante(idSucursal,idTipoProducto,volumen,peso,niveAbastecimiento); 
		log.info("Registrando estante" + estante);
		return estante;
	}

	public Orden registrarPedido(long idProveedor, long idSucursal, long idProducto, double precio,
			Timestamp fecha) throws Exception {
		// TODO Auto-generated method stub
		log.info("Registrando pedido de la sucursal "+idSucursal+ " al proveedor "+ idProveedor);
		Orden orden = (Orden)ps.registrarPedido(idProveedor,idSucursal, idProducto, precio, fecha);
		log.info("Registrando pedido: "+ orden);
		return orden;
	}

	public void registrarLlegadaPedido(int idOrden, int cantidad, String calificacion) {
		// TODO Auto-generated method stub
		log.info("Registrando la llegada de un pedido");
		ps.registrarLlegadaPedido(idOrden,cantidad,calificacion);

		log.info("Registrando la llegada del pedido "+ idOrden);
	}

	public Factura registrarVenta(long idSucursal, long idProducto, long idCliente,long numUnidades) throws Exception {
		// TODO Auto-generated method stub
		log.info("Registrando una venta en la sucursal: " + idSucursal + "del producto "+ idProducto);
		Factura factura = ps.registrarVenta(idSucursal,idProducto,idCliente,numUnidades);
		log.info("Registrando la venta " + factura);
		return factura;

	}
	
	public Promocion registrarPromocion(long idSucursal, long idProducto, long idProveedor,long cantProd, Timestamp fechaInicio,
			Timestamp fechaFin) throws Exception {
		log.info("Registrando una promocion en la sucursal: " + idSucursal + "del producto "+ idProducto);
		Promocion promocion = ps.registrarPromocion(idSucursal,idProducto,idProveedor,cantProd,fechaInicio,fechaFin);
		log.info("Registrando la promocion " + promocion);
		return promocion;
	}
	
	public CarritoCompras registrarCarritoSucursal(int idSucursal) throws Exception {
		log.info("Registrando un carrito de compras en la sucursal: " + idSucursal);
		CarritoCompras carrito = ps.registrarCarritoSucursal(idSucursal);
		return carrito;
	}

	public List<Object[]> consultarDineroRecolectadoSucursales(Timestamp fechaInicio, Timestamp fechaFinal) {
		log.info("Consultando ventas de sucursales entre "+fechaInicio.toString()+" y "+fechaFinal.toString());
		return ps.consultarDineroRecolectadoSucursales(fechaInicio,fechaFinal);
	}

	public List<VOFactura> consultarVentasUsuarioEnRango(String idUsuario,Timestamp fechaInicio, Timestamp fechaFinal) {
		// TODO Auto-generated method stub
		log.info("Consultando ventas a usuario entre "+fechaInicio.toString()+" y "+fechaFinal.toString());

		List<VOFactura> voFacturas= new LinkedList<VOFactura> ();
		for (Factura f : ps.consultarVentasUsuarioEnRango(idUsuario,fechaInicio,fechaFinal))
		{
			voFacturas.add (f);
		}
		return voFacturas;
	}

	public List<Object[]> consultarIndiceOcupacionEstantesPorSucursal(int  idSucursal) throws Exception {
		// TODO Auto-generated method stub
		log.info("Consultando indice de ocupacion de estantes de la sucursal "+idSucursal);

		return ps.consultarIndiceOcupacionEstantesPorSucursal(idSucursal);
	}

	public List<Object[]> consultarIndiceOcupacionBodegasPorSucursal(int idSucursal) throws Exception {
		log.info("Consultando indice de ocupacion de bodegas de la sucursal "+idSucursal);

		return ps.consultarIndiceOcupacionBodegasPorSucursal(idSucursal);
	}
	
	public List<Object[]> consultarOperacion(long idTipoProducto, Timestamp fechaInicio, Timestamp fechaFinal) {
		log.info("Consultando operación de Superandes del tipo producto: "+idTipoProducto);
		return (List<Object[]>) ps.consultarOperacion(idTipoProducto,fechaInicio,fechaFinal);
	}
	
	public List<Object[]> consultarClientesFrecuentes(long idSucursal) {
		log.info("Consultando los clientes frecuentes de la sucursal " + idSucursal);
		List<Object[]> lista = (List<Object[]>) ps.consultarClientesFrecuentes(idSucursal);
		log.info("Terminando de consultar los clientes frecuentes de la sucursal " + idSucursal);
		return lista;
	}

	public void agarrarCarrito(int idSucursal, int idCliente, int idCarrito) throws Exception {
		log.info("Agarrando el carrito " + idCarrito + " para el cliente" + idCliente + " en la sucursal " + idSucursal);
		
		ps.agarrarCarrito(idSucursal,idCliente,idCarrito);
	}

	public List<Object[]> adicionarProductoACarrito(long idCliente, long idCarrito, long idSucursal, 
			int cantidadCarrito, int idEstante, int idProducto) throws Exception {
		
		log.info("Adicionando el producto " + idProducto + "al carrito " + idCarrito + " del cliente " + idCliente);
		
		return (List<Object[]>)ps.adicionarProductoACarrito(idCliente,idCarrito,idSucursal,cantidadCarrito,idEstante,idProducto);
	}

	public List<Object[]> devolverProductoCarrito(int idProducto, int idCarrito, int cantidadCarrito, int idEstante) throws Exception {
		log.info("Devolviendo el producto " + idProducto + "del carrito " + idCarrito);
		
		return (List<Object[]>) ps.devolverProductoCarrito(idProducto,idCarrito,cantidadCarrito,idEstante);
	}

	public void abandonarCarrito(int idCarrito) {
		log.info("Abandonando el carrito " + idCarrito);
		ps.abandonarCarrito(idCarrito);
	}

	public Factura pagarProductosCarrito(int idCarrito, int idCliente, int idSucursal) throws Exception {
		log.info("Pagando productos del carrito " + idCarrito + " del cliente " + idCliente + " en la sucursal " + idSucursal);
		return ps.pagarProductosCarrito(idCarrito,idCliente,idSucursal);
	}

	public List<Object[]> generarInfoFactura(long idFactura) throws Exception {
		log.info("Generando informacion de factura " + idFactura);
		return (List<Object[]>) ps.generarInfoFactura(idFactura);
	}

	public List<Object[]> consultarConsumo(boolean except,long idCliente,long idSucursal,long idProducto, Timestamp fechaInicio, Timestamp fechaFinal,
			String criterioOrdenacion, String criterioOrdenacionAscDesc, String criterioAgrupacion) throws Exception {
		log.info("Consultando consumo del producto " + idProducto);
		return (List<Object[]>) ps.consultarConsumo(except,idCliente,idSucursal,idProducto,fechaInicio,fechaFinal,criterioOrdenacion,criterioOrdenacionAscDesc,criterioAgrupacion);
	}

	public Object[] consultarFuncionamiento() {
		log.info("Consultando funcionami¡ento");
		
		return (Object[]) ps.consultarFuncionamiento();
	}

	

	

	

}
