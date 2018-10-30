package uniandes.isis2304.superandes.negocio;

/**
 * Clase para modelar el concepto CARRITOCOMPRAS del negocio de SuperAndes.
 *
 * @author Juan Sebastián Bravo
 */
public class CarritoCompras implements VOCarritoCompras {


    /* ****************************************************************
     * 			Atributos
     *****************************************************************/

    /**
     * El identificador ÚNICO del carrito de compras.
     */
    private long id;

    /**
     * El estado del carrito de compras.
     */
    private String estado;

    /**
     * El identificador ÚNICO del cliente dueño del carrito de compras.
     */
    private long idCliente;

    /* ****************************************************************
     * 			Métodos
     *****************************************************************/

    /**
     * Constructor sin parámetros por defecto
     */
    public CarritoCompras()
    {
        this.setId(0);
        this.setEstado(null);
        this.setIdCliente(0);
    }
    /**
     * Constructor con valores.
     * @param id - El identificador del carrito de compras.
     * @param estado - El estado del carrito de compras.
     * @param idCliente - El identificador del cliente dueño del carrito de compras.
     */

    public CarritoCompras(long id, String estado, long idCliente)
    {
        this.setId(id);
        this.setEstado(estado);
        this.setIdCliente(idCliente);
    }
    /**
     * Retorna el identificador del carrito de compras.
     *
     * @return El identificador del carrito de compras.
     */
    public long getId() {
        return id;
    }

    /**
     * Asigna el identificador del carrito de compras.
     *
     * @param id - El identificador del carrito de compras.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna el estado del carrito de compras.
     *
     * @return El estado del carrito de compras.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Asigna el estado del carrito de compras.
     *
     * @param estado - El estado del carrito de compras.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Retorna el identificador del cliente dueño del de compras.
     *
     * @return El identificador del cliente dueño del de compras.
     */
    public long getIdCliente() {
        return idCliente;
    }

    /**
     * Asigna el identificador del cliente dueño del carrito de compras.
     *
     * @param idCliente - El identificador del cliente dueño del carrito de compras.
     */
    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }
}
