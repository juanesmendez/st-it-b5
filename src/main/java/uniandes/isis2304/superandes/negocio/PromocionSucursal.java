package uniandes.isis2304.superandes.negocio;

/**
 * Clase para modelar la relación PROMOCION-SUCURSAL del negocio de SuperAndes.
 *
 * @author Juan Sebastián Bravo
 */
public class PromocionSucursal implements VOPromocionSucursal {

    /* ****************************************************************
     * 			Atributos
     *****************************************************************/

    /**
     * El identificador ÚNICO de la promoción.
     */
    private long idPromocion;

    /**
     * El identificador de la sucursal donde está la promoción.
     */
    private long idSucursal;

    /* ****************************************************************
     * 			Métodos
     *****************************************************************/
    /**
     * Constructor sin parámetros por defecto
     */
    public PromocionSucursal()
    {
        this.setIdPromocion(0);
        this.setIdSucursal(0);
    }

    /**
     * Constructor con valores.
     * @param idPromocion - El identificador ÚNICO de la promoción.
     * @param idSucursal - El identificador de la sucursal donde está la promoción
     */
    public PromocionSucursal(long idPromocion, long idSucursal)
    {
        this.setIdPromocion(idPromocion);
        this.setIdSucursal(idSucursal);
    }

    /**
     * Retorna el identificador de la promoción.
     *
     * @return El identificador de la promoción.
     */
    public long getIdPromocion() {
        return idPromocion;
    }
    /**
     * Asigna el identificador de la promoción.
     *
     * @param idPromocion - El identificador de la promoción.
     */
    public void setIdPromocion(long idPromocion) {
        this.idPromocion = idPromocion;
    }

    /**
     * Retorna el identificador de la sucursal.
     *
     * @return El identificador de la sucursal.
     */
    public long getIdSucursal() {
        return idSucursal;
    }

    /**
     * Asigna el identificador de la sucursal.
     *
     * @param idSucursal - El identificador de la sucursal.
     */
    public void setIdSucursal(long idSucursal) {
        this.idSucursal = idSucursal;
    }
}
