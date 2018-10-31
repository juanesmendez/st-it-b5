package uniandes.isis2304.superandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
	
	public PanelAgregarProducto(InterfazSuperandesApp padre,List<Object[]> productos) {
		this.padre = padre;
		this.productos = new ArrayList<>(productos); //CHEQUEAR
		setLayout(new BorderLayout());
		setSize(400,400);
		setLocation(400,300);
		setResizable(true);
		setBackground(Color.WHITE);
		setTitle("Adicionar productos");
		
		columnas = new String[]{"ID Producto", "Nombre","Precio","Unidades Disponibles","Estante"};
		tabla = new JTable(transformarMatriz(productos), columnas);
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
				// TODO Auto-generated method stub
				int fila = tabla.getSelectedRow();
				if(fila != -1 && !fieldCantidad.getText().equals("")) {
					int cantidadTotal = Integer.valueOf(tabla.getValueAt(fila, 3).toString());
					int cantidad = Integer.valueOf(fieldCantidad.getText());
					System.out.println("IDESTANTE: "+tabla.getValueAt(fila, 4).toString());
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
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
	
}
