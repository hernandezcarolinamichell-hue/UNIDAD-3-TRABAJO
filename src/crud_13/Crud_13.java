package crud_13;

import controlador.CtrTienda;
import modelo.ConsultasProductos;
import modelo.Producto;
import vista.GestionProducto;
/**
 *
 * @author carol
 */

public class Crud_13 {

    public static void main(String[] args) {

        GestionProducto vGp = new GestionProducto();

        Producto articulo = new Producto();

        ConsultasProductos consultas = new ConsultasProductos();

        new CtrTienda(vGp, articulo, consultas);
    }
}