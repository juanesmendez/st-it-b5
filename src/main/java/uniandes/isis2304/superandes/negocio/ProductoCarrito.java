package uniandes.isis2304.superandes.negocio;

/**
 * Clase para modelar la asociación muchos a muchos de productos en un carrito de compras del negocio de SuperAndes.
 *
 * @author Juan Sebastián Bravo
 */
public class ProductoCarrito implements VOProductoCarrito {

    /* ****************************************************************
     * 			Atributos
     *****************************************************************/

    /**
     * El identificador ÚNICO del producto dentro del carrito de compras.
     */
    private long idProducto;

    /**
     * El identificador ÚNICO del carrito de compras.
     */
    private long idCarrito;

    /**
     * La cantidad de producto del producto.
     */
    private int cantidadProducto;

    /* ****************************************************************
     * 			Métodos
     *****************************************************************/

    /**
     * Constructor sin parámetros por defecto
     */
    public ProductoCarrito()
    {
        this.setIdProducto(0);
        this.setIdCarrito(0);
    }
    /**
     * Constructor con valores.
     * @param idProducto - El identificador del producto dentro del carrito de compras.
     * @param idCarrito - El identificador del carrito de compras.
     */

    public ProductoCarrito(long idProducto, long idCarrito)
    {
        this.setIdProducto(idProducto);
        this.setIdCarrito(idCarrito);
    }

    /**
     * Retorna el identificador del producto del carrito de compras.
     *
     * @return El identificador del producto del carrito de compras.
     */
    public long getIdProducto() {
        return idProducto;
    }

    /**
     * Asigna el identificador del producto del carrito de compras.
     *
     * @param idProducto - El identificador del producto del carrito de compras.
     */
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Retorna el identificador del carrito de compras.
     *
     * @return El identificador del carrito de compras.
     */
    public long getIdCarrito() {
        return idCarrito;
    }

    /**
     * Asigna el identificador del carrito de compras.
     *
     * @param idCarrito - El identificador del carrito de compras.
     */
    public void setIdCarrito(long idCarrito) {
        this.idCarrito = idCarrito;
    }

    /**
     * Retorna la cantidad de producto del producto en el carrito.
     *
     * @return La cantidad de producto del producto en el carrito.
     */
    public int getCantidadProducto() {
        return cantidadProducto;
    }

    /**
     * Asigna la cantidad de producto del producto en el carrito.
     *
     * @param cantidadProducto - La cantidad de producto del producto en el carrito.
     */
    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }
}
