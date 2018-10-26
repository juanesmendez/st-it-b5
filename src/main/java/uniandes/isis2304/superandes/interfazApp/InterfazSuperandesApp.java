package uniandes.isis2304.superandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;


import javafx.scene.control.skin.TableHeaderRow;
import uniandes.isis2304.superandes.negocio.Sucursal;
import uniandes.isis2304.superandes.negocio.Superandes;
import uniandes.isis2304.superandes.negocio.VOBodega;
import uniandes.isis2304.superandes.negocio.VOCategoria;
import uniandes.isis2304.superandes.negocio.VOCliente;
import uniandes.isis2304.superandes.negocio.VOEstante;
import uniandes.isis2304.superandes.negocio.VOFactura;
import uniandes.isis2304.superandes.negocio.VOOrden;
import uniandes.isis2304.superandes.negocio.VOProducto;
import uniandes.isis2304.superandes.negocio.VOProveedor;
import uniandes.isis2304.superandes.negocio.VOSucursal;
import uniandes.isis2304.superandes.negocio.VOTipoProducto;


public class InterfazSuperandesApp extends JFrame implements ActionListener{

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazSuperandesApp.class.getName());

	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD.json"; 

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
	 */
	private JsonObject tableConfig;

	/**
	 * Asociación a la clase principal del negocio.
	 */
	private Superandes superandes;

	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
	/**
	 * Objeto JSON con la configuración de interfaz de la app.
	 */
	private JsonObject guiConfig;

	/**
	 * Panel de despliegue de interacción para los requerimientos
	 */
	private PanelDatos panelDatos;

	/**
	 * Menú de la aplicación
	 */
	private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicación. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazSuperandesApp( )
	{
		// Carga la configuración de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gráfica
		configurarFrame ( );
		if (guiConfig != null) 	   
		{
			crearMenu( guiConfig.getAsJsonArray("menuBar") );
		}

		tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		superandes = new Superandes (tableConfig);

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos ( );

		setLayout (new BorderLayout());
		add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
		add( panelDatos, BorderLayout.CENTER );        
	}

	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuración deseada
	 * @param archConfig - Archivo Json que contiene la configuración
	 * @return Un objeto JSON con la configuración del tipo especificado
	 * 			NULL si hay un error en el archivo.
	 */
	private JsonObject openConfig (String tipo, String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/**
	 * Método para configurar el frame principal de la aplicación
	 */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuración por defecto" );			
			titulo = "Superandes APP Default";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
			titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocation (50,50);
		setResizable( true );
		setBackground( Color.WHITE );

		setTitle( titulo );
		setSize ( ancho, alto);        
	}

	/**
	 * Método para crear el menú de la aplicación con base em el objeto JSON leído
	 * Genera una barra de menú y los menús con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los menùs deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creación de la barra de menús
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creación de cada uno de los menús
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creación de cada una de las opciones del menú
				JsonObject jo = op.getAsJsonObject(); 
				String lb =   jo.get("label").getAsString();
				String event = jo.get("event").getAsString();

				JMenuItem mItem = new JMenuItem( lb );
				mItem.addActionListener( this );
				mItem.setActionCommand(event);

				menu.add(mItem);
			}       
			menuBar.add( menu );
		}        
		setJMenuBar ( menuBar );	
	}

	/**
	 * Muestra en la interfaz todas las sucursales que se encuentran en la base de datos de Superandes
	 */
	public void mostrarSucursales() 
	{
		try {
			List<VOSucursal> sucursales = superandes.darVOSucursales();

			String resultado = null;

			resultado = listarSucursales(sucursales);
			panelDatos.actualizarInterfaz(resultado);

		}catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}
	/**
	 * Muestra en la interfaz todas los Proveedores que se encuentran en la base de datos de Superandes
	 */
	public void mostrarProveedores() 
	{
		try {
			List<VOProveedor> proveedores= superandes.darVOProveedores();

			String resultado = null;

			resultado = listarProveedores(proveedores);
			panelDatos.actualizarInterfaz(resultado);


		}catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}
	/**
	 * Muestra en la interfaz todas las categorias que se encuentran en la base de datos de Superandes
	 */
	public void mostrarCategorias() 
	{
		try {
			List<VOCategoria> categorias= superandes.darVOCategorias();

			String resultado = null;

			resultado = listarCategorias(categorias);
			panelDatos.actualizarInterfaz(resultado);


		}catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}
	/**
	 * Muestra en la interfaz todas los tipos de producto que se encuentran en la base de datos de Superandes
	 */
	public void mostrarTipoProductos() 
	{
		try {
			List<VOTipoProducto> tiposProductos = superandes.darVOTipoProductos();

			String resultado = null;

			resultado = listarTipoProductos(tiposProductos);
			panelDatos.actualizarInterfaz(resultado);


		}catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Muestra en la interfaz todas los productos que se encuentran en la base de datos de Superandes
	 */
	public void mostrarProductos() 
	{
		try {
			List<VOProducto> productos = superandes.darVOProductos();

			String resultado = null;

			resultado = listarProductos(productos);
			panelDatos.actualizarInterfaz(resultado);


		}catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/**
	 * Registra un proveedor en el sistema de superandes
	 */
	public void registrarProveedor() {

		try {
			JTextField fieldNit = new JTextField();
			JTextField fieldNombreProveedor = new JTextField();
			Object message[] = {
					"Digite el NIT del proveedor a registrar: ", fieldNit,
					"Digite el nombre del nuevo proveedor: ", fieldNombreProveedor
					
			};
			int option = JOptionPane.showConfirmDialog (this, message, "Registrar proveedor", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION) {
				
				if(!fieldNit.getText().toString().equals("") && !fieldNombreProveedor.getText().toString().equals("")) {
					VOProveedor proveedor= superandes.registrarProveedor(Long.valueOf(fieldNit.getText().toString()), fieldNombreProveedor.getText().toString());
					if(proveedor != null) {
						JOptionPane.showMessageDialog(this, "Se registro el proveedor con exito!", "Registro de proveedor exitoso", JOptionPane.INFORMATION_MESSAGE);
						String resultado = "En registrarProveedor\n\n";
						resultado += "Proveedor registrado exitosamente: " + proveedor;
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
					}
				}else {
					JOptionPane.showMessageDialog(this, "Se deben llenar todos los campos", "Error registrando proveedor", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}





		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error registrando proveedor", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
	
	public void registrarCategoria() {
		try {
			JTextField fieldNombreCategoria = new JTextField();
			Object message[] = {
					"Ingrese el nombre de la categoria: ", fieldNombreCategoria
					
			};
			int option = JOptionPane.showConfirmDialog (this, message, "Registrar categoria", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION) {
				
				if(!fieldNombreCategoria.getText().toString().equals("")) {
					VOCategoria categoria= superandes.registrarCategoria(fieldNombreCategoria.getText().toString());
					if(categoria != null) {
						JOptionPane.showMessageDialog(this, "Se registro la categoria con exito!", "Registro de categoria exitoso", JOptionPane.INFORMATION_MESSAGE);
						String resultado = "En registrarCategoria\n\n";
						resultado += "Categoria registrada exitosamente: " + categoria;
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
					}
				}else {
					JOptionPane.showMessageDialog(this, "Se deben llenar todos los campos", "Error registrando categoria", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error registrando proveedor", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	public void registrarProducto() {
		try {
			JTextField fieldNombre = new JTextField();
			JTextField fieldMarca = new JTextField();
			JTextField fieldIdTipoProd = new JTextField();
			JTextField fieldPresentacion = new JTextField();
			JTextField fieldCantPresent = new JTextField();
			JComboBox comboUniMed = new JComboBox<String>();
			comboUniMed.addItem("gr");
			comboUniMed.addItem("ml");
			JTextField fieldVolEmpaque = new JTextField();
			JTextField fieldPesoEmpaque= new JTextField();
			JTextField fieldCodBarras = new JTextField();

			
			Object message[] = {
					"Ingrese el nombre del nuevo producto: ", fieldNombre,
					"Ingrese la marca del nuevo producto: ", fieldMarca,
					"Ingrese el ID del tipo de producto: ", fieldIdTipoProd,
					"Ingrese la presentacion del nuevo producto: ", fieldPresentacion,
					"Ingrese la cantidad en la presentacion del producto:", fieldCantPresent,
					"Escoga la unidad de medida del producto (gr o ml)", comboUniMed,
					"Ingrese el volumen del producto incluyendo empaque: ", fieldVolEmpaque,
					"Ingrese el peso del producto incluyendo empaque: ", fieldPesoEmpaque,
					"Ingrese el codigo de barras: ", fieldCodBarras
			};
			int option = JOptionPane.showConfirmDialog (this, message, "Registrar producto", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION) {
				if(!fieldNombre.getText().toString().equals("") && !fieldMarca.getText().toString().equals("") && !fieldIdTipoProd.getText().toString().equals("") &&
						!fieldPresentacion.getText().toString().equals("") && !fieldCantPresent.getText().toString().equals("") && !fieldVolEmpaque.getText().toString().equals("") &&
						!fieldPesoEmpaque.getText().toString().equals("") && !fieldCodBarras.getText().toString().equals("")) {
					String nombre = fieldNombre.getText().toString();
					String marca = fieldMarca.getText().toString();
					long idTipoproducto = Long.valueOf(fieldIdTipoProd.getText().toString());
					String presentacion = fieldPresentacion.getText().toString();
					double cantPres = Double.valueOf(fieldCantPresent.getText().toString());
					String uniMed = comboUniMed.getSelectedItem().toString();
					double volEmpaque = Double.valueOf(fieldVolEmpaque.getText().toString());
					double pesoEmpaque = Double.valueOf(fieldPesoEmpaque.getText().toString());
					String codBarras = fieldCodBarras.getText().toString();
					//FALTA VALIDAR QUE LOS CAMPOS NO ESTEN VACIOS
					VOProducto producto = superandes.registrarProducto(nombre,marca,idTipoproducto,presentacion,cantPres,uniMed,volEmpaque,pesoEmpaque,codBarras);
					if(producto != null) {
						JOptionPane.showMessageDialog(this, "Se registro el producto con exito!", "Registro de producto exitoso", JOptionPane.INFORMATION_MESSAGE);
						String resultado = "En registrarProducto\n\n";
						resultado += "Producto registrado exitosamente: " + producto;
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
					}
				}
				
				
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error registrando pedido", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	public void registrarCliente() {
		try {
			JTextField fieldIdentificacion = new JTextField();
			JComboBox comboTipo = new JComboBox<String>();
			comboTipo.addItem("NATURAL");
			comboTipo.addItem("EMPRESA");
			JTextField fieldNombre = new JTextField();
			JTextField fieldCorreo = new JTextField();
			JTextField fieldDireccion  = new JTextField();

			
			Object message[] = {
					"Ingrese la identificacion del cliente: ", fieldIdentificacion,
					"Escoga el tipo de cliente: ", comboTipo,
					"Ingrese el nombre del cliente: ", fieldNombre,
					"Ingrese un correo electronico: ", fieldCorreo,
					"Ingrese la direccion del cliente (Campo obligatorio para empresas):  ", fieldDireccion
					
			};
			int option = JOptionPane.showConfirmDialog (this, message, "Registrar cliente", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION) {
				if(!fieldIdentificacion.getText().toString().equals("") && !fieldNombre.getText().toString().equals("") && !fieldCorreo.getText().toString().equals("")) {
					long identificacion = Long.valueOf(fieldIdentificacion.getText().toString());
					String tipo = comboTipo.getSelectedItem().toString();
					String nombre = fieldNombre.getText().toString();
					String correo = fieldCorreo.getText().toString(); //Validar bien que si sea un correo
					String direccion;
					if(!fieldDireccion.getText().toString().equals("")) {
						direccion = fieldDireccion.getText().toString();
					}else {
						direccion = null;
					}
					
					VOCliente cliente = superandes.registrarCliente(identificacion,tipo,nombre,correo,direccion);
					if(cliente != null) {
						JOptionPane.showMessageDialog(this, "Se registro el cliente con exito!", "Registro de cliente exitoso", JOptionPane.INFORMATION_MESSAGE);
						String resultado = "En registrarCliente \n\n";
						resultado += "Cliente registrado exitosamente: " + cliente;
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
					}
				}else {
					JOptionPane.showMessageDialog(this, "Se deben llenar todos los campos. (Direccion es obligatorio para empresas)", "Error registrando cliente", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error registrando cliente", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void registrarSucursal() {
		
		try {
			JTextField fieldCiudad = new JTextField();
			JTextField fieldDireccion  = new JTextField();
			JTextField fieldNombre = new JTextField();
			

			
			Object message[] = {
					"Ingrese la ciudad de la sucursal: ", fieldCiudad,
					"Ingrese la direccion de la sucursal: ", fieldDireccion,
					"Ingrese el nombre de la sucursal: ", fieldNombre
			};
			int option = JOptionPane.showConfirmDialog (this, message, "Registrar sucursal", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION) {
				if(!fieldCiudad.getText().toString().equals("") && !fieldNombre.getText().toString().equals("") && !fieldDireccion.getText().toString().equals("")) {
					
					
					String ciudad = fieldCiudad.getText().toString();
					String direccion = fieldDireccion.getText().toString();
					String nombre = fieldNombre.getText().toString();
					
					VOSucursal sucursal = superandes.registrarSucursal(ciudad,direccion,nombre);
					if(sucursal != null) {
						JOptionPane.showMessageDialog(this, "Se registro la sucursal con exito!", "Registro de sucursal exitoso", JOptionPane.INFORMATION_MESSAGE);
						String resultado = "En registrarSucursal \n\n";
						resultado += "Sucursal registrado exitosamente: " + sucursal;
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
					}
				}else {
					JOptionPane.showMessageDialog(this, "Se deben llenar todos los campos.", "Error registrando Sucursal", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error registrando sucursal", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void registrarBodegaASucursal() {
		
		try {
			JTextField fieldIdSucursal = new JTextField();
			JTextField fieldIdTipoProducto  = new JTextField();
			JTextField fieldVolumen = new JTextField();
			JTextField fieldPeso = new JTextField();

			
			Object message[] = {
					"Ingrese el ID de la sucursal a la que se el registrara la bodega: ", fieldIdSucursal,
					"Ingrese el ID del tipo de producto que almacenara la bodega: ", fieldIdTipoProducto,
					"Ingrese el volumen de capacidad de almacenamiento de la bodega: ", fieldVolumen,
					"Ingrese el peso de capacidad de almacenamiento de la bodega: ",fieldPeso
			};
			int option = JOptionPane.showConfirmDialog (this, message, "Registrar bodega a sucursal", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION) {
				if(!fieldIdSucursal.getText().toString().equals("") && !fieldIdTipoProducto.getText().toString().equals("") && !fieldVolumen.getText().toString().equals("") &&
						!fieldPeso.getText().toString().equals("")) {
					
					
					long idSucursal = Long.valueOf(fieldIdSucursal.getText().toString());
					long idTipoProducto = Long.valueOf(fieldIdTipoProducto.getText().toString());
					double volumen = Long.valueOf(fieldVolumen.getText().toString());
					double peso = Long.valueOf(fieldPeso.getText().toString());
					
					VOBodega bodega = superandes.registrarBodega(idSucursal,idTipoProducto,volumen,peso);
					if(bodega != null) {
						JOptionPane.showMessageDialog(this, "Se registro la bodega de la sucursal con exito!", "Registro de la de bodega a sucursal exitoso", JOptionPane.INFORMATION_MESSAGE);
						String resultado = "En registrarBodegaASucursal \n\n";
						resultado += "Bodega registrada a Sucursal exitosamente: " + bodega;
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
					}
				}else {
					JOptionPane.showMessageDialog(this, "Se deben llenar todos los campos.", "Error registrando bodega", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error registrando bodega a sucursal", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void registrarEstanteASucursal() {
		try {
			JTextField fieldIdSucursal = new JTextField();
			JTextField fieldIdTipoProducto  = new JTextField();
			JTextField fieldVolumen = new JTextField();
			JTextField fieldPeso = new JTextField();
			JTextField fieldNivAbastecimiento = new JTextField();
			
			Object message[] = {
					"Ingrese el ID de la sucursal a la que se el registrara el estante: ", fieldIdSucursal,
					"Ingrese el ID del tipo de producto que almacenara el estante: ", fieldIdTipoProducto,
					"Ingrese el volumen de capacidad de almacenamiento del estante: ", fieldVolumen,
					"Ingrese el peso de capacidad de almacenamiento del estante: ",fieldPeso,
					"Ingrese el nivel de abastecimiento que tendra el estante:", fieldNivAbastecimiento
			};
			int option = JOptionPane.showConfirmDialog (this, message, "Registrar estante a sucursal", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION) {
				if(!fieldIdSucursal.getText().toString().equals("") && !fieldIdTipoProducto.getText().toString().equals("") && !fieldVolumen.getText().toString().equals("") &&
						!fieldPeso.getText().toString().equals("") && !fieldNivAbastecimiento.getText().toString().equals("")) {
					
					
					long idSucursal = Long.valueOf(fieldIdSucursal.getText().toString());
					long idTipoProducto = Long.valueOf(fieldIdTipoProducto.getText().toString());
					double volumen = Long.valueOf(fieldVolumen.getText().toString());
					double peso = Long.valueOf(fieldPeso.getText().toString());
					int niveAbastecimiento = Integer.valueOf(fieldNivAbastecimiento.getText().toString());
					
					VOEstante estante= superandes.registrarEstante(idSucursal,idTipoProducto,volumen,peso,niveAbastecimiento);
					if(estante != null) {
						JOptionPane.showMessageDialog(this, "Se registro el estante de la sucursal con exito!", "Registro del estante a sucursal exitoso", JOptionPane.INFORMATION_MESSAGE);
						String resultado = "En registrarEstanteASucursal \n\n";
						resultado += "Estante registrado a Sucursal exitosamente: " + estante;
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
					}
				}else {
					JOptionPane.showMessageDialog(this, "Se deben llenar todos los campos.", "Error registrando estante", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error registrando estante a sucursal", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Registra un pedido de una sucursal de superandes a un proveedor
	 */
	public void registrarPedido()
	{
		try {
			JTextField fieldIdProveedor = new JTextField();
			JTextField fieldIdSucursal = new JTextField();
			JTextField fieldIdProducto = new JTextField();
			JTextField fieldPrecio = new JTextField();
			JTextField fieldFecha = new JTextField();
			Object message[] = {
					"Digite el ID del proveedor al cual se le solicitará un pedido: ", fieldIdProveedor,
					"Digite el ID de la sucursal que esta solictitando un pedido: ", fieldIdSucursal,
					"Digite el ID del producto: ", fieldIdProducto,
					"Digite el precio al que se comprará la unidad del producto: ", fieldPrecio,
					"Digite la fecha esperada de entrega (DD/MM/AAAA): ", fieldFecha
			};
			int option = JOptionPane.showConfirmDialog (this, message, "Registrar pedido", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION) {
				if(!fieldIdProveedor.getText().equals("") && !fieldIdSucursal.getText().equals("") && !fieldIdProducto.getText().toString().equals("") 
						&& !fieldPrecio.getText().toString().equals("") && !fieldFecha.getText().toString().equals("") ) {

					System.out.println("ENTRA AL IF");

					long idProveedor = Long.valueOf(fieldIdProveedor.getText().toString());
					long idSucursal = Long.valueOf(fieldIdSucursal.getText().toString());
					long idProducto = Long.valueOf(fieldIdProducto.getText().toString());
					double precio = Double.valueOf(fieldPrecio.getText().toString());

					String stFecha = fieldFecha.getText().toString();
					StringTokenizer tokenizer = new StringTokenizer(stFecha, "/");
					int day =Integer.valueOf(tokenizer.nextToken().toString());
					int month = Integer.valueOf(tokenizer.nextToken().toString());
					int year = Integer.valueOf(tokenizer.nextToken().toString());
					System.out.println("YEAR: "+year);
					System.out.println("MONTH" + month);
					System.out.println("DAY"+day);
					//---
					//DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
					LocalDateTime date = LocalDateTime.of(year, month, day, 0, 0);

					System.out.println("FECHA TIPO DATE: "+date.toString());
					//long time = date.getTime();
					Timestamp fecha = Timestamp.valueOf(date);
					System.out.println("FECHA TIPO TIMESTAMP: "+fecha.toString());
					//-----------------

					VOOrden orden = superandes.registrarPedido(idProveedor, idSucursal,idProducto,precio, fecha);
					if(orden != null) {
						JOptionPane.showMessageDialog(this, "Se registro el pedido con exito!", "Registro de pedido exitoso", JOptionPane.INFORMATION_MESSAGE);
						String resultado = "En registrarPedido\n\n";
						resultado += "Pedido registrado exitosamente: " + orden;
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
					}


				}else {
					JOptionPane.showMessageDialog(this, "Se deben llenar todos los campos", "Error registrando pedido", JOptionPane.ERROR_MESSAGE);
				}




			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error registrando pedido", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}


	}
	
	/**
	 * Registra la venta de un producto en superandes
	 */
	public void registrarVenta() {
		
		try {
			JTextField fieldIdSucursal = new JTextField();
			JTextField fieldIdProducto = new JTextField();
			JTextField fieldIdCliente = new JTextField();
			JTextField fieldNumUnidades = new JTextField();
			Object message[] = {
					"Digite el ID de la sucursal que esta solictitando un pedido: ", fieldIdSucursal,
					"Digite el ID del producto: ", fieldIdProducto,
					"Digite la identificacion del cliente: ", fieldIdCliente,
					"Digite el numero de unidades que desea comprar: ", fieldNumUnidades
			};
			int option = JOptionPane.showConfirmDialog (this, message, "Registrar venta", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION) {
				if(!fieldIdSucursal.getText().equals("") && !fieldIdProducto.getText().toString().equals("") 
						&& !fieldNumUnidades.getText().toString().equals("") ) {


					long idSucursal = Long.valueOf(fieldIdSucursal.getText().toString());
					long idProducto = Long.valueOf(fieldIdProducto.getText().toString());
					long idCliente  = Long.valueOf(fieldIdCliente.getText().toString());
					long numUnidades = Integer.valueOf(fieldNumUnidades.getText().toString());

					
					VOFactura factura = superandes.registrarVenta(idSucursal,idProducto,idCliente,numUnidades);
					if(factura != null) {
						JOptionPane.showMessageDialog(this, "Se registro la venta con exito!", "Registro de venta exitoso", JOptionPane.INFORMATION_MESSAGE);
						String resultado = "En registrarVenta\n\n";
						resultado += "Venta registrada exitosamente: " + factura;
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
					}


				}else {
					JOptionPane.showMessageDialog(this, "Se deben llenar todos los campos", "Error registrando venta", JOptionPane.ERROR_MESSAGE);
				}




			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error registrando venta", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	/**
	 * Registra la llegada de un pedido de una sucursal a un proveedor
	 */
	public void registrarLlegadaPedido() {

		try {

			JTextField fieldIdOrden = new JTextField();
			JTextField fieldCantidad =  new JTextField();
			JTextField fieldCalificacion = new JTextField();
			int idOrden;
			int cantidad;
			String calificacion;
			Object[] message = {

					"Digite el ID del pedido: ", fieldIdOrden,
					"Digite la cantidad de productos que llegó en el pedido:",fieldCantidad,
					"Digite la calificacion que le dara al pedido (Excelente, Bueno, Capetable, Malo): ", fieldCalificacion
			};
			int option = JOptionPane.showConfirmDialog (this, message, "Registrar pedido", JOptionPane.OK_CANCEL_OPTION);

			if(option == JOptionPane.OK_OPTION) {
				if(!fieldIdOrden.getText().toString().equals("") && !fieldCantidad.getText().toString().equals("") && !fieldCalificacion.getText().toString().equals("")) {
					boolean cumpleIdOrden = true;
					boolean cumpleCantidad = true;
					if (fieldIdOrden.getText().toString().matches("[0-9]+")) {
						idOrden = Integer.valueOf(fieldIdOrden.getText().toString());

					}else {
						JOptionPane.showMessageDialog(this, "Registre un numero en idOrden", "Error en digitacion de ID de la orden", JOptionPane.ERROR_MESSAGE);
						cumpleIdOrden = false;
					}
					if (fieldCantidad.getText().toString().matches("[0-9]+")) {
						cantidad = Integer.valueOf(fieldIdOrden.getText().toString());
					}else {
						JOptionPane.showMessageDialog(this, "Registre un numero en la cantidad de productos", "Error en digitacion de cantidad de la orden", JOptionPane.ERROR_MESSAGE);
						cumpleCantidad = false;
					}
					calificacion = fieldCalificacion.getText().toString().toUpperCase();
					if((calificacion.equals("EXCELENTE") || calificacion.equals("BUENO") || calificacion.equals("ACEPTABLE") || calificacion.equals("MALO")) && cumpleIdOrden == true && cumpleCantidad == true) {
						//superandes.registrarLlegadaPedido(idOrden, cantidad, calificacion);
					}else {
						JOptionPane.showMessageDialog(this, "Registre un valor de califiacion valido", "Error en calificacion", JOptionPane.ERROR_MESSAGE);

					}

				}else {
					JOptionPane.showMessageDialog(this, "Se deben llenar todos los campos", "Error registrando pedido", JOptionPane.ERROR_MESSAGE);

				}
			}else {

			}



		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Superandes
	 */
	public void mostrarLogSuperandes ()
	{
		mostrarArchivo ("superandes.log");
	}

	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}

	/**
	 * Limpia el contenido del log de superandes
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogSuperandes ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("superandes.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de superandes ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
			//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
	 * @param e - La excepción recibida
	 * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
	/**
	 * Genera una cadena de caracteres con la lista de las Sucursales recibida: una línea por cada sucursal
	 * @param lista - La lista con las sucursales
	 * @return La cadena con una linea para cada sucursal recibida
	 */
	private String listarSucursales(List<VOSucursal> lista) 
	{
		String resp = "Las sucursales existentes son:\n";
		int i = 1;
		for (VOSucursal s : lista)
		{
			resp += i++ + ". " + s.toString() + "\n";
		}
		return resp;
	}


	/**
	 * Genera una cadena de caracteres con la lista de los Proveedores recibida: una línea por cada proveedor
	 * @param lista - La lista con los proveedores
	 * @return La cadena con una linea para cada proveedor recibido
	 */
	private String listarProveedores(List<VOProveedor> lista) {
		// TODO Auto-generated method stub
		String resp = "Los proveedores existentes son:\n";
		int i = 1;
		for (VOProveedor s : lista)
		{
			resp += i++ + ". " + s.toString() + "\n";
		}
		return resp;
	}
	/**
	 * Genera una cadena de caracteres con la lista de las Categorias recibida: una línea por cada categoria
	 * @param lista - La lista con las categorias
	 * @return La cadena con una linea para cada categoria recibido
	 */
	private String listarCategorias(List<VOCategoria> lista) 
	{
		// TODO Auto-generated method stub
		String resp = "Las categorias existentes son:\n";
		int i = 1;
		for (VOCategoria c : lista)
		{
			resp += i++ + ". " + c.toString() + "\n";
		}
		return resp;	
	}
	/**
	 * Genera una cadena de caracteres con la lista de los tipos de productos recibida: una línea por cada tipo de producto
	 * @param lista - La lista con los tipos de producto
	 * @return La cadena con una linea para cada tipo de producto recibido
	 */
	private String listarTipoProductos(List<VOTipoProducto> lista) {
		// TODO Auto-generated method stub
		String resp = "Las tipos de producto existentes son:\n";
		int i = 1;
		for (VOTipoProducto tp : lista)
		{
			resp += i++ + ". " + tp.toString() + "\n";
		}
		return resp;
	}
	/**
	 * Genera una cadena de caracteres con la lista los productos recibida: una línea por cada producto
	 * @param lista - La lista con los productos
	 * @return La cadena con una linea para cada producto recibido
	 */
	private String listarProductos(List<VOProducto> lista) {
		// TODO Auto-generated method stub
		String resp = "Las productos existentes son:\n";
		int i = 1;
		for (VOProducto p : lista)
		{
			resp += i++ + ". " + p.toString() + "\n";
		}
		return resp;
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
	/**
	 * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
	 * Invoca al método correspondiente según el evento recibido
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent sEvento) {
		// TODO Auto-generated method stub
		String evento = sEvento.getActionCommand( );		
		try 
		{
			Method req = InterfazSuperandesApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este método ejecuta la aplicación, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por línea de comandos
	 */
	public static void main( String[] args )
	{
		try
		{

			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			InterfazSuperandesApp interfaz = new InterfazSuperandesApp( );
			interfaz.setVisible( true );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
}
