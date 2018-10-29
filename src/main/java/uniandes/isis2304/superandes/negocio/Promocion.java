package uniandes.isis2304.superandes.negocio;

import java.util.Date;

/**
 * Clase para modelar el concepto PROMOCION del negocio de SuperAndes.
 *
 * @author Juan Sebastián Bravo
 */
public class Promocion implements VOPromocion {

    /* ****************************************************************
     * 			Atributos
     *****************************************************************/

    /**
     * El identificador ÚNICO de la promoción.
     */
    private long id;

    /**
     * El identificador del producto de la promoción.
     */
    private long idProducto;

    /**
     * El identificador del proveedor de la promoción.
     */
    private long idProveedor;
    /**
     * Si la promoción está disponible.
     */
    private boolean disponible;

    /**
     * La cantidad de productos que abarca la promoción.
     */
    private int cantidadProductos;

    /**
     * La fecha de inicio de la promoción.
     */
    private Date fechaInicio;

    /**
     * La fecha de fin de la promoción.
     */
    private Date fechaFin;

    /* ****************************************************************
     * 			Métodos
     *****************************************************************/

    /**
     * Constructor sin parámetros por defecto
     */
    public Promocion()
    {
        this.setId(0);
        this.setIdProveedor(0);
        this.setIdProducto(0);
        this.setCantidadProductos(0);
        this.setDisponible(false);
        this.setFechaInicio(new Date());
        this.setFechaFin(new Date());
    }

    /**
     * Constructor con valores.
     * @param id - El identificador de la promoción.
     * @param idProveedor - El id del proveedor del producto del cual es la promoción.
     * @param idProducto - El id del producto asociado a la promoción.
     * @param cantidadProductos - La cantidad de productos asociados a la promoción.
     * @param disponible - Si la promoción se encuentra disponible o no.
     * @param fechaInicio - La fecha de inicio de la promoción.
     * @param fechaFin - La fecha de fin de la promoción.
     */
    public Promocion(long id, long idProveedor, long idProducto, int cantidadProductos, boolean disponible, Date fechaInicio, Date fechaFin)
    {
        this.setId(id);
        this.setIdProveedor(idProveedor);
        this.setIdProducto(idProducto);
        this.setCantidadProductos(cantidadProductos);
        this.setDisponible(disponible);
        this.setFechaInicio(fechaInicio);
        this.setFechaFin(fechaFin);
    }

    /**
     * Retorna el identificador de la promoción.
     *
     * @return El identificador de la promoción.
     */
    public long getId() {
        return id;
    }

    /**
     * Asigna el identificador de la promoción.
     *
     * @param id - El identificador de la promoción.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna el id del proveedor del producto del cual es la promoción.
     *
     * @return El id del proveedor del producto del cual es la promoción.
     */
    public long getIdProveedor() {
        return idProveedor;
    }

    /**
     * Asigna el id del proveedor del producto del cual es la promoción.
     *
     * @param idProveedor - El id del proveedor del producto del cual es la promoción.
     */
    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * Retorna la fecha de inicio de la promoción.
     *
     * @return La fecha de inicio de la promoción.
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Asigna la fecha de inicio de la promoción.
     *
     * @param fechaInicio - La fecha de inicio de la promoción.
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Retorna la fecha de fin de la promoción.
     *
     * @return La fecha de fin de la promoción.
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Asigna la fecha de fin de la promoción.
     *
     * @param fechaFin - La fecha de fin de la promoción.
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Retorna la cantidad de productos que abarca la promoción.
     *
     * @return La cantidad de productos que abarca la promoción.
     */
    public int getCantidadProductos() {
        return cantidadProductos;
    }

    /**
     * Asigna la cantidad de productos que abarca la promoción.
     *
     * @param cantidadProductos - La cantidad de productos que abarca la promoción.
     */
    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    /**
     * Retorna el id del producto asociado a la promoción.
     *
     * @return El id del producto asociado a la promoción.
     */
    public long getIdProducto() {
        return idProducto;
    }

    /**
     * Asigna el id del producto asociado a la promoción.
     *
     * @param idProducto - El id del producto asociado a la promoción.
     */
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }
    /**
     * Retorna si la promoción está disponible o no.
     *
     * @return Si la promoción está disponible o no.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Asigna si la promoción está disponible o no.
     *
     * @param disponible - Si la promoción está disponible o no.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }





}
