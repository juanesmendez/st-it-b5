package uniandes.isis2304.superandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
		items.add(new Item("1", "Papa", 3, 2000));
		//-----
		this.productos = new JTable(transformarMatriz(items), columnas);
		//this.productos = new JTable(new DefaultTableModel(null,columnas));

		this.scrollPane = new JScrollPane(productos);


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
		// TODO Auto-generated method stub
		
		botonAbandonar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int option = JOptionPane.showConfirmDialog(parent, "Esta seguro que desea abandonar el carrito?", "Abandonar carrito", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if(option == JOptionPane.OK_OPTION) {
					parent.abandonarCarrito();
				}
			}
		});
		
		botonAgregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.agregarProductoCarrito(idSucursal,idCliente,idCarrito);
			}
		});
		
	}

	public Object[][] transformarMatriz(List<Item> items) {
		Object[][] matriz = new Object[items.size()][6];
		int cont=0;
		for(Item i:items) {
			matriz[cont][0] = i.getIdProducto();
			matriz[cont][1] = i.getNombre();
			matriz[cont][2] = i.getCantidad();
			matriz[cont][3] = i.getPrecio();
			matriz[cont][4] = i.getSubTotal();
	
			cont++;
		}
		return matriz;
	}


	public double calcularTotalCarrito(List<Item> items) {
		double total = 0;
		for(Item i:items) {
			total += i.getSubTotal();
		}
		return total;
	}
	
	
}
