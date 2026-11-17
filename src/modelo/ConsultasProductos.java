package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class ConsultasProductos extends Conexion {
/**
 *
 * @author carol
 */
    
    public boolean insertar(Producto art) {

        Connection con = getConexion();

        String query = "INSERT INTO productos(nombre, precio, stock) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, art.getNombre());
            ps.setDouble(2, art.getPrecio());
            ps.setInt(3, art.getStock());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.err.println("Error al insertar: " + e);

            return false;

        } finally {

            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }


    
    public boolean buscar(Producto art) {

        Connection con = getConexion();

        ResultSet rs = null;

        String query = "SELECT * FROM productos WHERE idProducto = ?";

        try {

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, art.getIdProducto());

            rs = ps.executeQuery();

            if (rs.next()) {

                art.setIdProducto(rs.getInt("idProducto"));
                art.setNombre(rs.getString("nombre"));
                art.setPrecio(rs.getDouble("precio"));
                art.setStock(rs.getInt("stock"));

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println("Error al buscar: " + e);

            return false;

        } finally {

            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }


    public boolean listar(DefaultTableModel modelo) {

        Connection con = getConexion();

        ResultSet rs = null;

        String query = "SELECT * FROM productos";

        try {

            PreparedStatement ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            int columnas = rsmd.getColumnCount();
            int filas = 0;

            modelo.setColumnCount(0);
            modelo.setRowCount(0);

            modelo.addColumn("ID");
            modelo.addColumn("Nombre");
            modelo.addColumn("Precio");
            modelo.addColumn("Stock");

            while (rs.next()) {

                Object[] fila = new Object[columnas];

                for (int i = 0; i < columnas; i++) {

                    fila[i] = rs.getObject(i + 1);

                }

                modelo.addRow(fila);

                filas++;
            }

            return filas > 0;

        } catch (SQLException e) {

            System.err.println("Error al listar: " + e);

            return false;

        } finally {

            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    
    
    
    public boolean modificar(Producto art) {

        Connection con = getConexion();

        String query = "UPDATE productos " + "SET nombre = ?, precio = ?, stock = ? "
                + "WHERE idProducto = ?";

        try {

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, art.getNombre());
            ps.setDouble(2, art.getPrecio());
            ps.setInt(3, art.getStock());
            ps.setInt(4, art.getIdProducto());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            System.err.println("Error al modificar: " + e);

            return false;

        } finally {

            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    
    
    
    
    public boolean eliminar(Producto art) {

        Connection con = getConexion();

        String query = "DELETE FROM productos WHERE idProducto = ?";

        try {

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, art.getIdProducto());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e);

            return false;

        } finally {

            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}