package uniandes.isis2304.superandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uniandes.isis2304.superandes.negocio.Item;

public class PanelAgregarProducto extends JFrame{
	
	private InterfazSuperandesApp padre;
	//private JPanel mainPanel;
	private JTable tabla;
	ListSelectionModel listSelectionModel;
	private String[] columnas;
	private JPanel panelInferior;
	private JPanel panelInferiorDerecho;
	private JButton botonAgregarProducto;
	private JLabel labelCantidad;
	private JTextField fieldCantidad;
	private List<Object[]> productos;
	
	private long idCliente;
	private long idCarrito;
	private long idSucursal;
	
	public PanelAgregarProducto(InterfazSuperandesApp padre) {
		this.padre = padre;
		this.productos = new ArrayList<>();
	}
	
	public PanelAgregarProducto(InterfazSuperandesApp padre,List<Object[]> productos, long idCliente, long idCarrito,long idSucursal) {
		this.padre = padre;
		this.productos = new ArrayList<>(productos); //CHEQUEAR
		this.idCliente = idCliente;
		this.idCarrito = idCarrito;
		this.idSucursal = idSucursal;
		setLayout(new BorderLayout());
		setSize(400,400);
		setLocation(400,300);
		setResizable(true);
		setBackground(Color.WHITE);
		setTitle("Adicionar productos");
		
		columnas = new String[]{"ID Producto", "Nombre","Precio","Unidades Disponibles","Estante"};
		tabla = new JTable(transformarMatriz(productos), columnas);
		tabla.getTableHeader().setBackground(new Color(73, 191, 214));
		//tabla.getTableHeader().setBackground(new Color(120,91,166));
		tabla.getTableHeader().setFont(new Font(tabla.getFont().getName(), Font.BOLD, tabla.getFont().getSize()));
		listSelectionModel = tabla.getSelectionModel();
		
		botonAgregarProducto = new JButton("Agregar");
		botonAgregarProducto.setBackground(Color.green);
		
		labelCantidad = new JLabel("Cantidad:");
		fieldCantidad = new JTextField();
		
		panelInferiorDerecho = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		panelInferiorDerecho.add(fieldCantidad,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		panelInferiorDerecho.add(botonAgregarProducto, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		panelInferiorDerecho.add(labelCantidad,c);
		
		panelInferior = new JPanel(new BorderLayout());
		panelInferior.add(panelInferiorDerecho, BorderLayout.EAST);
		
		add(new JScrollPane(tabla),BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);
		
		listeners();
	}
	
	private void listeners() {
		// TODO Auto-generated method stub
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int fila = tabla.getSelectedRow();
				fieldCantidad.setText(tabla.getValueAt(fila, 3).toString());
			}
		});
		
		botonAgregarProducto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int fila = tabla.getSelectedRow();
				if(fila != -1 && !fieldCantidad.getText().equals("")) {

					int cantidadCarrito = Integer.valueOf(fieldCantidad.getText());
					int idEstante = Integer.valueOf(tabla.getValueAt(fila, 4).toString());
					int idProducto = Integer.valueOf(tabla.getValueAt(fila, 0).toString());
					//System.out.println("IDESTANTE: "+tabla.getValueAt(fila, 4).toString());
					
					padre.adicionarProductoACarrito(idCliente,idCarrito,idSucursal, cantidadCarrito,idEstante,idProducto);
					//tabla.getValueAt(fila, column)
				}else {
					
					JOptionPane.showMessageDialog((((JPanel)(JPanel)((JButton)e.getSource()).getParent()).getParent()).getParent(), "Se debe seleccionar una fila de la tabla para continuar", "Error agregando producto", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});
	}

	public Object[][] transformarMatriz(List<Object[]> productos) {
		Object[][] matriz = new Object[productos.size()][5];
		int cont=0;
		for(Object[] tupla:productos) {
			matriz[cont][0] = tupla[0];
			matriz[cont][1] = tupla[1];
			matriz[cont][2] = tupla[2];
			matriz[cont][3] = tupla[3];
			matriz[cont][4] = tupla[4];
			cont++;
		}
		return matriz;
	}
	
	public List<Object[]> getListaProductos(){
		return this.productos;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
}
