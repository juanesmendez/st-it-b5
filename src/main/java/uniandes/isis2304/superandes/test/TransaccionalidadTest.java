package uniandes.isis2304.superandes.test;


import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.google.gson.JsonObject;

import uniandes.isis2304.superandes.interfazApp.InterfazSuperandesApp;
import uniandes.isis2304.superandes.negocio.CarritoCompras;
import uniandes.isis2304.superandes.negocio.Sucursal;
import uniandes.isis2304.superandes.negocio.Superandes;
import uniandes.isis2304.superandes.persistencia.*;

public class TransaccionalidadTest {
	
	
	@Test(expected = Exception.class)
	public void testAgarrarCarritoSucursalInexistente() throws Exception {
		int idSucursal = 1;
		int idCliente = 1018500219;
		int idCarrito = 281;
		PersistenciaSuperandes ps = new PersistenciaSuperandes();
		ps.agarrarCarrito(idSucursal, idCliente, idCarrito);
	}
	
	@Test(expected = Exception.class)
	public void testAgarrarCarritoOcupado() throws Exception {
		int idSucursal = 38;
		int idCliente = 1018500219;
		int idCarrito = 281;
		PersistenciaSuperandes ps = new PersistenciaSuperandes();
		ps.agarrarCarrito(idSucursal, idCliente, idCarrito);
		idCliente = 1018510315;
		ps.agarrarCarrito(idSucursal, idCliente, idCarrito);
	}
	
	@Test
	public void testAgarrarCarritoSatisfactorio() throws Exception {
		int idSucursal = 38;
		int idCliente = 1018510315;
		int idCarrito = 181;
		InterfazSuperandesApp interfaz = new InterfazSuperandesApp();
		Superandes s = interfaz.getSuperandes();
		PersistenciaSuperandes ps = s.getPersistenciaSuperandes();
		
		SQLSucursal sqlSucursal = ps.getSqlSucursal();
		SQLCliente sqlCliente = ps.getSqlCliente();
		SQLCarritoCompras sqlCarrito = ps.getSqlCarritoCompras();
		
		Sucursal sucursal = sqlSucursal.darSucursal(ps.getPersistenceManager().getPersistenceManager(), idSucursal);
		assertEquals(38, sucursal.getId());
		assertEquals("Bogotá", sucursal.getCiudad());
		assertEquals("Superandes Cedritos", sucursal.getNombre());
		assertEquals("Cl. 73 #9-72", sucursal.getDireccion());
		
		Object cli = sqlCliente.darCliente(ps.getPersistenceManager().getPersistenceManager(), idCliente);
		Object[] arrayCli = (Object[]) cli;
		assertEquals(idCliente, ((BigDecimal) arrayCli[0]).intValue());
		
		Object carrito =sqlCarrito.darObjetoCarritoComprasPorId(ps.getPersistenceManager().getPersistenceManager(), idCarrito);
		Object[] arrayCarrito = (Object[]) carrito;
		assertEquals(idCarrito, ((BigDecimal)arrayCarrito[0]).intValue());
		
		long tuplasActualizadas = sqlCarrito.actualizarCarritoComprasEstadoYIdCliente(ps.getPersistenceManager().getPersistenceManager(), idCarrito, "NO DISPONIBLE", idCliente);
		assertEquals(1, tuplasActualizadas);
		
		carrito =sqlCarrito.darObjetoCarritoComprasPorId(ps.getPersistenceManager().getPersistenceManager(), idCarrito);
		arrayCarrito = (Object[]) carrito;
		
		assertEquals(idCliente, ((BigDecimal) arrayCarrito[2]).intValue());
	}
	
	@Test
	public void testAgregarProductoCarrito() throws Exception {
		int idSucursal = 38;
		int idCliente = 1018510315;
		int idCarrito = 181;
		int idProducto = 23;
		InterfazSuperandesApp interfaz = new InterfazSuperandesApp();
		Superandes s = interfaz.getSuperandes();
		PersistenciaSuperandes ps = s.getPersistenciaSuperandes();
		
		SQLCarritoCompras sqlCarrito = ps.getSqlCarritoCompras();
		SQLVendeCarrito sqlVendeCarrito = ps.getSqlVendeCarrito();
		
		long tuplasActualizadas = sqlCarrito.actualizarCarritoComprasEstadoYIdCliente(ps.getPersistenceManager().getPersistenceManager(), idCarrito, "NO DISPONIBLE", idCliente);
		assertEquals(1, tuplasActualizadas);
		
		tuplasActualizadas = sqlVendeCarrito.agregarVendeCarrito(ps.getPersistenceManager().getPersistenceManager(), idCarrito, idProducto, 1);
		assertEquals(1, tuplasActualizadas);
		
	}
	
	@Test
	public void testDevolverProductoCarrito() throws Exception {
		int idSucursal = 38;
		int idCliente = 1018510315;
		int idCarrito = 181;
		int idProducto = 23;
		InterfazSuperandesApp interfaz = new InterfazSuperandesApp();
		Superandes s = interfaz.getSuperandes();
		PersistenciaSuperandes ps = s.getPersistenciaSuperandes();
		
		SQLCarritoCompras sqlCarrito = ps.getSqlCarritoCompras();
		SQLVendeCarrito sqlVendeCarrito = ps.getSqlVendeCarrito();
		
		//DEPENDE DE LA PRUEBA ANTERIOR
		
		sqlVendeCarrito.eliminarVendeCarrito(ps.getPersistenceManager().getPersistenceManager(), idCarrito, idProducto);
	}
	
	@Test
	public void testAbandonarCarrito() throws Exception {
		int idCarrito = 181;
		InterfazSuperandesApp interfaz = new InterfazSuperandesApp();
		Superandes s = interfaz.getSuperandes();
		PersistenciaSuperandes ps = s.getPersistenciaSuperandes();
		
		SQLCarritoCompras sqlCarrito = ps.getSqlCarritoCompras();
		SQLVendeCarrito sqlVendeCarrito = ps.getSqlVendeCarrito();
		
		//DEPENDE DE LA PRUEBA ANTERIOR
		
		long tuplasActualizadas = sqlCarrito.actualizarCarritoComprasAbandonado(ps.getPersistenceManager().getPersistenceManager(), idCarrito, "DISPONIBLE");
		assertEquals(1, tuplasActualizadas);
		
		tuplasActualizadas = sqlCarrito.actualizarCarritoComprasAbandonado(ps.getPersistenceManager().getPersistenceManager(), 281, "DISPONIBLE");
		assertEquals(1, tuplasActualizadas);
	}
	
	

}
