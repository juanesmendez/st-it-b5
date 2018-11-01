package uniandes.isis2304.superandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


import uniandes.isis2304.superandes.negocio.Item;
import uniandes.isis2304.superandes.negocio.VOItem;

public class PanelCarrito extends JPanel {

	public final static String PATH_CANECA = "./src/main/resources/config/caneca.png";
	public final static String PATH_CARRITO = "./src/main/resources/config/shopping-cart.png";


	private InterfazSuperandesApp parent;

	private JLabel labelIdCliente;
	private JLabel labelIdCarrito;
	private JLabel labelIdSucursal;
	private JLabel labelImagenCarrito;
	private JLabel labelTotalCarrito;
	private String[] columnas;
	private JButton botonAgregar;
	private JButton botonEliminar;
	private JButton botonAbandonar;
	private JButton botonComprar;
	private JScrollPane scrollPane;

	private JTable productos;

	private JPanel panelSuperior;
	private JPanel panelInferiorBotones;

	private List<Item> items;
	private int idSucursal;
	private int idCliente;
	private int idCarrito;

	public PanelCarrito() {
		setBorder(new TitledBorder("Carrito de compras"));
		setLayout(new BorderLayout());

		JTextArea textArea = new JTextArea("No se ha agarrado ningún carrito de compras");
		textArea.setEditable(false);
		add (new JScrollPane(textArea), BorderLayout.CENTER);

	}

	public PanelCarrito(InterfazSuperandesApp parent, int idSucursal,int idCliente, int idCarrito) {

		this.parent = parent;
		this.columnas = new String[]{"ID Producto", "Nombre","Cantidad","Precio","Subtotal"};
		this.labelIdCliente = new JLabel("ID Cliente: " + idCliente);
		this.labelIdCarrito = new JLabel("ID Carrito: " + idCarrito);
		this.labelIdSucursal = new JLabel("ID Sucursal: " + idSucursal);
		this.labelIdCliente.setForeground(Color.BLUE);
		this.labelIdCarrito.setForeground(new Color(52, 189, 0));
		this.labelIdSucursal.setForeground(Color.RED);
		
		this.labelImagenCarrito = new JLabel(new ImageIcon(PATH_CARRITO));

		this.botonAgregar = new JButton("Adicionar producto");
		this.botonEliminar = new JButton("Devolver producto");
		this.botonAbandonar = new JButton("Abandonar carrito");
		this.botonComprar = new JButton("Pagar");


		this.botonAgregar.setBackground(new Color(52, 189, 0));
		this.botonEliminar.setBackground(new Color(197, 0, 0));
		this.botonAbandonar.setBackground(new Color(197, 0, 0));
		this.botonComprar.setBackground(Color.GREEN);
		
		listeners();

		//-----
		items = new ArrayList<>();
		//-----
		crearTablaProductos(items);


		setBorder(new TitledBorder("Carrito de compras"));
		setLayout(new BorderLayout());
		
		panelSuperior = new JPanel(new BorderLayout());
		JPanel panelLabels = new JPanel(new GridLayout(3,1));
		panelLabels.add(labelIdCliente);
		panelLabels.add(labelIdCarrito);
		panelLabels.add(labelIdSucursal);
		panelSuperior.add(panelLabels,BorderLayout.WEST);
		panelSuperior.add(labelImagenCarrito,BorderLayout.EAST);
		

		panelInferiorBotones = new JPanel(new GridLayout(2, 3,50,10));

		panelInferiorBotones.add(botonEliminar);
		panelInferiorBotones.add(botonAgregar);
		this.labelTotalCarrito = new JLabel("TOTAL: "+String.valueOf(calcularTotalCarrito(this.items)));
		panelInferiorBotones.add(labelTotalCarrito);
		panelInferiorBotones.add(botonAbandonar);
		panelInferiorBotones.add(botonComprar);

		add(panelInferiorBotones,BorderLayout.SOUTH);
		add(panelSuperior,BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		
		this.idSucursal = idSucursal;
		this.idCliente = idCliente;
		this.idCarrito = idCarrito;
	}

	
	private void listeners() {
		
		botonAbandonar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(parent, "Esta seguro que desea abandonar el carrito?", "Abandonar carrito", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if(option == JOptionPane.OK_OPTION) {
					parent.abandonarCarrito();
				}
			}
		});
		
		botonAgregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.agregarProductoCarritoPanel(idSucursal,idCliente,idCarrito);
			}
		});
		
		botonEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int fila = productos.getSelectedRow();
				if(fila != -1 ) {
					List<Object[]> listaProductosPanelAgregar = parent.getPanelAgregarProducto().getListaProductos();
					int idProducto = Integer.valueOf(productos.getValueAt(fila, 0).toString());
					int cantidadCarrito = Integer.valueOf(productos.getValueAt(fila, 2).toString());
					int idEstante = -1;
					int cantidadEnEstante = -1;
					for(Object[] obj:listaProductosPanelAgregar) {
						if(Integer.valueOf(obj[0].toString()) == idProducto) {
							idEstante = Integer.valueOf(obj[4].toString());
							cantidadEnEstante = Integer.valueOf(obj[3].toString());
						}
					}
					System.out.println("ID ESTANTE: "+idEstante);
					parent.devolverProductoCarrito(idProducto,cantidadCarrito,idEstante,cantidadEnEstante);
				}else {
					JOptionPane.showMessageDialog(parent, "Se debe seleccionar una fila de la tabla para continuar", "Error eliminando producto del carrito", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}
	
	public int getIdCarrito() {
		return this.idCarrito;
	}
	
	public void crearTablaProductos(List<Item> items) {
		this.productos = new JTable(transformarMatriz(items), columnas);
		this.productos.getTableHeader().setBackground(new Color(73, 191, 214));
		this.productos.getTableHeader().setFont(new Font(productos.getFont().getName(), Font.BOLD, productos.getFont().getSize()));
		this.scrollPane = new JScrollPane(productos);
		add(scrollPane, BorderLayout.CENTER);
	}

	public Object[][] transformarMatriz(List<Item> items) {
		Object[][] matriz = new Object[items.size()][5];
		
		
		if(items != null) {
			int cont=0;
			for(VOItem i:items) {
				matriz[cont][0] = i.getIdProducto();
				matriz[cont][1] = i.getNombre();
				matriz[cont][2] = i.getCantidad();
				matriz[cont][3] = i.getPrecio();
				matriz[cont][4] = i.getSubTotal();
		
				cont++;
			}
		}
		
		return matriz;
	}
	
	public void actualizarTablaProductos(List<Object[]> listaItems) {
		//TODO
		List<Item> items = new ArrayList<>();
		
		if(listaItems != null) {
			for(int i = 0;i<listaItems.size();i++) {
				
				long idProducto = ((BigDecimal) listaItems.get(i)[0]).longValue();
				String nombre = (String) listaItems.get(i)[1];
				int cantidad = ((BigDecimal) listaItems.get(i)[2]).intValue();
				double precio = ((BigDecimal) listaItems.get(i)[3]).doubleValue();
				double subTotal = ((BigDecimal) listaItems.get(i)[4]).doubleValue();
				
				Item item = new Item(idProducto, nombre, cantidad, precio, subTotal);
				System.out.println(item);
				items.add(item);
			}
			this.items = items;
			actualizarLabelTotal(items);
			remove(scrollPane);
			crearTablaProductos(items);
		}else {
			this.items = items;
			this.labelTotalCarrito.setText("TOTAL: $0");
			this.labelTotalCarrito.setFont(new Font("Courier New", Font.BOLD, 13));
			this.labelTotalCarrito.setForeground(new Color(210, 85, 51));;
		}
		
		
		
		
		
		/*
		productos = new JTable(matriz, this.columnas);
		productos.getTableHeader().setBackground(new Color(73, 191, 214));
		this.productos.getTableHeader().setFont(new Font(productos.getFont().getName(), Font.BOLD, productos.getFont().getSize()));
		scrollPane = new JScrollPane(productos);
		*/
	}


	private void actualizarLabelTotal(List<Item> items) {
		// TODO Auto-generated method stub
		this.labelTotalCarrito.setText("TOTAL: $"+String.valueOf(calcularTotalCarrito(items)) + " COP");
		this.labelTotalCarrito.setFont(new Font("Courier New", Font.BOLD, 13));
		this.labelTotalCarrito.setForeground(new Color(210, 85, 51));;
		//this.labelTotalCarrito.revalidate();
	}

	public int calcularTotalCarrito(List<Item> items) {
		double total = 0;
		for(VOItem i:items) {
			total += i.getSubTotal();
		}
		//System.out.println("TOTAL CARRITO: "+ (int) total);
		return (int) total;
	}
	
	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		super.repaint();
	}
}
