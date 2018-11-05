package java.uniandes.isis2304.superandes.test;


import static org.junit.Assert.*;

import org.junit.Test;

import uniandes.isis2304.superandes.interfazApp.InterfazSuperandesApp;

import uniandes.isis2304.superandes.persistencia.*;

public class TransaccionalidadTest {

	@Test(expected = Exception.class)
	public void testAgarrarCarrito() {
		int idSucursal;
		int idCliente;
		int idCarrito;
		PersistenciaSuperandes ps = new PersistenciaSuperandes();
		
		try {
			ps.agarrarCarrito(1, 1018500219, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
