package uniandes.isis2304.superandes.negocio;

/**
 * Clase para modelar el concepto VENDECARRITO del negocio de SuperAndes.
 *
 * @author Juan Sebastián Bravo
 */
public class VendeCarrito implements VOVendeCarrito {

    /* ****************************************************************
     * 			Atributos
     *****************************************************************/

    /**
     * El identificador ÚNICO del carrito de compras.
     */
    private long idCarrito;

    /**
     * El identificador ÚNICO del producto.
     */
    private long idProducto;

    /**
     * La cantidad de producto del producto en el carrito de compras.
     */
    private int cantidadCarrito;




    /* ****************************************************************
     * 			Métodos
     *****************************************************************/

    /**
     * Constructor sin parámetros por defecto
     */
    public VendeCarrito()
    {
this.setIdCarrito(0);
        this.setIdProducto(0);
        this.setCantidadCarrito(0);
    }
    /**
     * Constructor con valores.
     * @param idCarrito - El identificador del carrito de compras.
     * @param idProducto - El identificador del producto en el carrito de compras.
     * @param cantidadCarrito - La cantidad de producto del producto en el carrito de compras.
     */

    public VendeCarrito(long idCarrito, long idProducto, int cantidadCarrito)
    {
        this.setIdCarrito(idCarrito);
        this.setIdProducto(idProducto);
        this.setCantidadCarrito(cantidadCarrito);
    }

    /**
     * Retorna el identificador del carro de compras.
     *
     * @return el identificador del carro de compras.
     */
    public long getIdCarrito() {
        return idCarrito;
    }

    /**
     * Asigna el identificador del carro de compras.
     *
     * @param idCarrito - El identificador del carro de compras.
     */
    public void setIdCarrito(long idCarrito) {
        this.idCarrito = idCarrito;
    }

    /**
     * Retorna el identificador del producto carro de compras.
     *
     * @return el identificador del producto carro de compras.
     */
    public long getIdProducto() {
        return idProducto;
    }

    /**
     * Asigna el identificador del producto carro de compras.
     *
     * @param idProducto - el identificador del producto carro de compras.
     */
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Retorna la cantidad de producto del producto en el carrito de compras.
     *
     * @return la cantidad de producto del producto en el carrito de compras.
     */
    public int getCantidadCarrito() {
        return cantidadCarrito;
    }

    /**
     * Asigna la cantidad de producto del producto en el carrito de compras.
     *
     * @param cantidadCarrito - la cantidad de producto del producto en el carrito de compras.
     */
    public void setCantidadCarrito(int cantidadCarrito) {
        this.cantidadCarrito = cantidadCarrito;
    }
}
