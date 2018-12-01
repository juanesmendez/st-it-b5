SELECT *
FROM CLIENTE
INNER JOIN FACTURA ON CLIENTE.ID = FACTURA.IDCLIENTE
INNER JOIN FACTURAPRODUCTO ON FACTURA.ID = FACTURAPRODUCTO.IDFACTURA
WHERE FACTURAPRODUCTO.ID = ? AND FACTURA.FECHA BETWEEN '30/11/2018' AND '12/12/2018';

-- REQUERIMIENTO 10
SELECT *
FROM CLIENTE
INNER JOIN FACTURA ON CLIENTE.ID = FACTURA.IDCLIENTE
INNER JOIN FACTURAPRODUCTO ON FACTURA.ID = FACTURAPRODUCTO.IDFACTURA
WHERE FACTURAPRODUCTO.IDPRODUCTO = 23 AND FACTURA.FECHA BETWEEN '01/01/2018' AND '12/12/2018';

-- REQUERIMIENTO 10 MOSTRANDO UNICAMENTE DATOS NECESARIOS
SELECT CLIENTE.ID, CLIENTE.NOMBRE, FACTURA.ID, FACTURA.IDSUCURSAL, FACTURA.FECHA, FACTURA.TOTAL, FACTURAPRODUCTO.IDPRODUCTO, FACTURAPRODUCTO.UNIVENDIDAS
FROM CLIENTE
INNER JOIN FACTURA ON CLIENTE.ID = FACTURA.IDCLIENTE
INNER JOIN FACTURAPRODUCTO ON FACTURA.ID = FACTURAPRODUCTO.IDFACTURA
WHERE FACTURAPRODUCTO.IDPRODUCTO = 23 AND FACTURA.FECHA BETWEEN '01/01/2018' AND '12/12/2018';

-- REQUERIMIENTO 10 ORDENAMIENTO POR FECHA
SELECT CLIENTE.ID, CLIENTE.NOMBRE, FACTURA.ID, FACTURA.IDSUCURSAL, FACTURA.FECHA, FACTURA.TOTAL, FACTURAPRODUCTO.IDPRODUCTO, FACTURAPRODUCTO.UNIVENDIDAS
FROM CLIENTE
INNER JOIN FACTURA ON CLIENTE.ID = FACTURA.IDCLIENTE
INNER JOIN FACTURAPRODUCTO ON FACTURA.ID = FACTURAPRODUCTO.IDFACTURA
WHERE FACTURAPRODUCTO.IDPRODUCTO = 23 AND FACTURA.FECHA BETWEEN '01/01/2018' AND '12/12/2018'
ORDER BY FACTURA.FECHA;


-- REQUERIMIENTO 10 ORDENAMIENTO POR DATOS DEL CLIENTE
SELECT CLIENTE.ID, CLIENTE.NOMBRE, FACTURA.ID, FACTURA.IDSUCURSAL, FACTURA.FECHA, FACTURA.TOTAL, FACTURAPRODUCTO.IDPRODUCTO, FACTURAPRODUCTO.UNIVENDIDAS
FROM CLIENTE
INNER JOIN FACTURA ON CLIENTE.ID = FACTURA.IDCLIENTE
INNER JOIN FACTURAPRODUCTO ON FACTURA.ID = FACTURAPRODUCTO.IDFACTURA
WHERE FACTURAPRODUCTO.IDPRODUCTO = 23 AND FACTURA.FECHA BETWEEN '01/01/2018' AND '12/12/2018'
ORDER BY CLIENTE.ID, CLIENTE.NOMBRE;

-- REQUERIMIENTO 10 ORDENAMIENTO POR UNIDADES COMPRADAS
SELECT CLIENTE.ID, CLIENTE.NOMBRE, FACTURA.ID, FACTURA.IDSUCURSAL, FACTURA.FECHA, FACTURA.TOTAL, FACTURAPRODUCTO.IDPRODUCTO, FACTURAPRODUCTO.UNIVENDIDAS
FROM CLIENTE
INNER JOIN FACTURA ON CLIENTE.ID = FACTURA.IDCLIENTE
INNER JOIN FACTURAPRODUCTO ON FACTURA.ID = FACTURAPRODUCTO.IDFACTURA
WHERE FACTURAPRODUCTO.IDPRODUCTO = 23 AND FACTURA.FECHA BETWEEN '01/01/2018' AND '12/12/2018'
ORDER BY FACTURAPRODUCTO.UNIVENDIDAS;

-- REQUERIMIENTO 10 AGRUPAMIENTO POR NUMERO TOTAL DE UNIDADES COMPRADAS
SELECT CLIENTE.ID, CLIENTE.NOMBRE, SUM(FACTURAPRODUCTO.UNIVENDIDAS)  AS SUMA_TOT
FROM CLIENTE
INNER JOIN FACTURA ON CLIENTE.ID = FACTURA.IDCLIENTE
INNER JOIN FACTURAPRODUCTO ON FACTURA.ID = FACTURAPRODUCTO.IDFACTURA
WHERE FACTURAPRODUCTO.IDPRODUCTO = 23 AND FACTURA.FECHA BETWEEN '01/01/2018' AND '12/12/2018'
GROUP BY CLIENTE.ID, CLIENTE.NOMBRE;